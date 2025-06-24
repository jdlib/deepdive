/*
 * Copyright (c) 2021 jdlib, https://github.com/jdlib
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package deepdive.actual.java.lang.reflect;


import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import javax.annotation.CheckReturnValue;
import deepdive.actual.Actual;
import deepdive.actual.ActualMixin;
import deepdive.actual.Actual.Internals;
import deepdive.actual.java.lang.annotation.AnnotationActual;


/**
 * A ActualMixin interface for {@link AnnotatedElement}.
 */
public interface AnnotatedElementActual<T extends AnnotatedElement,IMPL extends Actual<T,?,IMPL>> 
	extends ActualMixin<T,IMPL>
{
	/**
	 * Returns an AnnotationActual for the annotation with the given type. 
	 * @param type the annotation type
	 * @param <A> the annotation type
	 * @return the AnnotationActual
	 */
	public default <A extends Annotation> AnnotationActual<A,IMPL,?> annotation(Class<A> type)
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		return new AnnotationActual<>(value().getAnnotation(type), i.self()).as("annotation");
	}
	
	
	public default <A extends Annotation> AnnotationActual<A,IMPL,?> annotationDeclared(Class<A> type)
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		return new AnnotationActual<>(value().getDeclaredAnnotation(type), i.self()).as("annotationDeclared");
	}
	
	
	@CheckReturnValue
	public default AnnotationsBuilder<T,IMPL> annotations()
	{
		return new AnnotationsBuilder<>(this);
	}


	/**
	 * A Builder to access Annotations of an AnnotatedElement.
	 */
	public static class AnnotationsBuilder<AE extends AnnotatedElement,IMPL extends Actual<AE,?,IMPL>>
	{
		public AnnotationsBuilder(AnnotatedElementActual<AE,IMPL> actual)
		{
			actual_ = actual;
		}
		
		
		public AnnotationArrayActual<Annotation,IMPL> all()
		{
			return get(actual_.value().getAnnotations(), "annotations");
		}
		
		
		public <A extends Annotation> AnnotationArrayActual<A,IMPL> allByType(Class<A> type)
		{
			return get(actual_.value().getAnnotationsByType(type), "allByType");
		}
	
		
		public AnnotationArrayActual<Annotation,IMPL> declared()
		{
			return get(actual_.value().getDeclaredAnnotations(), "declared");
		}
	
		
		public <A extends Annotation> AnnotationArrayActual<A,IMPL> declaredByType(Class<A> type)
		{
			return get(actual_.value().getAnnotationsByType(type), "declaredByType");
		}
	
		
		private <A extends Annotation> AnnotationArrayActual<A,IMPL> get(A[] array, String name)
		{
			Internals<IMPL> i = ActualMixin.internals(actual_);
			return new AnnotationArrayActual<>(array, i.self()).as(name);
		}
	
		
		private final AnnotatedElementActual<AE,IMPL> actual_;
	}
}
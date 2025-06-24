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
package deepdive.actual.java.lang.annotation;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Function;
import deepdive.actual.Actual;


/**
 * An Actual implementation for {@link Annotation} objects.
 * @param <T> the annotation type
 * @param <BACK> the type of the owner of the AnnotationActual
 * @param <IMPL> the type of the AnnotationActual implementation 
 */
public class AnnotationActual<T extends Annotation,BACK,IMPL extends AnnotationActual<T,BACK,IMPL>> extends Actual<T,BACK,IMPL>
{
	/**
	 * Creates a new AnnotationActual.
	 * @param value the value
	 * @param back the owner
	 */
	public AnnotationActual(T value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that an annoation attribute has the expected value
	 * @param name the attribute name
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL attr(String name, Object expected)
	{
		expectEqual(expected, invoke(name), name);
		return self();
	}


	/**
	 * Returns an Actual for an Annotation attribute.
	 * @param name the attribute name
	 * @return the new actual
	 */
	public Actual<?,IMPL,?> attr(String name)
	{
		return new Actual<>(invoke(name), self()).as(name);
	}

	
	/**
	 * Asserts that an annoation attribute has the expected value.
	 * @param name the name of the attribute used to define the assertion context)
	 * @param fn a function to access the attribute
	 * @param expected the expected value
	 * @return this
	 * @param <S> the attribute type
	 */
	public <S> IMPL attr(String name, Function<T,S> fn, S expected)
	{
		expectEqual(expected, fn.apply(value()), name);
		return self();
	}


	/**
	 * Returns an Actual for an Annotation attribute.
	 * @param name the name of the attribute used to define the assertion context)
	 * @param fn a function to access the attribute
	 * @return the new actual
	 * @param <S> the attribute type
	 */
	public <S> Actual<?,IMPL,?> attr(String name, Function<T,S> fn)
	{
		return new Actual<>(fn.apply(value()), self()).as(name);
	}
	
	
	protected Method method(String methodName)
	{
		try
		{
			return value().getClass().getMethod(methodName);
		} 
		catch (Exception e)
		{
			return failure().addStmt("can't find method", methodName).cause(e).throwError();
		}
	}


	protected Object invoke(String methodName)
	{
		try
		{
			Method method = method(methodName);
			return method.invoke(value());
		} 
		catch (Exception e)
		{
			return failure().addStmt("can't invoke method", methodName).cause(e).throwError();
		}
	}
}
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
package deepdive.actual.reflect;


import java.lang.annotation.Annotation;
import deepdive.Context;
import deepdive.actual.anno.AnnotationActual;
import deepdive.actual.lang.ArrayActual;


/**
 * An ArrayActual implementation for {@link Annotation} arrays.
 */
public class AnnotationArrayActual<A extends Annotation,BACK> extends ArrayActual<A,BACK,AnnotationArrayActual<A,BACK>>
{
	/**
	 * Creates a new AnnotationArrayActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public AnnotationArrayActual(A[] value, BACK back)
	{
		super(value, back);
	}


	/**
	 * Returns a AnnotationActual for the element at the given index.
	 */
	@Override public AnnotationActual<A,AnnotationArrayActual<A,BACK>,?> elem(int index)
	{
		return elem(index, AnnotationActual::new).as(Context.indexed("elem", index));
	}
}	

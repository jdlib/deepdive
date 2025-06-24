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


import java.lang.reflect.Method;
import deepdive.actual.java.lang.ClassActual;
import deepdive.actual.java.lang.ClassArrayActual;


/**
 * An Actual implementation for {@link Method} objects.
 * @param <BACK> the type of the owner of the MethodActual
 * @param <IMPL> the type of the concrete MethodActual implementation 
 */
public class MethodActual<BACK,IMPL extends MethodActual<BACK,IMPL>> 
	extends ExecutableActual<Method,BACK,IMPL>
	implements AnnotatedElementActual<Method,IMPL>
{
	/**
	 * Creates a new MethodActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public MethodActual(Method value, BACK back)
	{
		super(value, back);
	}
	
	
	public ClassActual<IMPL,?> returnType()
	{
		return new ClassActual<>(value().getReturnType(), self()).as("returnType");
	}
	
	
	public IMPL returnType(Class<?> expected)
	{
		expectSame(expected, value().getReturnType(), "returnType");
		return self();
	}
	
	
	public ClassArrayActual<IMPL,?> exceptionTypes()
	{
		return new ClassArrayActual<>(value().getExceptionTypes(), self()).as("exceptionTypes");
	}
}

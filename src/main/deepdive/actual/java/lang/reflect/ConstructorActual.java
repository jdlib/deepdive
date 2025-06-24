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


import java.lang.reflect.Constructor;


/**
 * An Actual implementation for {@link Constructor} objects.
 * @param <BACK> the type of the owner of the ConstructorActual
 * @param <IMPL> the type of the concrete ConstructorActual implementation 
 */
public class ConstructorActual<BACK,IMPL extends ConstructorActual<BACK,IMPL>> 
	extends ExecutableActual<Constructor<?>,BACK,IMPL>
	implements AnnotatedElementActual<Constructor<?>,IMPL>
{
	/**
	 * Creates a new ConstructorActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public ConstructorActual(Constructor<?> value, BACK back)
	{
		super(value, back);
	}
}

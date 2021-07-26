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


import java.lang.reflect.Field;
import deepdive.actual.Actual;
import deepdive.actual.lang.ClassActual;


/**
 * An Actual implementation for {@link Field} objects.
 * @param <BACK> the type of the owner of the FieldActual
 * @param <IMPL> the type of the concrete FieldActual implementation 
 */
public class FieldActual<BACK,IMPL extends FieldActual<BACK,IMPL>> 
	extends Actual<Field,BACK,IMPL>
	implements AnnotatedElementActual<Field,IMPL>, MemberActual<Field,IMPL>
{
	/**
	 * Creates a new FieldActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public FieldActual(Field value, BACK back)
	{
		super(value, back);
	}
	
	
	public IMPL type(Class<?> expected)
	{
		expectSame(expected, value().getType(), "type");
		return self();
	}


	public ClassActual<IMPL,?> type()
	{
		return new ClassActual<>(value().getType(), self()).as("type");
	}
}

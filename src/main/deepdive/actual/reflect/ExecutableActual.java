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


import java.lang.reflect.Executable;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.lang.ClassActual;
import deepdive.actual.lang.ClassArrayActual;


/**
 * An Actual implementation for {@link Executable} objects.
 * @param <BACK> the type of the owner of the MethodActual
 * @param <IMPL> the type of the concrete MethodActual implementation 
 */
public class ExecutableActual<T extends Executable,BACK,IMPL extends ExecutableActual<T,BACK,IMPL>> 
	extends Actual<T,BACK,IMPL> implements MemberActual<T,IMPL>
{
	/**
	 * Creates a new ExecutableActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public ExecutableActual(T value, BACK back) 
	{
		super(value, back);
	}
	
	
	public IMPL paramCount(int expected)
	{
		expectEqual(expected, value().getParameterCount(), "paramCount");
		return self(); 
	}
	
	
	public ClassActual<IMPL,?> paramType(int index)
	{
		return new ClassActual<>(value().getParameterTypes()[index], self()).as(Context.indexed("paramClass", index));
	}
	
	
	public ClassArrayActual<IMPL,?> paramTypes()
	{
		return new ClassArrayActual<>(value().getParameterTypes(), self()).as("paramTypes");
	}
}

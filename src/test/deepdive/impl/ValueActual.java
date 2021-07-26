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
package deepdive.impl;


import deepdive.ExpectProtected;
import deepdive.actual.lang.ThrowableActual;
import deepdive.actual.util.SetActual;
import deepdive.function.CheckedRunnable;
import deepdive.impl.Value;


public class ValueActual extends ExpectProtected
{
	public ValueActual equal(Object o1, Object o2, boolean expected)
	{
		expectEqual(expected, Value.equal(o1, o2), "Value.equal");
		return this;
	}
	
	
	public ValueActual arraysEqual(Object o1, Object o2, boolean compareCompontentType, boolean expected)
	{
		expectEqual(expected, Value.arraysEqual(o1, o2, compareCompontentType), "Value.arraysEqual");
		return this;
	}

	
	public <T> ValueActual arrayToSet(T[] array, @SuppressWarnings("unchecked") T... expected)
	{
		new SetActual<>(Value.arrayToSet(array), null).elems(expected);
		return this;
	}
	
	
	public ValueActual format(Object object, String expected)
	{
		expectEqual(expected, Value.format(object), "toString");
		return this;
	}

	
	public ThrowableActual<IllegalArgumentException,ValueActual,?> illegalArg(CheckedRunnable<?> runnable)
	{
		IllegalArgumentException e = expectThrows(IllegalArgumentException.class, runnable);
		return new ThrowableActual<>(e, this);
	}
}


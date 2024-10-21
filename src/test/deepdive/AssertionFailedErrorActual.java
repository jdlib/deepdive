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
package deepdive;


import org.opentest4j.AssertionFailedError;
import org.opentest4j.ValueWrapper;
import deepdive.actual.lang.StringActual;
import deepdive.actual.lang.StringArrayActual;
import deepdive.actual.lang.ThrowableActual;
import deepdive.function.CheckedRunnable;


public class AssertionFailedErrorActual<BACK> extends ThrowableActual<AssertionFailedError,BACK,AssertionFailedErrorActual<BACK>>
{
	public AssertionFailedErrorActual(CheckedRunnable<?> assertion, BACK back)
	{
		this(ExpectStatic.expectThrows(AssertionFailedError.class, assertion), back);
	}

	
	public AssertionFailedErrorActual(AssertionFailedError actual, BACK back)
	{
		super(actual, back);
	}
	
	
	public AssertionFailedErrorActual<BACK> msgLines(String... expected)
	{
		expectEqual(expected, StringActual.splitLines(value().getMessage()), "msgLines");
		return this;
	}


	public StringArrayActual<AssertionFailedErrorActual<BACK>,?> msgLines()
	{
		return new StringArrayActual<>(StringActual.splitLines(value().getMessage()), self()).as("msgLines");
	}

	
	public AssertionFailedErrorActual<BACK> actual(Object expected)
	{
		expectEqual(expected, unwrap(value().getActual()), "actual");
		return this;
	}


	public AssertionFailedErrorActual<BACK> expected(Object expected)
	{
		expectEqual(expected, unwrap(value().getExpected()), "expected");
		return this;
	}
	
	
	private static Object unwrap(ValueWrapper w)
	{
		return w != null ? w.getValue() : null;
	}
}

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
package deepdive.actual.lang;


import deepdive.actual.Actual;


/**
 * An Actual implementation for Throwable objects.
 * @param <T> the Throwable type
 * @param <BACK> the type of the owner of the ThrowableActual
 * @param <IMPL> the type of the ThrowableActual implementation 
 */
public class ThrowableActual<T extends Throwable,BACK,IMPL extends ThrowableActual<T,BACK,IMPL>> extends Actual<T,BACK,IMPL>
{
	/**
	 * Creates a new ThrowableActual. 
	 * @param value a Throwable
	 * @param back the owner
	 */
	public ThrowableActual(T value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Asserts that the actual can be casted to the given class.
	 * @param type a class
	 * @return a new ThrowableActual whose type reflects the successful cast.
	 * @param <S> the type of the casted Throwable 
	 */
	public <S extends T> ThrowableActual<S,BACK,?> cast(Class<S> type)
	{
		return new ThrowableActual<>(expectInstance(type, value()), backOrNull());
	}

	
	/**
	 * Asserts that the message of the Throwable equals the expected message.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL message(String expected)
	{
		expectEqual(expected, value().getMessage(), "message");
		return self();
	}

	
	/**
	 * Returns a StringActual for the message of the Throwable.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> message()
	{
		return new StringActual<>(value().getMessage(), self()).as("message");
	}

	
	/**
	 * Returns a StringActual for the localized message of the Throwable.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> localizedMessage()
	{
		return new StringActual<>(value().getLocalizedMessage(), self()).as("localizedMessage");
	}

	
	/**
	 * Returns a ThrowableActual for the cause of the Throwable.
	 * @return the new actual
	 */
	public ThrowableActual<?,IMPL,?> cause()
	{
		return new ThrowableActual<>(value().getCause(), self()).as("cause");
	}


	/**
	 * Returns a ThrowableActual for the root cause of the Throwable.
	 * @return the new actual
	 */
	public ThrowableActual<?,IMPL,?> rootCause()
	{
		Throwable root = value();
		while (root.getCause() != null)
			root = root.getCause();
		return new ThrowableActual<>(root, self()).as("rootCause");
	}
	
	
	/**
	 * Returns an ArrayActual for the StackTraceElements of the Throwable.
	 * @return the new actual
	 */
	public ArrayActual<StackTraceElement,IMPL,?> stackTrace()
	{
		return new ArrayActual<>(value().getStackTrace(), self()).as("stackTrace");
	}
}

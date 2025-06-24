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
package deepdive.actual.java.lang;


import deepdive.actual.Actual;


/**
 * An Actual implementation for Enum objects of a certain Enum class.
 * @param <T> the enum type
 * @param <BACK> the type of the owner of the EnumActual
 * @param <IMPL> the type of the concrete EnumActual implementation 
 */
public class EnumActual<T extends Enum<T>,BACK,IMPL extends EnumActual<T,BACK,IMPL>> extends Actual<T,BACK,IMPL>
	implements ComparableActual<T,IMPL>
{
	/**
	 * Creates a new EnumActual.
	 * @param value the value
	 * @param back the owner
	 */
	public EnumActual(T value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that the name equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL name(String expected)
	{
		expectEqual(expected, value().name());
		return self();
	}
	
	
	/**
	 * Returns a StringActual for the Enum name.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> name()
	{
		return new StringActual<>(value().name(), self()).as("name");
	}
	
	
	/**
	 * Asserts that the ordinal equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL ordinal(int expected)
	{
		expectEqual(expected, value().ordinal(), "ordinal");
		return self();
	}
}

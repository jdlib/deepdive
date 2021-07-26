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
// Generated from NumberActualTemplate.txt, see build.xml/target[name="generate"]. 
// Do not modify directly.
package deepdive.actual.lang;


/**
 * ShortActual is a NumberActual implementation for actual Short or short values.
 */
public class ShortActual<BACK,IMPL extends ShortActual<BACK,IMPL>> extends NumberActual<Short,BACK,IMPL> 
	implements ComparableActual<Short,IMPL>
{
	/**
	 * Creates a ShortActual for the given primitive value and owner.
	 * @param value the primitive value
	 * @param back the owner
	 */
	public ShortActual(short value, BACK back)
	{
		this(Short.valueOf(value), back);
	}


	/**
	 * Creates a ShortActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public ShortActual(Short value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Returns the primitive actual value.
	 * @return the primitive value
	 */
	public short shortValue()
	{
		return value().shortValue();
	}

	
	/**
	 * Asserts that the actual short value is equal to the expected value.
	 * @param expected the expected value
	 * 
	 * @return this
	 */
	public IMPL equal(short expected)
	{
		expectEqual(expected, shortValue());
		return self();
	}

	
	/**
	 * Asserts that the actual short value is greater than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greater(short expected)
	{
		return greater(Short.valueOf(expected));
	}
	

	/**
	 * Asserts that the actual short value is greater or equal than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greaterEq(short expected)
	{
		return greaterEq(Short.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual short value is positive (i.e. greater zero).
	 * @return this
	 */
	public IMPL positive()
	{
		return greater((short)0);
	}

	
	/**
	 * Asserts that the actual short value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL less(short expected)
	{
		return less(Short.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual short value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL lessEq(short expected)
	{
		return lessEq(Short.valueOf(expected));
	}
	
	
	/**
	 * Asserts that the actual short value is negative (i.e. less zero).
	 * @return this
	 */
	public IMPL negative()
	{
		return less((short)0);
	}
}

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
 * LongActual is a NumberActual implementation for actual Long or long values.
 */
public class LongActual<BACK,IMPL extends LongActual<BACK,IMPL>> extends NumberActual<Long,BACK,IMPL> 
	implements ComparableActual<Long,IMPL>
{
	/**
	 * Creates a LongActual for the given primitive value and owner.
	 * @param value the primitive value
	 * @param back the owner
	 */
	public LongActual(long value, BACK back)
	{
		this(Long.valueOf(value), back);
	}


	/**
	 * Creates a LongActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public LongActual(Long value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Returns the primitive actual value.
	 * @return the primitive value
	 */
	public long longValue()
	{
		return value().longValue();
	}

	
	/**
	 * Asserts that the actual long value is equal to the expected value.
	 * @param expected the expected value
	 * 
	 * @return this
	 */
	public IMPL equal(long expected)
	{
		expectEqual(expected, longValue());
		return self();
	}

	
	/**
	 * Asserts that the actual long value is greater than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greater(long expected)
	{
		return greater(Long.valueOf(expected));
	}
	

	/**
	 * Asserts that the actual long value is greater or equal than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greaterEq(long expected)
	{
		return greaterEq(Long.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual long value is positive (i.e. greater zero).
	 * @return this
	 */
	public IMPL positive()
	{
		return greater(0);
	}

	
	/**
	 * Asserts that the actual long value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL less(long expected)
	{
		return less(Long.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual long value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL lessEq(long expected)
	{
		return lessEq(Long.valueOf(expected));
	}
	
	
	/**
	 * Asserts that the actual long value is negative (i.e. less zero).
	 * @return this
	 */
	public IMPL negative()
	{
		return less(0);
	}

	
	/**
	 * Asserts that the actual value modulo the given value
	 * equals the expected value.
	 * @param mod the module operand
	 * @param expected the expected value
	 * @return this
     */
	@SuppressWarnings("boxing")
	public IMPL mod(long mod, long expected)
	{
		long actual =  longValue() % mod;
		expectEqual(expected, actual, deepdive.Context.call("mod", expected));
		return self();
	}
}

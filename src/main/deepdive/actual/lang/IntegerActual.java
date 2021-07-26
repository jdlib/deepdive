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
 * IntegerActual is a NumberActual implementation for actual Integer or int values.
 */
public class IntegerActual<BACK,IMPL extends IntegerActual<BACK,IMPL>> extends NumberActual<Integer,BACK,IMPL> 
	implements ComparableActual<Integer,IMPL>
{
	/**
	 * Creates a IntegerActual for the given primitive value and owner.
	 * @param value the primitive value
	 * @param back the owner
	 */
	public IntegerActual(int value, BACK back)
	{
		this(Integer.valueOf(value), back);
	}


	/**
	 * Creates a IntegerActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public IntegerActual(Integer value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Returns the primitive actual value.
	 * @return the primitive value
	 */
	public int intValue()
	{
		return value().intValue();
	}

	
	/**
	 * Asserts that the actual int value is equal to the expected value.
	 * @param expected the expected value
	 * 
	 * @return this
	 */
	public IMPL equal(int expected)
	{
		expectEqual(expected, intValue());
		return self();
	}

	
	/**
	 * Asserts that the actual int value is greater than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greater(int expected)
	{
		return greater(Integer.valueOf(expected));
	}
	

	/**
	 * Asserts that the actual int value is greater or equal than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greaterEq(int expected)
	{
		return greaterEq(Integer.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual int value is positive (i.e. greater zero).
	 * @return this
	 */
	public IMPL positive()
	{
		return greater(0);
	}

	
	/**
	 * Asserts that the actual int value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL less(int expected)
	{
		return less(Integer.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual int value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL lessEq(int expected)
	{
		return lessEq(Integer.valueOf(expected));
	}
	
	
	/**
	 * Asserts that the actual int value is negative (i.e. less zero).
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
	public IMPL mod(int mod, int expected)
	{
		int actual =  intValue() % mod;
		expectEqual(expected, actual, deepdive.Context.call("mod", expected));
		return self();
	}
}

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
package deepdive.actual.java.lang;


/**
 * DoubleActual is a NumberActual implementation for actual Double or double values.
 */
public class DoubleActual<BACK,IMPL extends DoubleActual<BACK,IMPL>> extends NumberActual<Double,BACK,IMPL> 
	implements ComparableActual<Double,IMPL>
{
	/**
	 * Creates a DoubleActual for the given primitive value and owner.
	 * @param value the primitive value
	 * @param back the owner
	 */
	public DoubleActual(double value, BACK back)
	{
		this(Double.valueOf(value), back);
	}


	/**
	 * Creates a DoubleActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public DoubleActual(Double value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Returns the primitive actual value.
	 * @return the primitive value
	 */
	public double doubleValue()
	{
		return value().doubleValue();
	}

	
	/**
	 * Asserts that the actual double value is equal to the expected value.
	 * @param expected the expected value
	 * @param delta the delta
	 * @return this
	 */
	public IMPL equal(double expected, double delta)
	{
		expectEqual(expected, doubleValue(), delta);
		return self();
	}

	
	/**
	 * Asserts that the actual double value is greater than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greater(double expected)
	{
		return greater(Double.valueOf(expected));
	}
	

	/**
	 * Asserts that the actual double value is greater or equal than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greaterEq(double expected)
	{
		return greaterEq(Double.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual double value is positive (i.e. greater zero).
	 * @return this
	 */
	public IMPL positive()
	{
		return greater(0);
	}

	
	/**
	 * Asserts that the actual double value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL less(double expected)
	{
		return less(Double.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual double value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL lessEq(double expected)
	{
		return lessEq(Double.valueOf(expected));
	}
	
	
	/**
	 * Asserts that the actual double value is negative (i.e. less zero).
	 * @return this
	 */
	public IMPL negative()
	{
		return less(0);
	}


	/**
	 * Asserts that the NaN flag equals the expected value.
	 * @return this
	 */
	public IMPL isNaN()
	{
		expectTrue(value().isNaN(), "isNaN");
		return self();
	}


	/**
	 * Asserts that the Infinite flag equals the expected value.
	 * @return this
	 */
	public IMPL isInfinite()
	{
		expectTrue(value().isInfinite(), "isInfinite");
		return self();
	}


	/**
	 * Asserts that the Finite flag equals the expected value.
	 * @return this
	 */
	public IMPL isFinite()
	{
		expectTrue(Double.isFinite(doubleValue()), "isFinite");
		return self();
	}
}

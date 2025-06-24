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
 * An Actual implementation for Number objects.
 * @param <T> the Number type
 * @param <BACK> the type of the owner of the NumberActual
 * @param <IMPL> the type of the concrete NumberActual implementation 
 */
public class NumberActual<T extends Number,BACK,IMPL extends NumberActual<T,BACK,IMPL>> extends Actual<T,BACK,IMPL>
{
	/**
	 * Creates a new NumberActual.
	 * @param value the value
	 * @param back the owner
	 */
	public NumberActual(T value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Assert that the actual value turned into a byte equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL byteValue(byte expected)
	{
		expectEqual(expected, value().byteValue());
		return self();
	}
	
	
	/**
	 * Assert that the actual value turned into a double equals the expected value within a positive delta. 
	 * @param expected the expected value
	 * @param delta the delta
	 * @return this
	 */
	public IMPL doubleValue(double expected, double delta)
	{
		expectEqual(expected, value().doubleValue(), delta);
		return self();
	}
	
	
	/**
	 * Assert that the actual value turned into a float equals the expected value within a positive delta. 
	 * @param expected the expected value
	 * @param delta the delta
	 * @return this
	 */
	public IMPL floatValue(float expected, float delta)
	{
		expectEqual(expected, value().floatValue(), delta);
		return self();
	}
	
	
	/**
	 * Assert that the actual value turned into a int equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL intValue(int expected)
	{
		expectEqual(expected, value().intValue());
		return self();
	}
	
	
	/**
	 * Assert that the actual value turned into a long equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL longValue(long expected)
	{
		expectEqual(expected, value().longValue());
		return self();
	}
	
	
	/**
	 * Assert that the actual value turned into a ahort equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL shortValue(short expected)
	{
		expectEqual(expected, value().shortValue());
		return self();
	}
}

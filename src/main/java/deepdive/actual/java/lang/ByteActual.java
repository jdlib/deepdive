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
 * ByteActual is a NumberActual implementation for actual Byte or byte values.
 */
public class ByteActual<BACK,IMPL extends ByteActual<BACK,IMPL>> extends NumberActual<Byte,BACK,IMPL> 
	implements ComparableActual<Byte,IMPL>
{
	/**
	 * Creates a ByteActual for the given primitive value and owner.
	 * @param value the primitive value
	 * @param back the owner
	 */
	public ByteActual(byte value, BACK back)
	{
		this(Byte.valueOf(value), back);
	}


	/**
	 * Creates a ByteActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public ByteActual(Byte value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Returns the primitive actual value.
	 * @return the primitive value
	 */
	public byte byteValue()
	{
		return value().byteValue();
	}

	
	/**
	 * Asserts that the actual byte value is equal to the expected value.
	 * @param expected the expected value
	 * 
	 * @return this
	 */
	public IMPL equal(byte expected)
	{
		expectEqual(expected, byteValue());
		return self();
	}

	
	/**
	 * Asserts that the actual byte value is greater than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greater(byte expected)
	{
		return greater(Byte.valueOf(expected));
	}
	

	/**
	 * Asserts that the actual byte value is greater or equal than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greaterEq(byte expected)
	{
		return greaterEq(Byte.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual byte value is positive (i.e. greater zero).
	 * @return this
	 */
	public IMPL positive()
	{
		return greater((byte)0);
	}

	
	/**
	 * Asserts that the actual byte value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL less(byte expected)
	{
		return less(Byte.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual byte value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL lessEq(byte expected)
	{
		return lessEq(Byte.valueOf(expected));
	}
	
	
	/**
	 * Asserts that the actual byte value is negative (i.e. less zero).
	 * @return this
	 */
	public IMPL negative()
	{
		return less((byte)0);
	}
}

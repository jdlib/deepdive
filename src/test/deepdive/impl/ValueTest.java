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


import org.junit.Test;
import deepdive.AbstractTest;
import deepdive.impl.Value;


/**
 * Tests {@link Value}.
 */
public class ValueTest extends AbstractTest
{
	/**
	 * Tests {@link Value#arraysEqual(Object, Object, boolean)}.
	 */
	@Test public void testArraysEquals()
	{
		String[] asA  = new String[] { "A" };
		Object[] aoA  = new Object[] { "A" };
		int[] ai1     = new int[]    {  1  };
		
		new ValueActual()
			//--------------------
			// Value.arraysEqual
			// exclude non array args
			.illegalArg(() -> Value.arraysEqual("a", null, true)).message("arg1 is not an array: a").back()
			.illegalArg(() -> Value.arraysEqual(null, "b", true)).message("arg2 is not an array: b").back()
			// null edge cases
			.arraysEqual(null, null, true, true)
			.arraysEqual(asA, null, true, false)
			.arraysEqual(null, asA, true, false)
			// component type matters
			.arraysEqual(asA, aoA, true, false)
			.arraysEqual(asA, ai1, true, false)
			// invoking Arrays.equal
			.arraysEqual(asA, asA.clone(), true, true)
			.arraysEqual(ai1, ai1.clone(), true, true)
			//--------------------
			// Value.array2Set
			.arrayToSet(new String[] { "a", "a", "b" }, "a", "b")
			// array
			;
	}


	/**
	 * Tests {@link Value#equal(Object, Object)}.
	 */
	@Test public void testEqual()
	{
		String[] a = new String[] { "a" };
		
		new ValueActual()
			// arrays are forwarded to Value#arraysEqual
			.equal(a, a.clone(), true)
			// mixing arrays with normal values fails
			.equal(a, "a", false)
			.equal("a", a, false)
			// else Objects#equals is applied:
			// null edge cases
			.equal(null, null, true)
			.equal(null, "1", false)
			.equal("1", null, false)
			// two non-null non-arrays
			.equal("a", "a", true)
			.equal("a", new String("a"), true)
			.equal("a", "b", false);
	}


	/**
	 * Tests {@link Value#format(Object)}.
	 */
	@SuppressWarnings("boxing")
	@Test public void testToString()
	{
		new ValueActual()
			.format(null, "null")
			// primitives
			.format(1, "1")
			// strings
			.format("a", "a")
			// arrays
			.format(new String[] { "a" }, "[a]");
	}
	
	
	@Test public void testWithin()
	{
		expectTrue(Value.withinDelta(1.0, 1.0, 0.1));
		expectTrue(Value.withinDelta(7.0, 7.1, 0.1));
		expectFalse(Value.withinDelta(7.0, 7.2, 0.1));
		expectTrue(Value.withinDelta(1.0f, 1.0f, 0.1f));
		expectTrue(Value.withinDelta(7.0f, 7.1f, 0.1f));
		expectFalse(Value.withinDelta(7.0f, 7.2f, 0.1f));
	}
}

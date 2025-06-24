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


import org.junit.Test;


@SuppressWarnings("boxing")
public class ExpectSelfTest extends AbstractTest
{
	@Test public void testExpectAll()
	{
		String s = "abc";
		int n = 5;
		
		expectAll(a -> {
			a.expectEqual("abc", s);
			a.expectEqual(5, n);
		});
		failSoftAssert(() -> expectAll(a -> {
			a.expectEqual("xyz", s);
			a.expectEqual(6, n);
			a.expectAll(a2 -> 
				a2.expectNull(s));
		}),	"Multiple Failures (3 failures)",
			"	org.opentest4j.AssertionFailedError: expected: xyz",
			"but was : abc",
			"	org.opentest4j.AssertionFailedError: expected: 6",
			"but was : 5",
			"	org.opentest4j.AssertionFailedError: expected to be: null",
			"but was       : abc");
	}

	
	@Test public void testExpectEqual()
	{
		expectEqual(true, true);
		expectEqual(Byte.MAX_VALUE, Byte.MAX_VALUE);
		expectEqual(Character.MIN_VALUE, Character.MIN_VALUE);
		expectEqual(Short.MIN_VALUE, Short.MIN_VALUE);
		expectEqual(1, 1);
		expectEqual(2L, 2L);
		expectEqual(3f, 3f, 0.0);
		expectEqual(4d, 4d, 0.0);
		expectEqual("a", "a");
		
		failAssert(() -> expectEqual("a", "b")).msgLines(
			"expected: a",
			"but was : b");
		failAssert(() -> expectEqual("a", "b", "strings")).expected("a").actual("b").msgLines(
			"strings",
			"expected: a",
			"but was : b");
		
		not().expectEqual(true, false);
		not().expectEqual(Byte.MIN_VALUE, Byte.MAX_VALUE);
		not().expectEqual(Character.MIN_VALUE, Character.MAX_VALUE);
		not().expectEqual(Short.MIN_VALUE, Short.MAX_VALUE);
		not().expectEqual(1, 2);
		not().expectEqual(2L, 3L);
		not().expectEqual(3f, 4f, 0.0);
		not().expectEqual(4d, 5d, 0.0);
		not().expectEqual("a", "b");
		failAssert(() -> not().expectEqual("a", "a")).msgLines(
			"expected not: a");	
		failAssert(() -> expectEqual(1.0, 1.5, 0.1)).msgLines(
			"expected    : 1.0",
			"within delta: 0.1",
			"but was     : 1.5");	
	}
	
	
	@Test public void testExpectFalse()
	{
		expectFalse(false);
		not().expectFalse(true);
		
		failAssert(() -> expectFalse(true)).msgLines(
			"expected: false",
			"but was : true");
		failAssert(() -> expectFalse(true, "flag")).msgLines(
			"flag", 
			"expected: false",
			"but was : true");
	}
	
	
	@Test public void testExpectIndexValid()
	{
		expectIndexValid( 0, 1);
		expectIndexValid( 1, 5);
		not().expectIndexValid(-1, 5);
		not().expectIndexValid( 5, 5);
		not().expectIndexValid( 6, 5);
		
		failAssert(() -> expectIndexValid(4, 3)).msgLines(
			"index 4 invalid, must be 0 <= index < 3");
		
		failAssert(() -> expectIndexValid(4, -3)).msgLines(
			"index 4 invalid, must be 0 <= index < -3",
			"invalid size: -3");

		failAssert(() -> not().expectIndexValid(1, 2)).msgLines(
			"index 1 valid, it is 0 <= index < 2");
	}
	
	
	@Test public void testExpectInstance()
	{
		expectInstance(String.class, "a");
		expectInstance(Number.class, 1);
		not().expectInstance(String.class, 1);
		
		failAssert(() -> expectInstance(Number.class, "a")).msgLines(
			"expected instance of: java.lang.Number",
			"but was instance of : java.lang.String",
			"with value          : a");
		failAssert(() -> not().expectInstance(String.class, "a")).msgLines(
			"expected not instance of: java.lang.String",
			"with value              : a");
	}
	
	
	@Test public void testExpectNotNull()
	{
		expectNotNull("a");
		not().expectNotNull(null);
		
		failAssert(() -> expectNotNull(null)).msgLines(
			"expected not to be: null");
		failAssert(() -> not().expectNotNull("a")).msgLines(
			"expected to be: null",
			"but was       : a");
		failAssert(() -> expectNotNull(null, "value")).msgLines(
			"value",
			"expected not to be: null");
	}
	
	
	@Test public void testExpectNull()
	{
		expectNull(null);
		not().expectNull("a");
		
		failAssert(() -> expectNull("a")).msgLines(
			"expected to be: null",
			"but was       : a");
		failAssert(() -> not().expectNull(null)).msgLines(
			"expected not to be: null");
		failAssert(() -> expectNull("a", "value")).msgLines(
			"value",
			"expected to be: null",
			"but was       : a");
	}
	
	
	@Test public void testExpectSame()
	{
		expectSame("a", "a");
		expectSame(null, null);
		not().expectSame("a", "b");
		
		failAssert(() -> expectSame("a", "b")).msgLines(
			"expected same as: a",
			"but was         : b");
		failAssert(() -> expectSame("a", new String("a"))).msgLines()
			.elem(0).like("expected same as: a (java.lang.String@*)").back()
			.elem(1).like("but was         : a (java.lang.String@*)").back();
		failAssert(() -> not().expectSame("a", "a")).msgLines(
			"expected not same as: a");
	}
	
	
	@Test public void testExpectTrue()
	{
		expectTrue(true);
		not().expectTrue(false);

		failAssert(() -> expectTrue(false)).msgLines(
			"expected: true",
			"but was : false");
		failAssert(() -> expectTrue(false, "flag")).msgLines(
			"flag",
			"expected: true",
			"but was : false");
	}
	
	
	@Test public void testExpectThrows()
	{
		expectThrows(IllegalArgumentException.class, () -> { throw new IllegalArgumentException(); });
		
		failAssert(() -> expectThrows(null, () -> {}))
			.msgLines(
				"expected exception",
				"but runnable did not throw any exception");
		failAssert(() -> expectThrows(IllegalArgumentException.class, () -> {}))
			.msgLines(
				"expected exception: java.lang.IllegalArgumentException",
				"but runnable did not throw any exception");
		failAssert(() -> expectThrows(IllegalArgumentException.class, () -> { throw new IllegalStateException(); }))
			.msgLines(
				"expected instance of: java.lang.IllegalArgumentException",
				"but was instance of : java.lang.IllegalStateException",
				"with value          : java.lang.IllegalStateException");
	}
}

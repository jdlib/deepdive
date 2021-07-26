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


import static deepdive.ExpectThat.*;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;
import deepdive.actual.lang.ArrayActual;
import deepdive.actual.lang.StringActual;


/**
 * Tests {@link ArrayActual}.
 */
public class ArrayActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		ArrayActual<String,?,?> a = expectThat(new String[] { "a", "a", "b", "c" })
			.not().blank()
			.componentType(String.class)
			.contains("a")
			.contains()
				.allOf("b", "a")
				.exactly("b", "c", "a")
				.match(s -> s.length() == 1)
				.someOf("x", "a")
				.noneOf("x")
				.not().allOf("a", "x")
				.not().someOf("x", "y")
				.not().noneOf("a")
				.not().exactly("a")
				.not().match(s -> s.length() == 2)
				.back()
			.elems("a", "a", "b", "c")
			.elem(2, "b")
			.not().elem(2, "c")
			.elem(0, StringActual::new).equal("a").back()
			.elem(0).less("b").back()
			.not().empty()
			.equal(new String[] { "a", "a", "b", "c"})
			.indexOf("b", 2)
			.not().indexOf("c", -1)
			.indexValid(0)
			.indexValid(3)
			.not().indexValid(-1)
			.not().indexValid(4)
			.iterator()
				.next("a")
				.hasNext()
				.back()
			.stream().count$(4);
		
		a.switchTo().stream().count$(4);
		a.switchTo().iterator().next("a");
		a.switchTo().list().size(4);
		a.switchTo().set().size(3);
		
		expectThat(new Object[0])
			.blank()
			.empty();
	
		// if we test for array equality (at least in a direct comparison)
		// we deviate from java (see Value#equals)
		String[] s1 = new String[] { "a" };
		String[] s2 = new String[] { "a" };
		expectFalse(s1.equals(s2));
		expectThat(s1).equal(s2);
		
		failAssert(() -> a.contains("x")).msgLines(
			"String[]=<[a, a, b, c]>",
			"expected to contain: x");
		failAssert(() -> a.contains().allOf("a", "x")).msgLines(
			"String[]=<[a, a, b, c]>",
			"expected to contain: x");
		failAssert(() -> a.contains().allOf("a", "y", "x")).msgLines(
			"String[]=<[a, a, b, c]>",
			"expected to contain: [y, x]");
		failAssert(() -> a.contains().someOf("x", "y")).msgLines(
			"String[]=<[a, a, b, c]>",
			"expected to contain some of: [x, y]");
		failAssert(() -> a.contains().exactly("a", "y")).msgLines(
			"String[]=<[a, a, b, c]>.exactly",
			"expected: [a, y]",
			"but was : [a, b, c]",
			"differences",
			"- missing   : y",
			"- unexpected: [b, c]");
		failAssert(() -> a.contains().noneOf("a", "y")).msgLines(
			"String[]=<[a, a, b, c]>",
			"expected not to contain: a");
		failAssert(() -> a.elem(2, "x")).msgLines(
			"String[]=<[a, a, b, c]>.elem[2]",
			"expected: x",
			"but was : b");
		failAssert(() -> a.not().elems("a", "a", "b", "c")).msgLines(
			"String[]=<[a, a, b, c]>.elems",
			"expected not: [a, a, b, c]");
		failAssert(() -> a.empty()).msgLines(
			"String[]=<[a, a, b, c]>.length",
			"expected: 0",
			"but was : 4");
		failAssert(() -> expectThat(new Object[0]).not().empty()).msgLines(
			"Object[]=<[]>.length",
			"expected not: 0");
		failAssert(() -> a.contains().not().noneOf("x", "y")).msgLines(
			"String[]=<[a, a, b, c]>",
			"expected to contain: [x, y]");
	}
}

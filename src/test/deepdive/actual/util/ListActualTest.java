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
package deepdive.actual.util;


import static deepdive.ExpectThat.*;
import static deepdive.actual.Narrows.*;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;
import deepdive.actual.Actual;
import deepdive.actual.Narrows;


/**
 * Tests {@link ListActual}.
 */
public class ListActualTest extends AbstractActualTest
{
	@SuppressWarnings("boxing")
	@Test public void test()
	{
		List<String> list = Arrays.asList("a", "bc", "def");
		expectThat(list)
			.contains("a")
			.not().contains("x")
			.contains()
				.noneOf("x", "y")
				.someOf("a", "b")
				.back()
			.elems("a", "bc", "def")
			.not().elems("x")
			.not().empty()
			.map(String::length)
				.elems(1, 2, 3)
				.back()
			.size(3)
			.size()
				.less(4)
				.back();
		
		expectThat(list)
			.narrow(Narrows.stringList())
			.elem(0)
				.toUpperCase("A")
				.back();

		expectThat(list)
			.count(s -> s.length() == 2)
			.equal(1);	
		
		list = Arrays.asList("a", "b", "c");
		expectThat(list)
			//.elem(0).narrow(Narrows.string()).startsWith("a").back()
			.stream()
				.map(s -> s.toUpperCase())
				.filter(s -> s.startsWith("A"))
				.elems$("A");
		
		new StringListActual<>(list, null)
			.elem(0).startsWith("a").back();
		
		Actual<Object,?,?> a = expectThatObject(Arrays.asList("a", "b"));
		expectSame(Actual.class, a.getClass());
		a.narrow(list())
			.size(2);
	}
	
	
	@Test public void testErrorMessages()
	{
		List<String> list = Arrays.asList("a", "b");
		failAssert(() -> expectThat(list).elems("b")).msgLines(
			"ArrayList=<[a, b]>.elems",
			"expected: [b]", 
			"but was : [a, b]", 
			"differences",
			"- expected [0]  : b",
			"- but was  [0]  : a",
			"- expected size : 1",
			"- but was  size : 2",
			"- unexpected [1]: b");
		
		failAssert(() -> expectThat(list).empty()).msgLines(
			"ArrayList=<[a, b]>.size",
			"expected: 0",
			"but was : 2");
		
		failAssert(() -> expectThat(list).not().elems("a", "b")).msgLines(
			"ArrayList=<[a, b]>.elems",
			"expected not: [a, b]");

		failAssert(() -> expectThat(list).elems("a", "b", "c")).msgLines(
			"ArrayList=<[a, b]>.elems",
			"expected: [a, b, c]",
			"but was : [a, b]",
			"differences",
			"- expected size: 3",
			"- but was  size: 2",
			"- missing [2]  : c");
		
		failAssert(() -> expectThat(list).elems("a")).msgLines(
			"ArrayList=<[a, b]>.elems",
			"expected: [a]",
			"but was : [a, b]",
			"differences",
			"- expected size : 1",
			"- but was  size : 2",
			"- unexpected [1]: b");
	}
	
	
	@SuppressWarnings("boxing")
	@Test public void testElemAccess()
	{
		List<String> list = Arrays.asList("a", "b");
		expectThat(list)
			.stream().map(String::length).elems$(1, 1)
			;
//		.elem(0).narrow(Narrows.string()).isLowerCase().back()
//		.elem(1).narrow(Narrows.string()).isLowerCase().back();
	}
}

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
package deepdive.actual.java.util;


import static deepdive.ExpectThat.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link SetActual}.
 */
public class SetActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		Set<String> set = new LinkedHashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");
		
		expectThat(set)
			.not().blank()
			.contains("a")
			.not().contains("x")
			.contains()
				.allOf("c", "a")
				.noneOf("x", "z")
				.exactly("c", "b", "a")
				.back()
			.size(3)
			.size()
				.greater(2)
				.back();
		
		Set<String> set2 = new LinkedHashSet<>();
		set2.add("a");
		set2.add("d");
		failAssert(() -> expectThat(set).equal(set2)).msgLines(
			"LinkedHashSet=<[a, b, c]>",
			"expected: [a, d]",
			"but was : [a, b, c]",
			"differences",
			"- missing   : d",
			"- unexpected: [b, c]");
		
		failAssert(() -> expectThat(set).elems("a", "c", "d", "e")).msgLines(
			"LinkedHashSet=<[a, b, c]>.elems",
			"expected: [a, c, d, e]",
			"but was : [a, b, c]",
			"differences",
			"- missing   : [d, e]",
			"- unexpected: b");
	}
}

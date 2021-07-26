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
import java.util.regex.Pattern;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;
import deepdive.actual.lang.StringActual;


/**
 * Tests {@link StringActual}.
 */
public class StringActualTest extends AbstractActualTest 
{
	@Test public void test()
	{
		StringActual<?,?> abc = expectThat("abc")
			.not().blank()
			.codePointAt(0, 97)
			.charAt(1, 'b')
			.not().charAt(1, 'x')
			.charAt(2).isLetter().back()
			.contains("bc")
			.not().contains("x")
			.not().empty()
			.endsWith("c")
			.not().endsWith("x")
			.equalsIgnoreCase("AbC")
			.not().equalsIgnoreCase("aXc")
			.format("a%s", "bc")
			.indexOf('x', -1)
			.indexOf("c", 2)
			.isLowerCase()
			.not().isUpperCase()
			.lastIndexOf('b', 1)
			.lastIndexOf("x", -1)
			.length(3)
			.length().greater(2).back()
			.like("*c")
			.matches("[a-c]+")
			.not().matches("[a-b]+")
			.matches(Pattern.compile(".*"))
			.not().empty()
			.replace('a', 'x', "xbc")
			.split().by("b").elems("a", "c")
			.split().by("b").iterator()
				.next("a")
				.next("c")
				.end$()
			.split().by("a").limit(2).elems("", "bc")
			.startsWith("ab")
			.substring(1, "bc")
			.substring(0, 1, "a")
			.substring(0, 1)
				.equal("a")
				.back()
			.toLowerCase("abc")
			.toUpperCase("ABC");
		
		expectThat("")
			.blank()
			.empty();
		
		expectNull(expectThat("a,b,c")
			.switchTo().split().by(",").toList()
			.elems("a", "b", "c")
			.backOrNull());
		
		failAssert(() -> expectThat("").not().blank()).msgLines(
			"String=<>",
			"expected not to be null or empty",
			"but was: ");

		failAssert(() -> expectThat("a").blank()).msgLines(
			"String=<a>",
			"expected to be null or empty",
			"but was: a");

		failAssert(() -> abc.codePointAt(0, 98)).msgLines(
			"String=<abc>.codePointAt[0]",
			"expected: 98",
			"but was : 97");

		failAssert(() -> abc.contains("x")).msgLines(
			"String=<abc>",
			"expected to contain: x");
		
		failAssert(() -> abc.endsWith("x")).msgLines(
			"String=<abc>",
			"expected to end with: x");

		failAssert(() -> abc.not().endsWith("bc")).msgLines(
			"String=<abc>",
			"expected not to end with: bc");

		failAssert(() -> abc.equalsIgnoreCase("x")).msgLines(
			"String=<abc>",
			"expected to equal ignoring case: x");

		failAssert(() -> abc.indexOf('a', 2)).msgLines(
			"String=<abc>.indexOf(a)",
			"expected: 2",
			"but was : 0");

		failAssert(() -> abc.indexValid(3)).msgLines(
			"String=<abc>",
			"index 3 invalid, must be 0 <= index < 3");

		failAssert(() -> abc.isUpperCase()).msgLines(
			"String=<abc>",
			"expected to be in upper case: ABC");

		failAssert(() -> abc.lastIndexOf("b", 2)).msgLines(
			"String=<abc>.lastIndexOf(b)",
			"expected: 2",
			"but was : 1");

		failAssert(() -> abc.like("x*")).msgLines(
			"String=<abc>",
			"expected to be like: x*");

		failAssert(() -> abc.matches("[a-b]+")).msgLines(
			"String=<abc>",
			"expected to match pattern: [a-b]+");

		failAssert(() -> abc.not().matches(".+")).msgLines(
			"String=<abc>",
			"expected not to match pattern: .+");

		failAssert(() -> abc.toUpperCase("ABc")).msgLines(
			"String=<abc>.toUpperCase",
			"expected: ABc",
			"but was : ABC");
		
		failAssert(() -> abc.empty()).msgLines(
			"String=<abc>.length",
			"expected: 0",
			"but was : 3");
		
		failAssert(() -> abc.switchTo().split().by("b").iterator().not().hasNext()).msgLines(
			"split(abc, b)=<It([a, c], 0)>.hasNext",
			"expected not: true");
	}
}

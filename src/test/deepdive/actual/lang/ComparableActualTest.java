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
import deepdive.actual.lang.ComparableActual;
import deepdive.actual.lang.IntegerActual;
import deepdive.actual.lang.StringActual;


/**
 * Tests {@link ComparableActual}.
 */
public class ComparableActualTest extends AbstractActualTest 
{
	@Test public void test()
	{
		StringActual<?,?> abc = expectThat("abc")
			.greater("a")
			.not().greater("d")
			.greaterEq("abc")
			.less("b")
			.lessEq("abc");
		
		failAssert(() -> abc.greater("x")).msgLines(
			"String=<abc>",
			"expected to be > than: x",
			"but was              : abc");
		failAssert(() -> abc.greaterEq("x")).msgLines(
			"String=<abc>",
			"expected to be >= than: x",
			"but was               : abc");
		
		IntegerActual<?,?> i = expectThat(5);
		failAssert(() -> i.less(4)).msgLines(
			"Integer=<5>",
			"expected to be < than: 4",
			"but was              : 5");
		failAssert(() -> i.lessEq(4)).msgLines(
			"Integer=<5>",
			"expected to be <= than: 4",
			"but was               : 5");
	}
}

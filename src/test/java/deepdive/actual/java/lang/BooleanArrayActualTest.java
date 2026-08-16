/*
 * Copyright (c) 2026 jdlib, https://github.com/jdlib
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


import static deepdive.ExpectThat.*;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link BooleanArrayActual}.
 */
public class BooleanArrayActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(new boolean[] { false, true, false })
			.not().blank()
			.contains(true)
			.contains()
				.allOf(true, false)
				.exactly(false, true, false)
				.someOf(true, false)
				.not().noneOf(false)
				.back()
			.elem(0, false)
			.elem(1).equal(true).back()
			.elems(false, true, false)
			.not().empty()
			.indexValid(1)
			.not().indexValid(15)
			.length().greater(1);

		expectThat(new boolean[] { false })
			.contains()
				.not().allOf(true)
				.noneOf(true);

		expectThat(new boolean[] { true, false })
			.sort()
			.elems(false, true);

		expectThat(new boolean[0])
			.blank()
			.empty();

		expectThat((boolean[])null)
			.blank();

		failAssert(() -> expectThat(new boolean[] { false }).elem(1, true)).msgLines(
			"boolean[]=<[false]>",
			"index 1 invalid, must be 0 <= index < 1");

		failAssert(() -> expectThat(new boolean[] { true, false }).elems(true)).msgLines(
			"boolean[]=<[true, false]>",
			"expected: [true]",
			"but was : [true, false]",
			"differences",
			"- expected len  : 1",
			"- but was  len  : 2",
			"- unexpected [1]: false");
	}
}

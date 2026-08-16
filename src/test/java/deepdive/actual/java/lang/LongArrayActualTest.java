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
 * Tests {@link LongArrayActual}.
 */
public class LongArrayActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(new long[] { 1L, 1L, 2L, 3L, 5L, 8L })
			.not().blank()
			.contains(2L)
			.not().contains(127L)
			.contains()
				.allOf(1L, 3L, 5L)
				.not().allOf(1L, 17L)
				.exactly(1L, 2L, 3L, 5L, 8L)
				.not().someOf(7L, 19L)
				.noneOf(15L)
				.not().noneOf(1L)
				.back()
			.elem(0, 1L)
			.elem(1).equal(1L).back()
			.elems(1L, 1L, 2L, 3L, 5L, 8L)
			.not().empty()
			.indexValid(1)
			.not().indexValid(15)
			.length().equal(6).back();

		expectThat(new long[] { 3L, 1L })
			.sort()
			.elems(1L, 3L);

		expectThat(new long[0])
			.blank()
			.empty();

		expectThat((long[])null)
			.blank();

		failAssert(() -> expectThat(new long[] { 2L }).elem(1, 4L)).msgLines(
			"long[]=<[2]>",
			"index 1 invalid, must be 0 <= index < 1");

		failAssert(() -> expectThat(new long[] { 1L, 4L, 7L }).elems(4L, 6L)).msgLines(
			"long[]=<[1, 4, 7]>",
			"expected: [4, 6]",
			"but was : [1, 4, 7]",
			"differences",
			"- expected [0]  : 4",
			"- but was  [0]  : 1",
			"- expected [1]  : 6",
			"- but was  [1]  : 4",
			"- expected len  : 2",
			"- but was  len  : 3",
			"- unexpected [2]: 7");
	}
}

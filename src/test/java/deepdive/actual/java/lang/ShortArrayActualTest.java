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
 * Tests {@link ShortArrayActual}.
 */
public class ShortArrayActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(new short[] { (short)2, (short)4 })
			.not().blank()
			.contains((short)4)
			.not().contains((short)5)
			.contains()
				.allOf((short)2)
				.not().allOf((short)2, (short)3)
				.exactly((short)2, (short)4)
				.noneOf((short)3)
				.not().noneOf((short)4)
				.someOf((short)4, (short)5)
				.back()
			.elem(0, (short)2)
			.elem(1).equal((short)4).back()
			.elems((short)2, (short)4)
			.not().empty()
			.indexValid(1)
			.not().indexValid(15)
			.length().equal(2).back();

		expectThat(new short[] { (short)4, (short)2 })
			.sort()
			.elems((short)2, (short)4);

		expectThat(new short[0])
			.blank()
			.empty();

		expectThat((short[])null)
			.blank();

		failAssert(() -> expectThat(new short[] { (short)2 }).elem(1, (short)4)).msgLines(
			"short[]=<[2]>",
			"index 1 invalid, must be 0 <= index < 1");

		failAssert(() -> expectThat(new short[] { (short)2, (short)4 }).elems((short)2)).msgLines(
			"short[]=<[2, 4]>",
			"expected: [2]",
			"but was : [2, 4]",
			"differences",
			"- expected len  : 1",
			"- but was  len  : 2",
			"- unexpected [1]: 4");
	}
}

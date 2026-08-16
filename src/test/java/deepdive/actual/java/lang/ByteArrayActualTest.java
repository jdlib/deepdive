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
 * Tests {@link ByteArrayActual}.
 */
public class ByteArrayActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(new byte[] { (byte)2, (byte)4 })
			.not().blank()
			.contains((byte)4)
			.not().contains((byte)5)
			.contains()
				.allOf((byte)2)
				.not().allOf((byte)3)
				.noneOf((byte)3)
				.not().noneOf((byte)2)
				.someOf((byte)2)
				.not().someOf((byte)3)
				.back()
			.elem(0, (byte)2)
			.elem(1).equal((byte)4).back()
			.elems((byte)2, (byte)4)
			.not().empty()
			.indexValid(1)
			.not().indexValid(15)
			.length().less(10).back();

		expectThat(new byte[] { (byte)2, (byte)2 })
			.contains()
				.exactly((byte)2);

		expectThat(new byte[] { (byte)4, (byte)2 })
			.sort()
			.elems((byte)2, (byte)4);

		expectThat(new byte[0])
			.blank()
			.empty();

		expectThat((byte[])null)
			.blank();

		failAssert(() -> expectThat(new byte[] { (byte)2, (byte)4 }).elem(2, (byte)4)).msgLines(
			"byte[]=<[2, 4]>",
			"index 2 invalid, must be 0 <= index < 2");

		failAssert(() -> expectThat(new byte[] { (byte)2, (byte)4 }).elems((byte)2)).msgLines(
			"byte[]=<[2, 4]>",
			"expected: [2]",
			"but was : [2, 4]",
			"differences",
			"- expected len  : 1",
			"- but was  len  : 2",
			"- unexpected [1]: 4");
	}
}

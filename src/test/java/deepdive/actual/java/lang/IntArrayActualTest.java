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
package deepdive.actual.java.lang;


import static deepdive.ExpectThat.*;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link IntArrayActual}.
 */
public class IntArrayActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(new int[] { 1, 1, 2, 3, 5, 8 })
			.not().blank()
			.contains(2)
			.not().contains(127)
			.contains()
				.allOf(1, 3, 5)
				.exactly(1, 2, 3, 5, 8)
				.not().someOf(7, 19)
				.back()
			.elem(0, 1)
			.elem(1).equal(1).back()
			.elems(1, 1, 2, 3, 5, 8)
			.not().empty()
			.indexValid(1)
			.not().indexValid(15);
		
		expectThat(new int[0])
			.blank()
			.empty();
		
		expectThat((int[])null)
			.blank();

		failAssert(() -> expectThat(new int[] { 1, 4, 7 }).elems(4, 6)).msgLines(
			"int[]=<[1, 4, 7]>",
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

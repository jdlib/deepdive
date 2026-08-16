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
 * Tests {@link DoubleArrayActual}.
 */
public class DoubleArrayActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(new double[] { 1.1, 2.2, 3.3 })
			.not().blank()
			.contains(2.2)
			.not().contains(4.4)
			.contains()
				.allOf(1.1, 2.2)
				.not().allOf(1.2)
				.exactly(1.1, 2.2, 3.3)
				.not().someOf(0.4, 19.1)
				.noneOf(4.4)
				.not().noneOf(1.1)
				.back()
			.elem(0, 1.1, 0.0)
			.elem(1).equal(2.2, 0.0).back()
			.elems(1.1, 2.2, 3.3)
			.not().empty()
			.indexValid(1)
			.length(3)
			.length().equal(3).back()
			.not().indexValid(15);

		expectThat(new double[] { 2.0, 1.0 })
			.sort()
			.elems(1.0, 2.0);

		expectThat(new double[0])
			.blank()
			.empty();

		expectThat((double[])null)
			.blank();

		failAssert(() -> expectThat(new double[] { 5.0, 6.5 }).elems(5.0, 6.4)).msgLines(
			"double[]=<[5.0, 6.5]>",
			"expected: [5.0, 6.4]",
			"but was : [5.0, 6.5]",
			"differences",
			"- expected [1]: 6.4",
			"- but was  [1]: 6.5");
	}
}

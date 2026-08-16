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
 * Tests {@link FloatArrayActual}.
 */
public class FloatArrayActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(new float[] { 1.1f, 2.2f, 3.3f })
			.not().blank()
			.contains(2.2f)
			.not().contains(4.4f)
			.contains()
				.allOf(1.1f, 2.2f)
				.not().allOf(4.4f)
				.exactly(1.1f, 2.2f, 3.3f)
				.not().someOf(0.4f, 19.1f)
				.noneOf(1.2f)
				.not().noneOf(1.1f)
				.back()
			.elem(0, 1.1f, 0.0f)
			.elem(1).equal(2.2f, 0.0f).back()
			.elems(1.1f, 2.2f, 3.3f)
			.not().empty()
			.indexValid(1)
			.not().indexValid(15)
			.length(3)
			.length().equal(3).back();

		expectThat(new float[] { 2.0f, 1.0f })
			.sort()
			.elems(1.0f, 2.0f);

		expectThat(new float[0])
			.blank()
			.empty();

		expectThat((double[])null)
			.blank();

		failAssert(() -> expectThat(new float[] { 5.0f, 6.5f }).elems(5.0f, 6.4f)).msgLines(
			"float[]=<[5.0, 6.5]>",
			"expected: [5.0, 6.4]",
			"but was : [5.0, 6.5]",
			"differences",
			"- expected [1]: 6.4",
			"- but was  [1]: 6.5");
	}
}

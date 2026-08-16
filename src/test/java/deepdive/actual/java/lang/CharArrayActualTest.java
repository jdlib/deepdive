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
 * Tests {@link CharArrayActual}.
 */
public class CharArrayActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(new char[] { 'a', '5' })
			.not().blank()
			.contains('a')
			.not().contains('b')
			.contains()
				.allOf('5')
				.not().allOf('6')
				.noneOf('2')
				.not().noneOf('a')
				.not().exactly('b')
				.someOf('4', '5')
				.not().someOf('4', '6')
				.back()
			.elem(0, 'a')
			.elem(1).equal('5').back()
			.elems('a', '5')
			.not().empty()
			.indexValid(1)
			.not().indexValid(15)
			.length().equal(2).back();

		expectThat(new char[] { 'a', '5' })
			.sort()
			.elems('5', 'a');

		expectThat(new char[0])
			.blank()
			.empty();

		expectThat((char[])null)
			.blank();

		failAssert(() -> expectThat(new char[] { 'a', '5' }).elems('a', '4')).msgLines(
			"char[]=<[a, 5]>",
			"expected: [a, 4]",
			"but was : [a, 5]",
			"differences",
			"- expected [1]: 4",
			"- but was  [1]: 5");
	}
}

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


/**
 * Tests {@link NumberActual} implementations.
 */
public class NumberActualTest
{
	@Test public void testByte()
	{
		expectThat(Byte.MIN_VALUE)
			.less(Byte.MAX_VALUE)
			.equal(Byte.MIN_VALUE)
			.not().equal(null);
	}


	@Test public void testInteger()
	{
		expectThat(5)
			.mod(2, 1);
	}
}

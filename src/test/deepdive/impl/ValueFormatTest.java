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
package deepdive.impl;


import org.junit.Test;
import deepdive.AbstractTest;
import deepdive.impl.ValueFormat;


public class ValueFormatTest extends AbstractTest
{
	@Test public void testDefault()
	{
		ValueFormatActual.of(new ValueFormat())
			.shorten("a",   1, "a")
			.shorten("abc", 3, "abc")
			.shorten("abc", 2, "a…c")
			.shorten("abc", 1, "a…c")
			.shorten("123456789", 1, "1…9")
			.shorten("123456789", 2, "1…9")
			.format(null, "null")
			.format(String.class, "java.lang.String")
			.format(new int[] {1, 3, 5}, "[1, 3, 5]")
			.format("s", "s")
			.format(new Object[5000], "[null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, nul…ull, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]");
	}
}

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
package deepdive;


import static deepdive.ExpectThat.*;
import org.junit.Test;
import deepdive.ExpectInterface;
import deepdive.actual.lang.StringActual;


public class ContextTest extends AbstractTest
{
	@Test public void testDescribe()
	{
		ExpectInterface a = getExpect(null);
		expectNull(a.getContext());
		
		a = a.getExpect("1");
		expectSame("1", a.getContext());
		
		StringActual<?,?> s = expectThat("a"); 
		failAssert(() -> s.indexOf('b', 0)).msgLines(
			"String=<a>.indexOf(b)",
			"expected: 0",
			"but was : -1");
		failAssert(() -> s.length().equal(5)).msgLines(
			"String=<a>.length=<1>",
			"expected: 5",
			"but was : 1");
		
		failAssert(() -> expectThat(new String[] { "a", "b"}).elem(0).toStringActual().length().greater(5)).msgLines(
			"String[]=<[a, b]>.elem[0]=<a>.toString=<a>.length=<1>",
			"expected to be > than: 5",
			"but was              : 1");
	}

}

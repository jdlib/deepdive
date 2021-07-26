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
import deepdive.impl.Not;


public class NotTest extends AbstractTest
{
	@Test public void test()
	{
		expectTrue(Not.ON.isOn());
		expectFalse(Not.OFF.isOn());
		
		expectSame(Not.ON,  Not.OFF.other());
		expectSame(Not.OFF, Not.ON.other());
		
		expectEqual(true,  Not.OFF.apply(true));
		expectEqual(false, Not.OFF.apply(false));
		expectEqual(false, Not.ON.apply(true));
		expectEqual(true,  Not.ON.apply(false));
	}
}

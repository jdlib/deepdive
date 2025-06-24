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


public class ExpectResultTest extends AbstractTest
{
	@Test public void test()
	{
		expectSame(ExpectResult.FAILURE__NOT_WAS_ON, 	ExpectResult.eval(Not.ON,  true));
		expectSame(ExpectResult.SUCCESS__NOT_WAS_OFF,  	ExpectResult.eval(Not.OFF, true));
		expectSame(ExpectResult.SUCCESS__NOT_WAS_ON,    ExpectResult.eval(Not.ON,  false));
		expectSame(ExpectResult.FAILURE__NOT_WAS_OFF,	ExpectResult.eval(Not.OFF, false));
	}
}

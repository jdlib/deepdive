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
package deepdive;


import static deepdive.ExpectThat.*;
import org.junit.Test;
import deepdive.function.CheckedRunnable;


public class ExpectInterfaceTest implements ExpectInterface
{
	@Test public void testEqual()
	{
		expectEqual(true, true);
		expectEqual(false, Boolean.FALSE);

		expectEqual((byte)1, (byte)1);
		expectEqual((byte)1, Byte.valueOf((byte)1));

		expectEqual('a', 'a');
		expectEqual('a', Character.valueOf('a'));

		expectEqual(1.0, Double.valueOf(1.05), 0.1);
		expectEqual(1.0, 1.05, 0.1);

		expectEqual(1f, Float.valueOf(1.05f), 0.1f);
		expectEqual(1f, 1.05f, 0.1f);

		expectEqual(1, 1);
		expectEqual(1, Integer.valueOf(1));

		expectEqual(1L, 1L);
		expectEqual(1L, Long.valueOf(1L));

		expectEqual((short)1, (short)1);
		expectEqual((short)1, Short.valueOf((short)1));
	}


	@Test public void testEqualFailure()
	{
		testEqualFailure(() -> expectEqual(true, false));
		testEqualFailure(() -> expectEqual(true, Boolean.FALSE));
		testEqualFailure(() -> expectEqual((byte)1, (byte)2));
		testEqualFailure(() -> expectEqual('a', 'b'));
		testEqualFailure(() -> expectEqual(1.0, 2.0, 0.1));
		testEqualFailure(() -> expectEqual(1.0, Double.valueOf(2.0), 0.1));
		testEqualFailure(() -> expectEqual(1.0f, 2.0f, 0.1f));
		testEqualFailure(() -> expectEqual((short)1, (short)2));
	}


	private void testEqualFailure(CheckedRunnable<?> runnable)
	{
		expectError(runnable).isA(AssertionError.class).message().contains("expected").contains("but was");
	}


	@Test public void testFail()
	{
		expectError(() -> fail("now")).isA(AssertionError.class).message().contains("now");
		expectError(() -> fail()).isA(AssertionError.class);
	}


	@Test public void testNot()
	{
		not().expectEqual(false, true);
	}
}

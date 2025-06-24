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
package deepdive.actual.java.time;


import static deepdive.ExpectThat.*;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link LocalTimeActual}.
 */
public class LocalTimeActualTest extends AbstractActualTest
{
	@SuppressWarnings("boxing")
	@Test public void test()
	{
		expectThat(LocalTime.of(12, 13, 14))
			.equal(12, 13, 14)
			.get(ChronoField.MINUTE_OF_DAY, 733)
			.hour(12)
			.hour().contained().in(12, 14).back()
			.isAfter(LocalTime.of(12, 0, 0))
			.not().isBefore(LocalTime.of(12, 0, 0))
			.minute(13)
			.minute().greater(2).back()
			.nano(0)
			.not().nano(1)
			.nano().less(2).back()
			.second(14)
			.second().greaterEq(14).back();
	}
}

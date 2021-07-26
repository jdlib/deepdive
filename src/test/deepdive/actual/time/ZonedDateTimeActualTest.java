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
package deepdive.actual.time;


import static deepdive.ExpectThat.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;
import deepdive.actual.time.ZonedDateTimeActual;


/**
 * Tests {@link ZonedDateTimeActual}.
 */
public class ZonedDateTimeActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		ZoneId zoneId = ZoneId.systemDefault();
		
		expectThat(ZonedDateTime.of(2020, 12, 31, 10, 39, 42, 567, zoneId))
			.date().equal(2020, 12, 31).back()
			.time().equal(10, 39, 42).nano(567).back()
			.get(ChronoField.DAY_OF_YEAR, 366)
			.isSupported(ChronoField.HOUR_OF_DAY)
			.zone(zoneId);
	}
}

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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;
import deepdive.actual.time.LocalDateActual;


/**
 * Tests {@link LocalDateActual}.
 */
public class LocalDateActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(LocalDate.of(2020, 12, 31))
			.dayOfMonth(31)
			.dayOfWeek(DayOfWeek.THURSDAY)
			.dayOfYear(366)
			.get(ChronoField.DAY_OF_YEAR, 366)
			.get(ChronoField.DAY_OF_MONTH)
				.greater(30)
				.lessEq(31)
				.back()
			.isAfter(LocalDate.of(2020, 1, 1))
			.isBefore(LocalDate.of(2021, 1, 1))
			.not().isSupported(ChronoField.HOUR_OF_DAY)
			.month(12)
			.month(Month.DECEMBER)
			.equal(2020, 12, 31)
			.leapYear()
			.lengthOfMonth(31)
			.lengthOfYear(366)
			.year(2020);
	}
}

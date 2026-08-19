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
package deepdive.actual.java.time;


import static deepdive.ExpectThat.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import org.junit.Test;


public class TemporalActualTest
{
	@Test public void test()
	{
		LocalDate d = LocalDate.of(2026,  1, 31);
		expectThat(d)
			.get(ChronoField.YEAR)
				.greater(2000)
				.back()
			.get(ChronoField.YEAR, 2026)
			.getLong(ChronoField.YEAR)
				.greater(2000)
				.back()
			.getLong(ChronoField.YEAR, 2026)
			.plus(1, ChronoUnit.YEARS)
			.get(ChronoField.YEAR, 2027)
			.plus(Period.ofYears(1))
			.get(ChronoField.YEAR, 2028)
			.with(ChronoField.YEAR, 2029)
			.get(ChronoField.YEAR, 2029)
			.get(ChronoField.DAY_OF_MONTH, 31)
			.with(TemporalAdjusters.firstDayOfMonth())
			.get(ChronoField.DAY_OF_MONTH, 1);

	}
}

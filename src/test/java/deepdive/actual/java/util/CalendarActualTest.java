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
package deepdive.actual.java.util;


import static deepdive.ExpectThat.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.junit.Test;


public class CalendarActualTest
{
	@Test public void test()
	{
		GregorianCalendar cal = new GregorianCalendar(2026, 0, 31, 12, 13, 14);
		GregorianCalendar cal_m1 = (GregorianCalendar)cal.clone();
		cal_m1.add(Calendar.DAY_OF_MONTH, -1);
		GregorianCalendar cal_p1 = (GregorianCalendar)cal.clone();
		cal_p1.add(Calendar.DAY_OF_MONTH,  1);

		expectThat(cal)
			.actualMaximum(Calendar.MONTH, 11)
			.actualMinimum(Calendar.MONTH, 0)
			.after(cal_m1)
			.before(cal_p1)
			.calendarType("gregory")
			.displayName(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.ENGLISH, "Jan")
			.firstDayOfWeek(2)
			.get(Calendar.YEAR, 2026)
			.greatestMinimum(Calendar.DAY_OF_MONTH, 1)
			.isLenient()
			.isSet(Calendar.YEAR)
			.isWeekDateSupported()
			.leastMaximum(Calendar.DAY_OF_MONTH, 28)
			.maximum(Calendar.DAY_OF_MONTH, 31)
			.minimum(Calendar.DAY_OF_MONTH, 1)
			.minimalDaysInFirstWeek(4)
			.timeInMillis(cal.getTimeInMillis())
			.timeZone(cal.getTimeZone())
			.toInstant()
				.nano(0)
				.back()
			.weeksInWeekYear(53)
			.weekYear(2026);
	}
}

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
import java.util.Date;
import org.junit.Test;


public class DateActualTest
{
	@SuppressWarnings("deprecation")
	@Test public void test()
	{
		Date d = new Date(2026, 07, 16, 12, 13, 14);
		expectThat(d)
			.after(new Date(2026, 7, 15))
			.before(new Date(2026, 7, 17))
			.compareTo(d, 0)
			.dayOfMonth(16)
			.dayOfWeek(1)
			.hours(12)
			.minutes(13)
			.month(7)
			.seconds(14)
			.toInstant().back()
			.time(d.getTime())
			.timezoneOffset(d.getTimezoneOffset())
			.toGMTString(d.toGMTString())
			.toLocaleString(d.toLocaleString())
			.year(2026);
	}
}

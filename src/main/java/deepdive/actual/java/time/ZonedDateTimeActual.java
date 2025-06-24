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


import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import deepdive.actual.java.lang.IntegerActual;


/**
 * An Actual implementation for {@link ZonedDateTime}.
 */
public class ZonedDateTimeActual<BACK,IMPL extends ZonedDateTimeActual<BACK,IMPL>> 
	extends TemporalActual<ZonedDateTime,BACK,IMPL>
{
	/**
	 * Creates a new ZonedDateTimeActual.
	 * @param value the value
	 * @param back the owner
	 */
	public ZonedDateTimeActual(ZonedDateTime value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Compares the actual value to the other value and return the result as IntegerActual.
	 * @param other another ChronoZonedDateTime
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> compareTo(ChronoZonedDateTime<?> other)
	{
		return new IntegerActual<>(value().compareTo(other), self()).as("compareTo");
	}

	
	/**
	 * Asserts that the offset of the ChronoZonedDateTime equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL offset(ZoneOffset expected)
	{
		expectEqual(expected, value().getOffset(), "offset");
		return self();
	}
	
	
	/**
	 * Returns a LocalDateActual for the date part.
	 * @return the new actual
	 */
	public LocalDateActual<IMPL,?> date()
	{
		return new LocalDateActual<>(value().toLocalDate(), self()).as("date");
	}


	/**
	 * Returns a LocalDateTimeActual for the datetime part.
	 * @return the new actual
	 */
	public LocalDateTimeActual<IMPL,?> dateTime()
	{
		return new LocalDateTimeActual<>(value().toLocalDateTime(), self()).as("dateTime");
	}


	/**
	 * Returns a LocalTimeActual for the time part.
	 * @return the new actual
	 */
	public LocalTimeActual<IMPL,?> time()
	{
		return new LocalTimeActual<>(value().toLocalTime(), self()).as("time");
	}


	/**
	 * Asserts that the zone of the ChronoZonedDateTime equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL zone(ZoneId expected)
	{
		expectEqual(expected, value().getZone(), "zone");
		return self();
	}
}

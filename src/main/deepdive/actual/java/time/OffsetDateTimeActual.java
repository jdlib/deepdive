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


import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import deepdive.Context;
import deepdive.actual.java.lang.IntegerActual;


/**
 * An Actual implementation for {@link OffsetDateTime} objects.
 */
public class OffsetDateTimeActual<BACK,IMPL extends OffsetDateTimeActual<BACK,IMPL>> 
	extends TemporalActual<OffsetDateTime,BACK,IMPL>
{
	/**
	 * Creates a new OffsetDateTimeActual.
	 * @param value the value
	 * @param back the owner
	 */
	public OffsetDateTimeActual(OffsetDateTime value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Compares the actual value to the other value and return the result as IntegerActual.
	 * @param other another OffsetDateTime
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> compareTo(OffsetDateTime other)
	{
		return new IntegerActual<>(value().compareTo(other), self()).as("compareTo");
	}
	
	
	/**
	 * Returns a LocalDateActual for the date part of the OffsetDateTime.
	 * @return the LocalDateActual
	 */
	public LocalDateActual<IMPL,?> date()
	{
		return new LocalDateActual<>(value().toLocalDate(), self()).as("date");
	}
	
	
	/**
	 * Asserts that the date part of the OffsetDateTime equals the given date.
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @return this
	 */
	public IMPL date(int year, int month, int day)
	{
		date().equal(year, month, day);
		return self();
	}

	
	public IMPL isAfter(OffsetDateTime other)
	{
		expectTrue(value().isAfter(other), Context.call("isAfter", other));
		return self();
	}
	
	
	public IMPL isBefore(OffsetDateTime other)
	{
		expectTrue(value().isBefore(other), Context.call("isBefore", other));
		return self();
	}
	
	
	public IMPL offset(ZoneOffset expected)
	{
		expectEqual(expected, value().getOffset(), "offset");
		return self();
	}
	

	/**
	 * Returns a LocalTimeActual for the time part of the OffsetDateTime.
	 * @return the LocalTimeActual
	 */
	public LocalTimeActual<IMPL,?> time()
	{
		return new LocalTimeActual<>(value().toLocalTime(), self()).as("time");
	}
	

	/**
	 * Asserts that the time part of the OffsetDateTime equals the given time.
	 * @param hour the hour
	 * @param minute the minute
	 * @param second the second
	 * @return this
	 */
	public IMPL time(int hour, int minute, int second)
	{
		time().equal(hour, minute, second);
		return self();
	}
}

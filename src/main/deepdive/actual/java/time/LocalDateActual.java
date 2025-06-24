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


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Era;
import deepdive.Context;
import deepdive.actual.java.lang.IntegerActual;


/**
 * An Actual implementation for {@link LocalDate} objects.
 * @param <BACK> the type of the owner of the LocalDateActual
 * @param <IMPL> the type of the concrete LocalDateActual implementation 
 */
public class LocalDateActual<BACK,IMPL extends LocalDateActual<BACK,IMPL>> extends TemporalActual<LocalDate,BACK,IMPL>
{
	/**
	 * Creates a new LocalDateActual.
	 * @param value the value
	 * @param back the owner
	 */
	public LocalDateActual(LocalDate value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Compares the actual value to the other value and return the result as IntegerActual.
	 * @param other another value
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> compareTo(ChronoLocalDate other)
	{
		return new IntegerActual<>(value().compareTo(other), self()).as("compareTo");
	}

	
	public IMPL equal(int year, int month, int day)
	{
		return year(year).month(month).dayOfMonth(day);
	}
	
	
	public IMPL dayOfMonth(int expected)
	{
		expectEqual(expected, value().getDayOfMonth(), "dayOfMonth");
		return self();
	}


	public IntegerActual<IMPL,?> dayOfMonth()
	{
		return new IntegerActual<>(value().getDayOfMonth(), self()).as("dayOfMonth");
	}
	
	
	public IMPL dayOfWeek(DayOfWeek expected)
	{
		expectEqual(expected, value().getDayOfWeek(), "dayOfWeek");
		return self();
	}

	
	public IMPL dayOfYear(int expected)
	{
		expectEqual(expected, value().getDayOfYear(), "dayOfYear");
		return self();
	}

	
	public IMPL era(Era expected)
	{
		expectEqual(expected, value().getEra(), "era");
		return self();
	}


	public IMPL isAfter(ChronoLocalDate other)
	{
		expectTrue(value().isAfter(other), Context.call("isAfter", other));
		return self();
	}
	
	
	public IMPL isBefore(ChronoLocalDate other)
	{
		expectTrue(value().isBefore(other), Context.call("isBefore", other));
		return self();
	}

	
	public IMPL leapYear()
	{
		expectTrue(value().isLeapYear(), "leapYear");
		return self();
	}
	
	
	public IMPL lengthOfMonth(int expected)
	{
		expectEqual(expected, value().lengthOfMonth(), "lengthOfMonth");
		return self();
	}
	
	
	public IMPL lengthOfYear(int expected)
	{
		expectEqual(expected, value().lengthOfYear(), "lengthOfYear");
		return self();
	}

	
	public IMPL month(Month expected)
	{
		expectEqual(expected, value().getMonth(), "month");
		return self();
	}

	
	public IMPL month(int expected)
	{
		expectEqual(expected, value().getMonthValue(), "month");
		return self();
	}


	public IntegerActual<IMPL,?> month()
	{
		return new IntegerActual<>(value().getMonthValue(), self()).as("month");
	}


	public IMPL year(int expected)
	{
		expectEqual(expected, value().getYear(), "year");
		return self();
	}


	public IntegerActual<IMPL,?> year()
	{
		return new IntegerActual<>(value().getYear(), self()).as("year");
	}
}

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


import java.time.LocalTime;
import deepdive.Context;
import deepdive.actual.lang.IntegerActual;


/**
 * An Actual implementation for {@link LocalTime} objects.
 */
public class LocalTimeActual<BACK,IMPL extends LocalTimeActual<BACK,IMPL>> extends TemporalActual<LocalTime,BACK,IMPL>
{
	/**
	 * Creates a new LocalTimeActual.
	 * @param value the value
	 * @param back the owner
	 */
	public LocalTimeActual(LocalTime value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Compares the actual value to the other value and return the result as IntegerActual.
	 * @param other another LocalTime
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> compareTo(LocalTime other)
	{
		return new IntegerActual<>(value().compareTo(other), self()).as("compareTo");
	}
	
	
	public IMPL equal(int hour, int minute, int second)
	{
		return hour(hour).minute(minute).second(second);
	}

	
	public IMPL hour(int expected)
	{
		expectEqual(expected, value().getHour(), "hour");
		return self();
	}
	
	
	public IntegerActual<IMPL,?> hour()
	{
		return new IntegerActual<>(value().getHour(), self()).as("hour");
	}

	
	public IMPL isAfter(LocalTime other)
	{
		expectTrue(value().isAfter(other), Context.call("isAfter", other));
		return self();
	}
	
	
	public IMPL isBefore(LocalTime other)
	{
		expectTrue(value().isBefore(other), Context.call("isBefore", other));
		return self();
	}
	
	
	public IMPL minute(int expected)
	{
		expectEqual(expected, value().getMinute(), "minute");
		return self();
	}
	
	
	public IntegerActual<IMPL,?> minute()
	{
		return new IntegerActual<>(value().getMinute(), self()).as("minute");
	}
	
	
	public IMPL nano(int expected)
	{
		expectEqual(expected, value().getNano(), "nano");
		return self();
	}


	public IntegerActual<IMPL,?> nano()
	{
		return new IntegerActual<>(value().getNano(), self()).as("nano");
	}
	
	
	public IMPL second(int expected)
	{
		expectEqual(expected, value().getSecond(), "second");
		return self();
	}


	public IntegerActual<IMPL,?> second()
	{
		return new IntegerActual<>(value().getSecond(), self()).as("second");
	}
}

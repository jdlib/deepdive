package deepdive.actual.java.util;


import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.java.time.InstantActual;
import java.util.Date;


/**
 * An Actual implementation for {@link Date} objects.
 * @param <T> the type of the Date implementation
 * @param <BACK> the type of the owner of the DateActual
 * @param <IMPL> the type of the concrete DateActual implementation 
 */
@SuppressWarnings("deprecation")
public class DateActual<T extends Date,BACK,IMPL extends DateActual<T,BACK,IMPL>> extends Actual<T,BACK,IMPL>
{
	public DateActual(T value, BACK back)
	{
		super(value, back);
	}


	public IMPL after(Date other)
	{
		return expectTo(value().after(other), "after", other);
	}


	public IMPL before(Date other)
	{
		return expectTo(value().before(other), "before", other);
	}


	public IMPL compareTo(Date other, int expected)
	{
		expectEqual(expected, value().compareTo(other), Context.call("compareTo", other));
		return self();
	}


	public IMPL dayOfMonth(int expected)
	{
		expectEqual(expected, value().getDate(), "dayOfMonth");
		return self();
	}


	public IMPL dayOfWeek(int expected)
	{
		expectEqual(expected, value().getDay(), "dayOfWeek");
		return self();
	}


	public IMPL hours(int expected)
	{
		expectEqual(expected, value().getHours(), "hours");
		return self();
	}


	public IMPL minutes(int expected)
	{
		expectEqual(expected, value().getMinutes(), "minutes");
		return self();
	}


	public IMPL month(int expected)
	{
		expectEqual(expected, value().getMonth(), "month");
		return self();
	}


	public IMPL seconds(int expected)
	{
		expectEqual(expected, value().getSeconds(), "seconds");
		return self();
	}


	public IMPL time(long expected)
	{
		expectEqual(expected, value().getTime(), "time");
		return self();
	}


	public IMPL timezoneOffset(int expected)
	{
		expectEqual(expected, value().getTimezoneOffset(), "timezoneOffset");
		return self();
	}


	public IMPL toGMTString(String expected)
	{
		expectEqual(expected, value().toGMTString(), "toGMTString");
		return self();
	}


	public InstantActual<IMPL,?> toInstant()
	{
		return new InstantActual<>(value().toInstant(), self()).as("toInstant"); 
	}


	public IMPL toLocaleString(String expected)
	{
		expectEqual(expected, value().toLocaleString(), "toLocaleString");
		return self();
	}


	public IMPL year(int expected)
	{
		expectEqual(expected, value().getYear(), "year");
		return self();
	}
}

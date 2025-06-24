package deepdive.actual.java.util;


import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.java.time.InstantActual;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * An Actual implementation for {@link Calendar} objects.
 * @param <T> the type of the Calendar implementation
 * @param <BACK> the type of the owner of the CalendarActual
 * @param <IMPL> the type of the concrete CalendarActual implementation 
 */
public class CalendarActual<T extends Calendar,BACK,IMPL extends CalendarActual<T,BACK,IMPL>> extends Actual<T,BACK,IMPL>
{
	public CalendarActual(T value, BACK back)
	{
		super(value, back);
	}


	public IMPL get(int field, int expected)
	{
		expectEqual(expected, value().get(field), Context.call("get", field));
		return self();
	}


	public IMPL actualMaximum(int field, int expected)
	{
		expectEqual(expected, value().getActualMaximum(field), Context.call("actualMaximum", field));
		return self();
	}


	public IMPL actualMinimum(int field, int expected)
	{
		expectEqual(expected, value().getActualMinimum(field), Context.call("actualMinimum", field));
		return self();
	}


	public IMPL after(Object other)
	{
		return expectTo(value().after(other), "after", other);
	}


	public IMPL before(Object other)
	{
		return expectTo(value().before(other), "before", other);
	}


	public IMPL calendarType(String expected)
	{
		expectEqual(expected, value().getCalendarType(), "calendarType");
		return self();
	}


	public IMPL compareTo(Calendar other, int expected)
	{
		expectEqual(expected, value().compareTo(other), Context.call("compareTo", other));
		return self();
	}


	@SuppressWarnings("boxing")
	public IMPL displayName(int field, int style, Locale locale, String expected)
	{
		expectEqual(expected, value().getDisplayName(field, style, locale), Context.call("displayName", field, style, locale));
		return self();
	}


	public IMPL firstDayOfWeek(int expected)
	{
		expectEqual(expected, value().getFirstDayOfWeek(), "firstDayOfWeek");
		return self();
	}


	public IMPL greatestMinimum(int field, int expected)
	{
		expectEqual(expected, value().getGreatestMinimum(field), Context.call("greatestMinimum", field));
		return self();
	}


	public IMPL isLenient()
	{
		expectTrue(value().isLenient(), "isLenient");
		return self();
	}


	@SuppressWarnings("boxing")
	public IMPL isSet(int field)
	{
		return expectTo(value().isSet(field), "isSet", field);
	}


	public IMPL isWeekDateSupported()
	{
		expectTrue(value().isWeekDateSupported(), "isWeekDateSupported");
		return self();
	}


	public IMPL leastMaximum(int field, int expected)
	{
		expectEqual(expected, value().getLeastMaximum(field), Context.call("leastMaximum", field));
		return self();
	}


	public IMPL maximum(int field, int expected)
	{
		expectEqual(expected, value().getMaximum(field), Context.call("maximum", field));
		return self();
	}


	public IMPL minimalDaysInFirstWeek(int expected)
	{
		expectEqual(expected, value().getMinimalDaysInFirstWeek(), "minimalDaysInFirstWeek");
		return self();
	}


	public IMPL minimum(int field, int expected)
	{
		expectEqual(expected, value().getMinimum(field), Context.call("minimum", field));
		return self();
	}


	public IMPL timeInMillis(long expected)
	{
		expectEqual(expected, value().getTimeInMillis(), "timeInMillis");
		return self();
	}


	public IMPL timeZone(TimeZone expected)
	{
		expectEqual(expected, value().getTimeZone(), "timeZone");
		return self();
	}


	public InstantActual<IMPL,?> toInstant()
	{
		return new InstantActual<>(value().toInstant(), self()).as("toInstant"); 
	}


	public IMPL weekYear(int expected)
	{
		expectEqual(expected, value().getWeekYear(), "weekYear");
		return self();
	}


	public IMPL weeksInWeekYear(int expected)
	{
		expectEqual(expected, value().getWeeksInWeekYear(), "weeksInWeekYear");
		return self();
	}
}

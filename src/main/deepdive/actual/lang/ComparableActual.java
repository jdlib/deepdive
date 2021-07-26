package deepdive.actual.lang;


import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.ActualMixin;
import deepdive.actual.Actual.Internals;
import deepdive.impl.StmtTemplate;


/**
 * An ActualMixin interface for Comparables.
 */
public interface ComparableActual<T extends Comparable<T>,IMPL extends Actual<T,?,IMPL>> extends ActualMixin<T,IMPL> 
{
	public static <T extends Comparable<T>,IMPL extends Actual<T,?,IMPL>> IMPL assertCompare(ComparableActual<T,IMPL> actual, boolean ok, String operator, T operand)
	{
		Internals<IMPL> i = ActualMixin.internals(actual);
		return i.expectTrue(ok, StmtTemplate.ASSERT_COMPARE, operator, operand);
	}

	
	/**
	 * An Actual for Comparables.
	 */
	public static class Direct<T extends Comparable<T>,BACK> extends Actual<T,BACK,Direct<T,BACK>>
		implements ComparableActual<T,Direct<T,BACK>>
	{
		public static <T extends Comparable<T>> Direct<T,?> of(T actual)
		{
			return new Direct<>(actual, null);
		}
		
		
		public Direct(T actual, BACK back)
		{
			super(actual, back);
		}
	}
	
	
	/**
	 * Asserts that the actual value is greater than the expected value.
	 * @param other another object
	 * @return this
	 */
	public default IMPL greater(T other)
	{
		return assertCompare(this, value().compareTo(other) > 0, "> ", other);
	}
	

	/**
	 * Asserts that the actual value is greater or equal than the other value.
	 * @param other another object
	 * @return this
	 */
	public default IMPL greaterEq(T other)
	{
		return assertCompare(this, value().compareTo(other) >= 0, ">= ", other);
	}

	
	/**
	 * Asserts that the actual value is less than the other value.
	 * @param other another object
	 * @return this
	 */
	public default IMPL less(T other)
	{
		return assertCompare(this, value().compareTo(other) < 0, "< ", other);
	}

	
	/**
	 * Asserts that the actual value is less or equal than the other value.
	 * @param other another object
	 * @return this
	 */
	public default IMPL lessEq(T other)
	{
		return assertCompare(this, value().compareTo(other) <= 0, "<= ", other);
	}

	
	/**
	 * Compare the actual value to the other value and return the result as IntegerActual.
	 * @param other another object
	 * @return the new actual
	 */
	public default IntegerActual<IMPL,?> compareTo(T other)
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		return new IntegerActual<>(value().compareTo(other), i.self()).as("compareTo");
	}


	/**
	 * Compares this value to given value, reduces the return value to its sign or 0 (-1, +1, 0)
	 * and compares the result to the expected value.
	 * @param other another object
	 * @param expected the expected value: -1, +1 or 0.
	 * @return this
	 */
	public default IMPL compareTo(T other, int expected)
	{
		int actual = value().compareTo(other);
		if (actual < 0)
			actual = -1;
		else if (actual > 0)
			actual = 1;
		
		Internals<IMPL> i = ActualMixin.internals(this);
		i.expectEqual(expected, actual, Context.call("compareTo", other));
		return i.self();
	}
}

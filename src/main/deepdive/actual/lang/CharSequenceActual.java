package deepdive.actual.lang;


import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.ActualMixin;
import deepdive.actual.Actual.Internals;
import deepdive.impl.StmtTemplate;


/**
 * An {ActualMixin interface for {@link CharSequence}.
 */
public interface CharSequenceActual<T extends CharSequence,IMPL extends Actual<T,?,IMPL>> 
	extends ActualMixin<T,IMPL> 
{
	/**
	 * Asserts that the CharSequence is null or empty.
	 * @return this
	 */
	public default IMPL blank()
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		T s = valueOrNull();
		i.expectTrue((s == null) || (s.length() == 0), StmtTemplate.ASSERT_BLANK, null, null); 
		return i.self();
	}
	
	
	/**
	 * Asserts that the char at the given index equals the expected value.
	 * @param index an index
	 * @param expected the expected value
	 * @return this
	 */
	public default IMPL charAt(int index, char expected)
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		i.expectEqual(expected, value().charAt(index), Context.indexed("charAt", index));
		return i.self();
	}
	
	
	/**
	 * Returns a CharacterActual for the character at the given index.
	 * @param index an index
	 * @return the new actual
	 */
	public default CharacterActual<IMPL,?> charAt(int index)
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		return new CharacterActual<>(value().charAt(index), i.self()).as(Context.indexed("charAt", index));
	}
	
	
	/**
	 * Asserts that the CharSequence is empty.
	 * @return this
	 */
	public default IMPL empty()
	{
		return length(0);
	}

	
	/**
	 * Asserts that the CharSequence length equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public default IMPL length(int expected)
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		i.expectEqual(expected, value().length(), "length");
		return i.self();
	}


	/**
	 * Returns an IntegerActual for the CharSequence length.
	 * @return the new actual
	 */
	public default IntegerActual<IMPL,?> length()
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		return new IntegerActual<>(value().length(), i.self()).as("length");
	}
}

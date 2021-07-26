package deepdive.actual.lang;


import deepdive.actual.Actual;


/**
 * An Actual implementation for Boolean or boolean values.
 * @param <BACK> the type of the owner of the CharacterActual
 * @param <IMPL> the type of the concrete CharacterActual implementation 
 */
public class BooleanActual<BACK,IMPL extends BooleanActual<BACK,IMPL>> extends Actual<Boolean,BACK,IMPL>
	implements ComparableActual<Boolean,IMPL>
{
	/**
	 * Creates a BooleanActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public BooleanActual(boolean value, BACK back)
	{
		this(Boolean.valueOf(value), back);
	}
	

	/**
	 * Creates a BooleanActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public BooleanActual(Boolean value, BACK back)
	{
		super(value, back);
	}


	/**
	 * Returns the actual boolean value.
	 * @return the boolean value
	 */
	public boolean booleanValue()
	{
		return value().booleanValue();
	}

	
	/**
	 * Asserts that the actual value equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL equal(boolean expected)
	{
		expectEqual(expected, booleanValue());
		return self();
	}
}

package deepdive.actual.lang;

import deepdive.actual.Actual;

/**
 * An Actual implementation for Character or char values.
 * @param <BACK> the type of the owner of the CharacterActual
 * @param <IMPL> the type of the concrete CharacterActual implementation 
 */
public class CharacterActual<BACK,IMPL extends CharacterActual<BACK,IMPL>> extends Actual<Character,BACK,IMPL> 
	implements ComparableActual<Character,IMPL>
{
	/**
	 * Creates a CharacterActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public CharacterActual(char value, BACK back)
	{
		this(Character.valueOf(value), back);
	}


	/**
	 * Creates a CharacterActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public CharacterActual(Character value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Returns the actual char value.
	 * @return the value
	 */
	public char charValue()
	{
		return value().charValue();
	}

	
	/**
	 * Asserts that the actual value equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL equal(char expected)
	{
		expectEqual(expected, charValue());
		return self();
	}
	
	
	/**
	 * Asserts that the high surrogate property of the actual char is trze.
	 * @return this
	 */
	public IMPL isHighSurrogate()
	{
		expectTrue(Character.isHighSurrogate(charValue()), "isHighSurrogate");
		return self();
	}
	

	/**
	 * Asserts that the low surrogate property of the actual char is true.
	 * @return this
	 */
	public IMPL isLowSurrogate()
	{
		expectTrue(Character.isLowSurrogate(charValue()), "isLowSurrogate");
		return self();
	}

	
	/**
	 * Asserts that the surrogate property of the actual char eis true.
	 * @return this
	 */
	public IMPL isSurrogate()
	{
		expectTrue(Character.isSurrogate(charValue()), "isSurrogate");
		return self();
	}

	
	/**
	 * Asserts that the char is defined.
	 * @return this
	 */
	public IMPL isDefined()
	{
		expectTrue(Character.isDefined(charValue()), "isDefined");
		return self();
	}

	
	/**
	 * Asserts that the char is a digit.
	 * @return this
	 */
	public IMPL isDigit()
	{
		expectTrue(Character.isDigit(charValue()), "isDigit");
		return self();
	}

	
	/**
	 * Asserts that the char is a java identifier.
	 * @return this
	 */
	public IMPL isJavaIdentifierStart()
	{
		expectTrue(Character.isJavaIdentifierStart(charValue()), "isJavaIdentifierStart");
		return self();
	}

	
	/**
	 * Asserts that the char is a java identifier part.
	 * @return this
	 */
	public IMPL isJavaIdentifierPart()
	{
		expectTrue(Character.isJavaIdentifierPart(charValue()), "isJavaIdentifierPart");
		return self();
	}

	
	/**
	 * Asserts that the char is a letter.
	 * @return this
	 */
	public IMPL isLetter()
	{
		expectTrue(Character.isLetter(charValue()), "isLetter");
		return self();
	}

	
	/**
	 * Asserts that the char is in lower case.
	 * @return this
	 */
	public IMPL isLowerCase()
	{
		expectTrue(Character.isLowerCase(charValue()), "isLowerCase");
		return self();
	}

	
	/**
	 * Asserts that the char is in title case.
	 * @return this
	 */
	public IMPL isTitleCase()
	{
		expectTrue(Character.isTitleCase(charValue()), "isTitleCase");
		return self();
	}

	
	/**
	 * Asserts that the char is a unicide identifier start.
	 * @return this
	 */
	public IMPL isUnicodeIdentifierStart()
	{
		expectTrue(Character.isUnicodeIdentifierStart(charValue()), "isUnicodeIdentifierStart");
		return self();
	}

	
	/**
	 * Asserts that the char is a unicide identifier part.
	 * @return this
	 */
	public IMPL isUnicodeIdentifierPart()
	{
		expectTrue(Character.isUnicodeIdentifierPart(charValue()), "isUnicodeIdentifierPart");
		return self();
	}

	
	/**
	 * Asserts that the char is in upper case.
	 * @return this
	 */
	public IMPL isUpperCase()
	{
		expectTrue(Character.isUpperCase(charValue()), "isUpperCase");
		return self();
	}

	
	/**
	 * Asserts that the actual char value is greater than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL greater(char expected)
	{
		return greater(Character.valueOf(expected));
	}

	
	/**
	 * Asserts that the actual char value is less than the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL less(char expected)
	{
		return less(Character.valueOf(expected));
	}
}

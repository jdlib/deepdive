package deepdive.impl;


/**
 * Provides helper methods for argument checking.
 */
public class Check
{
	/**
	 * Tests that the value is not null.
	 * @param value a value
	 * @param what describes the value
	 * @return the value if not null
	 * @throws IllegalArgumentException if the value is null
	 * @param <T> the value type
	 */
	public static <T> T notNull(T value, String what)
	{
		if (value == null)
			throw new IllegalArgumentException(what + " is null");
		return value;
	}
}

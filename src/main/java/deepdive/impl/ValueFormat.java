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
package deepdive.impl;


import java.util.Arrays;


/**
 * ValueFormat is a service to turn an object into a string representation.
 * In the context of this libary it is used to format objects in order
 * to include them into messages of assertion errors. 
 * {@link Value#format(Object)} simply forwards to the currently active ValueFormat.
 * {@link #get()} returns the currently used ValueFormat.
 * If you want to adjust the ValueFormat, create a derived class 
 * and set it via {@link #setCurrent(ValueFormat)}.  
 */
public class ValueFormat
{
	public static final String ELLIPSIS = "\u2026"; 
	
	
	/**
	 * Returns the currently active ValueFormat. 
	 * @return the ValueFormat
	 * @see #setCurrent(ValueFormat)
	 */
	public static ValueFormat get()
	{
		return current_;
	}
	
	
	/**
	 * Sets the current ValueFormat.
	 * @param value the new ValueFormat
	 * @see #get() 
	 */
	public static void setCurrent(ValueFormat value)
	{
		current_ = Check.notNull(value, "value");
	}

	
	/**
	 * Returns a descriptive, non-null string for the object value.
	 * @param value a value
	 * @return the formatted value as string
	 */
	public String format(Object value) 
	{
		String s;
		if (value == null)
			s = nullString();
		else if (value.getClass().isArray())
			s = arrayString(value);
		else if (value instanceof Class) 
			s = classString((Class<?>)value);
		else
			s = objectString(value);
		return s;
	}
	
	
	protected String nullString()
	{
		return "null";
	}
	
	
	protected String arrayString(Object array)
	{
		return shorten(arrayStringRaw(array), 200);
	}
	
	
	protected String arrayStringRaw(Object array)
	{
		if (!array.getClass().getComponentType().isPrimitive()) 
			return Arrays.deepToString((Object[])array);
		else if (array instanceof boolean[])
			return Arrays.toString((boolean[])array);
		else if (array instanceof byte[]) 
			return Arrays.toString((byte[])array);
		else if (array instanceof char[])
			return Arrays.toString((char[])array);
		else if (array instanceof double[])
			return Arrays.toString((double[]) array);
		else if (array instanceof float[])
			return Arrays.toString((float[]) array);
		else if (array instanceof int[])
			return Arrays.toString((int[])array);
		else if (array instanceof long[])
			return Arrays.toString((long[]) array);
		else if (array instanceof short[])
			return Arrays.toString((short[])array);
		else
			return array.toString();
	}
	
	
	protected String classString(Class<?> type) 
	{
		String canonicalName = type.getCanonicalName();
		return canonicalName != null ? canonicalName : type.getName();
	}
	
	
	protected String objectString(Object value) 
	{
		return shorten(objectStringRaw(value), 200);
	}

	
	protected String objectStringRaw(Object value) 
	{
		return value.toString();
	}

	
	public String formatQualified(Object value, String valueString)
	{
		if (value == null)
			return valueString;
		String hash = '@' + Integer.toHexString(System.identityHashCode(value));
		if (value instanceof Class)
			return valueString + hash;
		else
			return valueString + " (" + classString(value.getClass()) + hash + ')';
	}

	
	public String shorten(String s, int maxLength)
	{
		if (s.length() <= maxLength)
			return s;
		else
		{
			int half = Math.max(1, Math.round(maxLength / 2));
			return shorten(s.substring(0, half), s.substring(s.length() - half));
		}
	}
		
		
	public static String shorten(String prefix, String suffix)
	{
		return prefix + ELLIPSIS + suffix;
	}

	
	@Override public String toString()
	{
		return "default";
	}

	
	private static ValueFormat current_ = new ValueFormat();
}

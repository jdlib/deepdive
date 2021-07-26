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


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import deepdive.Context;


/**
 * Value provides static helper methods to handle tested values, especially
 * <ul>
 * <li>{@link #format(Object)} returns a string representation of a value which can
 * 	   be used in error messages
 * <li>{@link #equal(Object, Object)} returns if two objects are equal
 * <li>{@link #withinDelta(double, double, double)} and {@link #withinDelta(float, float, float)} return
 * 	   if two double or two floats differ within some delta  
 * </ul>
 */
public class Value
{
	/**
	 * Returns a descriptive string for the object, suitable to be used in an error message.
	 * @param value an object 
	 * @return the formatted object
	 */
	public static String format(Object value) 
	{
		return ValueFormat.get().format(value);
	}
		
		
	/**
	 * Formats two values using {@link #format(Object)}.
	 * If their string representation is equal they are further qualified by appending class
	 * and identity hash code.
	 * @param v1 the expected value
	 * @param v2 the expected value
	 * @return an array of length 2 containing the formatted strings
	 */
	public static Pair<String,String> format(Object v1, Object v2) 
	{
		ValueFormat format = ValueFormat.get();
		String s1 = format.format(v1);
		String s2 = format.format(v2);
		if (s1.equals(s2))
		{
			s1 = format.formatQualified(v1, s1);
			s2 = format.formatQualified(v2, s2);
		}
		return new Pair<>(s1, s2);
	}

	
	public static String toQualifiedString(Object value) 
	{
		ValueFormat format = ValueFormat.get();
		return format.formatQualified(value, format.format(value));
	}
	
	
	public static boolean withinDelta(double d1, double d2, double delta) 
    {
        return (Double.compare(d1, d2) == 0) || (Math.abs(d1 - d2) <= delta); 
    }
    

	public static boolean withinDelta(float f1, float f2, float delta) 
    {
        return (Float.compare(f1, f2) == 0) || (Math.abs(f1 - f2) <= delta); 
    }

	
	/**
	 * If the collection is not null and has size 1
	 * returns the only element else return the collection.
	 * @param col an collection
	 * @return null, the only element or the collection
	 */
	public static Object onlyOrCollection(Collection<?> col)
	{
		return (col != null) && (col.size() == 1) ? col.iterator().next() : col;
	}
	

	/**
	 * Returns if two objects are equal:
	 * <ul>
	 * <li>if both arguments are arrays, equality is determined by using {@link #arraysEqual(Object, Object, boolean)}. 
	 * <li>else equality is determined by using {@link Objects#equals(Object, Object)}
     * </ul>
     * @param o1 an object or null
     * @param o2 an object or null
     * @return are the objects equal
	 */
	public static boolean equal(Object o1, Object o2)
	{
		return isArray(o1) && isArray(o2) ? arraysEqual(o1, o2, true) : Objects.equals(o1, o2);
	}
	
	
	/**
	 * Returns if two arrays are equal.
	 * <ul>
	 * <li>if both arrays are null, true is returned 
	 * <li>if one arrays is null and the other is not null, false is returned 
	 * <li>else if compareComponentType is true else if both arrays are not null but have different component type false is returned
	 * <li>else equality is determined by using {@link Arrays#equals(Object[], Object[])} or the respective
	 * 		method for arrays with primitive component type
     * </ul>
     * Note that this test deviates from standard Java which regards to arrays
     * as equal if they refer to the same object.
     * @param a1 an array 
     * @param a2 an array
     * @param compareComponentType true if the compontent type of the arrays should match, else it is ignored
     * @return are the arrays equal?
	 * @throws IllegalArgumentException if an argument is not null but not an array
	 */
	public static boolean arraysEqual(Object a1, Object a2, boolean compareComponentType)
	{
		// make sure both args are null or an array
		checkArrayOrNull(a1, "arg1");
		checkArrayOrNull(a2, "arg2");
		
		// null edge cases
		if (a1 == a2)
			return true;
		else if ((a1 == null) || (a2 == null))
			return false;
		
		// now: both args are arrays
		
		Class<?> componentType = a1.getClass().getComponentType(); 
		if (componentType.isPrimitive() || compareComponentType)
		{
			// check same component type
			if (componentType != a2.getClass().getComponentType())
				return false;
		}
		
		// forward to Arrays.equals() 
		if (!componentType.isPrimitive())
			return Arrays.deepEquals((Object[])a1, (Object[])a2);
		else if (a1 instanceof boolean[])
			return Arrays.equals((boolean[])a1, (boolean[])a2);
		else if (a1 instanceof byte[]) 
			return Arrays.equals((byte[])a1, (byte[])a2);
		else if (a1 instanceof char[])
			return Arrays.equals((char[])a1, (char[])a2);
		else if (a1 instanceof double[])
			return Arrays.equals((double[])a1, (double[])a2);
		else if (a1 instanceof float[])
			return Arrays.equals((float[])a1, (float[])a2);
		else if (a1 instanceof int[])
			return Arrays.equals((int[])a1, (int[])a2);
		else if (a1 instanceof long[])
			return Arrays.equals((long[])a1, (long[])a2);
		else if (a1 instanceof short[])
			return Arrays.equals((short[])a1, (short[])a2);
		else
			throw new IllegalStateException(componentType.toString());
	}


	/**
	 * Returns if the given object is an array.
	 * @param object an object
	 * @return is it an array?
	 */
	public static boolean isArray(Object object)
	{
		return (object != null) && object.getClass().isArray();
	}


	private static void checkArrayOrNull(Object object, String what)
	{
		if ((object != null) && !isArray(object))
			throw new IllegalArgumentException(what + " is not an array: " + object);
	}
	
	
	/**
	 * Returns an Set from the given collection.
	 * @param col the collection
	 * @return the set or null if the vararg param is null
	 * @param <T> the element type
	 */
	public static <T> Set<T> collectionToSet(Collection<T> col)
	{
		if (col == null)
			return null;
		else if (col instanceof Set)
			return (Set<T>)col;
		else
		{
			// LinkedHashSet has predictable iteration order
			return new LinkedHashSet<>(col);
		}
	}

	
	/**
	 * Returns an Set from the given array.
	 * @param elems the elements
	 * @return the set or null if the vararg param is null
	 * @param <T> the element type
	 */
	@SafeVarargs
	public static <T> Set<T> arrayToSet(T... elems)
	{
		if (elems == null)
			return null;
		else
		{
			// LinkedHashSet has predictable iteration order
			Set<T> set = new LinkedHashSet<>();
			Collections.addAll(set, elems);
			return set;
		}
	}
	

	/**
	 * Returns an Set from the given array param which is either
	 * an Object array or primitive array
	 * @param array an array
	 * @return the set or null if the array param is null
	 */
	public static Set<?> arrayObjectToSet(Object array)
	{
		checkArrayOrNull(array, "array");
		if (array == null)
			return null;
		else
		{
			// LinkedHashSet has predictable iteration order
			Set<Object> set = new LinkedHashSet<>();
			int length = Array.getLength(array);
			for (int i=0; i<length; i++)
				set.add(Array.get(array, i));
			return set;
		}
	}

	
	/**
	 * Returns an iterator for the array elements.
	 * @param elems the elements
	 * @param <T> the element type
	 * @return the iterator
	 */
	@SafeVarargs
	public static <T> Iterator<T> arrayIterator(T... elems)
	{
		return new ArrayIt<>(Check.notNull(elems, "elems"));
	}
	
	
	/**
	 * Sorts a boolean array (There is no java.util.Arrays.sort(boolean[]).
	 * @param array a boolean array
	 */
	public static void arraySort(boolean[] array)
	{
		int falseCount = 0;
		for (int i=0; i<array.length; i++)
		{
			if (array[i])
				falseCount++;
		}
		if ((falseCount > 0) && (falseCount < array.length))
		{
			Arrays.fill(array, 0, falseCount, false);
			Arrays.fill(array, falseCount, array.length, true);
		}
	}

	
	private static class ArrayIt<T> implements Iterator<T>
	{
		public ArrayIt(T[] array)
		{
			array_ = array;
		}


		@Override public boolean hasNext()
		{
			return index_ < array_.length;
		}


		@Override public T next()
		{
			if (!hasNext())
				throw new NoSuchElementException(String.valueOf(index_));
			return array_[index_++];
		}
		
		
		@Override public String toString()
		{
			return Context.call("It", array_, Integer.valueOf(index_)).toString();
		}


		private final T[] array_;
		private int index_;
	}

	
	/**
	 * Returns true iif 0 &lt;= index &lt; size.
	 * @param index an index
	 * @param size a size
	 * @return is the index valid?
	 */
	public static boolean indexValid(int index, int size)
	{
		return  (index >= 0) && (index < size);
	}
}

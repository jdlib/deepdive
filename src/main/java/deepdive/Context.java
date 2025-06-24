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
package deepdive;


import java.util.function.Supplier;
import deepdive.actual.Actual;
import deepdive.impl.Value;


/**
 * A Context is a lightweight object used to provide a description string of the context of an assertion.
 * If the assertion fails the context string will be included in the error message.<p>
 * DeepDive methods allow to specify the assertion context in form of a CharSequence parameter
 * (e.g. {@link ExpectStatic#expectTrue(boolean, CharSequence)} or {@link Actual#as(CharSequence)}):<br>
 * String constants can be passed as context parameter with no construction overhead. For other
 * case the Context class provides factory methods to create Context objects for various sources.
 */
public abstract class Context implements CharSequence 
{
	/**
	 * Returns a Context which returns the supplier value when it is evaluated.
	 * @param supplier a supplier
	 * @return the Context object or null if the supplier is null
	 */
	public static Context ofSupplier(Supplier<String> supplier)
	{
		return supplier == null ? null : new Context() 
		{
			@Override protected String build()
			{
				return supplier.get();
			}
		};
	}
	
	
	/**
	 * Returns a Context which uses {@link Object#toString()} on the provided object to build its string.
	 * @param object an object or null
	 * @return the Context object
	 */
	public static Context ofObject(Object object)
	{
		return object == null ? null : new Context() 
		{
			@Override protected String build()
			{
				return object.toString();
			}
		};
	}

	
	/**
	 * Returns a Context object which builds a formatted string 
	 * using the format and params.
	 * @param format the format string
	 * @param params the parameters
	 * @return the Context object
	 */
	public static Context format(String format, Object... params)
	{
		return format == null ? null : new Context() 
		{
			@Override protected String build()
			{
				return String.format(format, params);
			}
		};
	}

	
	/**
	 * Returns a Context object which builds a string of the form "[&lt;index&gt;]".
	 * @param index the index value
	 * @return the Context object
	 */
	public static Context indexed(int index)
	{
		return indexed(null, index);
	}
	
	
	/**
	 * Returns a Context object which builds a string of the form "&lt;property&gt;[&lt;index&gt;]".
	 * @param property the indexed property
	 * @param index the index value
	 * @return the Context object
	 */
	public static Context indexed(CharSequence property, int index)
	{
		return new Context() 
		{
			@Override protected String build()
			{
				return toString(property) + '[' + index + ']';
			}
		};
	}
	
	
	/**
	 * Returns a Context object which builds a string of the form "&lt;property&gt;[&lt;index&gt;]".
	 * @param property the indexed property
	 * @param index the index value
	 * @return the Context object
	 */
	public static Context indexed(CharSequence property, Object index)
	{
		return new Context() 
		{
			@Override protected String build()
			{
				return toString(property) + '[' + index + ']';
			}
		};
	}

	
	/**
	 * Returns a Context object which builds a string of the form "&lt;name&gt;(&lt;param&gt;)".
	 * @param fn the called function
	 * @param param a parameter to the call
	 * @return the Context object
	 */
	public static Context call(CharSequence fn, char param)
	{
		return new Context() 
		{
			@Override protected String build()
			{
				return toString(fn) + '(' + param + ')';
			}
		};
	}

	
	/**
	 * Returns a Context object which builds a string of the form "&lt;name&gt;(&lt;param&gt;)".
	 * @param fn the called function
	 * @param param a parameter to the call
	 * @return the Context object
	 */
	public static Context call(CharSequence fn, int param)
	{
		return new Context() 
		{
			@Override protected String build()
			{
				return toString(fn) + '(' + param + ')';
			}
		};
	}

	
	/**
	 * Returns a Context object which builds a string of the form "&lt;name&gt;(&lt;param&gt;)".
	 * @param fn the called function
	 * @param param a parameter to the call
	 * @return the Context object
	 */
	public static Context call(CharSequence fn, Object param)
	{
		return new Context() 
		{
			@Override protected String build()
			{
				return toString(fn) + '(' + Value.format(param) + ')';
			}
		};
	}

	
	/**
	 * Returns a Context object which builds a string of the form "&lt;name&gt;(&lt;param0&gt;, &lt;param1&gt;...)".
	 * @param name the name of the called function
	 * @param params parameters to the call
	 * @return the Context object
	 */
	public static Context call(CharSequence name, Object... params)
	{
		return new Context() 
		{
			@Override protected String build()
			{
				StringBuilder s = new StringBuilder();
				s.append(toString(name)).append('(');
				for (int i=0; i<params.length; i++)
				{
					if (i > 0)
						s.append(", ");
					s.append(Value.format(params[i]));
				}
				return s.append(')').toString();
			}
		};
	}


	/**
	 * Returns a Context object which builds the concatenation of two contexts.
	 * @param c1 a context
	 * @param c2 another context
	 * @return the Context object
	 */
	public static Context concat(CharSequence c1, CharSequence c2)
	{
		return new Context() 
		{
			@Override protected String build()
			{
				String s1 = toString(c1);
				String s2 = toString(c2);
				if (s2.length() == 0)
					return s1;
				else if (s1.length() == 0)
					return s2;
				else
					return s1 + '.' + s2;
			}
		};
	}


	/**
	 * Implements {@link CharSequence#length()}.
	 */
	@Override public int length()
	{
		return toString().length();
	}


	/**
	 * Implements {@link CharSequence#charAt(int)}.
	 */
	@Override public char charAt(int index)
	{
		return toString().charAt(index);
	}


	/**
	 * Implements {@link CharSequence#subSequence(int, int)}.
	 */
	@Override public CharSequence subSequence(int start, int end)
	{
		return toString().subSequence(start, end);
	}
	
	
	/**
	 * Lazily builds the String.
	 * @return the string representation of this Context object.
	 */
	protected abstract String build();
	
	
	protected String toString(CharSequence s)
	{
		return s != null ? s.toString() : "";
	}
	
	
	@Override public synchronized String toString()
	{
		if (string_ == null)
		{
			string_ = build();
			if (string_ == null)
				string_ = "?";
		}
		return string_;
	}
	
	
	private String string_;
}

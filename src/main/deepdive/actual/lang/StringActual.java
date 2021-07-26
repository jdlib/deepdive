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
package deepdive.actual.lang;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import javax.annotation.CheckReturnValue;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.util.StringIteratorActual;
import deepdive.actual.util.StringListActual;
import deepdive.impl.Value;
import deepdive.impl.NotMustBeOff;


/**
 * An Actual implementation for {@link String} objects.
 * @param <BACK> the type of the owner of the StringActual
 * @param <IMPL> the type of the concrete StringActual implementation 
 */
public class StringActual<BACK,IMPL extends StringActual<BACK,IMPL>> extends Actual<String,BACK,IMPL>
	implements ComparableActual<String,IMPL>, CharSequenceActual<String,IMPL>
{
	private static final Pattern LINEBREAKS = Pattern.compile("(\\r\\n|\\r|\\n)");
	
	
	public static String[] splitLines(String s)
	{
		return s != null ? LINEBREAKS.split(s) : null;
	}

	
	/**
	 * Creates a new StringActual.
	 * @param value the value
	 * @param back the owner
	 */
	public StringActual(String value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Asserts that the code point at the given index equals the expected value.
	 * @param index the index
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL codePointAt(int index, int expected)
	{
		expectEqual(expected, value().codePointAt(index), Context.indexed("codePointAt", index));
		return self();
	}

	
	/**
	 * Asserts that the String contains the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL contains(CharSequence expected)
	{
		return expectTo(value().contains(expected), "contain", expected);
	}
	
	
	/**
	 * Asserts that the actual String ends with the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL endsWith(String expected)
	{
		return expectTo(value().endsWith(expected), "end with", expected);
	}
	
	
	/**
	 * Asserts that the actual String equals the expected value ignoring case
     * considerations (using {@link String#equalsIgnoreCase(String)}.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL equalsIgnoreCase(String expected)
	{
		return expectTo(value().equalsIgnoreCase(expected), "equal ignoring case", expected); 
	}

	
	/**
	 * Asserts that the actual String equals the formatted String using the given parameters.
	 * @param format a format string
	 * @param params params to the format call
	 * @return this
	 */
	public IMPL format(String format, Object... params)
	{
		expectEqual(String.format(format, params), value(), "format");
		return self();
	}
	
	
	/**
	 * Asserts that the index of the given char equals the expected value.
	 * @param c a character
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL indexOf(char c, int expected)
	{
		expectEqual(expected, value().indexOf(c), Context.call("indexOf", c));
		return self();
	}

	
	/**
	 * Asserts that the index of the given String equals the expected value.
	 * @param s a string
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL indexOf(String s, int expected)
	{
		expectEqual(expected, value().indexOf(s), Context.call("indexOf", s));
		return self();
	}
	
	
	/**
	 * Asserts that the given index is valid, e.g. is in the range of [0, String.length[.
	 * @param index the index
	 * @return this
	 */
	public IMPL indexValid(int index)
	{
		expectIndexValid(index, value().length());
		return self();
	}

	
    /**
	 * Asserts that the String is in lower case.
	 * @return this
	 */
	public IMPL isLowerCase()
	{
		String s = value();
		String l = s.toLowerCase();
		return expectTo(s.equals(l), "be in lower case", l); 
	}

	
    /**
	 * Asserts that the String is in upper case.
	 * @return this
	 */
	public IMPL isUpperCase()
	{
		String s = value();
		String u = s.toUpperCase();
		return expectTo(s.equals(u), "be in upper case", u); 
	}

	
	/**
	 * Asserts that the last index of the given char equals the expected value.
	 * @param c a character
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL lastIndexOf(char c, int expected)
	{
		expectEqual(expected, value().lastIndexOf(c), Context.call("lastIndexOf", c));
		return self();
	}

	
	/**
	 * Asserts that the last index of the given String equals the expected value.
	 * @param s a string
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL lastIndexOf(String s, int expected)
	{
		expectEqual(expected, value().lastIndexOf(s), Context.call("lastIndexOf", s));
		return self();
	}
	
	
	/**
	 * Asserts that the String is "like" the given pattern.
	 * @param expectedPattern a String containing wildcard ? and *-
	 * @return this
	 */
	public IMPL like(String expectedPattern)
	{
		StringTokenizer st = new StringTokenizer(expectedPattern, "*?", true);
		StringBuffer sb = new StringBuffer(); 
		while (st.hasMoreElements())
		{
			String token = st.nextToken();
			if ("*".equals(token))
				sb.append(".*");
			else if ("?".equals(token)) 
				sb.append(".");
			else
				sb.append(Pattern.quote(token));
		}
		Pattern p = Pattern.compile(sb.toString());
		return expectTo(p.matcher(value()).matches(), "be like", expectedPattern);
	}
	
	
	/**
	 * Asserts that the String matches the given pattern.
	 * @param pattern a pattern
	 * @return this
	 */
	public IMPL matches(String pattern)
	{
		return matches(Pattern.compile(pattern));
	}


	/**
	 * Asserts that the String matches the given pattern.
	 * @param pattern a pattern
	 * @return this
	 */
	public IMPL matches(Pattern pattern)
	{
		return expectTo(pattern.matcher(value()).matches(), "match pattern", pattern);
	}

	
	/**
	 * Asserts that the String equals the expected String, when the oldChar is replaced by newChar.
	 * @param oldChar the old char
	 * @param newChar the new char
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL replace(char oldChar, char newChar, String expected)
	{
		expectEqual(expected, value().replace(oldChar, newChar), "replace");
		return self();
	}
	
	
	/**
	 * A Builder class to define how a String should be split.
	 */
	public interface Split<B>
	{
		/**
		 * Instructs the Split to use the given regex.
		 * @param regex a regex string
		 * @return a Split2 builder to proceed the split
		 */
		@CheckReturnValue
		public Split2<B> by(String regex);
		 
		
		/**
		 * Instructs the Split to use the given pattern.
		 * @param pattern a pattern
		 * @return a Split2 builder to proceed the split
		 */
		@CheckReturnValue
		public Split2<B> by(Pattern pattern);
		
		
		/**
		 * Instructs the Split to split into lines.
		 * @return a Split2 builder to proceed the split
		 */
		@CheckReturnValue
		public default Split2<B> byLines()
		{
			return by(LINEBREAKS);
		}
	}
	
	
	/**
	 * A Builder class to define how a String should be split.
	 */
	public interface Split2<B>
	{
		/**
		 * Limits the split.
		 * @param value a upper limit
		 * @return this builder
		 */
		public Split2<B> limit(int value);

	
		public B elems(String... expected);

		
		public StringIteratorActual<B,?> iterator();

		
		public StringArrayActual<B,?> toArray();
		
		
		public StringListActual<?,B,?> toList();
	}
	
	
	protected class SplitImpl<B> implements Split<B>, Split2<B>
	{
		@NotMustBeOff
		protected SplitImpl(B back)
		{
			self();
			back_ = back;
		}
		
		
		@Override public Split2<B> by(String regex)
		{
			regex_ = rejectNull(regex, "regex");
			return this;
		}
		
		
		@Override public Split2<B> by(Pattern pattern)
		{
			pattern_ = rejectNull(pattern, "pattern");
			return this;
		}
		
		
		@Override public B elems(String... expected)
		{
			toArray().equal(expected);
			return back_;
		}

		
		@Override public StringIteratorActual<B,?> iterator()
		{
			return new StringIteratorActual<>(Value.arrayIterator(applySplit()), back_).as(context());
		}

		
		@Override public StringArrayActual<B,?> toArray()
		{
			return new StringArrayActual<>(applySplit(), back_).as(context());
		}
		
		
		@Override public StringListActual<?,B,?> toList()
		{
			List<String> list = new ArrayList<>();
			Collections.addAll(list, applySplit());
			return new StringListActual<>(list, back_).as(context());
		}
		
		
		@Override public Split2<B> limit(int value)
		{
			limit_ = value > 0 ? value : 0;
			return this;
		}
		
		
		private String[] applySplit()
		{
			String s = value();
			return regex_ != null ? s.split(regex_, limit_) : pattern_.split(s, limit_);
		}
		
		
		private CharSequence context()
		{
			Object arg1 = regex_ != null ? regex_ : pattern_;
			return limit_ > 0 ? Context.call("split", arg1, Integer.valueOf(limit_)) : Context.call("split", value(), arg1);
		}
		
		
		private final B back_;
		private String regex_;
		private Pattern pattern_;
		private int limit_;
	}
	 
	
	/**
	 * Returns a Split builder to split the string.
	 * @return the builder
	 */
	@CheckReturnValue
	public Split<IMPL> split()
	{
		return new SplitImpl<>(self());
	}

	
	/**
	 * Asserts that the actual String starts with the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL startsWith(String expected)
	{
		return expectTo(value().startsWith(expected), "start with", expected);
	}


	/**
	 * Asserts that the substring starting at beginIndex equals the expected value.
	 * @param beginIndex the begin index
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL substring(int beginIndex, String expected)
	{
		expectEqual(expected, value().substring(beginIndex), Context.call("substring", beginIndex));
		return self();
	}


	/**
	 * Asserts that the substring starting at beginIndex and ending at endIndex equals the expected value.
	 * @param beginIndex the begin index
	 * @param endIndex the end index
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL substring(int beginIndex, int endIndex, String expected)
	{
		expectEqual(expected, value().substring(beginIndex, endIndex), Context.call("substring", beginIndex));
		return self();
	}
	
	
	/**
	 * Returns a StringActual for the substring.
	 * @param beginIndex the begin index
	 * @param endIndex the end index
	 * @return the new actual
	 */
	@SuppressWarnings("boxing")
	public StringActual<IMPL,?> substring(int beginIndex, int endIndex)
	{
		return new StringActual<>(value().substring(beginIndex, endIndex), self())
			.as(Context.call("substring", beginIndex, endIndex));
	}

	
	@CheckReturnValue
    public SwitchTo switchTo()
    {
    	return new SwitchTo();
    }

    
    public class SwitchTo
    {
		protected SwitchTo()
		{
		}
		
		
		/**
		 * Returns a Split builder to split the string.
		 * @return the builder
		 */
		@CheckReturnValue
		public Split<BACK> split()
		{
			return new SplitImpl<>(backOrNull());
		}
    }
    
    
    /**
	 * Asserts that the lower case String equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL toLowerCase(String expected)
	{
		expectEqual(value().toLowerCase(), expected, "toLowerCase");
		return self();
	}

	
	/**
	 * Returns a StringActual for the lower case String.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> toLowerCase()
	{
		return new StringActual<>(value().toLowerCase(), self()).as("toLowerCase");
	}


	/**
	 * Asserts that the upper case String equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL toUpperCase(String expected)
	{
		expectEqual(expected, value().toUpperCase(), "toUpperCase");
		return self();
	}


	/**
	 * Returns a StringActual for the upper case String.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> toUpperCase()
	{
		return new StringActual<>(value().toUpperCase(), self()).as("toUpperCase");
	}
}

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
package deepdive.actual.java.util.regex;


import java.util.regex.Matcher;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.java.lang.StringActual;


/**
 * An Actual implementation for {@link Matcher}.
 */
public class MatcherActual<BACK,IMPL extends MatcherActual<BACK,IMPL>> extends Actual<Matcher,BACK,IMPL>
{
	/**
	 * Creates a new MatcherActual.
	 * @param value the value.
	 * @param back the owner
	 */
	public MatcherActual(Matcher value, BACK back)
	{
		super(value, back);
	}
	
	
	public IMPL groupCount(int expected)
	{
		expectEqual(expected, value().groupCount(), "groupCount");
		return self();
	}

	
	public MatchResultActual groupResult(int i)
	{
		if (i < 0)
			fail("invalid group index " + i);
		return new MatchResultActual(i);
	}

	
	/**
	 * Returns the result of the last find operation.
	 * @return a MatchResultActual to test the result
	 */
	public MatchResultActual result()
	{
		return groupResult(0);
	}

	
	public IMPL find()
	{
		expectTrue(value().find(), "find");
		return self();
	}

	
	public IMPL find(int start)
	{
		expectTrue(value().find(start), Context.call("find", start));
		return self();
	}
	
	
	public IMPL hitEnd()
	{
		expectTrue(value().hitEnd(), "hitEnd");
		return self();
	}

	
	public IMPL matches()
	{
		expectTrue(value().matches(), "matches");
		return self();
	}


	public IMPL lookingAt()
	{
		expectTrue(value().lookingAt(), "lookingAt");
		return self();
	}
	
	
	/**
	 * Resets the matcher with a new input sequence.
	 * @param input the input
	 * @return this
	 */
	public IMPL reset(CharSequence input)
	{
		value().reset(input);
		return self();
	}
	
	
	/**
	 * Sets the region of the matcher.
	 * @param start start of region
	 * @param end end of region
	 * @return this
	 */
	public IMPL setRegion(int start, int end)
	{
		value().region(start, end);
		return self();
	}
	
	
	/**
	 * Tells the matcher to use transparent bounds.
	 * @param value the new value
	 * @return this
	 */
	public IMPL setUseAnchoringBounds(boolean value)
	{
		value().useAnchoringBounds(value);
		return self();
	}
	
	
	/**
	 * Tells the matcher to use transparent bounds.
	 * @param value the new value
	 * @return this
	 */
	public IMPL setUseTransparentBounds(boolean value)
	{
		value().useTransparentBounds(value);
		return self();
	}

	
	public StringActual<IMPL,?> replaceAll(String replacement)
	{
		return new StringActual<>(value().replaceAll(replacement), self()).as(Context.call("replaceAll", replacement));
	}
	
	
	public StringActual<IMPL,?> replaceFirst(String replacement)
	{
		return new StringActual<>(value().replaceFirst(replacement), self()).as(Context.call("replaceFirst", replacement));
	}


	public IMPL requireEnd()
	{
		expectTrue(value().requireEnd(), "requireEnd");
		return self();
	}


	public class MatchResultActual
	{
		private MatchResultActual(int group)
		{
			group_ = group;
			last_  = group == 0;
		}
		
		
		private CharSequence context(String what)
		{
			return last_ ? what : Context.call(what, group_);
		}
		
		
		public MatchResultActual end(int expected)
		{
			Matcher m = MatcherActual.this.value();
			expectEqual(expected, last_ ? m.end() : m.end(group_), context("end")); 
			return this;
		}
		
		
		public MatchResultActual start(int expected)
		{
			Matcher m = MatcherActual.this.value();
			expectEqual(expected, last_ ? m.start() : m.start(group_), context("start")); 
			return this;
		}
		
		
		public MatchResultActual value(String expected)
		{
			Matcher m = MatcherActual.this.value();
			expectEqual(expected, last_ ? m.group() : m.group(group_), context("group")); 
			return this;
		}
		
		
		public MatchResultActual not()
		{
			MatcherActual.this.not();
			return this;
		}
		
		
		public IMPL back()
		{
			return MatcherActual.this.self();
		}
		
		
		private final int group_;
		private final boolean last_;
	}
}

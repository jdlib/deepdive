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
package deepdive.actual.util.regex;


import java.util.regex.Pattern;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.lang.StringArrayActual;


/**
 * An Actual implementation for {@link Pattern}.
 */
public class PatternActual<BACK,IMPL extends PatternActual<BACK,IMPL>> extends Actual<Pattern,BACK,IMPL>
{
	/**
	 * Creates a new PatternActual.
	 * @param value the value.
	 * @param back the owner
	 */
	public PatternActual(Pattern value, BACK back)
	{
		super(value, back);
	}
	
	
	public IMPL flags(int expected)
	{
		expectEqual(expected, value().flags(), "flags");
		return self();
	}
	
	
	public MatcherActual<IMPL,?> matcher(CharSequence input)
	{
		return new MatcherActual<>(value().matcher(input), self()).as(Context.call("matcher", input));
	}
	
	
	public IMPL matches(CharSequence expected)
	{
		expectTo(value().matcher(expected).matches(), "match", expected);
		return self();
	}
	
	
	public StringArrayActual<IMPL,?> split(CharSequence input)
	{
		return split(input, 0, "split");
	}
	
	
	public StringArrayActual<IMPL,?> split(CharSequence input, int limit)
	{
		return split(input, limit, Context.call("split", Integer.valueOf(limit)));
	}


	private StringArrayActual<IMPL,?> split(CharSequence input, int limit, CharSequence context)
	{
		return new StringArrayActual<>(value().split(input, limit), self()).as(context);
	}
}

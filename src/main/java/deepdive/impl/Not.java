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


/**
 * Not represents the Not-mode of an assertion evaluation.
 * An assertion yields a basic boolean result (e.g. are two values equal?).
 * If Not is OFF the final assertion result is that basic result.
 * If Not is ON the final assertion result is negated basic result.
 */
public enum Not
{
	ON(true),
	OFF(false);
	
	
	Not(boolean on)
	{
		on_ = on;
	}

	
	public boolean apply(boolean flag)
	{
		return on_ ? !flag : flag; 
	}
	
	
	public boolean isOn()
	{
		return on_;
	}
	
	
	public boolean isOff()
	{
		return !isOn();
	}

	
	public Not other()
	{
		return this == ON ? OFF : ON;
	}
	
	
	public String qualifiedString()
	{
		return "Not." + name();
	}
	
	
	/**
	 * A class to hold a Not object and allows to get, set and toggle it.
	 */
	public static class Holder
	{
		/**
		 * Toogles not mode.
		 */
		public void toggle()
		{
			not_ = not_.other();
		}
		
		
		public Not get()
		{
			return not_;
		}
		
		
		public void set(Not not)
		{
			not_ = Check.notNull(not, "not");
		}
		
		
		public void reset()
		{
			set(Not.OFF);
		}
		
		
		public Not getAndClear()
		{
			Not not = not_;
			if (not.isOn())
				not_ = Not.OFF;
			return not;
		}
		
		
		private Not not_ = Not.OFF;
	}
	
	
	private final boolean on_;
}

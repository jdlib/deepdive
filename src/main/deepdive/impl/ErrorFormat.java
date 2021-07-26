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


import java.util.List;


/**
 * ErrorFormat is a service to build an assert error message from
 * a list of context strings and a list of statements.
 * {@link #get()} returns the currently used ErrorFormat.
 * If you want to adjust the ErrorFormat, create a derived class
 * and set it via {@link #set(ErrorFormat)}.  
 */
public class ErrorFormat
{
	/**
	 * Returns the currently active ErrorFormat. 
	 * @return the ErrorFormat
	 * @see #set(ErrorFormat)
	 */
	public static ErrorFormat get()
	{
		return current_;
	}
	
	
	/**
	 * Sets the current ErrorFormat.
	 * @param value the new ErrorFormat
	 * @see #get() 
	 */
	public static void set(ErrorFormat value)
	{
		current_ = Check.notNull(value, "value");
	}
	

	/**
	 * Builds the error message.
	 * @param contexts a list of context strings
	 * @param stmts a list of Stmts
	 * @return the message
	 */
	public String buildMsg(List<CharSequence> contexts, List<Stmt> stmts)
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<contexts.size(); i++)
		{
			if (i > 0)
				sb.append('.');
			sb.append(contexts.get(i));
		}
		
		Stmt.formatAll(stmts, sb);
		return sb.toString();
	}


	@Override public String toString()
	{
		return "default";
	}

	
	private static ErrorFormat current_ = new ErrorFormat();
}

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
import java.util.function.Consumer;
import deepdive.Failure;


/**
 * Stmt is the basic building block of an assertion error message to describe
 * what went wrong: The assertion failure, represented by a {@link Failure} object,
 * can contain multiple Stmt objects besides carrying information about
 * the context of the failing assertion.<p>
 * In order to streamline error messages for information and readability, each Stmt
 * is printed on a separated line and is limited to a certain format: It consists
 * either of text string, e.g.:<br>
 * <code>expected to be null</code>
 * or a text string and a value separated by a colon, e.g.:
 * <code>expected to be: 27</code>
 * @see Failure#addStmt(Stmt) 
 */
public class Stmt
{
	static final Object NONE_VALUE = new Object();

	
	public static StringBuilder formatAll(List<Stmt> stmts, StringBuilder sb)
	{
		formatAll(stmts, s -> {
			if (sb.length() > 0)
				sb.append('\n');
			sb.append(s);
		});
		return sb;
	}
	
	
	public static void formatAll(List<Stmt> stmts, Consumer<String> out)
	{
		int textSpan = -1;
		for (int i=0; i<stmts.size(); i++)
		{
			Stmt stmt = stmts.get(i);
			if (!stmt.hasValue())
			{
				out.accept(stmt.text);
				textSpan = -1;
			}
			else
			{
				if (textSpan < 0)
					textSpan = calcTextSpan(stmts, i);
				out.accept(stmt.format(textSpan));
			}
		}
	}
	
	
	private static int calcTextSpan(List<Stmt> stmts, int i)
	{
		int wd = 0;
		for (; i<stmts.size(); i++)
		{
			Stmt stmt = stmts.get(i);
			if (!stmt.hasValue())
				break;
			wd = Math.max(wd, stmt.text.length());
		}
		return wd;
	}
	

	public static Stmt title(String text)
	{
		return new Stmt(text);
	}
	
	
	public Stmt(String text)
	{
		this(text, NONE_VALUE);
	}
	
	
	public Stmt(String text, Object value)
	{
		this.text 	= text != null ? text : "";
		this.value 	= value;
	}
	
	
	public boolean hasValue()
	{
		return value != NONE_VALUE;
	}
	
	
	public String format(int textSpan)
	{
		if (!hasValue())
			return text;
		
		StringBuilder sb = new StringBuilder();
		sb.append(text);
		textSpan -= text.length();
		while(textSpan-- > 0)
			sb.append(' ');
		sb.append(": ").append(Value.format(value));
		return sb.toString();
	}
	
	
	@Override public String toString()
	{
		return format(0);
	}
	
		
	public final String text;
	public final Object value;
}

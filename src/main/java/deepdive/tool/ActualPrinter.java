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
package deepdive.tool;


import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Parameter;


class ActualPrinter implements Closeable 
{
	public ActualPrinter(Writer writer)
	{
		writer_ = writer;
	}
	
	
	public ActualPrinter p(String s) throws IOException
	{
		if (s != null)
		{
			printIndent();
			writer_.write(s);
		}
		return this;
	}

	
	public ActualPrinter p(String format, Object... params) throws IOException
	{
		return p(String.format(format, params));
	}

	
	public ActualPrinter pwhen(boolean condition, String s) throws IOException
	{
		if (condition)
			p(s);
		return this;
	}

	
	public ActualPrinter p(Sep sep) throws IOException
	{
		if (sep.apply)
			p(sep.text);
		else
			sep.apply = true;
		return this;
	}

	
	public ActualPrinter p(char c) throws IOException
	{
		printIndent();
		writer_.write(c);
		return this;
	}
	
	
	public ActualPrinter pquoted(String s) throws IOException
	{
		return p('"').p(s).p('"');
	}
	
	
	public ActualPrinter pcontext(String propName, Parameter[] params) throws IOException
	{
		p(", ");
		if ((params == null) || (params.length == 0))
			pquoted(propName);
		else
		{
			p("Context.call(").pquoted(propName);
			for (Parameter param : params)
				p(", ").p(param.getName());
			p(')');
		}
		return this;
	}
	
	
	public ActualPrinter pvalue(String memberName, Parameter[] params) throws IOException
	{
		p("value().").p(memberName);
		if (params != null)
		{
			p('(');
			Sep sep = new Sep(", ");
			if (params.length > 0)
			{
				for (Parameter param : params)
					p(sep).p(param.getName());
			}
			p(')');
		}
		return this;
	}

	
	private void printIndent() throws IOException
	{
		if (printIndent_)
		{
			printIndent_ = false;
			for (int i=0; i<indent_; i++)
				writer_.write('\t');
		}
	}

	
	public void ln() throws IOException
	{
		writer_.write(linesep_);
		printIndent_ = true;
	}


	public void ln(int n) throws IOException
	{
		while(n-- > 0)
			ln();
	}
	
	
	public void flush() throws IOException
	{
		writer_.flush();
	}
	
	
	@Override public void close() throws IOException
	{
		writer_.close();
	}
	
	
	public ActualPrinter startBlock() throws IOException
	{
		p('{').ln();
		indent_++;
		return this;
	}
	
	
	public ActualPrinter endBlock() throws IOException
	{
		indent_--;
		p('}').ln();
		return this;
	}
	
	
	public static class Sep
	{
		public Sep(String text)
		{
			this.text = text;
		}
		

		public final String text; 
		private boolean apply;
	}

	
	private final Writer writer_;
	private boolean printIndent_;
	private int indent_;
	private final String linesep_ = System.lineSeparator();
}

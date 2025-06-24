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


import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.tool.ActualPrinter.Sep;


/**
 * ActualGenerator is a command line tool to create an initial
 * Actual implementation for a class.<p>
 * Usage:<br>
 * <code>java deepdive.tool.ActualGenerator &lt;class&gt;</code>
 */
public class ActualGenerator
{
	public ActualGenerator(Class<?> type)
	{
		type_ 		= type;
		simpleName_ = type.getSimpleName();
		package_ 	= getPackage(type);
		actualName_	= simpleName_ + "Actual";
		
		addImport(Actual.class);
		for (Method m : type.getDeclaredMethods())
		{
			if (includeMember(m) && includeMethod(m))
			{
				entries_.add(new Entry(m));
				addImport(m.getReturnType());
				Parameter[] params = m.getParameters(); 
				for (Parameter param : params)
					addImport(param.getType());
				if (params.length >= (m.getReturnType() == Boolean.TYPE ? 2 : 1))
					addImport(Context.class);
			}
		}
		for (Field f : type.getDeclaredFields())
		{
			if (includeMember(f))
			{
				entries_.add(new Entry(f));
				addImport(f.getType());
			}
		}
		entries_.sort(null);
	}
	
	
	private boolean includeMember(Member member)
	{
		if (member.getDeclaringClass() != type_)
			return false;
				
		int m = member.getModifiers();
		return Modifier.isPublic(m) && !Modifier.isStatic(m);
	}
	
	
	private boolean includeMethod(Method method)
	{
		Class<?> returnType = method.getReturnType();
		return !isOverrriden(method) && 
			!method.getName().startsWith("set") &&
			(returnType != Void.TYPE) &&
			!returnType.isAssignableFrom(type_);	
	}
	
	
	private void addImport(Class<?> type)
	{
		if (!type.isPrimitive())
		{
			String p = getPackage(type);
			if (!p.equals("java.lang") && !p.equals(package_))
				imports_.add(type.getName());
		}
	}
	
	
	public void print(OutputStream out) throws IOException
	{
		print(new OutputStreamWriter(out, StandardCharsets.UTF_8));
	}
	
	
	public void print(Writer out) throws IOException
	{
		print(new ActualPrinter(out));
	}

	
	private void print(ActualPrinter out) throws IOException
	{
		out.p("package %s;", package_).ln();
		out.ln(2);
		if (!imports_.isEmpty())
		{
			for (String imp : imports_)
				out.p("import %s;", imp).ln();
			out.ln(2);
		}
		
		// class-decl
		out.p("public class ").p(actualName_).p("<");
		String typeParam;
		String implName;
		boolean isFinalType = Modifier.isFinal(type_.getModifiers()); 
		// <T>
		if (isFinalType)
			typeParam = simpleName_;
		else
		{
			typeParam = "T";
			out.p(typeParam).p(" extends ").p(simpleName_).p(',');
		}
		// <BACK>
		out.p("BACK");
		
		if (isFinalType)
			implName = actualName_ + "<BACK>";
		else
		{
			implName = "IMPL";
			out.p(",%s extends %s<%s,BACK,%s>", implName, actualName_, typeParam, implName);
		}
		out.p("> extends Actual<%s,BACK,%s>", typeParam, implName).ln();
		out.startBlock();
		// ctor
		out.p("public %s(%s value, BACK back)", actualName_, typeParam).ln();
		out.startBlock();
		out.p("super(value, back);").ln();
		out.endBlock();
		for (Entry entry : entries_)
		{
			out.ln(2);
			printEntry(out, implName, entry);
		}
		out.endBlock();
		out.flush();
	}
	
	
	private void printEntry(ActualPrinter out, String implName, Entry entry) throws IOException
	{
		if (entry.field != null)
		{
			Field f = entry.field;
			printAssertionMethod(out, implName, entry.name, f.getType(), f.getName(), null);
		}
		else
		{
			Method m = entry.method;
			printAssertionMethod(out, implName, entry.name, m.getReturnType(), m.getName(), m.getParameters());
		}
	}
	
	
	private void printAssertionMethod(ActualPrinter out, String implName, String propName, Class<?> propType, String memberName, Parameter[] params) throws IOException
	{
		boolean isBoolean = propType == Boolean.TYPE;
		boolean isDecimal = (propType == Double.TYPE) || (propType == Float.TYPE);
		boolean hasExpectedValue = !isBoolean;
		boolean hasParams = (params != null) && (params.length > 0); 
		
		out.p("public %s %s(", implName, propName);
		Sep sep = new Sep(", ");
		if (hasParams)
		{
			for (Parameter param : params)
				out.p(sep).p("%s %s", param.getType().getSimpleName(), param.getName());
		}
		if (hasExpectedValue)
		{
			out.p(sep).p(propType.getSimpleName()).p(" expected");
			if (isDecimal)
				out.p(sep).p(propType.getSimpleName()).p(" delta");
		}
		out.p(')').ln();
		out.startBlock();
		
		if (isBoolean)
		{
			if (hasParams && params.length == 1)
			{
				out.p("return expectTo(").pvalue(memberName, params);
				out.pcontext(propName, null /*drop*/);
				out.p(", ").p(params[0].getName()).p(");").ln();
			}
			else
			{
				out.p("expectTrue(").pvalue(memberName, params);
				out.pcontext(propName, null /*drop*/).p(");").ln();
				out.p("return self();").ln();
			}
		}
		else
		{
			out.p("expect").p(propType.isEnum() ? "Same" : "Equal").p("(expected, ").pvalue(memberName, params);
			if (isDecimal)
				out.p(", delta");
			out.pcontext(propName, params).p(");").ln();
			out.p("return self();").ln();
		}
		
		out.endBlock();
		out.flush();
	}
	
	
	private static boolean isOverrriden(Method method) 
	{
	    Class<?> declaringClass = method.getDeclaringClass();
	    if (declaringClass.equals(Object.class)) 
	        return false;
	    
	    if (declaresSameMethod(declaringClass.getSuperclass(), method))
	    	return true;
	    
        for (Class<?> iface : declaringClass.getInterfaces()) 
        {
    	    if (declaresSameMethod(iface, method))
    	    	return true;
        }
        return false;
	}	
	
	
	private static boolean declaresSameMethod(Class<?> type, Method method)
	{
	    try 
	    {
	        type.getMethod(method.getName(), method.getParameterTypes());
	        return true;
	    } 
	    catch (NoSuchMethodException e)
	    {
	    	return false;
	    }
	}

	
	private static String getPackage(Class<?> type)
	{
		String name = type.getName();
		int n 		= name.lastIndexOf('.');
		return n < 0 ? name : name.substring(0, n);
	}
	
	
	private static class Entry implements Comparable<Entry>
	{
		public Entry(Field field) 
		{
			this.field 	= field;
			this.method = null;
			this.name 	= field.getName();
		}

	
		public Entry(Method method)
		{
			this.field 	= null;
			this.method = method;
			
			String name = method.getName();
			if (name.startsWith("get") && (name.length() > 3))
			{
				name = name.length() > 5 ?
					Character.toLowerCase(name.charAt(3)) + name.substring(4) :
					name.substring(3).toLowerCase();
			}

			this.name = name;
		}
		
		
		@Override public int compareTo(Entry o)
		{
			return name.compareTo(o.name);
		}
		
		
		public final Field field;
		public final Method method;
		public final String name;
	}
	
	
	private static void printHelp()
	{
		System.out.println("Usage: java " + ActualGenerator.class.getName() + " <class-name>");
	}
	
	
	public static void main(String[] args) throws Exception
	{
		if (args.length == 0)
		{
			printHelp();
			return;
		}
		
		new ActualGenerator(Class.forName(args[0])).print(System.out);
	}
	
	
	private final Class<?> type_;
	private final String package_;
	private final String simpleName_;
	private final String actualName_;
	private final List<Entry> entries_ = new ArrayList<>();
	private final TreeSet<String> imports_ = new TreeSet<>();
}

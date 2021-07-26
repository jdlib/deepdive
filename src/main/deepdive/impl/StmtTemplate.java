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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import deepdive.ExpectStatic;
import deepdive.Failure;
import deepdive.actual.lang.ComparableActual;


/**
 * StmtTemplate allows to easily produce Stmt objects to be used in
 * {@link Failure#addStmt(Stmt) assertion failure messages}
 * based the following variables:
 * <ul>
 * <li>the actual value
 * <li>the expected value
 * <li>the {@link Not} active in the assertion
 * <li>an optional parameter named <i>what</i>
 * </ul>
 * @see Failure#addStmt(Stmt)
 * @see Failure#addStmts(StmtTemplate, Object, Object, Not, String)
 */
public abstract class StmtTemplate
{
	/**
	 * A StmtTemplate which shows "but was: &lt;actual&gt;" but only when Not was OFF.
	 */
	public static final StmtTemplate BUT_WAS_VACT = constant("but was", StmtValue.ACTUAL).when(Not.OFF);
	

	/**
	 * A StmtTemplate to build failure messages for 
	 * test if an actual value is blank, i.e. is either null or has size/length 0.
	 * Uses {@link Input#actual}, {@link Input#not}.
	 */
	public static final StmtTemplate ASSERT_BLANK = multi(
		wildcard("expected $not to be null or empty", StmtValue.NONE),
		constant("but was", StmtValue.ACTUAL));
	
	
	/**
	 * A StmtTemplate to build failure messages for 
	 * {@link ExpectStatic#expectSame(Object, Object) assertSame} and 
	 * {@link ExpectStatic#not() not.assertSame}.<br>
	 * Uses {@link Input#actual}, {@link Input#expected}, {@link Input#not}.
	 */
	public static final StmtTemplate ASSERT_SAME = multi(
		wildcard("expected $not same as", StmtValue.EXPECTED), 
		BUT_WAS_VACT);
	
	/**
	 * A StmtTemplate to build failure messages for 
	 * {@link ExpectStatic#expectInstance(Class, Object) assertInstance} and 
	 * {@link ExpectStatic#not() not.assertInstance}.<br>
	 * Uses {@link Input#actual}, {@link Input#expected}, {@link Input#not}.
	 */
	public static final StmtTemplate ASSERT_INSTANCE = multi(
		wildcard("expected $not instance of", StmtValue.EXPECTED),
		constant("but was instance of", StmtValue.ACTUAL_CLASS).when(Not.OFF),
		constant("with value", StmtValue.ACTUAL));
	
	/**
	 * A StmtTemplate to build failure messages for 
	 * {@link ExpectStatic#expectNull(Object) assertNull} and 
	 * {@link ExpectStatic#not() not.assertNull}.<br>
	 * Uses {@link Input#actual}, {@link Input#not}.
	 */
	public static final StmtTemplate ASSERT_NULL = multi(
		wildcard("expected $not to be", StmtValue.NULL),
		BUT_WAS_VACT);
	
	/**
	 * A StmtTemplate to build failure messages for 
	 * {@link ExpectStatic#expectNotNull(Object) assertNotNull} and 
	 * {@link ExpectStatic#not() not.assertNotNull}.<br>
	 * Uses {@link Input#actual}, {@link Input#expected}, {@link Input#not}.
	 */
	public static final StmtTemplate ASSERT_NOT_NULL = ASSERT_NULL.toggleNot();

	/**
	 * A StmtTemplate to build failure messages for comparisons
	 * (e.g. used by {@link ComparableActual}.
	 * Uses {@link Input#actual}, {@link Input#expected}, {@link Input#not}.
	 */
	public static final StmtTemplate ASSERT_COMPARE = multi(
		wildcard("expected $not to be $what than", StmtValue.EXPECTED),
		BUT_WAS_VACT);

	/**
	 * A StmtTemplate to build failure messages for failed expectations
	 * Uses {@link Input#expected}, {@link Input#not}, {@link Input#what}.
	 */
	public static final StmtTemplate ASSERT_EXPECTED_TO = wildcard("expected $not to $what", StmtValue.EXPECTED);

	/**
	 * A StmtTemplate to build failure messages for failed expectations with
	 * Not {@link Not#other() toggled}.
	 */
	public static final StmtTemplate ASSERT_EXPECTED_TO_TOGGLEDNOT = ASSERT_EXPECTED_TO.toggleNot();
	
	/**
	 * Returns a StmtTemplate which creates a constant Stmt with the given text and without value.
	 * @param text a text
	 * @return the new template
	 */
	public static StmtTemplate constant(String text)
	{
		return new ConstantTemplate(text, StmtValue.NONE);
	}

	
	/**
	 * Returns a StmtTemplate which creates a Stmt with the given constant text.
	 * and a value part defined by StmtValue parameter.
	 * @param text a text
	 * @param value a StmtValue
	 * @return the new template
	 */
	public static StmtTemplate constant(String text, StmtValue value)
	{
		return new ConstantTemplate(text, value);
	}

	
	/**
	 * Returns a StmtTemplate which creates a Stmt which is based on the given
	 * text containing wildcards $not and/or $what.
	 * Wildcard $not is replaced with <code>not</code> if Not was ON, else replaced with an empty string.  
	 * Wildcard $what is replaced by {@link Input#what}
	 * @param text a text which can contain the wildcards $not and $what.
	 * @param value a StmtValue
	 * @return the new template
	 */
	public static StmtTemplate wildcard(String text, StmtValue value)
	{
		return new WildcardTemplate(text, value);
	}

	
	/**
	 * Returns a StmtTemplate which when evaluated in turn will evaluate all
	 * the given templates.
	 * @param templates the templates
	 * @return the new template
	 */
	public static StmtTemplate multi(StmtTemplate... templates)
	{
		return new MultiTemplate(templates);
	}
	
	
	/**
	 * Returns a StmtTemplate to build failure messages for 
	 * {@link ExpectStatic#expectIndexValid(int, int) expectIndexValid} and 
	 * {@link ExpectStatic#not() not.expectIndexValid}.<br>
	 * @param index an index
	 * @param size a size
	 * @return the new template
	 */
	public static StmtTemplate indexValid(int index, int size)
	{
		return new IndexTemplate(index, size);
	}
	

	/**
	 * Asks the templates to build Stmts based on the given input and 
	 * feed them into the consumer.
	 * @param input the template input
	 * @param stmts receives the Stmts built by this template
	 */
	public abstract void getStmts(Input input, Consumer<Stmt> stmts);
	
	
	public List<Stmt> getStmts(Object expected, Object actual, Not not)
	{
		return getStmts(expected, actual, not, null);
	}
	
	
	public List<Stmt> getStmts(Object expected, Object actual, Not not, String what)
	{
		List<Stmt> list = new ArrayList<>();
		StmtTemplate.Input data = new StmtTemplate.Input(expected, actual, not, what);
		getStmts(data, list::add);
		return list;
	}
	
	
	/**
	 * Returns a StmtTemplate which wraps this template and evaluates it only
	 * when the {@link Input#not} is the same as the given Not object.
	 * @param not the not
	 * @return the new template
	 */
	public StmtTemplate when(Not not)
	{
		return new WhenNotTemplate(this, not);
	}
	
	
	/**
	 * Returns a StmtTemplate which wraps this template and evaluates it only
	 * when the test returns true for a given {@link Input}.
	 * @param test a test
	 * @return the new template
	 */
	public StmtTemplate when(Predicate<Input> test)
	{
		return new WhenTestTemplate(this, test);
	}

	
	/**
	 * Returns a StmtTemplate which wraps this template and evaluates it 
     * by passing an Input object with {@link Input#toggleNot() toggled Not}.
     * @return the new template
	 */
	public StmtTemplate toggleNot()
	{
		return new ToggleNotTemplate(this);
	}
	
	
	/**
	 * Returns a descriptive representation of this StmtTemplate.
	 * @return a info string
	 */
	@Override public abstract String toString();
	
	
	/**
	 * Input contains the input data which is passed to {@link StmtTemplate#getStmts(Input, Consumer)} 
	 */
	public static class Input
	{
		public Input(Object expected, Object actual, Not not, String what)
		{
			this.expected = expected;
			this.actual	  = actual;
			this.not	  = Check.notNull(not, "not");
			this.what     = what;
		}
		
		
		/**
		 * Returns a new Input with toggle Not.
		 * @return the new input
		 */
		public Input toggleNot()
		{
			return new Input(expected, actual, not.other(), what);
		}
		
		
		/**
		 * The expected value.
		 */
		public final Object expected;

		/**
		 * The actual value.
		 */
		public final Object actual;

		/**
		 * The Not which was active during the assertion.
		 */
		public final Not not;
		
		/**
		 * A user supplied parameter which can be inserted into text using 
		 * {@link StmtTemplate#wildcard(String, StmtValue) wildcard $not}
		 */
		public final String what;
	}
	
	
	/**
	 * Implementation for {@link StmtTemplate#multi(StmtTemplate...)}
	 */
	private static class MultiTemplate extends StmtTemplate
	{
		public MultiTemplate(StmtTemplate[] templates)
		{
			templates_ = Check.notNull(templates, "templates");
		}
		
		
		@Override public void getStmts(Input input, Consumer<Stmt> stmts)
		{
			for (StmtTemplate t : templates_)
				t.getStmts(input, stmts);
		}
		

		@Override public String toString()
		{
			return Arrays.toString(templates_);
		}
		
		
		private final StmtTemplate[] templates_;
	}
	
	
	/**
	 * An abstract StmtTemplate base class which produces 0 or 1 Stmts.
	 */
	protected static abstract class SingleTemplate extends StmtTemplate
	{
		/**
		 * Creates a new SingleTemplate.
		 * @param value the 
		 */
		public SingleTemplate(StmtValue value)
		{
			value_ = Check.notNull(value, "value");
		}

	
		@Override public final void getStmts(Input input, Consumer<Stmt> stmts)
		{
			String text = getStmtText(input);
			if (text != null)
			{
				Stmt stmt = new Stmt(text, value_.get(input.expected, input.actual));
				stmts.accept(stmt);
			}
		}

		
		public abstract String getStmtText(Input input);
		
		
		protected final StmtValue value_;
	}
	
	
	private static class ConstantTemplate extends SingleTemplate
	{
		public ConstantTemplate(String text, StmtValue value)
		{
			super(value);
			text_ = text;
		}
		
		
		@Override public String getStmtText(Input input)
		{
			return text_;
		}
		
		
		@Override public String toString()
		{
			return '"' + text_ + "\" " + value_;
		}

		
		private final String text_;
	}

	
	private static class WildcardTemplate extends SingleTemplate
	{
		public WildcardTemplate(String text, StmtValue value)
		{
			super(value);
			text_ = Check.notNull(text, "text");
		}
		
		
		@Override public String getStmtText(Input input)
		{
			String text = replace(text_, "$not ", "$not", input.not.isOn() ? "not " : "");
			if (input.what != null)
				text = replace(text, "$what ", "$what", input.what);
			return text;
		}
		
		
		private static String replace(String s, String wildcardPlusSpace, String wildcard, String with)
		{
			return s.replace(wildcardPlusSpace, with).replace(wildcard, with);
		}
		
		
		@Override public String toString()
		{
			return '"' + text_ + "\" " + value_;
		}
		
		
		private final String text_;
	}


	/**
	 * A StmtTemplate which holds another StmtTemplate.
	 */
	protected abstract static class ProxyTemplate extends StmtTemplate
	{
		public ProxyTemplate(StmtTemplate inner)
		{
			inner_ = Check.notNull(inner, "inner");
		}

		
		protected final StmtTemplate inner_;
	}

	
	private static class WhenNotTemplate extends ProxyTemplate
	{
		public WhenNotTemplate(StmtTemplate inner, Not not)
		{
			super(inner);
			not_ = Check.notNull(not, "not");
		}

		
		@Override public void getStmts(Input input, Consumer<Stmt> stmts)
		{
			if (not_ == input.not)
				inner_.getStmts(input, stmts);
		}
		
	
		@Override public String toString()
		{
			return "when " + not_.qualifiedString()  + '(' + inner_ + ')';
		}

		
		private final Not not_;
	}


	private static class WhenTestTemplate extends ProxyTemplate
	{
		public WhenTestTemplate(StmtTemplate inner, Predicate<Input> test)
		{
			super(inner);
			test_ = Check.notNull(test, "test");
		}

		
		@Override public void getStmts(Input input, Consumer<Stmt> stmts)
		{
			if (test_.test(input))
				inner_.getStmts(input, stmts);
		}
		
	
		@Override public String toString()
		{
			return "when " + test_  + '(' + inner_ + ')';
		}

		
		private final Predicate<Input> test_;
	}

	
	private static class ToggleNotTemplate extends ProxyTemplate
	{
		public ToggleNotTemplate(StmtTemplate inner)
		{
			super(inner);
		}

		
		@Override public void getStmts(Input input, Consumer<Stmt> stmts)
		{
			inner_.getStmts(input.toggleNot(), stmts);
		}
		
	
		@Override public String toString()
		{
			return "toggleNot(" + inner_ + ')';
		}
	}
	
	
	private static class IndexTemplate extends StmtTemplate
	{
		public IndexTemplate(int index, int size)
		{
			index_ = index;
			size_  = size;
		}
		
		
		@Override public void getStmts(Input input, Consumer<Stmt> stmts)
		{
			String range = "0 <= index < " + size_; 
			String msg = input.not.isOff() ? 
				"index " + index_ + " invalid, must be " + range :
				"index " + index_ + " valid, it is " + range;
			stmts.accept(new Stmt(msg));
			if (size_ < 0)
				stmts.accept(new Stmt("invalid size", Integer.valueOf(size_)));
		}

		
		@Override public String toString()
		{
			return "Index:" + index_ + ':' + size_;
		}
		
		
		private final int index_;
		private final int size_;
	}
}

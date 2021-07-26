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


import java.util.ArrayList;
import java.util.List;
import deepdive.actual.Actual;
import deepdive.impl.Difference;
import deepdive.impl.ErrorFactory;
import deepdive.impl.ErrorFormat;
import deepdive.impl.ExpectBase;
import deepdive.impl.Not;
import deepdive.impl.Pair;
import deepdive.impl.Stmt;
import deepdive.impl.StmtTemplate;
import deepdive.impl.Value;
import deepdive.impl.StmtTemplate.Input;


/**
 * Failure is a builder class to construct and finally throw assertion errors
 * for failed assertions.
 * It collects 
 * <ul>
 * <li>defining parts of the error message
 * <li>optional: an expected and actual value, to be stored in the assertion error
 * <li>optional: a Throwable which caused the assertion failure
 * </ul>
 * At the end of the build process you would throw the assertion error by calling
 * {@link #throwError()}.
 * <p>    
 * The error message consists of the context part, describing the context 
 * in which the assertion was made, and statements describing details of the failed assertion.<br>
 * The context is derived from the {@link ExpectBase} object which issued the assertion
 * and can be augmented by additional {@link #addContext(CharSequence) contexts}.<br>
 * Normally you won't invoke the Failure constructor directly but call the failure
 * method in one of the Expect classes:
 * <ul>
 * <li> {@link ExpectStatic#failure()}
 * <li> {@link ExpectInterface#failure()}
 * <li> {@link ExpectProtected#failure()}
 * </ul>
 * Formatting of the final error message is forwarded to the current {@link ErrorFormat#get() ErrorFormat},
 * which can be overriden if you want to deploy special formatting.
 * <p>
 * The creation of the thrown assertion error is forwarded to the current {@link ErrorFactory#get() ErrorFactory}
 * which can be overriden if you want different assertion errors to be thrown.   
 */
public class Failure
{
	/**
	 * Creates a new Failure.
	 * @param base optional: the ExpectBase which defines the base context of the failure.  
	 */
	public Failure(ExpectBase base)
	{
		checkpoint_	= base instanceof Checkpoint ? (Checkpoint)base : null;
		ExpectBase.addContexts(base, this::addContext);
	}

	
	/**
	 * Adds a context description to the Failure
	 * @param context a context description  
	 * @return this
	 */
	public Failure addContext(CharSequence context)
	{
		if (context != null)
			contexts_.add(context);
		return this;
	}
	
	
	/**
	 * Adds a Stmt to the Failure.
	 * @param stmt the Stmt  
	 * @return this
	 */
	public Failure addStmt(Stmt stmt)
	{
		if (stmt != null)
		{
			if (indentValueLines_ && stmt.hasValue())
				stmt = new Stmt("- " + stmt.text, stmt.value);
			stmts_.add(stmt);
		}
		return this;
	}
	
	
	/**
	 * Adds a Stmt consisting of the given text to the Failure
	 * @param text the Stmt text  
	 * @return this
	 */
	public Failure addStmt(String text)
	{
		return addStmt(new Stmt(text));
	}

	
	/**
	 * Adds a Stmt consisting of the given text and value to the Failure
	 * @param text the Stmt text  
	 * @param value the Stmt value
	 * @return this
	 */
	public Failure addStmt(String text, Object value)
	{
		return addStmt(new Stmt(text, value));
	}
	
	
	/**
	 * Evaluates the template and adds the resulting Stmts to the Failure
	 * @param template a template
	 * @param expected the expected object
	 * @param actual the actual object
	 * @param not the Not
	 * @param what a {@link Input#what what} parameter
	 * @return this
	 */
	public Failure addStmts(StmtTemplate template, Object expected, Object actual, Not not, String what)
	{
		StmtTemplate.Input data = new StmtTemplate.Input(expected, actual, not, what);
		template.getStmts(data, this::addStmt);
		return this;
	}
	
	
	/**
	 * Shortcut: Adds statements about expected and actual value not being equal
	 * to the failure. This also logs additional detail about the recognized
	 * differences between the values.
	 * to the failure and detail statements about the actual differences
	 * @param expected the expected value
	 * @param actual the actual value
	 * @param delta the delta used or null
	 * @param not the not used
	 * @return this
	 * @see Difference
	 */
    public Failure equalness(Object expected, Object actual, Object delta, Not not)
    {
    	setExpectedActual(expected, actual);
    	if (not.isOff())
    	{
        	notEqualImpl(null, expected, actual, delta);
        	int stmtSize = stmts_.size();
        	indentValueLines_ = true;
        	Difference.get().log(new Log(), expected, actual);
        	indentValueLines_ = false;
        	if (stmts_.size() > stmtSize)
        		stmts_.add(stmtSize, new Stmt("differences"));
    	}
    	else
    		addStmt("expected not", expected);
    	return this;
    }
    

	// Shortcut: Adds a "expected &lt;what&gt;: &lt;expected&gt;, but was &lt;what>: &lt;actual&gt;".
    private void notEqualImpl(String what, Object expected, Object actual, Object delta)
    {
       	Pair<String,String> disambiguated = Value.format(expected, actual);
    	addStmt(concat("expected", what), disambiguated.v1);
    	if (delta != null)
    		addStmt("within delta", delta);
    	addStmt(concat("but was ", what),  disambiguated.v2);
    }
    
    
	private static String concat(String s, String optional)
	{
		return optional != null ? s + ' ' + optional : s;
	}
	
	
   /**
	 * Shortcut: Adds a "expected: &lt;expected&gt;, but was: &lt;actual&gt;".
	 * @param expected the expected value
	 * @param actual the actual value
	 * @param not the not used
	 * @return this
	 */
    public Failure sameness(Object expected, Object actual, Not not)
    {
    	setExpectedActual(expected, actual);
    	if (not.isOff())
    	{
           	Pair<String,String> disambiguated = Value.format(expected, actual);
           	expected = disambiguated.v1;
           	actual   = disambiguated.v2;
    	}
		addStmts(StmtTemplate.ASSERT_SAME, expected, actual, not, null);
		return this;
    }
    
    
	public Failure notOn()
	{
		return addStmt("not() was called but no assertion was made to clear not mode"); 
	}
    
    
    
    private class Log implements Difference.Log
    {
		@Override public void add(String text)
		{
			addStmt(text);
		}

		@Override public void add(String text, Object value)
		{
			addStmt(text, value);
		}

		@Override public void addNotEqual(String what, Object expected, Object actual)
		{
			Failure.this.notEqualImpl(what, expected, actual, null);
		}
    }
    
    
    private void setExpectedActual(Object expected, Object actual)
    {
    	expected_ 			= expected;
    	actual_	  			= actual;
    	hasExpectedActual_	= true;
    }
    
    
    /**
     * Specifies the cause of the failure.
     * @param cause the cause
     * @return this
     */
    public Failure cause(Throwable cause)
    {
    	cause_ = cause;
    	return this;
    }
    
    
    /**
     * Creates the assertion error.
     * If the assertion is executed in soft mode, i.e. inside an {@link ExpectStatic#expectAll(deepdive.function.CheckedConsumer) expectAll}
     * or {@link Actual#all(deepdive.function.CheckedConsumer)} call, the error is not thrown yet, but collected.
     * In this case null is returned.
     * @param <RET> a generic return type to make it easier for callers to return an result to their respective caller
     * 		if the error is actually not thrown
     * @return returns null in case no error is thrown  
     */
	public <RET> RET throwError()
	{
		AssertionError error = ErrorFactory.get().create(toString(), cause_, hasExpectedActual_, expected_, actual_);
		error.fillInStackTrace();

		if ((checkpoint_ == null) || !checkpoint_.postponed(error))
			throw error;
		return null;
	}

	
	/**
	 * Returns the error message of this Failure.
	 */
	@Override public String toString()
	{
		return ErrorFormat.get().buildMsg(contexts_, stmts_);
	}
	
	
	private final List<Stmt> stmts_ = new ArrayList<>();
	private final List<CharSequence> contexts_ = new ArrayList<>();
	private final Checkpoint checkpoint_;
	private Throwable cause_;
	private boolean indentValueLines_;
	private Object expected_;
	private Object actual_;
	private boolean hasExpectedActual_;
}

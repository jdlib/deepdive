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
// Generated from ExpectInterface.txt, see build.xml/target[name="generate"]. 
// Do not modify directly.
package deepdive;


import javax.annotation.CheckReturnValue;
import deepdive.function.CheckedConsumer;
import deepdive.function.CheckedRunnable;
import deepdive.impl.ExpectBase;
import deepdive.impl.ExpectResult;
import deepdive.impl.Not;
import deepdive.impl.Value;


/**
 * ExpectInterface provides assert methods to test expectations on actual values.
 * All methods have default implementations. To use them simply let your 
 * test class implement ExpectInterface.
 */
public interface ExpectInterface extends ExpectBase
{
	//--------------------------------------
    // expect methods, sorted alphabetically
    //--------------------------------------

	
	/**
	 * Calls the given consumer which is expected to invoke assertion methods on 
	 * the provided ExpectInterface object. All assertion errors are collected
	 * and thrown at the end of the call.
	 * @param test a consumer to issue assertions    
	 */
    public default void expectAll(CheckedConsumer<ExpectInterface,?> test) 
    {
    	ExpectCommon.expectAll(test, this);
	}


    /**
     * Asserts that two objects are equal.
     * We use {@link Value#equal(Object, Object)} to test if two objects are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @param <T> the type of the actual value
     * @return the actual value
     */
    public default <T> T expectEqual(Object expected, T actual) 
    {
    	return expectEqual(expected, actual, null);
    }
    
    
    /**
     * Asserts that two objects are equal.
     * We use {@link Value#equal(Object, Object)} to test if two objects are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     * @return the actual value
     * @param <T> the type of the actual value
     */
    public default <T> T expectEqual(Object expected, T actual, CharSequence context) 
    {
    	return ExpectCommon.expectEqual(expected, actual, context, getNotAndClear(), this);
    }
    

    /**
     * Asserts that two booleans are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public default boolean expectEqual(boolean expected, boolean actual)
	{
		return expectEqual(expected, actual, null);
	}

	
	/**
     * Asserts that two booleans are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     * @return the actual value
     */
	@SuppressWarnings("boxing")
	public default boolean expectEqual(boolean expected, boolean actual, CharSequence context)
	{
		ExpectResult result = ExpectResult.eval(getNotAndClear(), expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, this);
		return actual;
	}

	
	/**
     * Asserts that two bytes are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public default byte expectEqual(byte expected, byte actual)
	{
		return expectEqual(expected, actual, null);
	}
    
    
    /**
     * Asserts that two objects are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     * @return the actual value
     */
	@SuppressWarnings("boxing")
    public default byte expectEqual(byte expected, byte actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(getNotAndClear(), expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, this);
		return actual;
    }

	
	/**
     * Asserts that two chars are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public default char expectEqual(char expected, char actual)
	{
		return expectEqual(expected, actual, null);
	}
    
    
    /**
     * Asserts that two chars are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     * @return the actual value
     */
	@SuppressWarnings("boxing")
    public default char expectEqual(char expected, char actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(getNotAndClear(), expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, this);
		return actual;
    }

	
	/**
     * Asserts that two shorts are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public default short expectEqual(short expected, short actual)
	{
		return expectEqual(expected, actual, null);
	}
    
    
    /**
     * Asserts that two shorts are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     * @return the actual value
     */
	@SuppressWarnings("boxing")
    public default short expectEqual(short expected, short actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(getNotAndClear(), expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, this);
		return actual;
    }

	
    /**
     * Asserts that two int values are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public default int expectEqual(int expected, int actual) 
    {
		return expectEqual(expected, actual, null);
    }
    
    
    /**
     * Asserts that two int values are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     * @return the actual value
     */
	@SuppressWarnings("boxing")
    public default int expectEqual(int expected, int actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(getNotAndClear(), expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, this);
		return actual;
    }


    /**
     * Asserts that two longs are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public default long expectEqual(long expected, long actual) 
    {
		return expectEqual(expected, actual, null);
   }
    
    
    /**
     * Asserts that two long values are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     * @return the actual value
     */
	@SuppressWarnings("boxing")
    public default long expectEqual(long expected, long actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(getNotAndClear(), expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, this);
		return actual;
    }


    /**
     * Asserts that two doubles are equal within a positive delta.
     * @param expected the expected value
     * @param actual the actual value
     * @param delta the delta
     * @return the actual value
     */
    public default double expectEqual(double expected, double actual, double delta) 
    {
		return expectEqual(expected, actual, delta, null);
    }
     
    
    /**
     * Asserts that two double values are equal within a positive delta.
     * @param expected the expected value
     * @param actual the actual value
     * @param delta the delta
     * @param context optional: describes the context of the assertion
     * @return the actual value
     */
	@SuppressWarnings("boxing")
    public default double expectEqual(double expected, double actual, double delta, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(getNotAndClear(), Value.withinDelta(expected, actual, delta));
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, delta, context, result.not, this);
		return actual;
	}
    

    /**
     * Asserts that two floats are equal within a positive delta.
     * @param expected the expected value
     * @param actual the actual value
     * @param delta the delta
     * @return the actual value
     */
    public default float expectEqual(float expected, float actual, float delta) 
    {
		return expectEqual(expected, actual, delta, null);
   }
     
    
    /**
     * Asserts that two float values are equal within a positive delta.
     * @param expected the expected value
     * @param actual the actual value
     * @param delta the delta
     * @param context optional: describes the context of the assertion
     * @return the actual value
     */
	@SuppressWarnings("boxing")
    public default float expectEqual(float expected, float actual, float delta, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(getNotAndClear(), Value.withinDelta(expected, actual, delta));
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, delta, context, result.not, this);
		return actual;
	}
    

    /**
     * Asserts that a condition is false.
     * @param condition the condition
     */
    public default void expectFalse(boolean condition)
    {
    	expectFalse(condition, null);
    }
    

    /**
     * Asserts that a condition is false.
     * @param condition the condition
     * @param context optional: describes the context of the assertion
     */
    public default void expectFalse(boolean condition, CharSequence context) 
    {
    	expectEqual(false, condition, context);
    }
    

	/**
	 * Runs a runnable which is expected to fail, i.e. to throw an Exception or Error.
	 * If the runnable does <i>not</i> fail then an assertion error is thrown.
	 * @param expectedType if not null and the thrown Throwable is not an instance of the expected type an assertion error is also thrown.
	 * @param runnable a runnable
	 * @return the Throwable thrown by the runnable
	 * @param<T> the type of the expected Throwable
	 */
	public default <T extends Throwable> T expectThrows(Class<T> expectedType, CheckedRunnable<?> runnable)
	{
		return ExpectCommon.expectThrows(runnable, expectedType, getNotAndClear(), this);
	}

	
	/**
	 * Asserts that the given index is valid, i.e. 0 &lt;= index &lt; size.
	 * @param index the index
	 * @param size the size
	 * @return this
	 */
	public default int expectIndexValid(int index, int size)
	{
		return ExpectCommon.expectIndexValid(index, size, getNotAndClear(), this);
	}


	/**
     * Asserts that an object is an instance of the expected class.
     * @param expectedClass the class
     * @param actual the actual value
     * @return the actual value, casted to the given class
     * @param <T> the expected type
     */
	public default <T> T expectInstance(Class<T> expectedClass, Object actual)
	{
		return ExpectCommon.expectInstance(expectedClass, actual, getNotAndClear(), this);
	}


	/**	
     * Asserts that an object is not null.
     * @param actual the actual value
     * @return the actual value
     * @param <T> the type of the actual value
     */
    public default <T> T expectNotNull(T actual) 
    {
    	return expectNotNull(actual, null);
    }

    
    /**
     * Asserts that an object is not null.
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     * @return the actual value
     * @param <T> the type of the actual value
     */
    public default <T> T expectNotNull(T actual, CharSequence context) 
    {
        return ExpectCommon.expectNull(false, actual, context, getNotAndClear(), this);
    }

    
    /**
     * Asserts that an object is null. 
     * @param actual the actual value
     */
    public default void expectNull(Object actual) 
    {
        expectNull(actual, null);
    }
   

    /**
     * Asserts that an object is null.
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     */
    public default void expectNull(Object actual, CharSequence context) 
    {
    	ExpectCommon.expectNull(true, actual, context, getNotAndClear(), this);
    }
    
    
    /**
     * Asserts that two objects refer to the same object.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     * @param <T> the type of the actual value
     */
    public default <T> T expectSame(Object expected, T actual) 
    {
    	return expectSame(expected, actual, null); 
	}
    

    /**
     * Asserts that two objects refer to the same object.
     * @param expected the expected value
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     * @return the actual value
     * @param <T> the type of the actual value
     */
    public default <T> T expectSame(Object expected, T actual, CharSequence context) 
    {
    	return ExpectCommon.expectSame(expected, actual, context, getNotAndClear(), this);
	}


    /**
     * Asserts that a condition is true.
     * @param condition the condition
     */
    public default void expectTrue(boolean condition) 
    {
    	expectTrue(condition, null);
    }
    
    
    /**
     * Asserts that a condition is true.
     * @param condition the condition
     * @param context optional: describes the context of the assertion
     */
    public default void expectTrue(boolean condition, CharSequence context) 
    {
    	expectEqual(true, condition, context);
   }
    

	//-----------------------
    // fail
    //-----------------------
    
    
    /**
     * Fails a test by throwing an assertion error.
     * @param <T> a dummy return type. 
     * @return either an assertion error is thrown (not returning anything) or in soft assertion mode null is returned.
     */
    public default <T> T fail() 
    {
    	return fail("fail");
    }


    /**
     * Fails a test by throwing an assertion error with the given message. 
     * @param <T> a dummy return type.
     * @param message an error message
     * @return either an assertion error is thrown (not returning anything) or in soft assertion mode null is returned.
     */
    public default <T> T fail(String message) 
    {
    	return failure().addStmt(message).throwError();
    }
        

    /**
     * Returns a Failure object which allows you to construct and throw an assertion error. 
	 * @return the failure object
     */
    public default Failure failure() 
    {
    	return new Failure(this);
    }


	//-----------------------
    // context
    //-----------------------
    

	/**
	 * Returns a sub ExpectInterface object which adds the given context string
	 * to the context description.
     * @param context an optional context 
	 * @return the Expect object
	 */    
	public default ExpectInterface getExpect(CharSequence context)
	{
		return new ExpectPublic(this, context);
	}

	
	/**
	 * Returns an assert interface with not-mode turned on.
	 * @return a new ExpectInterface object
	 */
	@CheckReturnValue
	public default ExpectInterface not()
	{
		return new ExpectPublic(this, null).not();
	}
	
	
	/**
	 * @return Not.OFF
	 */
	public default Not getNotAndClear()
	{
		return Not.OFF;
	}
}

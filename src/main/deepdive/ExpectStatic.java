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
// Generated from ExpectStatic.txt, see build.xml/target[name="generate"]. 
// Do not modify directly.
package deepdive;


import javax.annotation.CheckReturnValue;
import deepdive.function.CheckedConsumer;
import deepdive.function.CheckedRunnable;
import deepdive.impl.ExpectResult;
import deepdive.impl.Not;
import deepdive.impl.Value;


/**
 * ExpectStatic provides assert methods to test expectations on actual values.
 * All assert methods are static (like in org.junit.Assert) therefore in order
 * to use them you can import them statically (or derive your test class
 * from ExpectStatic).
 */
public class ExpectStatic
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
    public static void expectAll(CheckedConsumer<ExpectInterface,?> test) 
    {
    	ExpectCommon.expectAll(test, null);
	}


    /**
     * Asserts that two objects are equal.
     * We use {@link Value#equal(Object, Object)} to test if two objects are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @param <T> the type of the actual value
     * @return the actual value
     */
    public static <T> T expectEqual(Object expected, T actual) 
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
    public static <T> T expectEqual(Object expected, T actual, CharSequence context) 
    {
    	return ExpectCommon.expectEqual(expected, actual, context, Not.OFF, null);
    }
    

    /**
     * Asserts that two booleans are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public static boolean expectEqual(boolean expected, boolean actual)
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
	public static boolean expectEqual(boolean expected, boolean actual, CharSequence context)
	{
		ExpectResult result = ExpectResult.eval(Not.OFF, expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, null);
		return actual;
	}

	
	/**
     * Asserts that two bytes are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public static byte expectEqual(byte expected, byte actual)
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
    public static byte expectEqual(byte expected, byte actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(Not.OFF, expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, null);
		return actual;
    }

	
	/**
     * Asserts that two chars are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public static char expectEqual(char expected, char actual)
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
    public static char expectEqual(char expected, char actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(Not.OFF, expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, null);
		return actual;
    }

	
	/**
     * Asserts that two shorts are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public static short expectEqual(short expected, short actual)
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
    public static short expectEqual(short expected, short actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(Not.OFF, expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, null);
		return actual;
    }

	
    /**
     * Asserts that two int values are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public static int expectEqual(int expected, int actual) 
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
    public static int expectEqual(int expected, int actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(Not.OFF, expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, null);
		return actual;
    }


    /**
     * Asserts that two longs are equal.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     */
	public static long expectEqual(long expected, long actual) 
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
    public static long expectEqual(long expected, long actual, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(Not.OFF, expected == actual);
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, null, context, result.not, null);
		return actual;
    }


    /**
     * Asserts that two doubles are equal within a positive delta.
     * @param expected the expected value
     * @param actual the actual value
     * @param delta the delta
     * @return the actual value
     */
    public static double expectEqual(double expected, double actual, double delta) 
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
    public static double expectEqual(double expected, double actual, double delta, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(Not.OFF, Value.withinDelta(expected, actual, delta));
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, delta, context, result.not, null);
		return actual;
	}
    

    /**
     * Asserts that two floats are equal within a positive delta.
     * @param expected the expected value
     * @param actual the actual value
     * @param delta the delta
     * @return the actual value
     */
    public static float expectEqual(float expected, float actual, float delta) 
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
    public static float expectEqual(float expected, float actual, float delta, CharSequence context) 
    {
		ExpectResult result = ExpectResult.eval(Not.OFF, Value.withinDelta(expected, actual, delta));
		if (!result.ok)
			ExpectCommon.failEqual(expected, actual, delta, context, result.not, null);
		return actual;
	}
    

    /**
     * Asserts that a condition is false.
     * @param condition the condition
     */
    public static void expectFalse(boolean condition)
    {
    	expectFalse(condition, null);
    }
    

    /**
     * Asserts that a condition is false.
     * @param condition the condition
     * @param context optional: describes the context of the assertion
     */
    public static void expectFalse(boolean condition, CharSequence context) 
    {
    	expectEqual(false, condition, context);
    }
    

	/**
	 * Runs a runnable which is expected to fail, i.e. to throw an Exception or Error.
	 * If the runnable does <i>not</i> fail then an assertion error is thrown.
	 * @param expected the expected type of the thrown Throwable. If not null and the thrown 
	 *      Throwable is not an instance of the expected type an assertion error is also thrown.
	 * @param runnable a runnable
	 * @return the Throwable thrown by the runnable
	 * @param<T> the type of the expected Throwable
	 */
	public static <T extends Throwable> T expectThrows(Class<T> expected, CheckedRunnable<?> runnable)
	{
		return ExpectCommon.expectThrows(runnable, expected, Not.OFF, null);
	}

	
	/**
	 * Asserts that the given index is valid, i.e. 0 &lt;= index &lt; size.
	 * @param index the index
	 * @param size the size
	 * @return this
	 */
	public static int expectIndexValid(int index, int size)
	{
		return ExpectCommon.expectIndexValid(index, size, Not.OFF, null);
	}


	/**
     * Asserts that an object is an instance of the expected class.
     * @param expectedClass the class
     * @param actual the actual value
     * @return the actual value, casted to the given class
     * @param <T> the expected type
     */
	public static <T> T expectInstance(Class<T> expectedClass, Object actual)
	{
		return ExpectCommon.expectInstance(expectedClass, actual, Not.OFF, null);
	}


	/**	
     * Asserts that an object is not null.
     * @param actual the actual value
     * @return the actual value
     * @param <T> the type of the actual value
     */
    public static <T> T expectNotNull(T actual) 
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
    public static <T> T expectNotNull(T actual, CharSequence context) 
    {
        return ExpectCommon.expectNull(false, actual, context, Not.OFF, null);
    }

    
    /**
     * Asserts that an object is null. 
     * @param actual the actual value
     */
    public static void expectNull(Object actual) 
    {
        expectNull(actual, null);
    }
   

    /**
     * Asserts that an object is null.
     * @param actual the actual value
     * @param context optional: describes the context of the assertion
     */
    public static void expectNull(Object actual, CharSequence context) 
    {
    	ExpectCommon.expectNull(true, actual, context, Not.OFF, null);
    }
    
    
    /**
     * Asserts that two objects refer to the same object.
     * @param expected the expected value
     * @param actual the actual value
     * @return the actual value
     * @param <T> the type of the actual value
     */
    public static <T> T expectSame(Object expected, T actual) 
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
    public static <T> T expectSame(Object expected, T actual, CharSequence context) 
    {
    	return ExpectCommon.expectSame(expected, actual, context, Not.OFF, null);
	}


    /**
     * Asserts that a condition is true.
     * @param condition the condition
     */
    public static void expectTrue(boolean condition) 
    {
    	expectTrue(condition, null);
    }
    
    
    /**
     * Asserts that a condition is true.
     * @param condition the condition
     * @param context optional: describes the context of the assertion
     */
    public static void expectTrue(boolean condition, CharSequence context) 
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
    public static <T> T fail() 
    {
    	return fail("fail");
    }


    /**
     * Fails a test by throwing an assertion error with the given message. 
     * @param <T> a dummy return type.
     * @param message an error message
     * @return either an assertion error is thrown (not returning anything) or in soft assertion mode null is returned.
     */
    public static <T> T fail(String message) 
    {
    	return failure().addStmt(message).throwError();
    }
        

    /**
     * Returns a Failure object which allows you to construct and throw an assertion error. 
	 * @return the failure object
     */
    public static Failure failure() 
    {
    	return new Failure(null);
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
	public static ExpectInterface getExpect(CharSequence context)
	{
		return new ExpectPublic(null, context);
	}


	/**
	 * Returns a new ExpecttInterface object with not-mode turned on.
	 * @return the object
	 */
	@CheckReturnValue
	public static ExpectInterface not()
	{
		return new ExpectPublic(null, null).not();
	}
}

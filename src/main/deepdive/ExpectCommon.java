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


import java.util.List;
import deepdive.function.CheckedConsumer;
import deepdive.function.CheckedRunnable;
import deepdive.impl.ErrorFactory;
import deepdive.impl.ExpectBase;
import deepdive.impl.ExpectResult;
import deepdive.impl.Not;
import deepdive.impl.StmtTemplate;
import deepdive.impl.Value;


/**
 * Contains common implementations for methods in 
 * ExpectInterface, ExpectProtected and ExpectStatic.
 * We could have moved all these methods into AssertTemplate.txt 
 * but this would have triplicated the code. 
 */
class ExpectCommon 
{
	/**
	 * This method is called by
	 * ExpectStatic|Interface|Protected.assertAll() and Actual.all()
	 * @param test a consumer which receives a ExpectInterface to make its assertions
	 * 		All failures are not thrown until the consumer call returns.
	 * 		Important: Actual passes a Consumer which does not use the ExpectInterface, instead
	 * 		it passes itself to the consumer it received.,
	 * @param a base. Null if invoked by ExpectStatic, this otherwise. 
	 */
	static void expectAll(CheckedConsumer<ExpectInterface,?> test, ExpectBase base)
	{
		if (test != null)
		{
			Checkpoint softCp	= Checkpoint.findSoftCheckpoint(base);
			ExpectPublic expect = new ExpectPublic(base, null);
			Checkpoint cp     	= base instanceof Checkpoint ? (Checkpoint)base : expect;
			if (softCp == null)
				cp.startSoftMode();
			try
			{
				test.accept(expect);
			}
			catch (Throwable t)
			{
				expect.failure().addStmt("unexpected").cause(t).throwError();
			}
			if (softCp == null)
			{
				List<AssertionError> errors = cp.endSoftMode();
				switch(errors.size())
				{
					case 0: break;
					case 1: throw errors.get(0);
					default:
						AssertionError all = ErrorFactory.get().createMulti(null, errors);
						all.fillInStackTrace();
						throw all;
				}
			}
		}
	}

	
    static <T> T expectEqual(Object expected, T actual, CharSequence context, Not not, ExpectBase base) 
    {
		ExpectResult result = ExpectResult.eval(not, Value.equal(expected, actual));
		return result.ok ? actual : failure(base, context).equalness(expected, actual, null, result.not).throwError();
    }


	static int expectIndexValid(int index, int size, Not not, ExpectBase base)
	{
		boolean valid = Value.indexValid(index, size);
		ExpectResult result = ExpectResult.eval(not, valid);
		if (!result.ok)
			failure(base, null).addStmts(StmtTemplate.indexValid(index, size), null, null, result.not, null).throwError();
		return index;
	}


	@SuppressWarnings("unchecked")
	static <T> T expectInstance(Class<T> expectedClass, Object actual, Not not, ExpectBase base)
	{
		ExpectResult result = ExpectResult.eval(not, (expectedClass != null) && expectedClass.isInstance(actual));
		return result.ok ?
			(T)actual    :
			failure(base, null).addStmts(StmtTemplate.ASSERT_INSTANCE, expectedClass, actual, result.not, null).throwError();
	}

    
    // common implementation for assertNull(actual) and assertNotNull(actual)
    static <T> T expectNull(boolean expectNull, T actual, CharSequence context, Not not, ExpectBase base) 
    {
		ExpectResult result = ExpectResult.eval(not, (actual == null) == expectNull);
		return result.ok ?
			actual    :
			failure(base, context).addStmts(expectNull ? StmtTemplate.ASSERT_NULL : StmtTemplate.ASSERT_NOT_NULL, null, actual, result.not, null).throwError();
    }
    
    
    static <T> T expectSame(Object expected, T actual, CharSequence context, Not not, ExpectBase base) 
    {
		ExpectResult result = ExpectResult.eval(not, expected == actual);
		return result.ok ? 
			actual : 
			failure(base, context).sameness(expected, actual, result.not).throwError();
    }

    
	@SuppressWarnings("unchecked")
	static <T extends Throwable> T expectThrows(CheckedRunnable<?> runnable, Class<T> expected, Not not, ExpectBase base)
	{
		if (runnable == null)
			return failure(base, null).addStmt("runnable is null").throwError();
		if (not == Not.ON)
			return failure(base, null).notOn().throwError();

		Throwable thrown;
		try
		{
			runnable.run();
			thrown = null;
		}
		catch (Throwable e)
		{
			thrown = e;
		}
		
		if (thrown == null)
		{
			Failure f  = failure(base, null);
			String msg = "expected exception"; 
			if (expected == null)
				f.addStmt(msg);
			else
				f.addStmt(msg, expected);
			f.addStmt("but runnable did not throw any exception");
			f.throwError();
		}
		if (expected != null)
			expectInstance(expected, thrown, Not.OFF, base);
		return (T)thrown;
	}

    
    static void failEqual(Object expected, Object actual, Object delta, CharSequence context, Not not, ExpectBase base) 
    {
		failure(base, context).equalness(expected, actual, delta, not).throwError();
    }
	
	
	private static Failure failure(ExpectBase base, CharSequence context)
	{
		return new Failure(base).addContext(context);
	}
}

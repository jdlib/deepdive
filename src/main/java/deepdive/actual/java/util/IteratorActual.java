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
package deepdive.actual.java.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import deepdive.actual.Actual;
import deepdive.function.CheckedBiFunction;


/**
 * An Actual implementation for {@link Iterator} objects.
 * @param <ELEM> the type of the iterator elements
 * @param <BACK> the type of the owner of the SetActual
 * @param <IMPL> the type of the SetActual implementation 
 */
public class IteratorActual<ELEM,BACK,IMPL extends IteratorActual<ELEM,BACK,IMPL>> extends Actual<Iterator<ELEM>,BACK,IMPL>
{
	/**
	 * Creates a new IteratorActual.
	 * @param value the value.
	 * @param back the owner
	 */
	public IteratorActual(Iterator<ELEM> value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that remaining elements equal the expected elements and then returns the back object.
	 * (By convention the trailing $ signals that we leave this Actual and return the back object).
	 * @param expected the expected remaining elements
	 * @return this
	 */
	@SafeVarargs
	public final BACK elems$(ELEM... expected)
	{
		List<ELEM> expectedList = expected != null ? Arrays.asList(expected) : null;
		List<ELEM> actualList = new ArrayList<>();
		Iterator<ELEM> it = value();
		while (it.hasNext())
			actualList.add(it.next());
		expectEqual(expectedList, actualList, "elems");
		return backOrNull();
	}

	
	/**
	 * Asserts that the iterator has a next element.
	 * @return this
	 */
	public IMPL hasNext()
	{
		expectTrue(value().hasNext(), "hasNext");
		return self();
	}

	
	/**
	 * Skips count elements.
	 * @param count the count
	 * @return this
	 */
	public IMPL skip(int count)
	{
		while(count-- > 0)
			value().next();
		return self();
	}

	
	/**
	 * Asserts that the next element equals the expected value.
	 * @param expected the expected value.
	 * @return this
	 */
	public IMPL next(ELEM expected)
	{
		expectEqual(expected, value().next(), "next");
		return self();
	}
	
	
	/**
	 * Returns an Actual for the next element.
	 * @return the new actual
	 */
	public Actual<ELEM,IMPL,?> next()
	{
		return next(Actual::new).as("next");
	}


	/**
	 * Calls the function with the next element and this Actual and returns the value
	 * @param function a function
	 * @return the function result
	 * @throws E if the function fails
	 * @param <R> the function return type
	 * @param <E> type of the exceptions thrown by the function
	 */
	public <R,E extends Exception> R next(CheckedBiFunction<ELEM,IMPL,R,E> function) throws E
	{
		return function.apply(value().next(), self());
	}


	/**
	 * Asserts that the iterator reached its end and returns the back object.
	 * (By convention the trailing $ signals that we leave this Actual and return the back object).
	 * @return the owner object
	 */
	public BACK end$()
	{
		not().hasNext();
		return backOrNull();
	}
}

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
package deepdive.actual.util;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.function.CheckedBiFunction;
import deepdive.impl.NotMustBeOff;
import deepdive.impl.ActualChange;
import deepdive.impl.NotAgnostic;


/**
 * An Actual implementation for {@link List} objects.
 * @param <ELEM> the type of the list elements
 * @param <T> the type of the list implementation
 * @param <BACK> the type of the owner of the ListActual
 * @param <IMPL> the type of the concrete ListActual implementation 
 */
public class ListActual<ELEM,T extends List<ELEM>,BACK,IMPL extends ListActual<ELEM,T,BACK,IMPL>> 
	extends CollectionActual<ELEM,T,BACK,IMPL>
{
	/**
	 * Creates a new ListActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public ListActual(T value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Adds the element at the given index.
	 * @param index the index
	 * @param elem the new element
	 * @return this
	 */
	@ActualChange
	public IMPL add(int index, ELEM elem)
	{
		value().add(index, elem);
		return self();
	}
	
	
	/**
	 * Asserts that the element at the given index equals the expected value.
	 * @param index the index
	 * @param expected the expected element
	 * @return this
	 */
	public IMPL elem(int index, ELEM expected)
	{
		expectEqual(expected, getElem(index), Context.indexed("elem", index));
		return self();
	}
	

	/**
	 * Returns an Actual for the element at the given index.
	 * @param index the index
	 * @return the new actual
	 */
	public Actual<ELEM,IMPL,?> elem(int index)
	{
		return elem(index, Actual::new).as(Context.indexed("elem", index));
	}
	
	
	/**
	 * Applies the function to the element at the given index and returns the result.
	 * @param index the index
	 * @param function the function
	 * @return the result
	 * @param <R> the return type
	 * @param <E> the type of the exceptions thrown by the function
	 * @throws E if the function fails
	 */
	public <R,E extends Exception> R elem(int index, CheckedBiFunction<ELEM,IMPL,R,E> function) throws E
	{
		return function.apply(getElem(index), self());
	}
	
	
	/**
	 * Asserts that the list consists of the expected elements.
	 * @param expected the expected elements.
	 * @return this
	 */
	@SafeVarargs
	public final IMPL elems(ELEM... expected)
	{
		List<Object> expectedList = expected != null ? Arrays.asList(expected) : null;
		expectEqual(expectedList, value(), "elems");
		return self();
	}

	
	/**
	 * Asserts that the index of the given element within the list equals the expected value. 
	 * @param elem an element
	 * @param expected the expected index value
	 * @return this
	 */
	public IMPL indexOf(ELEM elem, int expected)
	{
		expectEqual(expected, value().indexOf(elem), Context.call("indexOf", elem));
		return self();
	}

	
	/**
	 * Tests if when accessing the list element with the given index an {@link IndexOutOfBoundsException} is thrown.
	 * @param index a list index
	 * @return this
	 */
	public IMPL indexOutOfBounds(int index)
	{
		fails(a -> value().get(index)).isA(IndexOutOfBoundsException.class);
		return self();
	}
	
	
	/**
	 * Asserts that given index is valid.
	 * An index is valid if it is in the range of [0, list.size[.
	 * @param index the index
	 * @return this
	 */
	public IMPL indexValid(int index)
	{
		expectIndexValid(index, value().size());
		return self();
	}

	
	/**
	 * Asserts that the last index of the element equals the expected value.
	 * @param elem an element
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL lastIndexOf(ELEM elem, int expected)
	{
		expectEqual(expected, value().lastIndexOf(elem), Context.call("lastIndexOf", elem));
		return self();
	}


	/**
	 * Maps the list elements using the given mapper and returns a new ListActual for the result.
	 * @param mapper a mapper
	 * @return this
     * @param <R> The element type of the new stream
	 */
	@NotMustBeOff
	public <R> ListActual<R,List<R>,IMPL,?> map(Function<? super ELEM, ? extends R> mapper)
	{
		return new ListActual<>(value().stream().map(mapper).collect(Collectors.toList()), self());
	}

	
	/**
	 * Removes the element at the given index.
	 * @param index the index
	 * @return this
	 */
	@ActualChange
	public IMPL remove(int index)
	{
		value().remove(index);
		return self();
	}


	/**
	 * Removes the element at the given index
	 * and asserts that the remove element equals the expected value
	 * @param index the index
	 * @param expected the expected value
	 * @return this
	 */
	@ActualChange
	public IMPL remove(int index, ELEM expected)
	{
		expectEqual(expected, value().remove(index), Context.indexed("remove", index));
		return self();
	}
	
	
	/**
	 * Sets the value of the list at the specified index to the given element
	 * @param index the index
	 * @param elem the element
	 * @return this
	 */
	@ActualChange
	public IMPL set(int index, ELEM elem)
	{
		value().set(index, elem);
		return self();
	}


	/**
	 * Sorts the array using the given comparator.
	 * @param comparator a comparator or null if natural ordering should be used.
	 * @return this
	 */
	@ActualChange
	public IMPL sort(Comparator<? super ELEM> comparator)
	{
		value().sort(comparator);
		return self();
	}

	
	/**
	 * Returns the element at the given index.
	 * This method should throw an assertion error if the index is not valid. 
	 * Note to implementors: index validity should not 
	 * be based on {@link #indexValid(int)}, since that method
	 * obeys {@link Actual#not() not-mode} whereas this method
	 * should be not-mode agnostic.
	 * @param index the index
	 * @return the element
	 */
	@NotAgnostic
	protected ELEM getElem(int index)
	{
		// must not use indexValid to check index validity!
		T list = value();
		checkIndex(index, list.size());
		return list.get(index);
	}
}

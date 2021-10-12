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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.CheckReturnValue;
import deepdive.Context;
import deepdive.actual.lang.ContainerActual;
import deepdive.actual.lang.IntegerActual;
import deepdive.actual.util.stream.StreamActual;
import deepdive.impl.ActualChange;
import deepdive.impl.Value;


/**
 * An Actual implementation for {@link Collections} objects.
 * @param <ELEM> the type of the collection elements
 * @param <T> the type of the collection implementation
 * @param <BACK> the type of the owner of the CollectionActual
 * @param <IMPL> the type of the concrete CollectionActual implementation 
 */
public class CollectionActual<ELEM,T extends Collection<ELEM>,BACK,IMPL extends CollectionActual<ELEM,T,BACK,IMPL>> 
	extends ContainerActual<ELEM,T,BACK,IMPL>
{
	/**
	 * Creates a new CollectionActual.
	 * @param value the value
	 * @param back the owner
	 */
	public CollectionActual(T value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Adds the element to the collection.
	 * @param elem the new element 
	 * @return this
	 */
	@ActualChange
	public IMPL add(ELEM elem)
	{
		value().add(elem);
		return self();
	}
	
	
	/**
	 * Adds the element to the collection and asserts that the 
	 * return value of {@link Collection#add(Object)} equals the expected value.
	 * @param elem an element
	 * @param expected the expected return value of the add call
	 * @return this
	 */
	@ActualChange
	public IMPL add(ELEM elem, boolean expected)
	{
		expectEqual(expected, value().add(elem), Context.call("add", elem));
		return self();
	}
	
	
	/**
	 * Asserts that the Collection is null or empty.
	 * @return this
	 */
	@Override public IMPL blank()
	{
		T col = valueOrNull();
		return blank((col == null) || (col.size() == 0)); 
	}

	
	/**
	 * Clears the collection. 
	 * @return this
	 */
	@ActualChange
	public IMPL clear()
	{
		value().clear();
		return self();
	}

	
	/**
	 * Asserts that the Collection is empty.
	 * @return this
	 */
	@Override public IMPL empty()
	{
		expectEqual(0, value().size(), "size");
		return self();
	}

	
	/**
	 * Returns an IteratorActual for an iterator of the collection. 
	 * @return the new actual
	 */
	public IteratorActual<ELEM,IMPL,?> iterator()
	{
		return new IteratorActual<>(value().iterator(), self());
	}
	
	
	/**
	 * Removes an element from the collection.
	 * @param elem the element 
	 * @return this
	 */
	@ActualChange
	public IMPL remove(ELEM elem)
	{
		value().remove(elem);
		return self();
	}

	
	/**
	 * Removes an element from the collection and asserts that the
	 * return value of the remove call equals the expected value.
	 * @param elem the element 
	 * @param expected the expected value
	 * @return this
	 */
	@ActualChange
	public IMPL remove(ELEM elem, boolean expected)
	{
		expectEqual(expected, value().remove(elem), Context.call("remove", elem));
		return self();
	}

	
	/**
	 * Asserts that the collections size equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL size(int expected)
	{
		expectEqual(expected, value().size(), "size");
		return self();
	}

	
	/**
	 * Returns an IntegerActual for the collection size.
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> size()
	{
		return new IntegerActual<>(value().size(), self()).as("size");
	}


	/**
	 * Returns a builder to replace this CollectionActual with a more 
	 * specific one.
	 * @return the builder
	 */
	@CheckReturnValue
	public SwitchTo switchTo()
    {
    	return new SwitchTo();
    }
    

    public class SwitchTo
    {
        protected SwitchTo()
    	{
    	}

    
    	public IteratorActual<ELEM,BACK,?> iterator()
    	{
    		return new IteratorActual<>(value().iterator(), backOrNull());
    	}
    	
    	
		public ListActual<ELEM,?,BACK,?> list() 
    	{
    		CollectionActual<ELEM,T,BACK,IMPL> a = CollectionActual.this;
    		return a instanceof ListActual ?
    			(ListActual<ELEM,?,BACK,?>)a :
    			new ListActual<>(new ArrayList<>(value()), backOrNull()).as("list");
    	}

    
    	public SetActual<ELEM,?,BACK,?> set()
    	{
    		CollectionActual<ELEM,T,BACK,IMPL> a = CollectionActual.this;
    		return a instanceof SetActual ?
    			(SetActual<ELEM,?,BACK,?>)a :
    			new SetActual<>(Value.collectionToSet(value()), backOrNull()).as("set");
    	}
    	
    	
    	public StreamActual<ELEM,BACK,?> stream() 
    	{
    		return new StreamActual<>(value().stream(), backOrNull()).as("stream");
    	}
    }
	

	@Override protected boolean doesContain(ELEM elem) 
	{
		return value().contains(elem);
	}


	@Override protected Stream<ELEM> getStream() 
	{
		return value().stream();
	}

	
	@Override protected Set<ELEM> actualAsSet() 
	{
		T actual = value();
		if (actual instanceof Set)
			return (Set<ELEM>)actual;
		else
		{
			// LinkedHashSet has predictable iteration order
			Set<ELEM> set = new LinkedHashSet<>(); 
			set.addAll(value());
			return set;
		}
	}
}

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
package deepdive.actual.java.util.stream;


import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.CheckReturnValue;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.java.lang.ArrayActual;
import deepdive.actual.java.lang.LongActual;
import deepdive.actual.java.util.ListActual;
import deepdive.actual.java.util.MapActual;
import deepdive.actual.java.util.SetActual;
import deepdive.impl.ActualChange;


/**
 * An Actual implementations for {@link Stream} objects.<p>
 * Since a Stream can't be used after a terminal operation, we handle terminal operations
 * in the following way:
 * <ol>
 * <li>The invokation result is used to test an assertion, and we then go back to the BACK object (e.g. #count$(long)}. 
 * <li>The invokation result is returned by an Actual whose back object is the same as the back object of the StreamActual.
 * 		(see all operations avaiable from {@link #collectTo()}  
 * </ol>
 * go back to BACK object or return a new Actual
 */
public class StreamActual<ELEM,BACK,IMPL extends StreamActual<ELEM,BACK,IMPL>> extends Actual<Stream<ELEM>,BACK,IMPL>
{
	/**
	 * Creates a new StreamActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public StreamActual(Stream<ELEM> value, BACK back)
	{
		super(value, back);
	}
	
	
    /**
     * Asserts that all stream elements match the predicate and then returns to the back object. 
	 * (By convention the trailing $ signals that we leave this Actual and return the back object).
     * @param predicate a predicate
     * @return the back object
     */
	public BACK allMatch$(Predicate<? super ELEM> predicate)
	{
		expectTrue(value().allMatch(predicate), "allMatch");
		return backOrNull();
	}

	
    /**
     * Asserts that there are stream elements that match the predicate and then returns to the back object. 
	 * (By convention the trailing $ signals that we leave this Actual and return the back object).
     * @param predicate a predicate
     * @return the back object
     */
	public BACK anyMatch$(Predicate<? super ELEM> predicate)
	{
		expectTrue(value().anyMatch(predicate), "anyMatch");
		return backOrNull();
	}


    
	/**
	 * Returns a {@link CollectTo} builder to invoke a terminal operation on 
	 * the Stream and return the result as a new Actual. Since after
	 * a terminal operation the stream can no longer be used the back
	 * object of that new Actual will not be this StreamActual but the current back
	 * object instead, i.e. we effectively switch from the StreamActual
	 * to the resulting Actual. 
	 * @return the builder
	 */
	@CheckReturnValue
	public CollectTo collectTo()
    {
    	return new CollectTo();
    }

    
    /**
     * Asserts that the stream has the expected count and returns the back object. 
	 * (By convention the trailing $ signals that we leave this Actual and return the back object).
     * @param expected the expected count
     * @return the back object
     */
    public BACK count$(long expected)
    {
    	expectEqual(expected, value().count(), "count");
    	return backOrNull();
    }
	
	
	/**
	 * Makes the stream distinct.
	 * @return this
	 */
	@ActualChange
    public IMPL distinct()
    {
    	return setValue(value().distinct());
    }
    
    
	/**
	 * Asserts that the stream result consists of the given elements
	 * and return to the back object
	 * @param expected the expected elements
	 * @return the owner
	 */
    @SafeVarargs
	public final BACK elems$(ELEM... expected)
    {
    	collectTo().list().elems(expected);
    	return backOrNull();
    }

    
	/**
	 * Applies the filter to the stream.
	 * @param filter this filter
	 * @return this
	 */
	@ActualChange
    public IMPL filter(Predicate<? super ELEM> filter)
    {
    	return setValue(value().filter(filter));
    }
    
    
	/**
	 * Flatmaps the stream,
	 * @param mapper a mapper
	 * @return a new StreamActual with same back object and the mapped stream
	 * @param <R> the return type
	 */
	public <R> StreamActual<R,BACK,?> flatMap(Function<? super ELEM, ? extends Stream<? extends R>> mapper)
    {
		Stream<R> s = value().flatMap(mapper);
    	return new StreamActual<>(s, backOrNull()).as("flatMap");
    }
    
    
	/**
	 * Limits the stream to the given maximal size,
	 * @param maxSize the maximal size
	 * @return this
	 */
	@ActualChange
    public IMPL limit(long maxSize)
    {
    	return setValue(value().limit(maxSize));
    }


	/**
	 * Maps the stream.
	 * @param mapper a mapper
	 * @return a new StreamActual with same back object and the mapped stream
	 * @param <R> the return type
	 */
	public <R> StreamActual<R,BACK,?> map(Function<? super ELEM, ? extends R> mapper)
    {
		Stream<R> s = value().map(mapper);
    	return new StreamActual<>(s, backOrNull()).as("map");
    }
	
	
    /**
     * Asserts that no stream elements match the predicate and then returns to the back object. 
	 * (By convention the trailing $ signals that we leave this Actual and return the back object).
     * @param predicate a predicate
     * @return the back object
     */
	public BACK noneMatch$(Predicate<? super ELEM> predicate)
	{
		expectTrue(value().noneMatch(predicate), "noneMatch");
		return backOrNull();
	}

	
	/**
	 * Skips n elements in the stream.
	 * @param n a count
	 * @return this
	 */
	@ActualChange
    public IMPL skip(long n)
    {
    	return setValue(value().skip(n));
    }

    
	/**
	 * Sorts the stream.
	 * @return this
	 */
	@ActualChange
    public IMPL sorted()
    {
    	return setValue(value().sorted());
    }
    

	/**
	 * Sorts the stream.
	 * @param comparator the sort comparator
	 * @return this
	 */
	@ActualChange
    public IMPL sorted(Comparator<? super ELEM> comparator)
    {
    	return setValue(value().sorted(comparator));
    }

	
	/**
	 * A builder class to specify the collector for a Stream.
	 */
    public class CollectTo
    {
        /**
         * Returns an ArrayActual with an object array for the stream elements.
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
        public ArrayActual<Object,BACK,?> array()
        {
        	return new ArrayActual<>(value().toArray(), backOrNull()).as("array");
        }

        
        /**
         * Returns an ArrayActual with an array for the stream elements.
         * @param generator used to create the array
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
        public ArrayActual<ELEM,BACK,?> array(IntFunction<ELEM[]> generator)
        {
        	return new ArrayActual<>(value().toArray(generator), backOrNull()).as("array");
        }
        
        
        /**
         * Returns an LongActual for the stream count.
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
        public LongActual<BACK,?> count()
        {
        	return new LongActual<>(value().count(), backOrNull()).as("count");
        }
        

        /**
         * Returns an Actual for the result of findAny applied to the stream.
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
		public Actual<ELEM,BACK,?> findAny() 
    	{
        	return new Actual<>(value().findAny().orElse(null), backOrNull()).as("findAny");
    	}

		
        /**
         * Returns an Actual for the result of findFirst applied to the stream.
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
		public Actual<ELEM,BACK,?> findFirst() 
    	{
        	return new Actual<>(value().findFirst().orElse(null), backOrNull()).as("findFirst");
    	}

		
        /**
         * Returns a ListActual for the stream elements collected to a List.
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
		public ListActual<ELEM,?,BACK,?> list() 
    	{
        	return new ListActual<>(value().collect(Collectors.toList()), backOrNull()).as("list");
    	}


        /**
         * Returns a MapActual for the stream elements collected using the given map collector.
         * @param collector the collector
         * @return the new actual. It's back object is the same as the back object of this StreamActual
	     * @param <K> the output type of the key mapping function
	     * @param <V> the output type of the value mapping function
	     * @param <M> the type of the resulting Map
         */
        public <K,V,M extends Map<K,V>> MapActual<K,V,M,BACK,?> map(Collector<? super ELEM,?,M> collector)
        {
        	return new MapActual<>(value().collect(collector), backOrNull()).as("map");
        }

		
        /**
         * Returns an Actual for the maximum stream element in natural order.
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
		public Actual<ELEM,BACK,?> max() 
    	{
			return max(null);
    	}

		
        /**
         * Returns an Actual for the maximum stream element.
         * @param comparator a comparator
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
		public Actual<ELEM,BACK,?> max(Comparator<? super ELEM> comparator) 
    	{
        	return new Actual<>(value().max(safeComparator(comparator)).orElse(null), backOrNull()).as("max");
    	}

		
        /**
         * Returns an Actual for the minimum stream element in natural order.
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
		public Actual<ELEM,BACK,?> min() 
    	{
        	return min(null);
    	}
		
		
        /**
         * Returns an Actual for the minimum stream element.
         * @param comparator a comparator
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
		public Actual<ELEM,BACK,?> min(Comparator<? super ELEM> comparator) 
    	{
        	return new Actual<>(value().min(safeComparator(comparator)).orElse(null), backOrNull()).as("min");
    	}


        /**
         * Returns an Actual for the stream elements collected using the given collector.
         * @param collector the collector
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         * @param <A> the mutable accumulation type of the reduction operation
         * @param <R> the result type of the reduction operation
         */
        public <R,A> Actual<R,BACK,?> result(Collector<? super ELEM,A,R> collector)
        {
        	return new Actual<>(value().collect(collector), backOrNull()).as(Context.call("result", collector));
        }
		
		
        /**
         * Returns a SetActual for the stream elements collected to a Set.
         * @return the new actual. It's back object is the same as the back object of this StreamActual
         */
		public SetActual<ELEM,?,BACK,?> set()
    	{
        	return new SetActual<>(value().collect(Collectors.toSet()), backOrNull()).as("set");
    	}


        @SuppressWarnings("unchecked")
		private Comparator<? super ELEM> safeComparator(Comparator<? super ELEM> comparator)
		{
			return comparator != null ? comparator : (Comparator<? super ELEM>)Comparator.naturalOrder(); 
		}
    }
}

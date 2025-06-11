package deepdive.actual.lang;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.CheckReturnValue;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.util.IteratorActual;
import deepdive.actual.util.ListActual;
import deepdive.actual.util.SetActual;
import deepdive.actual.util.stream.StreamActual;
import deepdive.function.CheckedBiFunction;
import deepdive.impl.ActualChange;
import deepdive.impl.ExpectResult;
import deepdive.impl.NotAgnostic;
import deepdive.impl.Value;


/**
 * An Actual implementation for object arrays.
 * @param <ELEM> the array component type
 * @param <BACK> the type of the owner of the ArrayActual
 * @param <IMPL> the type of the concrete ArrayActual implementation 
 */
public class ArrayActual<ELEM,BACK,IMPL extends ArrayActual<ELEM,BACK,IMPL>> 
	extends ContainerActual<ELEM,ELEM[],BACK,IMPL>
{
	/**
	 * Creates an ArrayActual for the given elements.
	 * Note that in order to use a varargs parameter the back value
	 * comes first, and the actual elem values second.
	 * @param back the owner
	 * @param actual the array elements.
	 */
	public ArrayActual(ELEM[] actual, BACK back)
	{
		super(actual, back);
	}
	
	
	/**
	 * Asserts that the Array is null or empty.
	 * @return this
	 */
	@Override public IMPL blank()
	{
		ELEM[] value = valueOrNull();
		return blank((value == null) || (value.length == 0)); 
	}
	
	
	/**
	 * Asserts that the component type of the actual array is the same as the expected class.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL componentType(Class<?> expected)
	{
		expectSame(expected, value().getClass().getComponentType());
		return self();
	}
	
	
	/**
	 * Asserts that the element at the given index equals the expected value.
	 * @param index an index
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL elem(int index, ELEM expected)
	{
		expectEqual(expected, getElem(index), Context.indexed("elem", index));
		return self();
	}
	

	/**
	 * Returns an Actual for the element at the given index.
	 * @param index an index
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
	 * @param <E> the type of exception thrown by the function
	 * @param <R> the function return type
	 * @return the function result
	 * @throws E can be thrown by the function.
	 */
	public <R,E extends Exception> R elem(int index, CheckedBiFunction<ELEM,IMPL,R,E> function) throws E
	{
		return function.apply(getElem(index), self());
	}
	
	
	/**
	 * Asserts that the array consists of the given elements in that exact order.
	 * If you want to compare elements ignoring order and duplicates use 
	 * {@link deepdive.actual.lang.ContainerActual.ContainsActual#exactly(Object...) contains().exactly()}
	 * @param expected the expected elements
	 * @return this
	 */
	@SafeVarargs
	public final IMPL elems(ELEM... expected)
	{
		ExpectResult result = ExpectResult.eval(getNotAndClear(), Value.arraysEqual(expected, value(), false));
		if (!result.ok)
			failure().addContext("elems").equalness(expected, value(), null, result.not).throwError();
		return self();
	}
	
	
	/**
	 * Asserts that the array is empty.
	 * @return this
	 */
	@Override public IMPL empty()
	{
		expectEqual(0, value().length, "length");
		return self();
	}

	
	/**
	 * {@inheritDoc}
	 */
	public IMPL indexOf(ELEM elem, int expected)
	{
		int actual = Arrays.asList(value()).indexOf(elem);
		expectEqual(expected, actual, Context.call("indexOf", elem));
		return self();
	}

	
	/**
	 * {@inheritDoc}
	 */
	public IMPL indexValid(int index)
	{
		expectIndexValid(index, value().length);
		return self();
	}

	
	/**
	 * Returns an IteratorActual for the array elements
	 * @return the new actual
	 */
	public IteratorActual<ELEM,IMPL,?> iterator()
	{
		return new IteratorActual<>(Value.arrayIterator(value()), self());
	}
	
	
	/**
	 * Asserts that array length equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL length(int expected)
	{
		expectEqual(expected, value().length, "length");
		return self();
	}


	/**
	 * Returns an IntegerActual for the actual array length.
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> length()
	{
		return new IntegerActual<>(value().length, self()).as("length");
	}
	
	
	/**
	 * Sorts the array of this actual using the given comparator.
	 * @param comparator a comparator or null if natural ordering should be used.
	 * @return this
	 */
	@ActualChange
	public IMPL sort(Comparator<? super ELEM> comparator)
	{
		Arrays.sort(value(), comparator);
		return self();
	}
	
	
	@Override public StreamActual<ELEM,IMPL,?> stream() 
	{
		return new StreamActual<>(Stream.of(value()), self()).as("stream");
	}
	
	
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
    		return new IteratorActual<>(Value.arrayIterator(value()), backOrNull()).as("iterator");
    	}
    	
    	
		public ListActual<ELEM,?,BACK,?> list() 
    	{
    		List<ELEM> list = new ArrayList<>();
    		Collections.addAll(list, value());
    		return new ListActual<>(list, backOrNull()).as("list");
    	}

    
    	public SetActual<ELEM,?,BACK,?> set()
    	{
    		return new SetActual<>(Value.arrayToSet(value()), backOrNull()).as("set");
    	}
    	
    	
    	public StreamActual<ELEM,BACK,?> stream() 
    	{
    		return new StreamActual<>(Stream.of(value()), backOrNull()).as("stream");
    	}
    }

    
    //-----------------------------
	// ContainerActual impl
	//-----------------------------


	/**
	 * {@inheritDoc}
	 */
	@Override protected boolean doesContain(ELEM elem)
	{
		for (ELEM e : value())
		{
			if (Objects.equals(elem, e))
				return true;
		}
		return false;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override protected Stream<ELEM> getStream()
	{
		return Stream.of(value());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override protected Set<ELEM> actualAsSet() 
	{
		return Value.arrayToSet(value());
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
		ELEM[] array = value();
		checkIndex(index, array.length);
		return array[index];
	}
}

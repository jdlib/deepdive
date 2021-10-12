package deepdive.actual.lang;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.util.stream.StreamActual;
import deepdive.impl.Not;
import deepdive.impl.StmtTemplate;
import deepdive.impl.Value;
import deepdive.impl.NotMustBeOff;


/**
 * A common abstract Actual base class for IterableActual, CollectionActual, ArrayActual. 
 */
public abstract class ContainerActual<ELEM,T,BACK,IMPL extends ContainerActual<ELEM,T,BACK,IMPL>> 
	extends Actual<T,BACK,IMPL>
{
	/**
	 * Creates a new ContainerActual.
	 * @param value the value
	 * @param back the owner
	 */
	public ContainerActual(T value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that the Container is null or empty.
	 * @return this
	 */
	public abstract IMPL blank();
	
	
	/**
	 * Helper method to implement {@link #blank()}.
	 * @param isBlank is the container blank?
	 * @return this
	 */
	protected IMPL blank(boolean isBlank)
	{
		return expectTrue(isBlank, StmtTemplate.ASSERT_BLANK, null, null); 
	}

	
	/**
	 * Asserts that the container contains the expected element.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL contains(ELEM expected)
	{
		return expectTo(doesContain(expected), "contain", expected);
	}
	
	
	/**
	 * Returns a ContainsActual object to make further assertions about the contained elements.
	 * @return the ContainsActual
	 */
	@NotMustBeOff
	public ContainsActual contains()
	{
		self();
		return new ContainsActual();
	}
	
	
	/**
	 * Allows to make assertions about the contained elements of the container (array or collection).
	 * @see ContainerActual#contains()
	 */
	public class ContainsActual
	{
		protected ContainsActual()
		{
		}
		
		
		public IMPL back()
		{
			return self();
		}
		
		
		/**
		 * Asserts that the container contains at least all the given elements.
		 * But it may contain more elements. 
		 * @param elems the elements
		 * @return this ContainsActual object
		 */
		@SafeVarargs
		public final ContainsActual allOf(ELEM... elems)
		{
			return allOf(Arrays.asList(elems));
		}
		
		
		/**
		 * Asserts that the container contains at least all the given elements
		 * But it may contain more elements. 
		 * @param elems an iterable over the elems 
		 * @return this ContainsActual object
		 */
		public final ContainsActual allOf(Iterable<ELEM> elems)
		{
			List<ELEM> failing = null;
			for (ELEM elem : elems)
			{
				if (!doesContain(elem))
				{
					if (failing == null)
						failing = new ArrayList<>();
					failing.add(elem);
				}
			}
			expectTo(failing == null, "contain", Value.onlyOrCollection(failing));
			return this;
		}
		
		
		/**
		 * Asserts that the container exactly consists of the expected elements
		 * (ignoring order and duplicates).
		 * @param elems the elements
		 * @return this ContainsActual object
		 */
		@SafeVarargs
		public final ContainsActual exactly(ELEM... elems)
		{
			// LinkedHashSet has predictable iteration order
			return exactly(new LinkedHashSet<>(Arrays.asList(elems)));
		}
		
		
		/**
		 * Asserts that the container consists exactly of the expected elements
		 * (ignoring order and duplicates).
		 * @param expected the expected elements
		 * @return this ContainsActual object
		 */
		public ContainsActual exactly(Set<ELEM> expected)
		{
			expectEqual(expected, actualAsSet(), "exactly");
			return this;
		}
		
		
		/**
		 * Asserts that the container contains an element that matches
		 * the predicate.
		 * @param test the predicate
		 * @return this ContainsActual object
		 */
		public ContainsActual match(Predicate<ELEM> test)
		{
			expectTrue(getStream().anyMatch(test), "match");
			return this;
		}

		
		/**
		 * Asserts that the container does not contain the given elements.
		 * @param elems the elements
		 * @return this ContainsActual object
		 */
		@SafeVarargs
		public final ContainsActual noneOf(ELEM... elems)
		{
			List<ELEM> failing = null;
			for (ELEM elem : elems)
			{
				if (doesContain(elem))
				{
					if (failing == null)
						failing = new ArrayList<>(elems.length);
					failing.add(elem);
				}
			}
	    	expectTrue(failing == null, 
	    		StmtTemplate.ASSERT_EXPECTED_TO_TOGGLEDNOT, 
	    		"contain", getNotHolder().get().isOff() ? Value.onlyOrCollection(failing) : elems);
	    	return this;
		}
		
		
		/**
		 * Toggles not mode.
		 * @return this ContainsActual object
		 */
		public final ContainsActual not()
		{
			ContainerActual.this.not();
			return this;
		}
		
		
		/**
		 * Asserts that the container contains at least one of the given elements.
		 * @param elems the elements
		 * @return this ContainsActual object
		 */
		@SafeVarargs
		public final ContainsActual someOf(ELEM... elems)
		{
			boolean found = false;
			for (ELEM elem : elems)
			{
				if (doesContain(elem))
				{
					found = true;
					break;
				}
			}
			expectTo(found, "contain some of", elems);
			return this;
		}
	}
	
	
	/**
	 * Asserts that the container is empty.
	 * @return this
	 */
	public abstract IMPL empty();
	
	
	/**
	 * Returns a IntegerActual for the elements of this container
	 * which match the predicate
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> count(Predicate<ELEM> test)
	{
		notMustBeOff();
		rejectNull(test, "test");
		int count = (int)getStream().filter(test).count();
		return new IntegerActual<>(count, self()).as(Context.call("count", test));
	}

	
	/**
	 * Returns a StreamActual for the elements of this container.
	 * @return the new actual
	 */
	public StreamActual<ELEM,IMPL,?> stream()
	{
		return new StreamActual<>(getStream(), self()).as("stream");
	}

	
	//----------------------------------
	// implementation
	//----------------------------------

	
	/**
	 * Returns if the actual value of this ContainerActual contains the given element.
	 * @param elem the element
	 * @return the contains result
	 */
	protected abstract boolean doesContain(ELEM elem);

	
	/**
	 * Returns if the actual value of this ContainerActual contains an element
	 * matching the predicate
	 * @return the stream
	 */
	protected abstract Stream<ELEM> getStream();

	
	/**
	 * Returns the container values as a set.
	 * @return the set
	 */
	protected abstract Set<ELEM> actualAsSet();
	
	
	protected void checkIndex(int index, int size)
	{
		if (!Value.indexValid(index, size))
			failure().addStmts(StmtTemplate.indexValid(index, size), null, null, Not.OFF, null).throwError();
	}
}

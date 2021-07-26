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
// Generated from PrimitiveArrayActual.txt, see build.xml/target[name="generate"]. 
// Do not modify directly.
package deepdive.actual.lang;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.CheckReturnValue;
import deepdive.actual.Actual;
import deepdive.impl.ActualChange;
import deepdive.impl.Not;
import deepdive.impl.NotMustBeOff;
import deepdive.impl.StmtTemplate;
import deepdive.impl.Value;


/**
 * An Actual implementation for primitive long arrays.
 */
public class LongArrayActual<BACK,IMPL extends LongArrayActual<BACK,IMPL>> extends Actual<long[],BACK,IMPL> 
{
	/**
	 * Creates a LongArrayActual for the given value and owner.
	 * @param value the value
	 * @param back the owner
	 */
	public LongArrayActual(long[] value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Asserts that the array is null or empty.
	 * @return this
	 */
	public IMPL blank()
	{
		long[] array = valueOrNull();  
		return expectTrue((array == null) || (array.length == 0), StmtTemplate.ASSERT_BLANK, null, null); 
	}
	
	
	/**
	 * Asserts that the array contains the expected element.
	 * @param expected the expected value
	 * @return this
	 */
	@CheckReturnValue
	@SuppressWarnings("boxing")
	public IMPL contains(long expected)
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
		notMustBeOff();
		return new ContainsActual();
	}
	
	
	/**
	 * Allows to make assertions about the contained elements of the array.
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
		 * Asserts that the array contains at least all the given elements
		 * @param elems the elements
		 * @return this ContainsActual
		 */
		@SuppressWarnings("boxing")
		public final ContainsActual allOf(long... elems)
		{
			List<Long> failing = null;
			for (long elem : elems)
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
		 * Asserts that the array exactly consists of the expected elements
		 * (ignoring order and duplicates).
		 * @param elems the elements
		 * @return this ContainsActual
		 */
		@SafeVarargs
		public final ContainsActual exactly(long... elems)
		{
			expectEqual(Value.arrayObjectToSet(elems), Value.arrayObjectToSet(valueOrNull()), "exactly");  
			return this;
		}
		
		
		/**
		 * Asserts that the array does not contain the given elements.
		 * @param elems the elements
		 * @return this ContainsActual
		 */
		@SuppressWarnings("boxing")
		public final ContainsActual noneOf(long... elems)
		{
			List<Long> failing = null;
			for (long elem : elems)
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
	    		"contain",
	    		getNotHolder().get().isOff() ? Value.onlyOrCollection(failing) : elems);
			return this;
		}
		
		
		/**
		 * Toggles not mode.
		 * @return this ContainsActual
		 */
		public final ContainsActual not()
		{
			LongArrayActual.this.not();
			return this;
		}

		
		/**
		 * Asserts that the array contains at least one of the given elements.
		 * @param elems the elements
		 * @return this ContainsActual
		 */
		public final ContainsActual someOf(long... elems)
		{
			boolean found = false;
			for (long elem : elems)
			{
				if (doesContain(elem))
				{
					found = true;
					break;
				}
			}
			expectTo(found, "contain one of", elems);
			return this;
		}
	}


	/**
	 * Asserts that the array element at the given index equals the expected value.
	 * @param index the index
	 * @param expected the expected value
	 * 
	 * @return this
	 */
	public IMPL elem(int index, long expected)
	{
		expectEqual(expected, getElem(index));
		return self();
	}


	/**
	 * Returns a LongActual for the element at the given index.
	 * @param index the index
	 * @return the new actual
	 */
	public LongActual<IMPL,?> elem(int index)
	{
		return new LongActual<>(getElem(index), self());
	}


	/**
	 * Asserts that the actual array equals the expected values.
	 * @param expected the expected values
	 * @return this
	 */
	public IMPL elems(long... expected)
	{
		expectEqual(expected, value());
		return self();
	}


	/**
	 * Asserts that the array is empty, i.e. has length 0.
	 * @return this
	 */
	public IMPL empty()
	{
		return length(0);
	}


	/**
	 * Asserts that the given index is valid, i.e. 0 &lt;= index &lt; array size.
	 * @param index the index
	 * @return this
	 */
	public IMPL indexValid(int index)
	{
		expectIndexValid(index, value().length);
		return self();
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
	 * Sorts the array.
	 * @return this
	 */
	@ActualChange
	public IMPL sort()
	{
		java.util.Arrays.sort(value());
		return self();
	}


	/**
	 * Returns the element at the given index.
	 * This method should throw an assertion error if the index is not valid. 
	 * Note to implementors: index validity should not 
	 * be based on {@link #indexValid(int)}, since that method
	 * obeys {@link Actual#not() not-mode} whereas this method
	 * should be not-mode agnostic. Use {@link #checkIndex(int, int)} to
	 * check index validity.
	 * @param index the index
	 * @return the element
	 */
	@NotMustBeOff
	protected long getElem(int index)
	{
		notMustBeOff();
		// must not use indexValid to check index validity!
		long[] array = value();
		checkIndex(index, array.length);
		return array[index];
	}	


	// don't use indexValid 
	protected int checkIndex(int index, int length)
	{
		if (!Value.indexValid(index, length))
			failure().addStmts(StmtTemplate.indexValid(index, length), null, null, Not.OFF, null).throwError();
		return index;
	}


	/**
	 * Returns if the actual value of this Actual contains the given element.
	 * @param elem the element
	 * @return contained?
	 */
	protected boolean doesContain(long elem)
	{
		for (long e : value())
		{
			if (elem == e)
				return true;
		}
		return false;
	}
}
package deepdive.actual.lang;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import deepdive.actual.util.IteratorActual;
import deepdive.impl.StmtTemplate;


public class IterableActual<ELEM,T extends Iterable<ELEM>,BACK,IMPL extends IterableActual<ELEM,T,BACK,IMPL>>
	extends ContainerActual<ELEM,T,BACK,IMPL>
{
	/**
	 * Creates a new IterableActual.
	 * @param value the value
	 * @param back the owner
	 */
	public IterableActual(T value, BACK back)
	{
		super(value, back);
	}
	

	@Override public IMPL blank()
	{
		T iterable = valueOrNull();
		return blank((iterable == null) || !iterable.iterator().hasNext()); 
	}

	
	@Override public IMPL empty()
	{
		boolean empty = !value().iterator().hasNext();
		return expectTrue(empty, StmtTemplate.ASSERT_EMPTY, null, null);
	}

	
	/**
	 * Returns an IteratorActual for an Iterator of the Iterable. 
	 * @return the new actual
	 */
	public IteratorActual<ELEM,IMPL,?> iterator()
	{
		return new IteratorActual<>(value().iterator(), self());
	}

	
	/**
	 * Asserts that the size of the Iterable equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL size(int expected)
	{
		expectEqual(expected, getSize(), "size");
		return self();
	}

	
	/**
	 * Returns an IntegerActual for the Iterable size.
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> size()
	{
		return new IntegerActual<>(getSize(), self()).as("size");
	}

	
	@Override protected boolean doesContain(ELEM elem)
	{
		for (ELEM a : value())
		{
			if (Objects.equals(elem, a))
				return true;
		}
		return false;
	}

	
	@Override protected Stream<ELEM> getStream()
	{
		return StreamSupport.stream(value().spliterator(), false);
	}


	@SuppressWarnings("unchecked")
	@Override protected Set<ELEM> actualAsSet()
	{
		T actual = value();
		if (actual instanceof Set)
			return (Set<ELEM>)actual;
		else
		{
			// LinkedHashSet has predictable iteration order
			Set<ELEM> set = new LinkedHashSet<>();
			for (ELEM elem : value())
				set.add(elem);
			return set;
		}
	}
	
	
	protected int getSize()
	{
		int size = 0;
		Iterator<ELEM> it = value().iterator();
		while(it.hasNext())
		{
			it.next();
			size++;
		}
		return size;
	}
}

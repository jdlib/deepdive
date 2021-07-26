package deepdive.actual.lang;


import deepdive.Context;


/**
 * An ArrayActual implementation for Class arrays.
 */
public class ClassArrayActual<BACK,IMPL extends ClassArrayActual<BACK,IMPL>> extends ArrayActual<Class<?>,BACK,IMPL>
{
	public ClassArrayActual(Class<?>[] actual, BACK back)
	{
		super(actual, back);
	}
	
	
	/**
	 * Returns a ClassActual for the element at the given index.
	 * @return the new actual
	 */
	@Override public ClassActual<IMPL,?> elem(int index)
	{
		return elem(index, ClassActual::new).as(Context.indexed("elem", index));
	}
}

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


import java.util.List;
import deepdive.Context;
import deepdive.actual.lang.StringActual;


/**
 * A ListActual implementation for String elements.
 */
public class StringListActual<T extends List<String>,BACK,IMPL extends StringListActual<T,BACK,IMPL>> 
	extends ListActual<String,T,BACK,IMPL>
{
	/**
	 * Creates a new StringListActual.
	 * @param value the value.
	 * @param back the owner
	 */
	public StringListActual(T value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Returns a StringActual for the element at the index.
	 * @param index the index
	 * @return the new actual
	 */
	@Override public StringActual<IMPL,?> elem(int index)
	{
		return elem(index, StringActual::new).as(Context.indexed("elem", index));
	}


	/**
	 * Returns a StringIteratorActual for an iterator of the list.
	 * @return the new actual
	 */
	@Override public StringIteratorActual<IMPL,?> iterator()
	{
		return new StringIteratorActual<>(value().iterator(), self()).as("iterator");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override public StringSwitchTo switchTo()
    {
    	return new StringSwitchTo();
    }
    

    public class StringSwitchTo extends SwitchTo
    {
    	@Override public StringIteratorActual<BACK,?> iterator()
    	{
    		return new StringIteratorActual<>(value().iterator(), backOrNull());
    	}
    }
}

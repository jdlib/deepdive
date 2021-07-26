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
package deepdive.actual.lang;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.CheckReturnValue;
import deepdive.Context;
import deepdive.actual.util.StringIteratorActual;
import deepdive.actual.util.StringListActual;
import deepdive.impl.Value;


/**
 * An ArrayActual implementation for String arrays.
 */
public class StringArrayActual<BACK,IMPL extends StringArrayActual<BACK,IMPL>> 
	extends ArrayActual<String,BACK,IMPL>
{
	/**
	 * Creates a StringArrayActual for the given elements.
	 * @param back the owner
	 * @param actual the array elements.
	 */
	public StringArrayActual(String[] actual, BACK back)
	{
		super(actual, back);
	}

	
	/**
	 * Returns a StringActual for the element at the given index.
	 * @return the new actual
	 */
	@Override public StringActual<IMPL,?> elem(int index)
	{
		return elem(index, StringActual::new).as(Context.indexed("elem", index));
	}

	
	/**
	 * Returns a StringIteratorActual.
	 */
	@Override public StringIteratorActual<IMPL,?> iterator()
	{
		return new StringIteratorActual<>(Value.arrayIterator(value()), self());
	}


	/**
	 * {@inheritDoc}
	 */
	@CheckReturnValue
	@Override public StringSwitchTo switchTo()
    {
    	return new StringSwitchTo();
    }
    

    public class StringSwitchTo extends SwitchTo
    {
        protected StringSwitchTo()
    	{
    	}

    
    	@Override public StringIteratorActual<BACK,?> iterator()
    	{
    		return new StringIteratorActual<>(Value.arrayIterator(value()), backOrNull()).as("iterator");
    	}
    	
    	
		@Override public StringListActual<?,BACK,?> list() 
    	{
    		List<String> list = new ArrayList<>();
    		Collections.addAll(list, value());
    		return new StringListActual<>(list, backOrNull()).as("list");
    	}
    }
}

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


import java.util.Iterator;
import deepdive.actual.lang.StringActual;


/**
 * A IteratorActual implementation for String Iterators.
 */
public class StringIteratorActual<BACK,IMPL extends StringIteratorActual<BACK,IMPL>> extends IteratorActual<String,BACK,IMPL>
{
	/**
	 * Creates a new StringIteratorActual.
	 * @param value the value.
	 * @param back the owner
	 */
	public StringIteratorActual(Iterator<String> value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Returns a StringActual for the next element.
	 * @return the new actual
	 */
	@Override public StringActual<IMPL,?> next()
	{
		return new StringActual<>(value().next(), self());
	}
}

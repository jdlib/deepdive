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
package deepdive.impl;


import java.lang.reflect.Array;
import java.util.AbstractList;


/**
 * GenericArrayList presents an Object or primitive array as List.  
 */
class GenericArrayList extends AbstractList<Object>
{
	public GenericArrayList(Object array)
	{
		array_  = Check.notNull(array, "array");
		size_   = Array.getLength(array);
	}
	
	
	@Override public Object get(int index)
	{
		return Array.get(array_, index);
	}


	@Override public int size()
	{
		return size_;
	}
	
	
	private final Object array_;
	private final int size_;
}
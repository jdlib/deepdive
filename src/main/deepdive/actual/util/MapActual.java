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


import java.util.Collection;
import java.util.Map;
import java.util.Set;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.lang.IntegerActual;
import deepdive.impl.StmtTemplate;


/**
 * An Actual implementation for {@link Map} objects.
 * @param <K> the type of the map keys
 * @param <V> the type of the map values
 * @param <T> the type of the Map implementation
 * @param <BACK> the type of the owner of the MapActual
 * @param <IMPL> the type of the concrete MapActual implementation 
 */
public class MapActual<K,V,T extends Map<K,V>,BACK,IMPL extends MapActual<K,V,T,BACK,IMPL>> extends Actual<T,BACK,IMPL>
{
	/**
	 * Creates a new MapActual.
	 * @param value the value.
	 * @param back the owner
	 */
	public MapActual(T value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that the Map is either null or empty.
	 * @return this
	 */
	public IMPL blank()
	{
		Map<?,?> map = valueOrNull();
		return expectTrue((map == null) || (map.size() == 0), StmtTemplate.ASSERT_BLANK, null, null); 
	}

	
	/**
	 * Clears the Map. 
	 * @return this
	 */
	public IMPL clear()
	{
		value().clear();
		return self();
	}

	
	/**
	 * Asserts that the Map is empty.
	 * @return this
	 */
	public IMPL empty()
	{
		expectTrue(value().isEmpty(), "empty");
		return self();
	}

	
	/**
	 * Asserts that the Map contains the key.
	 * @param key a key
	 * @return this
	 */
	public IMPL containsKey(K key)
	{
		expectTrue(value().containsKey(key), "containsKey");
		return self();
	}

	
	/**
	 * Asserts that the Map contains the value.
	 * @param expected the value
	 * @return this
	 */
	public IMPL containsValue(V expected)
	{
		expectTrue(value().containsValue(expected), "containsValue");
		return self();
	}
	
	
	/**
	 * Returns a SetActual for the keyset of the map.
	 * @return the new Actual
	 */
	public SetActual<K,Set<K>,IMPL,?> keySet()
	{
		return new SetActual<>(value().keySet(), self()).as("keySet");
	}

	
	/**
     * Removes the mapping for a key from this map if it is present.
	 * @param key a key
     * @return this
	 */
	public IMPL remove(K key)
	{
		value().remove(key);
		return self();
	}

	
	/**
	 * Asserts that the Map has the expected size.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL size(int expected)
	{
		expectEqual(expected, value().size(), "size");
		return self();
	}

	
	/**
	 * Returns an IntegerActual for the map size.
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> size()
	{
		return new IntegerActual<>(value().size(), self()).as("size");
	}
	
	
	/**
	 * Asserts that the value mapped to the key equals the expected value.
	 * @param key a key
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL value(K key, V expected) 
	{
		expectEqual(expected, value().get(key), Context.call("value", key));
		return self();
	}
	
	
	/**
	 * Returns an Actual for the value mapped to given key.
	 * @param key a key
	 * @return the new actual
	 */
	public Actual<V,IMPL,?> value(K key)
	{
		return new Actual<>(value().get(key), self()).as(Context.call("value", key));
	}
	
	
	/**
	 * Returns a CollectionActual for the values of the map.
	 * @return the new actual
	 */
	public CollectionActual<V,Collection<V>,IMPL,?> values()
	{
		return new CollectionActual<>(value().values(), self()).as("values");
	}
}

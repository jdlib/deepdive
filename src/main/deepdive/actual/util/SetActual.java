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


import java.util.Set;
import deepdive.impl.Value;


/**
 * An Actual implementation for {@link Set} objects.
 * @param <ELEM> the type of the Set elements
 * @param <T> the type of the Set implementation
 * @param <BACK> the type of the owner of the SetActual
 * @param <IMPL> the type of the SetActual implementation 
 */
public class SetActual<ELEM,T extends Set<ELEM>,BACK,IMPL extends SetActual<ELEM,T,BACK,IMPL>> extends CollectionActual<ELEM,T,BACK,IMPL>
{
	/**
	 * Creates a new SetActual
	 * @param value the actual set.
	 * @param back the owner
	 */
	public SetActual(T value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Asserts that the set consists of the expected elements.
	 * @param expected the expected elements
	 * @return this
	 */
	@SafeVarargs
	public final IMPL elems(ELEM... expected)
	{
		Set<Object> expectedSet = expected != null ? Value.arrayToSet(expected) : null;
		expectEqual(expectedSet, value(), "elems");
		return self();
	}
}

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
package deepdive.examples.tolkien.test;


import java.util.List;
import deepdive.Context;
import deepdive.actual.util.ListActual;
import deepdive.examples.tolkien.TolkienCharacter;


public class TolkienCharacterListActual extends ListActual<TolkienCharacter,List<TolkienCharacter>,Void,TolkienCharacterListActual>
{
	public TolkienCharacterListActual(List<TolkienCharacter> actual)
	{
		super(actual, null);
	}
	
	
	/**
	 * Returns an Actual for the element at the given index.
	 */
	@Override public TolkienCharacterActual<TolkienCharacterListActual,?> elem(int index)
	{
		return elem(index, TolkienCharacterActual::new).as(Context.indexed("elem", index));
	}
}

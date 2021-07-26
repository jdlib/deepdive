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


import deepdive.actual.Actual;
import deepdive.actual.lang.IntegerActual;
import deepdive.actual.lang.StringActual;
import deepdive.examples.tolkien.Alignment;
import deepdive.examples.tolkien.TolkienCharacter;
import deepdive.examples.tolkien.Type;


public class TolkienCharacterActual<BACK,IMPL extends TolkienCharacterActual<BACK,IMPL>> 
	extends Actual<TolkienCharacter,BACK,IMPL>
{
	public TolkienCharacterActual(TolkienCharacter actual, BACK back)
	{
		super(actual, back);
	}
	
	
	public IMPL name(String name)
	{
		expectEqual(name, value().getName(), "name");
		return self();
	}
	
	
	public StringActual<IMPL,?> name()
	{
		return new StringActual<>(value().getName(), self()).as("name");
	}

	
	public IMPL age(int age)
	{
		expectEqual(age, value().getAge(), "age");
		return self();
	}
	
	
	public IntegerActual<IMPL,?> age()
	{
		return new IntegerActual<>(value().getAge(), self()).as("age");
	}

	
	public IMPL type(Type type)
	{
		expectSame(type, value().getType(), "type");
		return self();
	}

	
	public TypeActual<IMPL> type()
	{
		return new TypeActual<>(value().getType(), self());
	}

	
	public IMPL alignment(Alignment alignment)
	{
		expectSame(alignment, value().getType().alignment, "alignment");
		return self();
	}
}

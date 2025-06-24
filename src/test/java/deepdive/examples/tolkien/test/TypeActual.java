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
import deepdive.examples.tolkien.Alignment;
import deepdive.examples.tolkien.Type;


public class TypeActual<BACK> extends Actual<Type,BACK,TypeActual<BACK>>
{
	public TypeActual(Type value, BACK back)
	{
		super(value, back);
	}
	
	
	public TypeActual<BACK> name(String expected)
	{
		expectEqual(expected, value().name(), "name");
		return this;
	}
	
	
	public TypeActual<BACK> immortal()
	{
		expectTrue(value().immortal, "immortal");
		return this;
	}
	
	
	public TypeActual<BACK> alignment(Alignment expected)
	{
		expectEqual(expected, value().alignment, "alignment");
		return this;
	}
}

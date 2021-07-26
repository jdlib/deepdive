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
package deepdive.actual;


import deepdive.actual.lang.ComparableActual;
import deepdive.actual.lang.IntegerActual;
import deepdive.actual.lang.StringActual;


/**
 * ActualMixin is base interface for partial Actual implementations.
 * Given a specific class to be tested one will usually provide an Actual implementation for that class.
 * But given an interface which defines a certain functionality which is then
 * implemented by multiple classes we don't want to repeat implementation of the assertion methods
 * corresponding to the interface methods.<p>
 * In this case we can provide a partial Actual implementation for the interface,
 * not deriving from {@link Actual} but instead from ActualMixin. Each final Actual implementation
 * class can then inherit from that mixin implementation.<p>
 * Example: {@link ComparableActual} with provides a partial implementation for {@link Comparable}
 * which can then be used in any Actual implementation for types which inherit from Comparable, e.g.
 * {@link StringActual} or {@link IntegerActual} .
 */
public interface ActualMixin<T,IMPL extends Actual<T,?,IMPL>> 
{
	public static <T,IMPL extends Actual<T,?,IMPL>> Actual.Internals<IMPL> internals(ActualMixin<T,IMPL> mixin)
	{
		@SuppressWarnings("unchecked")
		Actual<T,?,IMPL> actual = (Actual<T,?,IMPL>)mixin;
		return actual.internals();
	}
	
	
	public T value();


	public T valueOrNull();
}

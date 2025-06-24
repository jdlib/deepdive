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
package deepdive.actual.java.lang.reflect;


import java.lang.reflect.Member;
import deepdive.actual.Actual;
import deepdive.actual.ActualMixin;
import deepdive.actual.Actual.Internals;
import deepdive.actual.java.lang.ClassActual;
import deepdive.actual.java.lang.StringActual;


/**
 * A ActualMixin interface for {@link Member}.
 */
public interface MemberActual<T extends Member,IMPL extends Actual<T,?,IMPL>> extends ActualMixin<T,IMPL>
{
	public default IMPL name(String expected)
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		i.expectEqual(expected, value().getName(), "name");
		return i.self(); 
	}

	
	public default StringActual<IMPL,?> name()
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		return new StringActual<>(value().getName(), i.self()).as("name");
	}
	

	public default ModifierActual<IMPL,?> modifiers()
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		return new ModifierActual<>(value().getModifiers(), i.self()).as("modifiers");
	}
	
	
	public default IMPL declaringClass(Class<?> expected)
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		i.expectSame(expected, value().getDeclaringClass(), "declaringClass");
		return i.self();
	}


	public default ClassActual<IMPL,?> declaringClass()
	{
		Internals<IMPL> i = ActualMixin.internals(this);
		return new ClassActual<>(value().getDeclaringClass(), i.self()).as("declaringClass");
	}
}

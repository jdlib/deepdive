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


import java.util.function.Function;


/**
 * StmtValue is a service which returns when given the expected and actual
 * value of an assertion returns a object.
 * It provides a flexible way to refer to actual value, expected value or
 * further derived values in StmtTemplates.
 */
public interface StmtValue
{
	public Object get(Object expected, Object actual);
	
	
	public default StmtValue extracting(Function<Object,Object> fn)
	{
		Function<Object,Object> fRaw = Check.notNull(fn, "fn");
		return (e,a) -> {
			Object temp = get(e,a);
			return temp != null ? fRaw.apply(temp) : null;
		};
	}
	
	
	public static StmtValue named(String name, StmtValue value)
	{
		return new NamedStmtValue(name, value);
	}
	
	
	public static final StmtValue NONE 			= named("NONE", (e,a) -> Stmt.NONE_VALUE);
	public static final StmtValue NULL 			= named("NULL", (e,a) -> null);
	public static final StmtValue EXPECTED 		= named("EXPECTED", (e,a) -> e);
	public static final StmtValue ACTUAL 		= named("ACTUAL",   (e,a) -> a);
	public static final StmtValue ACTUAL_CLASS 	= named("ACTUAL_CLASS", ACTUAL.extracting(Object::getClass));
}


class NamedStmtValue implements StmtValue
{
	public NamedStmtValue(String name, StmtValue impl)
	{
		name_ 	= Check.notNull(name, "name");
		impl_	= Check.notNull(impl, "impl");
	}
	
	
	@Override public Object get(Object expected, Object actual)
	{
		return impl_.get(expected, actual);
	}
	
	
	@Override public StmtValue extracting(Function<Object,Object> fn)
	{
		return impl_.extracting(fn);
	}
	
	
	@Override public String toString()
	{
		return name_;
	}
	
	private final String name_;
	private final StmtValue impl_;
}


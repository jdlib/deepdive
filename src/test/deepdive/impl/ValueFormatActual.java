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


import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.impl.ValueFormat;


public class ValueFormatActual<BACK,IMPL extends ValueFormatActual<BACK,IMPL>> extends Actual<ValueFormat,BACK,IMPL>
{
	public static ValueFormatActual<?,?> of(ValueFormat value)
	{
		return new ValueFormatActual<>(value, null);
	}

	
	public ValueFormatActual(ValueFormat value, BACK back)
	{
		super(value, back);
	}
	
	
	public IMPL shorten(String s, int maxLength, String expected)
	{
		expectEqual(expected, value().shorten(s, maxLength), Context.call("shorten", s, Integer.valueOf(maxLength)));
		return self();
	}

	
	public IMPL format(Object value, String expected)
	{
		expectEqual(expected, value().format(value), "format");
		return self();
	}
}

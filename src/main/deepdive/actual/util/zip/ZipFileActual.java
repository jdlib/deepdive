/*
 * Copyright (c) 2022 jdlib, https://github.com/jdlib
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
package deepdive.actual.util.zip;


import java.util.zip.ZipFile;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.lang.LongActual;
import deepdive.actual.lang.StringActual;

public class ZipFileActual<BACK,IMPL extends ZipFileActual<BACK,IMPL>> extends Actual<ZipFile,BACK,IMPL>
{
	public ZipFileActual(ZipFile value, BACK back)
	{
		super(value, back);
	}


	/**
	 * Returns a StringActual for the comment.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> comment()
	{
		return new StringActual<>(value().getComment(), self()).as("comment");
	}
	
	
	/**
	 * Returns a StringActual for the name.
	 * @param name the name
	 * @return the new actual
	 */
	public ZipEntryActual<IMPL,?> entry(String name)
	{
		ZipFile value = value();
		return new ZipEntryActual<>(value.getEntry(name), self(), value::getInputStream).as(Context.call("entry", name));
	}


	/**
	 * Asserts that the name equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
    public IMPL name(String expected) 
    {
		expectEqual(expected, value().getName(), "name");
		return self();
	}
	
    
	/**
	 * Returns a StringActual for the name.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> name()
	{
		return new StringActual<>(value().getName(), self()).as("name");
	}


	/**
	 * Asserts that the size of the Zipfile equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL size(int expected)
	{
		expectEqual(expected, value().size(), "size");
		return self();
	}

	
	/**
	 * Returns a LongActual for the size.
	 * @return the new actual
	 */
	public LongActual<IMPL,?> size()
	{
		return new LongActual<>(value().size(), self()).as("size");
	}
}

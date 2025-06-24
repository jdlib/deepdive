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
package deepdive.actual.java.util.zip;


import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import javax.annotation.CheckReturnValue;
import deepdive.actual.Actual;
import deepdive.actual.java.io.ByteContentBuilder;
import deepdive.actual.java.lang.LongActual;
import deepdive.actual.java.lang.StringActual;
import deepdive.function.CheckedFunction;
import deepdive.impl.NotMustBeOff;


/**
 * An Actual implementation for {@link ZipEntry} objects.
 */
public class ZipEntryActual<BACK,IMPL extends ZipEntryActual<BACK,IMPL>> extends Actual<ZipEntry,BACK,IMPL>
{
	public ZipEntryActual(ZipEntry value, BACK back)
	{
		this(value, back, null);
	}
	
	
	public ZipEntryActual(ZipEntry value, BACK back, CheckedFunction<ZipEntry,InputStream,IOException> inFactory)
	{
		super(value, back);
		inFactory_ = inFactory;
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
	 * Returns a LongActual for the size.
	 * @return the new actual
	 */
	public LongActual<IMPL,?> compressedSize()
	{
		return new LongActual<>(value().getCompressedSize(), self()).as("compressedSize");
	}


	/**
	 * Returns a LongActual for the crc.
	 * @return the new actual
	 */
	public LongActual<IMPL,?> crc()
	{
		return new LongActual<>(value().getCrc(), self()).as("crc");
	}
	
	
	/**
	 * Tests  if the entry is a directory.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL isDirectory(boolean expected)
	{
		expectEqual(expected, value().isDirectory(), "isDirectory");
		return self();
	}

	
	/**
	 * Asserts that the method equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
    public IMPL method(int expected) 
    {
		expectEqual(expected, value().getMethod(), "method");
		return self();
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
	 * Returns a ByteContentBuilder builder to access the content of the ZipEntry.
	 * @return the builder
	 */
	@CheckReturnValue
	@NotMustBeOff
	public ByteContentBuilder<IMPL,IOException> read()
	{
		notMustBeOff();
		
		return new ByteContentBuilder<>(self(), true, this::getInputStream);
	}

	
	/**
	 * Returns a LongActual for the size.
	 * @return the new actual
	 */
	public LongActual<IMPL,?> size()
	{
		return new LongActual<>(value().getSize(), self()).as("size");
	}


	/**
	 * Returns a LongActual for the time.
	 * @return the new actual
	 */
	public LongActual<IMPL,?> time()
	{
		return new LongActual<>(value().getTime(), self()).as("time");
	}
	
	
	private InputStream getInputStream() throws IOException 
	{
		if (inFactory_ == null)
			fail("no factory for InputStream specified in ctor");
		return inFactory_.apply(value());
	}
	
	
	private final CheckedFunction<ZipEntry,InputStream,IOException> inFactory_;
}

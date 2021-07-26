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
package deepdive.actual.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.CheckReturnValue;
import deepdive.actual.lang.StringActual;
import deepdive.actual.util.StringIteratorActual;
import deepdive.actual.util.StringListActual;
import deepdive.actual.util.stream.StreamActual;
import deepdive.function.CheckedSupplier;
import deepdive.impl.Check;
import deepdive.impl.Value;


/**
 * CharContent is a builder to access the character content provided by a Reader.
 * @param<BACK> the type of the back object which owns the resulting actual
 */
public abstract class CharContentBuilder<BACK,E extends Exception>
{
	/**
	 * The final builder step to select the reprsentation of the char content.
	 */
	public interface To<BACK,E extends Exception>
	{
		/**
		 * Reads the content into a String and returns it as StringActual.
		 * @return the new StringActual
		 * @throws E if reading of the content fails
		 * @throws IOException if reading of the content fails
		 */
		public StringActual<BACK,?> string() throws E, IOException;

		
		/**
		 * Reads the content, splits it into lines and returns a StringListActual for these lines.
		 * @return the new actual
		 * @throws E if reading of the content fails
		 * @throws IOException if reading of the content fails
		 */
		public StringListActual<?,BACK,?> lines() throws E, IOException;

		
		/**
		 * Reads the content, splits it into lines and returns a StringIteratorActual for these lines.
		 * @return the new actual
		 * @throws E if reading of the content fails
		 * @throws IOException if reading of the content fails
		 */
		public StringIteratorActual<BACK,?> lineIterator() throws E, IOException;

	
		/**
		 * Reads the content, splits it into lines and returns a StreamActual for these lines.
		 * @return the new actual
		 * @throws E if reading of the content fails
		 * @throws IOException if reading of the content fails
		 */
		public StreamActual<String,BACK,?> lineStream() throws E, IOException;
	}
	
	
	public static <BACK> CharContentBuilder<BACK,RuntimeException> of(BACK back, boolean close, Reader reader)
	{
		Check.notNull(reader, "reader");
		return of(back, close, () -> reader);
	}
	
	
	public static <BACK> CharContentBuilder<BACK,RuntimeException> of(BACK back, boolean close, InputStream in, Charset charset)
	{
		Check.notNull(in, "in");
		Check.notNull(charset, "charset");
		return of(back, close, () -> new InputStreamReader(in, charset));
	}
	
	
	public static <BACK> CharContentBuilder<BACK,IOException> of(BACK back, File file, Charset charset)
	{
		Check.notNull(file, "file");
		Check.notNull(charset, "charset");
		return of(back, true, () -> new InputStreamReader(new FileInputStream(file), charset));
	}

	
	public static <BACK,E extends Exception> CharContentBuilder<BACK,E> of(BACK back, boolean close, CheckedSupplier<Reader,E> readerSupplier)
	{
		return new CharContentBuilderImpl<>(back, close, readerSupplier, 0);
	}
	

	/**
	 * Limits the content size to the given maximum length.
	 * @param max the maximum number of chars to read. If &lt;= 0 no limitation is applied.
	 * @return this
	 */
	@CheckReturnValue
	public CharContentBuilder<BACK,E> max(long max)
	{
		max_ = max;
		return this;
	}
	
	
	@CheckReturnValue
	public abstract To<BACK,E> to();
	
	
	/**
	 * Reads the content of a Reader into a String.
	 * @param reader the Reader
	 * @param max the maximum number of char to read, or &lt;0 if unlimited
	 * @return the String
	 * @throws IOException thrown if an I/O error occurs
	 */
	public static String read(Reader reader, long max) throws IOException
	{
		Check.notNull(reader, "reader");
		StringBuilder sb = new StringBuilder();
		
		long toRead = max <= 0 ? Long.MAX_VALUE : max;
		char[] buffer = new char[2048];
		while (toRead > 0)
		{
			int read = reader.read(buffer);
			if (read < 0)
				toRead = 0;
			else
			{
				sb.append(buffer, 0, read);
				toRead -= read;
			}
		}
		return sb.toString();
	}

	
	protected long max_;
}


class CharContentBuilderImpl<BACK,E extends Exception> extends CharContentBuilder<BACK,E> implements CharContentBuilder.To<BACK,E>
{
	CharContentBuilderImpl(BACK back, boolean close, CheckedSupplier<Reader,E> readerSupplier, long max)
	{
		back_ 				= back;
		close_				= close;
		readerSupplier_   	= Check.notNull(readerSupplier, "readerSupplier");
		max_				= max;
	}


	@Override public To<BACK,E> to()
	{
		return this;
	}
	
	
	
	@Override public StringActual<BACK,?> string() throws E, IOException
	{
		return new StringActual<>(read(), back_).as("text");
	}
	
	
	@Override public StringListActual<?,BACK,?> lines() throws E, IOException
	{
		// don't use Arrays.asList since we want a modifiable list
		List<String> list = new ArrayList<>();
		Collections.addAll(list, readLines());
		return new StringListActual<>(list, back_).as("lines");
	}

	
	@Override public StringIteratorActual<BACK,?> lineIterator() throws E, IOException
	{
		return new StringIteratorActual<>(Value.arrayIterator(readLines()), back_).as("lineIterator");
	}

	
	@Override public StreamActual<String,BACK,?> lineStream() throws E, IOException
	{
		return new StreamActual<>(Stream.of(readLines()), back_).as("lineStream");
	}
	
	
	private String[] readLines() throws E, IOException
	{
		return StringActual.splitLines(read());
	}

	
	private String read() throws E, IOException
	{
		Reader reader = null;
		try
		{
			reader = readerSupplier_.get();
			return read(reader, max_);
		}
		finally
		{
			if (close_ && (reader != null))
				reader.close();
		}
	}
	
	
	private final BACK back_;
	private final CheckedSupplier<Reader,E> readerSupplier_;
	private final boolean close_;
}
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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import javax.annotation.CheckReturnValue;
import deepdive.actual.lang.ByteArrayActual;
import deepdive.function.CheckedSupplier;
import deepdive.impl.Check;


/**
 * ByteContentBuilder is a builder to access the content provided by an InputStream.
 */
public class ByteContentBuilder<BACK,E extends Exception>
{
	public static <BACK> ByteContentBuilder<BACK,RuntimeException> of(BACK back, boolean close, InputStream in)
	{
		Check.notNull(in, "in");
		return new ByteContentBuilder<>(back, close, () -> in);
	}
	
	
	public static <BACK> ByteContentBuilder<BACK,IOException> of(BACK back, File file)
	{
		Check.notNull(file, "file");
		return new ByteContentBuilder<>(back, true, () -> new FileInputStream(file));
	}
	
	
	public ByteContentBuilder(BACK back, boolean close, CheckedSupplier<InputStream,E> inSupplier)
	{
		back_ 		= back;
		inSupplier_ = Check.notNull(inSupplier, "inSupplier");
		close_		= close;
	}
	
	
	/**
	 * Limits the content size to the given maximum length.
	 * @param max the maximum content length. If the content is read as bytes this defines the maximum number of bytes
	 * 		else the maximum number of chars
	 * @return this
	 */
	@CheckReturnValue
	public ByteContentBuilder<BACK,E> max(long max)
	{
		max_ = max;
		return this;
	}
	
	
	/**
	 * Returns a CharContentBuilder to access the InputStream content
	 * decoded with the given charset.
	 * @param charset a charset
	 * @return the CharContentBuilder
	 */
	@CheckReturnValue
	public CharContentBuilderImpl<BACK,E> chars(Charset charset)
	{
		Check.notNull(charset, "charset");
		CheckedSupplier<InputStream,E> in 	= inSupplier_;
		CheckedSupplier<Reader,E> reader 	= () -> new InputStreamReader(in.get(), charset);
		return new CharContentBuilderImpl<>(back_, close_, reader, max_);
	}


	/**
	 * Reads the content bytes and returns a ByteArrayActual for the result.
	 * @return the new actual
	 * @throws E if reading of the content fails
	 * @throws IOException if reading of the content fails
	 */
	public ByteArrayActual<BACK,?> bytes() throws E, IOException
	{
		return new ByteArrayActual<>(read(max_), back_);
	}
	
	
	private byte[] read(long max) throws E, IOException
	{
		InputStream in = null;
		try
		{
			in = Check.notNull(inSupplier_.get(), "in");
			return read(in, max);
		}
		finally
		{
			if (close_ && (in != null))
				in.close();
		}
	}

	
	/**
	 * Reads the content of an InputStream into a byte array.
	 * @param in the InputStream
	 * @param max the maximum number of bytes to read, or &lt;0 if unlimited
	 * @return the byte array
	 * @throws IOException thrown if an I/O error occurs
	 */
	public static byte[] read(InputStream in, long max) throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		long toRead 	= max <= 0 ? Long.MAX_VALUE : max;
		byte[] buffer 	= new byte[2048];
		while (toRead > 0)
		{
			int read = in.read(buffer);
			if (read < 0)
				toRead = 0;
			else
			{
				out.write(buffer, 0, read);
				toRead -= read;
			}
		}
		
		return out.toByteArray();
	}

	
	private final BACK back_;
	private final CheckedSupplier<InputStream,E> inSupplier_;
	private final boolean close_;
	private long max_;
}

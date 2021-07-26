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
package deepdive.actual.net;


import java.io.IOException;
import java.net.URL;
import javax.annotation.CheckReturnValue;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.io.ByteContentBuilder;
import deepdive.actual.lang.StringActual;
import deepdive.actual.util.MapActual;
import deepdive.impl.NotMustBeOff;


/**
 * An Actual implementation for {@link URL} objects.
 */
public class URLActual<BACK,IMPL extends URLActual<BACK,IMPL>> extends Actual<URL,BACK,IMPL> 
{
	/**
	 * Creates a new URLActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public URLActual(URL value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that the authority of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL authority(String expected)
	{
		expectEqual(expected, value().getAuthority(), "authority");
		return self();
	}
	
	
	/**
	 * Returns an Actual for URL content.
	 * @return the new actual
	 * @throws IOException thrown when the content cannot be read
	 */
	public Actual<Object,IMPL,?> content() throws IOException
	{
		return new Actual<>(value().getContent(), self()).as("content");
	}
	

	/**
	 * Returns an Actual for the URL content.
	 * @param classes Java classes
	 * @return the new actual
	 * @throws IOException thrown when the content cannot be read
	 */
	public Actual<Object,IMPL,?> content(Class<?>... classes) throws IOException
	{
		return new Actual<>(value().getContent(classes), self()).as(Context.call("content", (Object[])classes));
	}

	
	/**
	 * Asserts that the default port of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL defaultPort(int expected)
	{
		expectEqual(expected, value().getDefaultPort(), "defaultPort");
		return self();
	}
	
	
	/**
	 * Asserts that the external form of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL externalForm(String expected)
	{
		expectEqual(expected, value().toExternalForm(), "externalForm");
		return self();
	}
	
	
	/**
	 * Asserts that the file of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL file(String expected)
	{
		expectEqual(expected, value().getFile(), "file");
		return self();
	}
	
	
	/**
	 * Asserts that the host of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL host(String expected)
	{
		expectEqual(expected, value().getHost(), "host");
		return self();
	}
	

	/**
	 * Returns a StringActual for the actual URL host.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> host()
	{
		return new StringActual<>(value().getHost(), self()).as("host");
	}
	
	
	/**
	 * Asserts that the path of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL path(String expected)
	{
		expectEqual(expected, value().getPath(), "path");
		return self();
	}
	

	/**
	 * Returns a StringActual for the actual URL path.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> path()
	{
		return new StringActual<>(value().getPath(), self()).as("path");
	}
	
	
	/**
	 * Returns a StringActual for the actual URL port.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL port(int expected)
	{
		expectEqual(expected, value().getPort(), "port");
		return self();
	}
	
	
	/**
	 * Asserts that the path of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL protocol(String expected)
	{
		expectEqual(expected, value().getProtocol(), "protocol");
		return self();
	}
	
	
	/**
	 * Asserts that the query of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL query(String expected)
	{
		expectEqual(expected, value().getQuery(), "query");
		return self();
	}
	
	
	/**
	 * Returns a StringActual for the query part.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> query()
	{
		return new StringActual<>(value().getQuery(), self()).as("query");
	}
	
	
	/**
	 * Splits the query string into parameter names and values and returns 
	 * them as a MapActual.
	 * @return the new actual
	 */
	public MapActual<String,String,?,IMPL,?> queryMap()
	{
		return new MapActual<>(URIActual.queryMap(value().getQuery()), self());
	}

	
	/**
	 * Returns a ByteContentBuilder builder to access the content of the URL.
	 * @return the builder
	 */
	@CheckReturnValue
	@NotMustBeOff
	public ByteContentBuilder<IMPL,IOException> read()
	{
		notMustBeOff();
		URL url = value();
		return new ByteContentBuilder<>(self(), true, () -> url.openStream());
	}
	
	
	/**
	 * Asserts that the ref (anchor) of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL ref(String expected)
	{
		expectEqual(expected, value().getRef(), "ref");
		return self();
	}

	
	/**
	 * Asserts that the user info of the URL equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL userInfo(String expected)
	{
		expectEqual(expected, value().getUserInfo(), "userInfo");
		return self();
	}
}

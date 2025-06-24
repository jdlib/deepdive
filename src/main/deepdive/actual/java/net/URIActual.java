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
package deepdive.actual.java.net;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import deepdive.actual.Actual;
import deepdive.actual.java.lang.StringActual;
import deepdive.actual.java.util.MapActual;


/**
 * An Actual implementation for {@link URI} objects.
 */
public class URIActual<BACK,IMPL extends URIActual<BACK,IMPL>> extends Actual<URI,BACK,IMPL> 
{
	/**
	 * Creates a new URIActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public URIActual(URI value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that the authority of the actual URI equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL authority(String expected)
	{
		expectEqual(expected, value().getAuthority(), "authority");
		return self();
	}
	
	
	/**
	 * Asserts that the fragment of the actual URI equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL fragment(String expected)
	{
		expectEqual(expected, value().getFragment(), "fragment");
		return self();
	}
	
	
	/**
	 * Asserts that the host of the actual URI equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL host(String expected)
	{
		expectEqual(expected, value().getHost(), "host");
		return self();
	}
	

	/**
	 * Returns a StringActual for the actual URI host.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> host()
	{
		return new StringActual<>(value().getHost(), self()).as("host");
	}
	
	
	/**
	 * Asserts that the path of the actual URI equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL path(String expected)
	{
		expectEqual(expected, value().getPath(), "path");
		return self();
	}
	

	/**
	 * Returns a StringActual for the actual URI path.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> path()
	{
		return new StringActual<>(value().getPath(), self()).as("path");
	}

	
	/**
	 * Asserts that the port of the actual URI equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL port(int expected)
	{
		expectEqual(expected, value().getPort(), "port");
		return self();
	}
	
	
	/**
	 * Asserts that the query of the actual URI equals the expected value.
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
		return new MapActual<>(queryMap(value().getQuery()), self());
	}

	
	/**
	 * Asserts that the scheme of the actual URI equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL scheme(String expected)
	{
		expectEqual(expected, value().getScheme(), "scheme");
		return self();
	}
	
	
	/**
	 * Asserts that the userInfo of the actual URI equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL userInfo(String expected)
	{
		expectEqual(expected, value().getUserInfo(), "userInfo");
		return self();
	}


	/**
	 * Asserts that the URI is absolute.
	 * @return this
	 */
	public IMPL isAbsolute()
	{
		expectTrue(value().isAbsolute(), "isAbsolute");
		return self();
	}


	/**
	 * Asserts that the URI is opaque.
	 * @return this
	 */
	public IMPL isOpaque()
	{
		expectTrue(value().isOpaque(), "isOpaque");
		return self();
	}


	/**
	 * Splits the query string into parameter names and values and returns 
	 * them as a Map.
	 * @param query a query string
	 * @return the map, mapping parameter names to parameter value
	 */
	public static Map<String,String> queryMap(String query)
	{
		Map<String,String> map = new HashMap<>();
		if (query != null)
		{
			String[] params = query.split("&");
			for (String param : params)
			{
				String[] nv = param.split("=");
				map.put(nv[0], nv.length > 1 ? nv[1] : "");
			}
		}
		return map;
	}
}

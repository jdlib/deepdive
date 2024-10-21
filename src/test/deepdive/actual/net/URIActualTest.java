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


import static deepdive.ExpectThat.*;
import java.net.URI;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link URIActual}.
 */
public class URIActualTest extends AbstractActualTest
{
	@Test public void test() throws Exception
	{
		URI uri = new URI("http://example.org:8001/some/path?q1=hello&q2=world&q3#frag");
		expectThat(uri)
			.isAbsolute()
			.scheme("http")
			.port(8001)
			.host("example.org")
			.path("/some/path")
			.query("q1=hello&q2=world&q3")
			.query().contains("hello").back()
			.queryMap()
				.value("q1", "hello")
				.value("q2", "world")
				.value("q3", "")
				.back()
			.fragment("frag");
	}
}

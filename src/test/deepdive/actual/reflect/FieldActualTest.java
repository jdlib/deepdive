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
package deepdive.actual.reflect;


import static deepdive.ExpectThat.*;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;
import deepdive.actual.anno.TestResource;


/**
 * Tests {@link FieldActual}.
 */
public class FieldActualTest extends AbstractActualTest
{
	@Test public void test() throws Exception
	{
		expectThat(getClass()).fieldDeclared("field")
			.annotation(TestResource.class)
				.attr("name", "alpha")
				.attr("description", "d")
				.back()
			.modifiers()
				.isFinal()
				.isPrivate( )
				.not().isStatic()
				.back()
			.name("field")
			.type(String.class)
			.not().type(Number.class)
			.type()
				.contained().in(String.class, Number.class)
				.back();
	}
	
	
	@TestResource(description="d", name="alpha")
	private final String field = "abc";
}

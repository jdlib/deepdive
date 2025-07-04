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
package deepdive.actual.java.lang.annotation;


import static deepdive.ExpectThat.*;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link AnnotationActual}.
 */
@TestResource(shareable=false, name="Test")
public class AnnotationActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(getClass())
			.annotation(TestResource.class)
				.attr("name", TestResource::name, "Test")
				.attr("shareable", TestResource::shareable)
					.isA(Boolean.class)
					.equal(Boolean.FALSE)
					.back()
				.not().attr("shareable", Boolean.TRUE);
	}
}

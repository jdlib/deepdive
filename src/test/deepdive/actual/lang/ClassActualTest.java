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
package deepdive.actual.lang;


import static deepdive.ExpectThat.*;
import java.io.Serializable;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link ClassActual}.
 */
public class ClassActualTest extends AbstractActualTest implements Serializable
{
	private static final long serialVersionUID = 1L;


	@Test public void test() throws Exception
	{
		expectThat(ClassActualTest.class)
			.annotation(Test.class)
				.isNull()
				.back()
			.annotations().all()
				.empty()
				.back()
			.interfaces().elems(Serializable.class).back()
			.name(ClassActualTest.class.getName())
			.name()
				.contains(".actual.")
				.back()
			.simpleName(ClassActualTest.class.getSimpleName())
			.superClass()
				.equal(AbstractActualTest.class)
				.back()
			.packageName("deepdive.actual.lang")
			.isAssignableTo(AbstractActualTest.class)
			.not().isAssignableFrom(AbstractActualTest.class)
			.fieldDeclared("field_")
				.modifiers()
					.isPrivate()
					.back()
				.type(String.class)
				.back();
	}
	
	
	@SuppressWarnings("unused")
	private static void some(String s) throws UnsupportedOperationException
	{
	}
	

	@SuppressWarnings("unused")
	private String field_ = "a";
}

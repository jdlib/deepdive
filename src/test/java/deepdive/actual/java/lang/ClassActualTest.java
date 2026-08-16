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
package deepdive.actual.java.lang;


import static deepdive.ExpectThat.*;
import java.io.Serializable;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link ClassActual}.
 */
public class ClassActualTest extends AbstractActualTest implements Serializable
{
	public static final long DUMMY = 2L;
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
			.canonicalName(ClassActualTest.class.getCanonicalName())
			.constructorPublic().back()
			.fieldDeclared("serialVersionUID").back()
			.fieldPublic("DUMMY").back()
			.interfaces().elems(Serializable.class).back()
			.not().isArray()
			.isInstance(this, true)
			.not().isAnonymous()
			.not().isInterface()
			.not().isPrimitive()
			.modifiers()
				.isPublic()
				.back()
			.name(ClassActualTest.class.getName())
			.name()
				.contains(".actual.")
				.back()
			.simpleName(ClassActualTest.class.getSimpleName())
			.superClass()
				.equal(AbstractActualTest.class)
				.back()
			.packageName("deepdive.actual.java.lang")
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


	@Test public void testEnclosing() throws Exception
	{
		expectThat(InnerClass.class)
			.enclosingClass().same(ClassActualTest.class).back()
			.enclosingMethod().isNull().back()
			.enclosingConstructor().isNull().back();
	}


	private static class InnerClass
	{
	}


	@SuppressWarnings("unused")
	private String field_ = "a";
}

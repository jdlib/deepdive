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
package deepdive.actual;


import static deepdive.ExpectThat.*;
import java.util.Arrays;
import org.junit.Test;
import deepdive.actual.Actual;
import deepdive.actual.Narrows;
import deepdive.actual.lang.StringActual;


/**
 * Tests {@link Actual}.
 */
public class ActualTest extends AbstractActualTest
{
	@Test public void testAll()
	{
		failSoftAssert(() -> 
			expectThat("abc")
				.all(s -> s.startsWith("x").contains("b").endsWith("y").length()
					.all(l -> l.greater(4))
			),
			"Multiple Failures (3 failures)",
		 	"	org.opentest4j.AssertionFailedError: String=<abc>",
		 	"expected to start with: x",
		 	"	org.opentest4j.AssertionFailedError: String=<abc>",
		 	"expected to end with: y",
		 	"	org.opentest4j.AssertionFailedError: String=<abc>.length=<3>",
		 	"expected to be > than: 4",
		 	"but was              : 3");
	}
	
	
	@Test public void testContained()
	{
		StringActual<?,?> a = expectThat("b")
			.contained().in("a", "b", "c")
			.not().contained().in("x", "y")
			.contained().in(Arrays.asList("a", "b", "c"))
			.not().contained().in(Arrays.asList("x", "y"));
		
		failAssert(() -> a.contained().in("x", "y")).msgLines(
			"String=<b>",
			"expected to be one of: [x, y]");

		failAssert(() -> a.not().contained().in("a", "b")).msgLines(
			"String=<b>",
			"expected not to be one of: [a, b]");
	}
	
	
	@Test public void testNot()
	{
		new Actual<>(this, null)
			.isA(AbstractActualTest.class)
			.equal(this)
			.same(this)
			.not().isA(String.class)
			.not().isNull()
			.not().equal("a")
			.not().same("a");
			
		new Actual<>(null, null)
			.isNull()
			.equal(null)
			.same(null);
	}
	
	
	@Test public void testMisc() throws Exception
	{
		expectThatObject(this)
			.prop(Object::getClass).as("class").same(getClass()).back()
// ECJ		.prop(Object::hashCode).as("hashCode").narrow(Narrows.integer()).not().equal(0).back()
			.toString(toString())
			.toStringActual().equal(toString()).back();
	}


	@Test public void testSetValue() throws Exception
	{
		expectThat("a")
			.equal("a")
			.setValue("b")
			.equal("b")
			.setValue(String::toUpperCase)
			.equal("B");
	}
	
	
	@Test public void testNarrow() throws Exception
	{
		Actual<String,?,?> a = expectThatObject("abc");
		Object back = a.narrow(String.class, StringActual::new)
			.toUpperCase("ABC")
			.backOrNull();
		expectNull(back);
		
		expectThat(Arrays.asList("a"))
			.elem(0)
				.narrow(Narrows.string())
				.isLowerCase()
				.back()
			.size(1);
	}
}

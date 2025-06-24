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
package deepdive.actual.java.util.regex;


import static deepdive.ExpectThat.*;
import java.util.regex.Pattern;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link PatternActual} and {@link MatcherActual}.
 */
public class PatternActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat(Pattern.compile("a[0-9]b"))
			.flags(0)
			.not().matches("a")
			.matches("a1b")
			.matcher("a").not().matches().back()
			.matcher("a1b").lookingAt().matches().back();

		expectThat(Pattern.compile(","))
			.split("a,b,c")
				.elems("a", "b", "c")
				.back()
			.split("a,b,c", 2)
				.elems("a", "b,c")
				.back();

		expectThat(Pattern.compile(","))
			.matcher("a,b,c")
				.find()
				.back();

		expectThat(Pattern.compile(","))
			.matcher("a,b,c")
				.replaceAll(";")
					.equal("a;b;c")
					.back()
				.replaceFirst(";")
					.equal("a;b,c")
					.back();
		
		expectThat(Pattern.compile("[0-9]+"))
			.matcher("a123bc56d7")
				.find().result().start(1).end(4).value("123").back()
				.find().result().start(6).end(8).value("56").back()
				.find().result().start(9).end(10).value("7").back()
				.not().find();
		
		expectThat(Pattern.compile("(a+)(b)"))
			.matcher("aabcabbc")
			.groupCount(2)
			.find()
				.result().value("aab").back()
				.groupResult(1)
					.start(0).end(2).value("aa")
					.back()
				.groupResult(2)
					.start(2).end(3).value("b")
					.back();
	}
}

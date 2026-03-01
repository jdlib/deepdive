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
package deepdive.tool;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import deepdive.AbstractTest;
import deepdive.actual.java.io.CharContentBuilder;


public class ActualGeneratorTest extends AbstractTest
{
	@Test public void test() throws Exception
	{
		test(new ActualGenerator(Pojo1.class), "Pojo1");
		test(new ActualGenerator(Pojo1.class).useTypeDirectly(true), "Pojo1Direct");
		test(new ActualGenerator(Pojo2.class), "Pojo2");
	}


	private void test(ActualGenerator generator, String name) throws Exception
	{
		StringWriter s = new StringWriter();
		generator.print(s);
		String actual = s.toString();
		String expected = readExcepted(name);

		expectEqual(expected, actual, name);
	}


	private String readExcepted(String name) throws Exception
	{
		String file = name + "Actual.gen";
		try (InputStream in = expectNotNull(getClass().getResourceAsStream(file), file))
		{
			return CharContentBuilder.read(new InputStreamReader(in, StandardCharsets.UTF_8), 0);
		}
	}
}

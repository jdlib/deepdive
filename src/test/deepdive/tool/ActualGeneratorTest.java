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
import deepdive.actual.io.CharContentBuilder;
import deepdive.tool.ActualGenerator;


public class ActualGeneratorTest extends AbstractTest
{
	@Test public void test() throws Exception
	{
		test(Pojo1.class);
		test(Pojo2.class);
	}
	
	
	private void test(Class<?> pojoClass) throws Exception
	{
		StringWriter s = new StringWriter();
		new ActualGenerator(pojoClass, s);
		
		expectEqual(readActualFile(pojoClass), s.toString(), pojoClass.getSimpleName());
	}
	
	
	private String readActualFile(Class<?> pojoClass) throws Exception
	{
		String file = pojoClass.getSimpleName() + "Actual.gen";
		try (InputStream in = expectNotNull(pojoClass.getResourceAsStream(file), file))
		{
			return CharContentBuilder.read(new InputStreamReader(in, StandardCharsets.UTF_8), 0);
		}
	}
}

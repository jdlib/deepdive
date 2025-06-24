/*
 * Copyright (c) 2022 jdlib, https://github.com/jdlib
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
package deepdive.actual.java.util.zip;


import static deepdive.ExpectThat.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipFile;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


public class ZipFileActualTest extends AbstractActualTest
{
	@Test
	public void test() throws Exception
	{
		File file 		= new File(getClass().getResource("test.zip").toURI());
		ZipFile zipFile = new ZipFile(file); 
		
		ZipFileActual<?,?> actual = expectThat(zipFile);
		actual
			.name(file.getPath())
			.size(5)
			.entry("d1/")
				.isDirectory(true)
				.back()
			.entry("d1/d2/c.txt")
				.isDirectory(false)
				.read()
					.chars(StandardCharsets.UTF_8).to().string().equal("gamma");
	}
}

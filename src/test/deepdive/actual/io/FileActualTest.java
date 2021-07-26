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
package deepdive.actual.io;


import static deepdive.ExpectThat.*;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;
import deepdive.actual.io.FileActual;


/**
 * Tests {@link FileActual}.
 */
public class FileActualTest extends AbstractActualTest
{
	@Test public void test() throws Exception
	{
		createFile("test", ".txt", "abc", this::test);
	}
	
	
	private void test(File file) throws Exception
	{
		FilenameFilter filter = (d,n) -> n.equals(file.getName());
		
		expectThat(file)
			.equal(file)
			.get().absoluteFile()
				.equal(file.getAbsoluteFile())
				.isAbsolute()
				.back()
			.canExecute()
			.get().canonicalFile()
				.equal(file.getCanonicalFile())
				.back()
			.canRead()
			.canWrite()
			.not().extension("pdf")
			.extension()
				.toUpperCase("TXT")
				.back()
			.not().isDirectory()
			.isFile()
			.not().isHidden()
			.lastModified()
				.equal(file.lastModified())
				.back()
			.length(3)
			.length()
				.less(10)
				.back()
			.name(file.getName())
			.name()
				.startsWith("test")
				.endsWith(".txt")
				.back()
			.path(file.getPath())
			.path()
				.equal(file.getPath())
				.back()
			.read().max(100).bytes()
				.equal("abc".getBytes())
				.back()
			.read().max(100).chars(StandardCharsets.UTF_8).to().string()
				.equal("abc")
				.back()
			.totalSpace()
				.equal(file.getTotalSpace())
				.back()
			.set().parentFile()
			.isDirectory()
			.not().isFile()
			.list(filter)
				.elems(file.getName())
				.back()
			.listFiles(filter)
				.elems(file)
				.back();
	}
}

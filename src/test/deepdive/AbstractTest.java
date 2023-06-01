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
package deepdive;


import static deepdive.ExpectThat.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import org.junit.Ignore;
import org.opentest4j.MultipleFailuresError;
import deepdive.function.CheckedConsumer;
import deepdive.function.CheckedRunnable;


@Ignore("abstract")
public abstract class AbstractTest extends ExpectStatic
{
	protected AssertionFailedErrorActual<?> failAssert(CheckedRunnable<?> assertion)
	{
		return new AssertionFailedErrorActual<>(assertion, null);
	}


	protected void failSoftAssert(CheckedRunnable<?> test, String... lines)
	{
		expectError(test)
			.isA(MultipleFailuresError.class)
			.message()
				.split().by("\r?\n")
				.elems(lines);
	}


	protected void createFile(String prefix, String suffix, String content, CheckedConsumer<File,?> consumer) throws Exception
	{
		File file = File.createTempFile(prefix, suffix);
		try
		{
			try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))
			{
				out.write(content);
			}
			consumer.accept(file);
		}
		finally
		{
			if (!file.delete())
				fail("cannot delete file " + file);
		}
	}
}

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
package deepdive.actual.nio.file;


import static deepdive.ExpectThat.*;
import java.io.File;
import java.nio.file.Path;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link PathActual}.
 */
public class PathActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		Path path = new File(".").toPath();

		expectThat(path)
			.endsWith(path)
			.set().absolutePath()
			.isAbsolute()
			.get().fileName()
				.not().isAbsolute()
				.back();
	}
}

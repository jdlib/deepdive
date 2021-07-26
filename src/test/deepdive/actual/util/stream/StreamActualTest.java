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
package deepdive.actual.util.stream;


import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;
import deepdive.actual.util.stream.StreamActual;


public class StreamActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		stream().count$(3);
		stream().collectTo().count().equal(3);
		stream().collectTo().findAny().not().isNull();
		stream().collectTo().findFirst().equal("b");
		stream().collectTo().list().elems("b", "a", "c");
		stream().collectTo().set().elems("b", "a", "c");
		stream().collectTo().max().equal("c");
		stream().collectTo().min().equal("a");
		stream().anyMatch$(s -> "a".equals(s));
		stream().not().anyMatch$(s -> "x".equals(s));
		
		stream().collectTo().map(Collectors.toMap(Function.identity(), String::toUpperCase))
			.size(3)
			.value("a", "A");
	}
	
	
	private StreamActual<String,?,?> stream()
	{
		return stream("b", "a", "c");
	}
	
	
	private StreamActual<String,?,?> stream(String... elems)
	{
		return new StreamActual<>(Stream.of(elems), null);
	}
}

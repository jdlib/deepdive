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
package deepdive.actual.util;


import static deepdive.ExpectThat.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link MapActual}.
 */
public class MapActualTest extends AbstractActualTest
{
	@SuppressWarnings("boxing")
	@Test public void test()
	{
		Map<Integer,String> map = new LinkedHashMap<>();
		map.put(1, "uno");
		map.put(2, "due");
		map.put(3, "tre");
		
		expectThat(map)
			.not().blank()
			.containsKey(1)
			.containsValue("due")
			.not().empty()
			.size(3)
			.value(3, "tre")
			.values().contains().exactly("uno", "due", "tre");
		
		expectThat(Collections.emptyMap())
			.blank()
			.empty();
		
		Map<Integer,String> map2 = new LinkedHashMap<>(map);
		map2.put(4, "quattro");
		map2.put(5, "cinque");
		map2.put(2, "dos");
		map2.remove(1);
		
		failAssert(() -> expectThat(map).equal(map2)).msgLines(
			"LinkedHashMap=<{1=uno, 2=due, 3=tre}>",
			"expected: {2=dos, 3=tre, 4=quattro, 5=cinque}",
			"but was : {1=uno, 2=due, 3=tre}",
			"differences",
			"keys with wrong values",
			"- for key : 2",
			"- expected: due",
			"- but was : dos",
			"missing entries in actual",
			"- for key : 4",
			"- expected: quattro",
			"- for key : 5",
			"- expected: cinque",
			"unexpected entries in actual",
			"- for key   : 1",
			"- unexpected: uno");
	}
}

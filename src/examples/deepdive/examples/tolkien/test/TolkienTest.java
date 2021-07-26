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
package deepdive.examples.tolkien.test;


import static deepdive.examples.tolkien.Alignment.*;
import static deepdive.examples.tolkien.TolkienCharacter.*;
import static deepdive.examples.tolkien.Type.*;
import static deepdive.examples.tolkien.test.ExpectTolkien.*;
import org.junit.Test;
import deepdive.examples.tolkien.TolkienCharacter;


public class TolkienTest
{
	@Test public void testCharacter()
	{
		expectThat(frodo)
			.name("Frodo")
			.not().name("Sam")
			.name()	
				.startsWith("Fro")
				.endsWith("do")
				.not().endsWith("am")
				.equalsIgnoreCase("fRoDo")
				.toUpperCase("FRODO")
				.like("F*o*o")
				.back()
			.not().same(sauron)
			.type(HOBBIT)
			.type()
				.alignment(GOOD)
				.not().immortal()
				.back()
			.age(33)
			.age().greater(18).less(88).back()
			.contained().in(fellowshipOfTheRing)
			.not().contained().in(orcsWithHobbitPrisoners);
	}
	
	
	@Test public void testCharacterList()
	{
		expectThat(fellowshipOfTheRing)
			.size(9)
			.contains(frodo)
			.not().contains(sauron)
			.contains()
				.allOf(merry, pippin)
				.noneOf(guruk, isildur, sauron)
				.not().someOf(sauron, elrond)
				.back()
			.elem(2)
				.name("Merry")
				.contained().in(orcsWithHobbitPrisoners)
				.type(HOBBIT)
				.back()
			.stream()
				.filter(c -> c.getType() == MAN)
				.map(TolkienCharacter::getName)
				.sorted()
				.collectTo().list()
				.contains()
					.exactly("Aragorn", "Boromir")
					.noneOf("Sauron", "Elrond")
					.back()
				.back()
			.stream()
				.filter(character -> character.getName().contains("o"))
				.collectTo().list()
				.contains()
					.exactly(aragorn, frodo, legolas, boromir)
					.back()
				.stream()
					.map(character -> character.getType().getName())
					.distinct()
					.sorted()
					.elems$("Elf", "Hobbit", "Man");
	}
}

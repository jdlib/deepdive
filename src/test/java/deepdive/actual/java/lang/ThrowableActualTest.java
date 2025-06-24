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
package deepdive.actual.java.lang;


import static deepdive.ExpectThat.*;
import java.sql.SQLException;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link ThrowableActual}.
 */
public class ThrowableActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectError(() -> { throw new IllegalArgumentException("test"); })
			.isA(IllegalArgumentException.class)
			.message("test")
			.message().contains("es").back()
			.cause().isNull().back()
			.stackTrace()
				.contains().match(elem -> ThrowableActualTest.class.getName().equals(elem.getClassName()))
				.back();
		
		Throwable t = new SQLException("X", "S100"); 
		expectThat(t)
			.message("X")
			.cast(SQLException.class)
			.prop(SQLException::getSQLState).as("SQLState").equal("S100");
	}
}

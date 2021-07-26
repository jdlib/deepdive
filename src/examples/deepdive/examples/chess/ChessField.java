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
package deepdive.examples.chess;


import java.util.HashMap;
import java.util.Map;


public enum ChessField
{
	A1, B1, C1, D1, E1, F1, G1, H1,
	A2, B2, C2, D2, E2, F2, G2, H2,
	A3, B3, C3, D3, E3, F3, G3, H3,
	A4, B4, C4, D4, E4, F4, G4, H4,
	A5, B5, C5, D5, E5, F5, G5, H5,
	A6, B6, C6, D6, E6, F6, G6, H6,
	A7, B7, C7, D7, E7, F7, G7, H7,
	A8, B8, C8, D8, E8, F8, G8, H8;
	
	
	public static ChessField of(char col, int row)
	{
		int ordinal = (row - 1)*8 + (col - 'A');
		if (!validOrdinal(ordinal))
			throw new IllegalArgumentException("invalid " + col + row + ", " + ordinal);
		return LIST[ordinal];
	}

	
	public static ChessField of(String name)
	{
		ChessField f = MAP.get(name);
		if (f != null)
			return f;
		throw new IllegalArgumentException("invalid field " + name);
	}

	
	public static ChessField ofOrdinal(int ordinal)
	{
		if (!validOrdinal(ordinal))
			throw new IllegalArgumentException("invalid " + ordinal);
		return LIST[ordinal];
	}
	
	
	private static boolean validOrdinal(int ordinal)
	{
		return (ordinal >= 0) && (ordinal < LIST.length);
	}

	
	ChessField()
	{
		col 		= name().charAt(0); 
		row 		= name().charAt(1);
		colIndex	= col - 'A';
		rowIndex	= row - '1';
		light		= (colIndex % 2 == 0) == (rowIndex % 2 == 0);
	}
	
	
	public final char col;
	public final char row;
	public final int colIndex;
	public final int rowIndex;
	public final boolean light;
	private static final ChessField[] LIST = values();
	private static final Map<String,ChessField> MAP = new HashMap<>();
	static
	{
		for (ChessField f : LIST)
			MAP.put(f.name(), f);
	}
}

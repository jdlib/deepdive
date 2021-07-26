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


public enum ChessFigure
{
	KING  ('\u2654', '\u265A'),
	QUEEN ('\u2655', '\u265B'),
	ROOK  ('\u2656', '\u265C'),
	BISHOP('\u2657', '\u265D'),
	KNIGHT('\u2658', '\u265E'),
	PAWN  ('\u2659', '\u265F');
	
	
	ChessFigure(char whiteSymbol, char blackSymbol)
	{
		this.whiteSymbol = whiteSymbol;
		this.blackSymbol = blackSymbol;
	}
	
	
	public char whiteSymbol;
	public char blackSymbol;
}

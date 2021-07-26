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
package deepdive.examples.chess.test3;


import deepdive.actual.Actual;
import deepdive.examples.chess.ChessColor;
import deepdive.examples.chess.ChessFigure;
import deepdive.examples.chess.ChessPiece;


public class ChessPieceActual<BACK> extends Actual<ChessPiece,BACK,ChessPieceActual<BACK>> implements ChessPiece.Builder<ChessPieceActual<BACK>>
{
	public ChessPieceActual(ChessPiece actual, BACK back)
	{
		super(actual, back);
	}
	
	
	@Override public ChessPieceActual<BACK> figure(ChessFigure expected)
	{
		expectSame(expected, value().figure);
		return self();
	}

	
	@Override public ChessPieceActual<BACK> color(ChessColor expected)
	{
		expectSame(expected, value().color);
		return self();
	}
}

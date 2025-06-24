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


import static deepdive.examples.chess.ChessField.*;
import static deepdive.examples.chess.ChessPiece.*;
import org.junit.Test;
import deepdive.examples.chess.ChessBoard;


/**
 * ChessTest3 uses Actual implementations for the domain classes
 * to improve readability and clarity.
 */
public class ChessTest3
{
	@Test public void test() throws Exception
	{
		ChessBoardActual.of(new ChessBoard().start())
			.whiteMoves()
			// test invalid moves
			.moveFail(E4, E2, "no piece at E4")
			.moveFail(D7, D5, "BLACK_PAWN at D7 may not move now")
			// move white E2-E4
			.piece(E2, WHITE_PAWN)
			.empty(E4)
			.move(E2, E4)
			.empty(E2)
			.piece(E4, WHITE_PAWN)
			.not().whiteMoves()
			// move black D7-D5
			.move(D7, D5)
			.piece(D5, BLACK_PAWN)
			// move white E4-D5
			.count(8, c -> c.black().pawn())
			.move(E4, D5)
			.piece(D5, WHITE_PAWN)
			.count(7, c -> c.black().pawn());
	}
}

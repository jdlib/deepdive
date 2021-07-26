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
package deepdive.examples.chess.test1;


import static deepdive.examples.chess.ChessColor.*;
import static deepdive.examples.chess.ChessField.*;
import static deepdive.examples.chess.ChessFigure.*;
import org.junit.Test;
import deepdive.ExpectStatic;
import deepdive.examples.chess.ChessBoard;
import deepdive.examples.chess.ChessMoveException;
import deepdive.examples.chess.ChessPiece;


/**
 * Traditional unit test: 
 * Only uses basic assertions provided by the tessting framework (in this case {@link ExpectStatic}
 * or equivalent {@link org.junit.Assert}) to issue assertions.
 * Drawback: Tests get verbose, writing tests is not really fun.
 */
public class ChessTest1 extends ExpectStatic
{
	@Test public void test() throws Exception
	{
		ChessBoard board = new ChessBoard().start();
		ChessPiece piece;
		
		expectTrue(board.whiteMoves());
		
		// test invalid moves
		ChessMoveException e = expectThrows(ChessMoveException.class, () -> board.move(E4, E2));
		expectEqual("no piece at E4", e.getMessage());
		expectSame(E4, e.getSource());
		expectSame(E2, e.getTarget());
		
		e = expectThrows(ChessMoveException.class, () -> board.move(D7, D5));
		expectEqual("BLACK_PAWN at D7 may not move now", e.getMessage());
		expectSame(D7, e.getSource());
		expectSame(D5, e.getTarget());

		// move white E2-E4
		piece = board.get(E2);
		expectSame(PAWN, piece.figure);
		expectSame(WHITE, piece.color);
		expectNull(board.get(E4));
		expectFalse(board.canMove(E4, E2));
		expectTrue(board.canMove(E2, E4));
		board.move(E2, E4);
		expectFalse(board.whiteMoves());
		expectNull(board.get(E2));
		piece = board.get(E4);
		expectSame(PAWN, piece.figure);
		expectSame(WHITE, piece.color);
		
		// move black D7-D5
		board.move(D7, D5);
		expectNull(board.get(D7));
		piece = board.get(D5);
		expectSame(PAWN, piece.figure);
		expectSame(BLACK, piece.color);
		
		// move white E4 - D5
		expectEqual(8, board.count().black().figure(PAWN).get());
		board.move(E4, D5);
		piece = board.get(D5);
		expectSame(PAWN, piece.figure);
		expectSame(WHITE, piece.color);
		expectEqual(7, board.count().black().figure(PAWN).get());
	}
}

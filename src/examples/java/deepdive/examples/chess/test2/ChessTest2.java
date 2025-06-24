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
package deepdive.examples.chess.test2;


import static deepdive.examples.chess.ChessField.*;
import static deepdive.examples.chess.ChessFigure.*;
import static deepdive.examples.chess.ChessPiece.*;
import org.junit.Test;
import deepdive.ExpectStatic;
import deepdive.examples.chess.ChessBoard;
import deepdive.examples.chess.ChessField;
import deepdive.examples.chess.ChessMoveException;
import deepdive.examples.chess.ChessPiece;
import deepdive.examples.chess.test1.ChessTest1;


/**
 * ChessTest2 refactors {@link ChessTest1} by introducing test helper methods like assertFigure.
 */
public class ChessTest2 extends ExpectStatic
{
	@Test public void test() throws Exception
	{
		board_.start();
		
		expectTrue(board_.whiteMoves());
		
		// test invalid moves
		expectEmpty(E4);
		expectMoveFail(E4, E2, "no piece at E4");
		expectMoveFail(D7, D5, "BLACK_PAWN at D7 may not move now");
		
		// move white E2-E4
		expectPiece(E2, WHITE_PAWN);
		expectEmpty(E4);
		expectMoveOk(E2, E4);
		expectEmpty(E2);
		expectPiece(E4, WHITE_PAWN);
		expectFalse(board_.whiteMoves());

		// move black D7-D5
		expectMoveOk(D7, D5);
		expectEmpty(D7);
		expectPiece(D5, BLACK_PAWN);
		
		// move white E4-D5
		expectEqual(8, board_.count().black().figure(PAWN).get());
		expectMoveOk(E4, D5);
		expectEqual(7, board_.count().black().figure(PAWN).get());
	}		

	
	private void expectPiece(ChessField field, ChessPiece expected)	
	{
		expectSame(expected, board_.get(field));
	}
	
	
	private void expectEmpty(ChessField field)
	{
		expectNull(board_.get(field));
	}

	
	private void expectMoveOk(ChessField src, ChessField target) throws Exception
	{
		expectTrue(board_.canMove(src, target), "canMove");
		board_.move(src, target);
	}
	
	
	private void expectMoveFail(ChessField src, ChessField target, String message) throws Exception
	{
		expectFalse(board_.canMove(src, target), "canMove");
		ChessMoveException e = expectThrows(ChessMoveException.class, () -> board_.move(src, target));
		expectEqual(message, e.getMessage());
		expectSame(src, e.getSource());
		expectSame(target, e.getTarget());
	}

	
	private final ChessBoard board_ = new ChessBoard();
}

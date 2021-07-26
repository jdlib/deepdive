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


import java.util.function.Consumer;
import deepdive.actual.Actual;
import deepdive.examples.chess.ChessBoard;
import deepdive.examples.chess.ChessField;
import deepdive.examples.chess.ChessMoveException;
import deepdive.examples.chess.ChessPiece;


public class ChessBoardActual<BACK> extends Actual<ChessBoard,BACK,ChessBoardActual<BACK>>
{
	public static ChessBoardActual<Void> of(ChessBoard actual)
	{
		return new ChessBoardActual<>(actual, null);
	}

	
	public ChessBoardActual(ChessBoard actual, BACK back)
	{
		super(actual, back);
	}
	
	
	public ChessBoardActual<BACK> empty(ChessField field)
	{
		expectNull(value().get(field), field.name());
		return this;
	}

	
	public ChessPieceActual<ChessBoardActual<BACK>> piece(ChessField field)
	{
		return new ChessPieceActual<>(value().get(field), self()).as(field.name());
	}


	public ChessBoardActual<BACK> piece(ChessField field, ChessPiece expected)
	{
		expectSame(expected, value().get(field), field.name());
		return this;
	}

	
	public ChessBoardActual<BACK> move(ChessField src, ChessField target)
	{
		expectTrue(value().canMove(src, target), "canMove");
		try 
		{
			value().move(src, target);
		}
		catch (ChessMoveException e) 
		{
			failure().addStmt("unexpected").cause(e).throwError();
		}
		return self();
	}


	public ChessBoardActual<BACK> moveFail(ChessField source, ChessField target, String message)
	{
		notMustBeOff();
		expectFalse(value().canMove(source, target), "canMove");
		ChessMoveException e = expectThrows(ChessMoveException.class, () -> value().move(source, target));
		expectSame(e.getSource(), source, "source");
		expectSame(e.getTarget(), target, "target");
		return self();
	}

	
	public ChessBoardActual<BACK> count(int expected, Consumer<ChessBoard.Count> filter)
	{
		ChessBoard.Count count = value().count();
		filter.accept(count);
		expectEqual(expected, count.get());
		return self();
	}


	public ChessBoardActual<BACK> whiteMoves()
	{
		expectTrue(value().whiteMoves());
		return self();
	}
}

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


import java.util.Arrays;
import java.util.Objects;


public class ChessBoard
{
	public ChessBoard empty()
	{
		Arrays.fill(pieces_, null);
		return this;
	}
	
	
	public ChessBoard start()
	{
		empty();
		for (char col='A'; col<='H'; col++)
			start(col, ChessFigure.PAWN);
		start('A', ChessFigure.ROOK);
		start('B', ChessFigure.KNIGHT);
		start('C', ChessFigure.BISHOP);
		start('D', ChessFigure.QUEEN);
		start('E', ChessFigure.KING);
		start('F', ChessFigure.BISHOP);
		start('G', ChessFigure.KNIGHT);
		start('H', ChessFigure.ROOK);
		whiteMoves_ = true;
		return this;
	}
	
	
	private void start(char col, ChessFigure figure)
	{
		int row = figure == ChessFigure.PAWN ? 2 : 1;
		set(ChessField.of(col, row), 		ChessPiece.of(ChessColor.WHITE, figure));
		set(ChessField.of(col, 9 - row), 	ChessPiece.of(ChessColor.BLACK, figure));
	}
	
	
	public boolean whiteMoves()
	{
		return whiteMoves_;
	}

	
	public ChessBoard setWhiteMoves(boolean value)
	{
		whiteMoves_ = value;
		return this;
	}

	
	public ChessPiece get(ChessField field)
	{
		Objects.requireNonNull(field);
		return pieces_[field.ordinal()];
	}
	
	
	public ChessBoard set(ChessField field, ChessPiece piece)
	{
		Objects.requireNonNull(field);
		pieces_[field.ordinal()] = piece;
		return this;
	}
	
	
	public boolean canMove(ChessField src, ChessField target)
	{
		return canMoveImpl(src, target, get(src)) == null;
	}
	
	
	public ChessBoard move(ChessField src, ChessField target) throws ChessMoveException
	{
		ChessPiece srcPiece = get(src);
		String error = canMoveImpl(src, target, srcPiece);
		if (error != null)
			throw new ChessMoveException(error, src, target, srcPiece);
		set(src, null);
		set(target, srcPiece);
		whiteMoves_ = !whiteMoves_;
		return this;
	}
	
	
	private String canMoveImpl(ChessField src, ChessField target, ChessPiece srcPiece)
	{
		Objects.requireNonNull(src);
		Objects.requireNonNull(target);
		ChessPiece piece = get(src);
		if (srcPiece == null)
			return "no piece at " + src;
		if (srcPiece.isWhite() != whiteMoves_)
			return piece + " at " + src + " may not move now";
		// TODO implement figure moves
		return null;
	}
	
	
	public Count count()
	{
		return new Count();
	}
	
	
	public class Count implements ChessPiece.Builder<Count>
	{
		private Count()
		{
		}
		
		
		@Override public Count figure(ChessFigure figure)
		{
			figure_ = figure;
			return this;
		}
		

		@Override public Count color(ChessColor color)
		{
			color_ = color;
			return this;
		}
		
		
		public int get()
		{
			int count = 0;
			for (ChessPiece piece : pieces_)
			{
				if (piece != null)
				{
					if ((figure_ != null) && (figure_ != piece.figure))
						continue;
					if ((color_ != null) && (color_ != piece.color))
						continue;
					count++;
				}
			}
			return count;
		}

		
		private ChessFigure figure_;
		private ChessColor color_;
	}

	
	@Override public String toString()
	{
		StringBuilder s = new StringBuilder();
		for (int i=pieces_.length-1; i>=0; i--)
		{
			ChessPiece piece = pieces_[i];
			if (piece == null)
				s.append('\u2003'); // Field.ofOrdinal(i).light ? ' ' : '\u2630');
			else
				s.append(piece.getSymbol());
			if (i % 8 == 0)
				s.append('\n');
		}
		return s.toString();
	}
	
	
	public static void main(String[] args) throws Exception
	{
		ChessBoard b = new ChessBoard();
		b.start();
		System.out.println(b);
	}
	
	
	private final ChessPiece[] pieces_ = new ChessPiece[64];
	private boolean whiteMoves_;
}

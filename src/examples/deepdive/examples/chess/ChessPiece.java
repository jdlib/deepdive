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


import static deepdive.examples.chess.ChessColor.*;
import static deepdive.examples.chess.ChessFigure.*;
import java.util.Objects;


public enum ChessPiece
{
	WHITE_KING	(WHITE, KING),
	WHITE_QUEEN	(WHITE, QUEEN),
	WHITE_ROOK	(WHITE, ROOK),
	WHITE_BISHOP(WHITE, BISHOP),
	WHITE_KNIGHT(WHITE, KNIGHT),
	WHITE_PAWN	(WHITE, PAWN),
	BLACK_KING	(BLACK, KING),
	BLACK_QUEEN	(BLACK, QUEEN),
	BLACK_ROOK	(BLACK, ROOK),
	BLACK_BISHOP(BLACK, BISHOP),
	BLACK_KNIGHT(BLACK, KNIGHT),
	BLACK_PAWN	(BLACK, PAWN);

	
	ChessPiece(ChessColor color, ChessFigure figure)
	{
		this.figure 	= Objects.requireNonNull(figure);
		this.color 		= Objects.requireNonNull(color);
	}
	
	
	public boolean isWhite()
	{
		return color.isWhite();
	}

	
	public char getSymbol()
	{
		return isWhite() ? figure.whiteSymbol : figure.blackSymbol;
	}
	
	
	public interface Builder<IMPL>
	{
		public IMPL figure(ChessFigure fiure);


		public default IMPL king()
		{
			return figure(KING);
		}

		
		public default IMPL queen()
		{
			return figure(QUEEN);
		}

		
		public default IMPL rook()
		{
			return figure(ROOK);
		}

		
		public default IMPL knight()
		{
			return figure(KNIGHT);
		}

		
		public default IMPL bishop()
		{
			return figure(BISHOP);
		}

		
		public default IMPL pawn()
		{
			return figure(PAWN);
		}
		
		
		public IMPL color(ChessColor color);
		
		
		public default IMPL white()
		{
			return color(WHITE);
		}


		public default IMPL black()
		{
			return color(BLACK);
		}
	};
	
	
	public final ChessFigure figure;
	public final ChessColor color;
	
	
	public static ChessPiece of(ChessColor color, ChessFigure figure)
	{
		return PIECES[Objects.requireNonNull(color).ordinal()][Objects.requireNonNull(figure).ordinal()];
	}
	
	
	private static final ChessPiece[][] PIECES;
	static
	{
		PIECES = new ChessPiece[2][6];
		for (ChessPiece piece : values())
		{
			PIECES[piece.color.ordinal()][piece.figure.ordinal()] = piece;
		}
	}
}

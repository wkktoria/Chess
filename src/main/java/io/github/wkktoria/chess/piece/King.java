package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;
import io.github.wkktoria.chess.Type;

public class King extends Piece {
    public King(final int color, final int col, final int row) {
        super(color, col, row);

        type = Type.KING;

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-king");
        } else {
            image = getImage("/piece/b-king");
        }
    }

    @Override
    public boolean canMove(final int targetCol, final int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            if (Math.abs(targetCol - previousCol) + Math.abs(targetRow - previousRow) == 1
                    || Math.abs(targetCol - previousCol) * Math.abs(targetRow - previousRow) == 1) {
                return isValidSquare(targetCol, targetRow);
            }
        }

        if (!movedBefore) {
            // Right castling
            if (targetCol == previousCol + 2 && targetRow == previousRow
                    && !isPieceOnStraightLine(targetCol, targetRow)) {
                for (Piece piece : GamePanel.piecesOnBoard) {
                    if (piece.getCol() == previousCol + 3 && piece.getRow() == previousRow
                            && !piece.movedBefore) {
                        GamePanel.castlingPiece = piece;
                        return true;
                    }
                }
            }

            // Left castling
            if (targetCol == previousCol - 2 && targetRow == previousRow
                    && !isPieceOnStraightLine(targetCol, targetRow)) {
                Piece[] p = new Piece[2];

                for (Piece piece : GamePanel.piecesOnBoard) {
                    if (piece.getCol() == previousCol - 3 && piece.getRow() == targetRow) {
                        p[0] = piece;
                    }

                    if (piece.getCol() == previousCol - 4 && piece.getRow() == targetRow) {
                        p[1] = piece;
                    }

                    if (p[0] == null && p[1] != null && !p[1].movedBefore) {
                        GamePanel.castlingPiece = p[1];
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

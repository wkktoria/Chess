package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;
import io.github.wkktoria.chess.Type;

public class Pawn extends Piece {
    public Pawn(final int color, final int col, final int row) {
        super(color, col, row);

        type = Type.PAWN;

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-pawn");
        } else {
            image = getImage("/piece/b-pawn");
        }
    }

    @Override
    public boolean canMove(final int targetCol, final int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            MoveDirection moveDirection;

            if (color == GamePanel.WHITE) {
                moveDirection = MoveDirection.UP;
            } else {
                moveDirection = MoveDirection.DOWN;
            }

            hittingPiece = getHittingPiece(targetCol, targetRow);

            // Moving by 1 square
            if (targetCol == previousCol && targetRow == previousRow + moveDirection.getValue() && hittingPiece == null) {
                return true;
            }

            // Moving by 2 squares
            if (targetCol == previousCol && targetRow == previousRow + moveDirection.getValue() * 2
                    && hittingPiece == null && !movedBefore && !isPieceOnStraightLine(targetCol, targetRow)) {
                return true;
            }

            // Moving diagonal and capturing
            if (Math.abs(targetCol - previousCol) == 1 && targetRow == previousRow + moveDirection.getValue()
                    && hittingPiece != null && hittingPiece.getColor() != color) {
                return true;
            }

            // En passant
            if (Math.abs(targetCol - previousCol) == 1 && targetRow == previousRow + moveDirection.getValue()) {
                for (Piece piece : GamePanel.piecesOnBoard) {
                    if (piece.getCol() == targetCol && piece.getRow() == previousRow && piece.isTwoStepped()) {
                        hittingPiece = piece;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected enum MoveDirection {
        UP(-1), DOWN(1);

        private final int value;

        MoveDirection(final int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }
}

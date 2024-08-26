package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;
import io.github.wkktoria.chess.Type;

public class Queen extends Piece {
    public Queen(final int color, final int col, final int row) {
        super(color, col, row);

        type = Type.QUEEN;

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-queen");
        } else {
            image = getImage("/piece/b-queen");
        }
    }

    @Override
    public boolean canMove(final int targetCol, final int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            // Vertical and horizontal moves
            if (targetCol == previousCol || targetRow == previousRow) {
                return isValidSquare(targetCol, targetRow) && !isPieceOnStraightLine(targetCol, targetRow);
            }

            // Diagonal moves
            if (Math.abs(targetCol - previousCol) == Math.abs(targetRow - previousRow)) {
                return isValidSquare(targetCol, targetRow) && !isPieceOnDiagonalLine(targetCol, targetRow);
            }
        }

        return false;
    }
}

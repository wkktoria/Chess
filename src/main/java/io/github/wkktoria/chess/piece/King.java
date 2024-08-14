package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;

public class King extends Piece {
    public King(final int color, final int col, final int row) {
        super(color, col, row);

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

        return false;
    }
}

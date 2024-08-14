package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;

public class Knight extends Piece {
    public Knight(final int color, final int col, final int row) {
        super(color, col, row);

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-knight");
        } else {
            image = getImage("/piece/b-knight");
        }
    }

    @Override
    public boolean canMove(final int targetCol, final int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            if (Math.abs(targetCol - previousCol) * Math.abs(targetRow - previousRow) == 2) {
                return isValidSquare(targetCol, targetRow);
            }
        }

        return false;
    }
}

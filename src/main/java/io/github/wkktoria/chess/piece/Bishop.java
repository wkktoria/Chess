package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;
import io.github.wkktoria.chess.Type;

public class Bishop extends Piece {
    public Bishop(final int color, final int col, final int row) {
        super(color, col, row);

        type = Type.BISHOP;

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-bishop");
        } else {
            image = getImage("/piece/b-bishop");
        }
    }

    @Override
    public boolean canMove(final int targetCol, final int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            if (Math.abs(targetCol - previousCol) == Math.abs(targetRow - previousRow)) {
                return isValidSquare(targetCol, targetRow) && !isPieceOnDiagonalLine(targetCol, targetRow);
            }
        }

        return false;
    }
}

package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;
import io.github.wkktoria.chess.Type;

public class Rook extends Piece {
    public Rook(final int color, final int col, final int row) {
        super(color, col, row);

        type = Type.ROOK;

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-rook");
        } else {
            image = getImage("/piece/b-rook");
        }
    }

    @Override
    public boolean canMove(final int targetCol, final int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            if (targetCol == previousCol || targetRow == previousRow) {
                return isValidSquare(targetCol, targetRow) && !isPieceOnStraightLine(targetCol, targetRow);
            }
        }

        return false;
    }
}

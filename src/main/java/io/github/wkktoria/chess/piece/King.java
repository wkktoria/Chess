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
}

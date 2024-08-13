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
}

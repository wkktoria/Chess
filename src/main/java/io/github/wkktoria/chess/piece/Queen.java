package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;

public class Queen extends Piece {
    public Queen(final int color, final int col, final int row) {
        super(color, col, row);

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-queen");
        } else {
            image = getImage("/piece/b-queen");
        }
    }
}

package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;

public class Rook extends Piece {
    public Rook(final int color, final int col, final int row) {
        super(color, col, row);

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-rook");
        } else {
            image = getImage("/piece/b-rook");
        }
    }
}

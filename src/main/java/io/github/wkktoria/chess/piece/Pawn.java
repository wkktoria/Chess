package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.GamePanel;

public class Pawn extends Piece {
    public Pawn(final int color, final int col, final int row) {
        super(color, col, row);

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-pawn");
        } else {
            image = getImage("/piece/b-pawn");
        }
    }
}

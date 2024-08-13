package io.github.wkktoria.chess;

import java.awt.*;

public class Board {
    public static final int SQUARE_SIZE = 100;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;

    private final int maxCol = 8;
    private final int maxRow = 8;

    void draw(Graphics2D g2) {
        boolean isDarkColor = false;

        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {

                if (isDarkColor) {
                    g2.setColor(new Color(150, 77, 34));
                    isDarkColor = false;
                } else {
                    g2.setColor(new Color(238, 220, 151));
                    isDarkColor = true;
                }

                g2.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }

            isDarkColor = !isDarkColor;
        }
    }
}

package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Piece {
    protected BufferedImage image;
    protected int x;
    protected int y;
    protected int col, previousCol;
    protected int row, previousRow;
    protected int color;

    public Piece(final int color, final int col, final int row) {
        this.color = color;
        this.col = col;
        this.row = row;

        x = getX(col);
        y = getY(row);
        previousCol = col;
        previousRow = row;
    }

    public BufferedImage getImage(final String imagePath) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return image;
    }

    public int getX(final int col) {
        return col * Board.SQUARE_SIZE;
    }

    public int getY(final int row) {
        return row * Board.SQUARE_SIZE;
    }

    public void draw(final Graphics2D g2) {
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}

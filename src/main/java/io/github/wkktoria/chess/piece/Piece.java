package io.github.wkktoria.chess.piece;

import io.github.wkktoria.chess.Board;
import io.github.wkktoria.chess.GamePanel;

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
    protected Piece hittingPiece;

    public Piece(final int color, final int col, final int row) {
        this.color = color;
        this.col = col;
        this.row = row;

        x = getX(col);
        y = getY(row);
        previousCol = col;
        previousRow = row;
    }

    protected boolean isWithinBoard(final int col, final int row) {
        return (col >= 0 && col <= 7) && (row >= 0 && row <= 7);
    }

    public boolean canMove(final int targetCol, final int targetRow) {
        return isWithinBoard(targetCol, targetRow);
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

    public int getCol(final int x) {
        return (x + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }

    public int getRow(final int y) {
        return (y + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }

    public void draw(final Graphics2D g2) {
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    public int getColor() {
        return color;
    }

    public int getCol() {
        return col;
    }

    public void setCol(final int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(final int row) {
        this.row = row;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void updatePosition() {
        x = getX(col);
        y = getY(row);
        previousCol = getCol(x);
        previousRow = getRow(y);
    }

    public void resetPosition() {
        col = previousCol;
        row = previousRow;
        x = getX(col);
        y = getY(row);
    }

    protected Piece getHittingPiece(final int targetCol, final int targetRow) {
        for (Piece piece : GamePanel.piecesOnBoard) {
            if (piece.getCol() == targetCol && piece.getRow() == targetRow && piece != this) {
                return piece;
            }
        }

        return null;
    }

    public boolean isValidSquare(final int targetCol, final int targetRow) {
        hittingPiece = getHittingPiece(targetCol, targetRow);

        if (hittingPiece == null) {
            return true;
        } else {
            if (hittingPiece.getColor() != this.getColor()) {
                return true;
            } else {
                hittingPiece = null;
            }
        }

        return false;
    }

    public Piece getHittingPiece() {
        return hittingPiece;
    }

    public int getIndex() {
        for (int index = 0; index < GamePanel.piecesOnBoard.size(); index++) {
            if (GamePanel.piecesOnBoard.get(index) == this) {
                return index;
            }
        }

        return -1;
    }
}

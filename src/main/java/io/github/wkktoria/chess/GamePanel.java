package io.github.wkktoria.chess;

import io.github.wkktoria.chess.piece.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> piecesOnBoard = new ArrayList<>();
    public static ArrayList<Piece> promotionPieces = new ArrayList<>();
    public static Piece castlingPiece;

    private final int fps = 60;
    private Thread gameThread;
    private Board board = new Board();
    private Mouse mouse = new Mouse();
    private int currentColor = WHITE;
    private Piece activePiece;
    private boolean canMove;
    private boolean isValidSquare;
    private boolean isPromotion;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(36, 26, 15));
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setPieces();
        copyPieces(pieces, piecesOnBoard);
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void setPieces() {
        // White pieces
        pieces.add(new Pawn(WHITE, 0, 6));
        pieces.add(new Pawn(WHITE, 1, 6));
        pieces.add(new Pawn(WHITE, 2, 6));
        pieces.add(new Pawn(WHITE, 3, 6));
        pieces.add(new Pawn(WHITE, 4, 6));
        pieces.add(new Pawn(WHITE, 5, 6));
        pieces.add(new Pawn(WHITE, 6, 6));
        pieces.add(new Pawn(WHITE, 7, 6));
        pieces.add(new Rook(WHITE, 0, 7));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new Knight(WHITE, 1, 7));
        pieces.add(new Knight(WHITE, 6, 7));
        pieces.add(new Bishop(WHITE, 2, 7));
        pieces.add(new Bishop(WHITE, 5, 7));
        pieces.add(new Queen(WHITE, 3, 7));
        pieces.add(new King(WHITE, 4, 7));

        // Black pieces
        pieces.add(new Pawn(BLACK, 0, 1));
        pieces.add(new Pawn(BLACK, 1, 1));
        pieces.add(new Pawn(BLACK, 2, 1));
        pieces.add(new Pawn(BLACK, 3, 1));
        pieces.add(new Pawn(BLACK, 4, 1));
        pieces.add(new Pawn(BLACK, 5, 1));
        pieces.add(new Pawn(BLACK, 6, 1));
        pieces.add(new Pawn(BLACK, 7, 1));
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 7, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new Queen(BLACK, 3, 0));
        pieces.add(new King(BLACK, 4, 0));
    }

    private void copyPieces(final ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        target.addAll(source);
    }

    private void update() {
        if (isPromotion) {
            promoting();
        } else {
            if (mouse.isPressed()) {
                if (activePiece == null) {
                    for (Piece piece : piecesOnBoard) {
                        if (piece.getColor() == currentColor
                                && piece.getCol() == mouse.getX() / Board.SQUARE_SIZE
                                && piece.getRow() == mouse.getY() / Board.SQUARE_SIZE) {
                            activePiece = piece;
                        }
                    }
                } else {
                    simulate();
                }
            }

            if (!mouse.isPressed()) {
                if (activePiece != null) {
                    if (isValidSquare) {
                        copyPieces(piecesOnBoard, pieces);
                        activePiece.updatePosition();

                        if (castlingPiece != null) {
                            castlingPiece.updatePosition();
                        }

                        if (canPromote()) {
                            isPromotion = true;
                        } else {
                            changePlayer();
                        }
                    } else {
                        copyPieces(pieces, piecesOnBoard);
                        activePiece.resetPosition();
                        activePiece = null;
                    }
                }
            }
        }
    }

    private void simulate() {
        canMove = false;
        isValidSquare = false;

        copyPieces(pieces, piecesOnBoard);

        if (castlingPiece != null) {
            castlingPiece.setCol(castlingPiece.getPreviousCol());
            castlingPiece.setX(castlingPiece.getCol());
            castlingPiece = null;
        }

        activePiece.setX(mouse.getX() - Board.HALF_SQUARE_SIZE);
        activePiece.setY(mouse.getY() - Board.HALF_SQUARE_SIZE);
        activePiece.setCol(activePiece.getCol(activePiece.getX()));
        activePiece.setRow(activePiece.getRow(activePiece.getY()));

        if (activePiece.canMove(activePiece.getCol(), activePiece.getRow())) {
            canMove = true;

            if (activePiece.getHittingPiece() != null) {
                piecesOnBoard.remove(activePiece.getHittingPiece().getIndex());
            }

            checkCastling();

            isValidSquare = true;
        }
    }

    private void changePlayer() {
        if (currentColor == WHITE) {
            currentColor = BLACK;

            for (Piece piece : pieces) {
                if (piece.getColor() == BLACK) {
                    piece.setTwoStepped(false);
                }
            }
        } else {
            currentColor = WHITE;

            for (Piece piece : pieces) {
                if (piece.getColor() == WHITE) {
                    piece.setTwoStepped(false);
                }
            }
        }

        activePiece = null;
    }

    private void checkCastling() {
        if (castlingPiece != null) {
            if (castlingPiece.getCol() == 0) {
                castlingPiece.setCol(castlingPiece.getCol() + 3);
            } else if (castlingPiece.getCol() == 7) {
                castlingPiece.setCol(castlingPiece.getCol() - 2);
            }

            castlingPiece.setX(castlingPiece.getX(castlingPiece.getCol()));
        }
    }

    private boolean canPromote() {
        if (activePiece.getType() == Type.PAWN) {
            if ((currentColor == WHITE && activePiece.getRow() == 0) || (currentColor == BLACK && activePiece.getRow() == 7)) {
                promotionPieces.clear();
                promotionPieces.add(new Rook(currentColor, 9, 2));
                promotionPieces.add(new Knight(currentColor, 9, 3));
                promotionPieces.add(new Bishop(currentColor, 9, 4));
                promotionPieces.add(new Queen(currentColor, 9, 5));
                return true;
            }
        }

        return false;
    }

    private void promoting() {
        if (mouse.isPressed()) {
            for (Piece piece : promotionPieces) {
                if (piece.getCol() == mouse.getX() / Board.SQUARE_SIZE && piece.getRow() == mouse.getY() / Board.SQUARE_SIZE) {
                    switch (piece.getType()) {
                        case ROOK:
                            piecesOnBoard.add(new Rook(currentColor, activePiece.getCol(), activePiece.getRow()));
                            break;
                        case KNIGHT:
                            piecesOnBoard.add(new Knight(currentColor, activePiece.getCol(), activePiece.getRow()));
                            break;
                        case BISHOP:
                            piecesOnBoard.add(new Bishop(currentColor, activePiece.getCol(), activePiece.getRow()));
                            break;
                        case QUEEN:
                            piecesOnBoard.add(new Queen(currentColor, activePiece.getCol(), activePiece.getRow()));
                            break;
                        default:
                            break;
                    }

                    piecesOnBoard.remove(activePiece.getIndex());
                    copyPieces(piecesOnBoard, pieces);

                    activePiece = null;
                    isPromotion = false;
                    changePlayer();
                }
            }
        }
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        board.draw(g2);

        for (Piece piece : piecesOnBoard) {
            piece.draw(g2);
        }

        if (activePiece != null) {
            if (canMove) {
                g2.setColor(Color.WHITE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                g2.fillRect(activePiece.getCol() * Board.SQUARE_SIZE, activePiece.getRow() * Board.SQUARE_SIZE,
                        Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            activePiece.draw(g2);
        }

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.setColor(new Color(204, 204, 204));

        if (isPromotion) {
            g2.drawString("Promote to:", 860, 140);
            for (Piece piece : promotionPieces) {
                g2.drawImage(piece.getImage(), piece.getX(piece.getCol()), piece.getY(piece.getRow()),
                        Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
            }
        } else {
            if (currentColor == WHITE) {
                g2.drawString("White's turn", 900, 550);
            } else {
                g2.drawString("Black's turn", 900, 250);
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null && gameThread.isAlive()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
}

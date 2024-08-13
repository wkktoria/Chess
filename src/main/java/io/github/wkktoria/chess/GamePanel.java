package io.github.wkktoria.chess;

import javax.swing.*;
import java.awt.*;

class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 600;

    private final int fps = 60;

    private Thread gameThread;
    private Board board = new Board();

    GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(36, 26, 15));
    }

    void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    private void update() {
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        board.draw(g2d);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
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

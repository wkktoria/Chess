package io.github.wkktoria.chess;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    private int x;
    private int y;
    private boolean isPressed;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        isPressed = true;
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        isPressed = false;
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}

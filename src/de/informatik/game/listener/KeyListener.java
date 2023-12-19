package de.informatik.game.listener;

import de.informatik.game.JumpAndRun;

import java.awt.event.KeyEvent;

/**
 * Der {@link KeyListener} wird dann getriggert, wenn eine Taste auf der Tastatur gedrückt wird.
 */
public final class KeyListener implements java.awt.event.KeyListener {

    //<editor-fold desc="implementation">
    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> JumpAndRun.GAME_INSTANCE.getGameHandler().moveLeft();
            case KeyEvent.VK_RIGHT -> JumpAndRun.GAME_INSTANCE.getGameHandler().moveRight();
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }
    //</editor-fold>
}

package de.informatik.game.listener;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.MovementState;

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
            case KeyEvent.VK_LEFT -> JumpAndRun.GAME_INSTANCE.getKeyboardTask().setMovementState(MovementState.LEFT);
            case KeyEvent.VK_RIGHT -> JumpAndRun.GAME_INSTANCE.getKeyboardTask().setMovementState(MovementState.RIGHT);
            case KeyEvent.VK_UP -> JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().jump();
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            JumpAndRun.GAME_INSTANCE.getKeyboardTask().setMovementState(null);
        }

        JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().resetAnimationCount();
    }
    //</editor-fold>
}

package de.informatik.game.listener;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.GameState;

import java.awt.event.KeyEvent;

/**
 * Der {@link KeyListener} wird dann getriggert, wenn eine Taste auf der Tastatur gedrückt wird.
 */
public final class KeyListener implements java.awt.event.KeyListener {

    //<editor-fold desc="CONSTANTS">
    /** Der Zustand, ob das Zurücksetzen der Animation kurz verzögert werden soll. */
    private static final boolean DELAY_ANIMATION_RESET = true;
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getGameState() == GameState.LOSE) {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) return;
            JumpAndRun.startLevel(JumpAndRun.GAME_INSTANCE.getGameHandler().getCurrentLevel());
            return;
        }

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getGameState() == GameState.WIN) {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) return;
            final int nextLevel = JumpAndRun.GAME_INSTANCE.getGameHandler().getCurrentLevel() + 1;

            if (nextLevel > JumpAndRun.LEVEL_AMOUNT) {
                JumpAndRun.GAME_INSTANCE.getCurrentGameGui().dispose();
                return;
            }

            JumpAndRun.startLevel(nextLevel);
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                JumpAndRun.GAME_INSTANCE.getGameTask().setKeyCode(e.getKeyCode());
                break;
            case KeyEvent.VK_UP:
                JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().jump();
                break;
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            JumpAndRun.GAME_INSTANCE.getGameTask().setKeyCode(-1);
        }

        JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().resetAnimation(DELAY_ANIMATION_RESET);
    }
    //</editor-fold>
}

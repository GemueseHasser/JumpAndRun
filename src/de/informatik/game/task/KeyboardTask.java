package de.informatik.game.task;

import de.informatik.game.JumpAndRun;

import java.awt.event.KeyEvent;

/**
 * Der {@link KeyboardTask} stellt eine sich konstant wiederholende Prozedur dar, wobei immer wieder überprüft wird, ob
 * sich der Spieler bewegt und wenn ja in welche Richtung. Diese Bewegung, die vom
 * {@link de.informatik.game.listener.KeyListener} initiiert wird, wird dann umgesetzt.
 */
public class KeyboardTask implements Runnable {

    //<editor-fold desc="LOCAL FIELDS">
    /** Die aktuelle Taste, die gedrückt wird. */
    private int keyCode;
    //</editor-fold>


    //<editor-fold desc="Setter">

    /**
     * Legt die aktuelle Taste fest, die gedrückt wird.
     *
     * @param keyCode Die aktuelle Taste, die gedrückt wird.
     */
    public void setKeyCode(final int keyCode) {
        this.keyCode = keyCode;
    }
    //</editor-fold>

    //<editor-fold desc="implementation">
    @Override
    public void run() {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> JumpAndRun.GAME_INSTANCE.getGameHandler().moveLeft();
            case KeyEvent.VK_RIGHT -> JumpAndRun.GAME_INSTANCE.getGameHandler().moveRight();
        }
    }
    //</editor-fold>
}

package de.informatik.game.task;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.MovementState;

/**
 * Der {@link KeyboardTask} stellt eine sich konstant wiederholende Prozedur dar, wobei immer wieder überprüft wird, ob
 * sich der Spieler bewegt und wenn ja in welche Richtung. Diese Bewegung, die vom
 * {@link de.informatik.game.listener.KeyListener} initiiert wird, wird dann umgesetzt.
 */
public class KeyboardTask implements Runnable {

    //<editor-fold desc="LOCAL FIELDS">
    /** Der aktuelle Status der Bewegung des Spielers. */
    private MovementState movementState;
    //</editor-fold>


    //<editor-fold desc="Setter">

    /**
     * Legt den aktuellen Status der Bewegung des Spielers fest.
     *
     * @param movementState Der aktuelle Status der Bewegung des Spielers.
     */
    public void setMovementState(final MovementState movementState) {
        this.movementState = movementState;
    }
    //</editor-fold>

    //<editor-fold desc="implementation">
    @Override
    public void run() {
        if (movementState == null) return;

        switch (movementState) {
            case LEFT -> JumpAndRun.GAME_INSTANCE.getGameHandler().moveLeft();
            case RIGHT -> JumpAndRun.GAME_INSTANCE.getGameHandler().moveRight();
        }
    }
    //</editor-fold>
}

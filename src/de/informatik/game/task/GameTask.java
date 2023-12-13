package de.informatik.game.task;

import de.informatik.game.JumpAndRun;

/**
 * Der {@link GameTask} stellt eine sich konstant wiederholende Prozedur dar, wodurch das Spiel immer wieder während des
 * Spielbetriebs aktualisiert wird.
 */
public final class GameTask implements Runnable {

    //<editor-fold desc="implementation">
    @Override
    public void run() {
        JumpAndRun.GAME_INSTANCE.getGameHandler().updateBackgroundPosition();
    }
    //</editor-fold>
}

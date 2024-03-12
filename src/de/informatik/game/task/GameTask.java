package de.informatik.game.task;

import de.informatik.game.JumpAndRun;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;

import java.awt.event.KeyEvent;

/**
 * Der {@link GameTask} stellt eine sich konstant wiederholende Prozedur dar, wobei alle Prozesse, die konstant
 * wiederholt werden sollen, in diesem Spiel, aktualisiert werden.
 */
public final class GameTask implements Runnable {

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
        // update movement
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                JumpAndRun.GAME_INSTANCE.getGameHandler().moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                JumpAndRun.GAME_INSTANCE.getGameHandler().moveRight();
                break;
        }

        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        // update player gravity
        player.setGravity(true);

        for (final Opponent opponent : JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLoadedOpponents()) {
            final int rightPlayerPosition = player.getScreenPositionX() + Player.PLAYER_SIZE;
            final int bottomPlayerPosition = player.getPositionY() + Player.PLAYER_SIZE;

            // check if player collides with opponent
            if (rightPlayerPosition >= opponent.getPositionX() && player.getScreenPositionX() <= opponent.getPositionX() + opponent.getWidth()) {
                if (bottomPlayerPosition >= opponent.getPositionY() && player.getPositionY() <= opponent.getPositionY() + opponent.getHeight()) {
                    // trigger player collide event
                    opponent.playerCollideOpponentEvent();

                    if (bottomPlayerPosition == opponent.getPositionY() && !opponent.isPermeable()) {
                        player.setGravity(false);
                    }
                }
            }
        }

        if (player.getPositionY() >= Player.START_POSITION_Y) return;
        if (!player.hasGravity() || player.isJumping()) return;

        player.setPositionY(player.getPositionY() + (Player.STEP_SIZE * 2));
    }
    //</editor-fold>
}

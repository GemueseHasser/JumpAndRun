package de.informatik.game.task;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.GameState;
import de.informatik.game.constant.SoundType;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;

import javax.sound.sampled.Clip;

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
        // check if game is running
        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getGameState() != GameState.RUNNING) return;

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

        if (player.getHealth() <= 0) {
            // update game-state
            JumpAndRun.GAME_INSTANCE.getGameHandler().updateGameState(GameState.LOSE);

            // stop game music
            SoundType.GAME_BACKGROUND.stop();

            // play death-sound and new background music
            SoundType.DEATH.play(0);
            SoundType.SAD_MUSIC.play(Clip.LOOP_CONTINUOUSLY);
            return;
        }

        // update player gravity
        player.setGravity(true);

        for (final Opponent opponent : JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLoadedOpponents()) {
            final int bottomPlayerPosition = player.getPositionY() + Player.PLAYER_SIZE;

            // check if player collides with opponent
            if (player.collides(opponent)) {
                if (bottomPlayerPosition - Player.STEP_SIZE <= opponent.getPositionY() && bottomPlayerPosition + Player.STEP_SIZE >= opponent.getPositionY() && !opponent.isPermeable()) {
                    player.setGravity(false);
                    continue;
                }
                if (!opponent.isPermeable() && player.isJumping() && player.getPositionY() > opponent.getPositionY() + (opponent.getHeight() / 2)) {
                    player.stopCurrentJump();
                    continue;
                }

                // trigger player collide event
                opponent.playerCollideOpponentEvent();
            }
        }

        if (player.getPositionY() >= Player.START_POSITION_Y) return;
        if (!player.hasGravity() || player.isJumping()) return;

        player.setPositionY(player.getPositionY() + (Player.STEP_SIZE * 2));
    }
    //</editor-fold>
}

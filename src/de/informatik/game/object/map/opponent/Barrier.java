package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.object.graphic.gui.GameGui;
import de.informatik.game.object.map.Opponent;

import java.awt.Graphics2D;

/**
 * Eine {@link Barrier Barriere} stellt eine Instanz eines {@link Opponent Gegners} dar, welcher sich auf der
 * {@link de.informatik.game.object.map.Map} befinden kann. Dieser Gegner stellt eine unflexible, sich nicht bewegende
 * Barriere dar, welche der Spieler überwinden muss, um weiterzukommen.
 */
public final class Barrier implements Opponent {

    //<editor-fold desc="LOCAL FIELDS">
    /** Die Start-Koordinate der Barriere. */
    private int startX;
    /** Die aktuelle x-Koordinate der Barriere. */
    private int x;
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
            JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.BARRIER),
            x,
            310,
            50,
            50,
            null
        );
    }

    @Override
    public void playerMoveLeftEvent(final int playerPosition, final boolean isMovingBackground) {
        if (!isMovingBackground) return;
        if (playerPosition < startX - GameGui.WIDTH - 60)
            return;

        x = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() + startX;
    }

    @Override
    public void playerMoveRightEvent(final int playerPosition, final boolean isMovingBackground) {
        if (!isMovingBackground) return;
        if (playerPosition > startX + GameGui.WIDTH + 60) {
            return;
        }


        x = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() + startX;
    }

    @Override
    public void initializeOpponent(final int startX) {
        this.startX = startX;
        this.x = startX;
    }

    @Override
    public int getPositionX() {
        return x;
    }
    //</editor-fold>
}

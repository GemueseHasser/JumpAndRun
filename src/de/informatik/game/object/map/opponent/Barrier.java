package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.constant.MovementState;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;

import java.awt.Graphics2D;

/**
 * Eine {@link Barrier Barriere} stellt eine Instanz eines {@link Opponent Gegners} dar, welcher sich auf der
 * {@link de.informatik.game.object.map.Map} befinden kann. Dieser Gegner stellt eine unflexible, sich nicht bewegende
 * Barriere dar, welche der Spieler überwinden muss, um weiterzukommen.
 */
public final class Barrier implements Opponent {

    //<editor-fold desc="CONSTANTS">
    /** Der Zustand, ob dieser Gegner von der Oberseite aus durchlässig sein soll. */
    private static final boolean PERMEABLE = false;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die Start-Koordinate der Barriere. */
    private int initialStartingX;
    /** Die y-Koordinate dieses Gegners. */
    private int yCoordinate;
    /** Die Breite dieses Gegners. */
    private int width;
    /** Die Höhe dieses Gegners. */
    private int height;
    /** Die aktuelle x-Koordinate der Barriere. */
    private int currentX;
    /** Die Menge an x-Koordinaten, die der Hintergrund sich verschoben hat. */
    private int backgroundCounterX;
    /** Die letzte x-Koordinate des Hintergrundes. */
    private int lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
            JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.BARRIER),
            getPositionX(),
            getPositionY(),
            getWidth(),
            getHeight(),
            null
        );
    }

    @Override
    public void playerMoveLeftEvent(final int playerPosition, final boolean isBackgroundMovable) {
        if (!isBackgroundMovable) return;

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != lastBackgroundCentreX) {
            backgroundCounterX += Player.STEP_SIZE;
        }

        currentX = backgroundCounterX + initialStartingX;
        lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();

    }

    @Override
    public void playerMoveRightEvent(final int playerPosition, final boolean isBackgroundMovable) {
        if (!isBackgroundMovable) return;

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != lastBackgroundCentreX) {
            backgroundCounterX -= Player.STEP_SIZE;
        }

        currentX = backgroundCounterX + initialStartingX;
        lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    }

    @Override
    public void playerCollideOpponentEvent() {
        // decrement health, etc.
        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        // check if the player runs away
        if (player.getScreenPositionX() > currentX && player.getCurrentMovementState() == MovementState.RIGHT) return;
        if (player.getScreenPositionX() < currentX && player.getCurrentMovementState() == MovementState.LEFT) return;

        // freeze player
        player.stay();
    }

    @Override
    public void initializeOpponent(final int startX, final int startY, final int startWidth, final int startHeight) {
        this.initialStartingX = startX;
        this.yCoordinate = startY;
        this.width = startWidth;
        this.height = startHeight;
        this.currentX = startX;
    }

    @Override
    public boolean isPermeable() {
        return PERMEABLE;
    }

    @Override
    public int getPositionX() {
        return currentX;
    }

    @Override
    public int getPositionY() {
        return yCoordinate;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    //</editor-fold>
}

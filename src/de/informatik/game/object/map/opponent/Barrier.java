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
    /** Die Breite jeder Barriere. */
    private static final int WIDTH = 50;
    /** Die Höhe jeder Barriere. */
    private static final int HEIGHT = 60;
    /** Die y-Koordinate jeder Barriere. */
    private static final int Y_COORDINATE = 300;
    /** Der Zustand, ob dieser Gegner durchlässig sein soll. */
    private static final boolean PERMEABLE = false;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die Start-Koordinate der Barriere. */
    private int initialStartingX;
    /** Die aktuelle x-Koordinate der Barriere. */
    private int currentX;
    /** Die Menge an x-Koordinaten, die der Background sich verschoben hat. */
    private int backgroundCounterX;
    /** Die letzte x-Koordinate des Backgrounds. */
    private int lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
            JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.BARRIER),
                currentX,
            Y_COORDINATE,
            WIDTH,
            HEIGHT,
            null
        );
    }

    @Override
    public void playerMoveLeftEvent(final int playerPosition, final boolean isBackgroundMoving) {
        if (!isBackgroundMoving) return;

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != lastBackgroundCentreX){
            backgroundCounterX += Player.STEP_SIZE;
        }

        currentX = backgroundCounterX + initialStartingX;
        lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();

    }

    @Override
    public void playerMoveRightEvent(final int playerPosition, final boolean isBackgroundMoving) {
        if (!isBackgroundMoving) return;

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != lastBackgroundCentreX){
            backgroundCounterX -= Player.STEP_SIZE;
        }

        currentX = backgroundCounterX + initialStartingX;
        lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    }

    @Override
    public void playerCollideOpponentEvent() {
        // decrement health, etc.
        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        if (player.getScreenPositionX() > x && player.getCurrentMovementState() == MovementState.RIGHT) return;
        if (player.getScreenPositionX() < x && player.getCurrentMovementState() == MovementState.LEFT) return;

        player.stay();
    }

    @Override
    public void initializeOpponent(final int startX) {
        this.initialStartingX = startX;
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
        return Y_COORDINATE;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
    //</editor-fold>
}

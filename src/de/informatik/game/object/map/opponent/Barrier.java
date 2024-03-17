package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.constant.MovementState;
import de.informatik.game.handler.MapHandler;
import de.informatik.game.object.map.Map;
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
    /** Das Objekt, welches die Bewegung dieses Gegners simuliert. */
    private Map.StaticOpponentMovement staticOpponentMovement;
    /** Die y-Koordinate dieses Gegners. */
    private int yCoordinate;
    /** Die Breite dieses Gegners. */
    private int width;
    /** Die Höhe dieses Gegners. */
    private int height;
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
            ImageType.BARRIER.getImage(),
            getPositionX(),
            getPositionY(),
            getWidth(),
            getHeight(),
            null
        );
    }

    @Override
    public void playerMoveLeftEvent() {
        if (!MapHandler.isBackgroundMovable()) return;
        staticOpponentMovement.simulateLeftMovement();
    }

    @Override
    public void playerMoveRightEvent() {
        if (!MapHandler.isBackgroundMovable()) return;
        staticOpponentMovement.simulateRightMovement();
    }

    @Override
    public void playerCollideOpponentEvent() {
        // decrement health, etc.
        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        // check if the player runs away
        if (player.getScreenPositionX() > staticOpponentMovement.getCurrentX() && player.getCurrentMovementState() == MovementState.RIGHT)
            return;
        if (player.getScreenPositionX() < staticOpponentMovement.getCurrentX() && player.getCurrentMovementState() == MovementState.LEFT)
            return;

        // freeze player
        player.stay();
    }

    @Override
    public void initializeOpponent(final int startX, final int startY, final int startWidth, final int startHeight) {
        staticOpponentMovement = new Map.StaticOpponentMovement(startX);
        this.yCoordinate = startY;
        this.width = startWidth;
        this.height = startHeight;
    }

    @Override
    public boolean isPermeable() {
        return PERMEABLE;
    }

    @Override
    public int getPositionX() {
        return staticOpponentMovement.getCurrentX();
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

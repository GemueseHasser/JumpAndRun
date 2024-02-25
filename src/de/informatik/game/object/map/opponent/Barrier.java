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
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die Start-Koordinate der Barriere. */
    private int startX;
    /** Die aktuelle x-Koordinate der Barriere. */
    private int x;
    /** Die Menge an x-Koordinaten, die der Background sich verschoben hat. */
    private int Xcounter;
    /** Die letzte x-Koordinate des Backgrounds. */
    private int middleXbg = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
            JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.BARRIER),
            x,
            Y_COORDINATE,
            WIDTH,
            HEIGHT,
            null
        );
    }

    @Override
    public void playerMoveLeftEvent(final int playerPosition, final boolean isMovingBackground) {
        if (!isMovingBackground) return;

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != middleXbg){
            Xcounter += Player.STEP_SIZE;
        }
        // this is not quite working as intended
        // stacks the barriers, once they pass the screen

        // isMovingBackground was working, but named improperly

        x = Xcounter + startX;
        middleXbg = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();

        // JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() not updating properly
        // always jumps back to 0 at -615, when wrapping background, due to the bg's x-coordinate resetting
    }

    @Override
    public void playerMoveRightEvent(final int playerPosition, final boolean isMovingBackground) {
        if (!isMovingBackground) return;

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != middleXbg){
            Xcounter -= Player.STEP_SIZE;
        }
        // something is going wrong here
        // once the player moves left, the values get reset to the edge-coordinates

        // and now it won't work at all anymore....

        // why does it feel like this code wants to torture us

        x = Xcounter + startX;
        middleXbg = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    }

    @Override
    public void playerCollideOpponentEvent() {
        // decrement health, etc.
        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        if (player.isJumping()) {
            player.delayCurrentJumpUntilNoCollision(this);
            return;
        }

        if (player.getLastMovementState() == MovementState.LEFT && player.getCurrentMovementState() == MovementState.RIGHT) return;
        if (player.getLastMovementState() == MovementState.RIGHT && player.getCurrentMovementState() == MovementState.LEFT) return;

        player.stay();
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

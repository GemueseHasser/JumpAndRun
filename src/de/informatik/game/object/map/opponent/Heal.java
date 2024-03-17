package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;

import java.awt.Graphics2D;

/**
 * Eine {@link Heal Heilung} stellt eine Instanz eines {@link Opponent Gegners} dar, welcher sich auf der
 * {@link de.informatik.game.object.map.Map} befinden kann. Dieser Gegner stellt eine unflexible, sich nicht bewegende
 * Heilung dar, welche einmal vom Spieler genutzt werden kann und die dem Spieler eine gewisse Anzahl an Leben
 * verschafft.
 */
public final class Heal implements Opponent {

    //<editor-fold desc="CONSTANTS">
    /** Der Zustand, ob dieser Gegner von der Oberseite aus durchlässig sein soll. */
    private static final boolean PERMEABLE = true;
    /** Die Anzahl an Leben, um die der Spieler geheilt werden soll. */
    private static final int HEAL_AMOUNT = 10;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die Start-Koordinate des Stachels. */
    private int initialStartingX;
    /** Die y-Koordinate dieses Stachels. */
    private int yCoordinate;
    /** Die Breite dieses Stachels. */
    private int width;
    /** Die Höhe dieses Stachels. */
    private int height;
    /** Die aktuelle x-Koordinate des Stachels. */
    private int currentX;
    /** Die Menge an x-Koordinaten, die der Hintergrund sich verschoben hat. */
    private int backgroundCounterX;
    /** Die letzte x-Koordinate des Hintergrundes. */
    private int lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    /** Der Zustand, ob dieses Objekt bereits zur Auffüllung der Leben eingelöst wurde. */
    private boolean used;
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        if (used) return;

        g.drawImage(
            JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.HEAL),
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
        if (used) return;

        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        player.setHealth(player.getHealth() + HEAL_AMOUNT);
        used = true;
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

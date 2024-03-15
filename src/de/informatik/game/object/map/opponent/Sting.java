package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;

import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;

public final class Sting implements Opponent {

    //<editor-fold desc="CONSTANTS">
    /** Der Zustand, ob dieser Gegner von der Oberseite aus durchlässig sein soll. */
    private static final boolean PERMEABLE = true;
    /** Die zeitlichen Abstände in Millisekunden, in denen der Spieler von diesem Stachel Schaden zugefügt bekommt. */
    private static final int DAMAGE_PERIOD_IN_MILLIS = 100;
    /** Der Schaden, den dieser Stachel bei dem Spieler verursacht. */
    private static final int DAMAGE = 4;
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
    /** Der Zeitpunkt, zu dem der Spieler durch diesen Stachel zuletzt Schaden bekommen hat. */
    private Instant lastDamageMoment = Instant.now();
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
            JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.STING),
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
        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        if (Duration.between(lastDamageMoment, Instant.now()).toMillis() >= DAMAGE_PERIOD_IN_MILLIS) {
            player.setHealth(player.getHealth() - DAMAGE);
            lastDamageMoment = Instant.now();
        }
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

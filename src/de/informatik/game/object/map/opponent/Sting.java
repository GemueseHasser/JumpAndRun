package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.handler.MapHandler;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;
import de.informatik.game.object.map.StaticOpponentMovement;

import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;

/**
 * Ein {@link Sting Stachel} stellt eine Instanz eines {@link Opponent Gegners} dar, welcher sich auf der
 * {@link de.informatik.game.object.map.Map} befinden kann. Dieser Gegner stellt einen unflexiblen, sich nicht
 * bewegenden Stachel dar, welcher dem Spieler in gewissen zeitlichen Abständen einen gewissen Schaden zufügt, sollte
 * dieser den Stachel berühren.
 */
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
    /** Das Objekt, welches die Bewegung dieses Gegners simuliert. */
    private StaticOpponentMovement staticOpponentMovement;
    /** Die y-Koordinate dieses Stachels. */
    private int yCoordinate;
    /** Die Breite dieses Stachels. */
    private int width;
    /** Die Höhe dieses Stachels. */
    private int height;
    /** Der Zeitpunkt, zu dem der Spieler durch diesen Stachel zuletzt Schaden bekommen hat. */
    private Instant lastDamageMoment = Instant.now();
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
            ImageType.STING.getImage(),
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
        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        if (Duration.between(lastDamageMoment, Instant.now()).toMillis() >= DAMAGE_PERIOD_IN_MILLIS) {
            player.setHealth(player.getHealth() - DAMAGE);
            lastDamageMoment = Instant.now();
        }
    }

    @Override
    public void initializeOpponent(final int startX, final int startY, final int startWidth, final int startHeight) {
        staticOpponentMovement = new StaticOpponentMovement(startX);
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

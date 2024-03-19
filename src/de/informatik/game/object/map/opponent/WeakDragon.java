package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.handler.MapHandler;
import de.informatik.game.object.graphic.gui.GameGui;
import de.informatik.game.object.map.Map;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;

import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;

/**
 * Ein {@link WeakDragon Dragon} stellt eine Instanz eines {@link Opponent Gegners} dar, welcher sich auf der
 * {@link de.informatik.game.object.map.Map} befinden kann. Dieser Gegner stellt eine flexible, sich bewegendes
 * Objekt dar, welches der Spieler bewältigen muss, um weiterzukommen.
 */
public final class WeakDragon implements Opponent {

    //<editor-fold desc="LOCAL FIELDS">
    private Map.StaticOpponentMovement staticOpponentMovement;
    private int yCoordinate;
    private int width;
    private int height;

    private static final int DAMAGE_PERIOD_IN_MILLIS = 100;
    /** Der Schaden, den dieser Stachel bei dem Spieler verursacht. */
    private static final int DAMAGE = 2;
    //</editor-fold>
    private Instant lastDamageMoment = Instant.now();

    private int DMGFlame = 10;
    /** Der Schaden den der Drache mit seiner Flamme verursacht. */
    private int HP = 100;
    /** Die Lebenspunkte des Drachen. */
    private int SPEED = 5;

    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
                ImageType.WEAKDRAGON.getImage(),
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

        if (Duration.between(lastDamageMoment, Instant.now()).toMillis() >= DAMAGE_PERIOD_IN_MILLIS) {
            player.setHealth(player.getHealth() - DAMAGE);
            lastDamageMoment = Instant.now();
        }}



    @Override
    public boolean isPermeable() {
        return false;
    }

    @Override
    public void initializeOpponent(final int startX, final int yCoordinate, final int width, final int height) {
        staticOpponentMovement = new Map.StaticOpponentMovement(startX);
        this.yCoordinate = yCoordinate;
        this.width = width;
        this.height = height;
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
    /**
    public void animation() {


    }
*/
    //</editor-fold>
/**
    public void spitFire () {




        return DMGFlame*difficulty


    }
    private int flameDamage(int difficulty) { difficulty z.B. 1-3
    }



    */
}

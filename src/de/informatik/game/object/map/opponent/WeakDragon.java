package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.object.graphic.gui.GameGui;
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

    //<editor-fold desc="CONSTANTS">
    /** Die Größe von jedem Dragon. */
    private static final int SIZE = 150;
    /** Die y-Koordinate jeder Dragon. */
    private static final int Y_COORDINATE = 235;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die Start-Koordinate der Dragon. */
    private int startX = 50;
    /** Die aktuelle x-Koordinate der Dragon. */
    private int x;
    //</editor-fold>


    private int DMGHit = 5;
    private int DMGFlame = 10;
    /** Der Schaden den Der Drache mit seiner Flamme verursacht. */
    private int HP = 1000;
    /** Die Lebenspunkte des Drachen. */
    private int SPEED = 5;


    private Instant lastHitMoment = Instant.now();
    private Instant lastFlameMoment = Instant.now();

    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
                JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.WEAKDRAGON),
                x,
                Y_COORDINATE,
                2*SIZE,
                SIZE,
                null
        );
    }

    @Override
    public void playerMoveLeftEvent(final int playerPosition, final boolean isMovingBackground) {
        if (!isMovingBackground) return;
        if (playerPosition < startX - GameGui.WIDTH - 70)
            return;

        x = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() + startX;
    }

    @Override
    public void playerMoveRightEvent(final int playerPosition, final boolean isMovingBackground) {
        if (!isMovingBackground) return;
        if (playerPosition > startX + GameGui.WIDTH + 70) {
            return;
        }


        x = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() + startX;
    }

    @Override
    public void playerCollideOpponentEvent() {
        // decrement health, etc.
        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        if (Duration.between(lastHitMoment, Instant.now()).toMillis() > 1000) {
            player.setHealth(player.getHealth() - DMGHit);
            lastHitMoment = Instant.now();
        }
    }

    public void flameAttack(){
        if(Duration.between(lastFlameMoment,Instant.now()).toMillis() > 2000) {
            spitFire();
        }

    }

    private void spitFire(){

    };


    @Override
    public void initializeOpponent(final int startX) {
        this.startX = startX;
        this.x = startX;
    }
    @Override
    public int getHeight(){
        return SIZE;
    }

    @Override
    public int getWidth(){
        return 2*SIZE;
    }

    @Override
    public int getPositionX() {
        return x;
    }

    @Override
    public int getPositionY() {
        return Y_COORDINATE;
    }

   // @Override
   // public int getSize() {
  //      return SIZE;
  //  }
    //</editor-fold>
/**
    public void spitFire () {




        return DMGFlame*difficulty


    }
    private int flameDamage(int difficulty) { difficulty z.B. 1-3
    }



    */
}
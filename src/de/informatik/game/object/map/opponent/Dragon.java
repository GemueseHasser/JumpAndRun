package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.object.graphic.gui.GameGui;
import de.informatik.game.object.map.Opponent;

import java.awt.Graphics2D;

/**
 * Ein {@link Dragon Dragon} stellt eine Instanz eines {@link Opponent Gegners} dar, welcher sich auf der
 * {@link de.informatik.game.object.map.Map} befinden kann. Dieser Gegner stellt eine flexible, sich bewegendes
 * Objekt dar, welches der Spieler bewältigen muss, um weiterzukommen.
 */
public final class Dragon implements Opponent {

    //<editor-fold desc="CONSTANTS">
    /** Die Größe von jedem Dragon. */
    private static final int SIZE = 100;
    /** Die y-Koordinate jeder Dragon. */
    private static final int Y_COORDINATE = 390;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die Start-Koordinate der Dragon. */
    private int startX;
    /** Die aktuelle x-Koordinate der Dragon. */
    private int x;
    //</editor-fold>


    private int DMGFlame = 10;
    /** Der Schaden den Der Drache mit seiner Flamme verursacht. */
    private int HP = 100;
    /** Die Lebenspunkte des Drachen. */
    private int SPEED = 5;


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        g.drawImage(
                JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.BARRIER),
                x,
                Y_COORDINATE,
                SIZE,
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
    public int getSize() {
        return SIZE;
    }
    //</editor-fold>
/**
    public void spitFire () {




        return DMGFlame*difficulty


    }
    private int flameDamage(int difficulty) { difficulty z.B. 1-3
    }



    */
}

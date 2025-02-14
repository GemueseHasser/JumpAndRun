package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.GameState;
import de.informatik.game.constant.ImageType;
import de.informatik.game.constant.SoundType;
import de.informatik.game.handler.MapHandler;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.StaticOpponentMovement;

import javax.sound.sampled.Clip;

import java.awt.Graphics2D;

/**
 * Eine {@link Scroll Schriftrolle} stellt eine Instanz eines {@link Opponent Gegners} dar, welcher sich auf der
 * {@link de.informatik.game.object.map.Map} befinden kann. Dieser Gegner stellt eine unflexible, sich nicht bewegende
 * Schriftrolle dar, welche als "Sammelobjekt" dazu dient, das Level mit einem Sieg zu beenden.
 */
public final class Scroll implements Opponent {

    //<editor-fold desc="CONSTANTS">
    /** Der Zustand, ob dieser Gegner von der Oberseite aus durchlässig sein soll. */
    private static final boolean PERMEABLE = true;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Das Objekt, welches die Bewegung dieses Gegners simuliert. */
    private StaticOpponentMovement staticOpponentMovement;
    /** Die y-Koordinate dieses Gegners. */
    private int yCoordinate;
    /** Die Breite dieses Gegners. */
    private int width;
    /** Die Höhe dieses Gegners. */
    private int height;
    /** Der Zustand, ob dieses Objekt bereits eingesammelt wurde. */
    private boolean used;
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void drawOpponent(final Graphics2D g) {
        if (used) return;

        g.drawImage(
            ImageType.SCROLL.getImage(),
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
        if (used) return;

        // update game-state
        JumpAndRun.GAME_INSTANCE.getGameHandler().updateGameState(GameState.WIN);
        used = true;

        // stop game music
        SoundType.GAME_BACKGROUND.stop();

        // play death-sound and new background music
        SoundType.WIN_SOUND.play(0);
        SoundType.WIN_MUSIC.play(Clip.LOOP_CONTINUOUSLY);
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

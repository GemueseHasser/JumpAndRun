package de.informatik.game.object.map;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.constant.MovementState;
import de.informatik.game.object.graphic.gui.GameGui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Der Spieler, welcher vom Nutzer dieses Spiels gesteuert wird und welcher sich in der {@link Map Spielumgebung}
 * befindet.
 */
public final class Player {

    //<editor-fold desc="CONSTANTS">
    /** Der Punkt, den der Spieler maximal nach links gehen kann. */
    public static final int MAX_LEFT_POINT_ON_SCREEN = 10;
    /** Der Punkt, den der Spieler maximal nach rechts gehen kann. */
    public static final int MAX_RIGHT_POINT_ON_SCREEN = GameGui.WIDTH - 70;
    /** Die Größe jedes Schrittes des Spielers. */
    public static final int STEP_SIZE = 5;
    /** Die Größe des Spielers. */
    public static final int PLAYER_SIZE = 60;
    /** Die Anzahl an einzelnen Animationen, die es für den Spieler gibt. */
    private static final int ANIMATION_SIZE = 4;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die aktuelle absolute Position des Spielers. */
    private int absolutePositionX;
    /** Die aktuelle Position auf dem Bildschirm des Spielers. */
    private int screenPositionX;
    /** Die Zählvariable für die Animation des Spielers. */
    private int currentAnimationCount = 1;
    /** Die aktuelle Animation des Spielers in Form eines Bildes. */
    private BufferedImage currentAnimation;
    /** Der aktuelle Status, in welche Richtung sich der Spieler bewegt. */
    private MovementState currentMovementState = MovementState.RIGHT;
    //</editor-fold>


    /**
     * Initialisiert diesen Spieler.
     */
    public void initialize() {
        // set players position
        absolutePositionX = MAX_LEFT_POINT_ON_SCREEN;
        screenPositionX = MAX_LEFT_POINT_ON_SCREEN;

        // update animation
        updateAnimation();
    }

    /**
     * Zeichnet diesen Spieler.
     *
     * @param g Das {@link Graphics2D Graphics-Objekt}, auf dessen Grundlage der Spieler gezeichnet wird.
     */
    public void drawPlayer(final Graphics2D g) {
        switch (currentMovementState) {
            case LEFT -> g.drawImage(currentAnimation, screenPositionX + PLAYER_SIZE, 300, -PLAYER_SIZE, PLAYER_SIZE, null);
            case RIGHT -> g.drawImage(currentAnimation, screenPositionX, 300, PLAYER_SIZE, PLAYER_SIZE, null);
        }
    }

    /**
     * Simuliert die Bewegung nach links.
     */
    public void moveLeft() {
        // check if player should overall move left
        if (absolutePositionX - STEP_SIZE <= MAX_LEFT_POINT_ON_SCREEN) return;

        // update current movement state
        currentMovementState = MovementState.LEFT;

        // update player animation
        updateAnimation();
        currentAnimationCount--;

        // decrement absolute position
        absolutePositionX -= STEP_SIZE;

        // check if player should move left on screen
        if (screenPositionX <= MAX_LEFT_POINT_ON_SCREEN) return;

        // update position on screen
        screenPositionX -= STEP_SIZE;
    }

    /**
     * Simuliert die Bewegung nach rechts.
     */
    public void moveRight() {
        // check if player should overall move right
        if (absolutePositionX + STEP_SIZE >= JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getMapLength()) return;

        // update current movement state
        currentMovementState = MovementState.RIGHT;

        // update player animation
        updateAnimation();
        currentAnimationCount++;

        // increment absolute position
        absolutePositionX += STEP_SIZE;

        // check if player should move right on screen
        if (screenPositionX >= MAX_RIGHT_POINT_ON_SCREEN) return;

        // update position on screen
        screenPositionX += STEP_SIZE;
    }

    /**
     * Setzt die Animation immer auf die Standard-Position zurück.
     */
    public void resetAnimationCount() {
        currentAnimationCount = 1;
        updateAnimation();
    }

    /**
     * Aktualisiert auf Grundlage der Zählvariable die aktuelle Animation des Spielers. Sollte sich die Zählvariable
     * außerhalb der festgelegten Grenzen befinden, wird diese wieder korrekt aktualisiert.
     */
    private void updateAnimation() {
        if (currentAnimationCount > ANIMATION_SIZE) currentAnimationCount = 1;
        if (currentAnimationCount < 1) currentAnimationCount = ANIMATION_SIZE;

        currentAnimation = ImageType.valueOf("UNC_" + currentAnimationCount).getImage();
    }

    //<editor-fold desc="Getter">

    /**
     * Gibt die aktuelle absolute Position des Spielers zurück.
     *
     * @return Die aktuelle absolute Position des Spielers.
     */
    public int getAbsolutePositionX() {
        return absolutePositionX;
    }

    /**
     * Gibt die aktuelle Position auf dem Bildschirm des Spielers zurück.
     *
     * @return Die aktuelle Position auf dem Bildschirm des Spielers.
     */
    public int getScreenPositionX() {
        return screenPositionX;
    }

    /**
     * Gibt den aktuellen Status, in welche Richtung sich der Spieler bewegt, zurück.
     *
     * @return Der aktuelle Status, in welche Richtung sich der Spieler bewegt.
     */
    public MovementState getCurrentMovementState() {
        return this.currentMovementState;
    }
    //</editor-fold>

}

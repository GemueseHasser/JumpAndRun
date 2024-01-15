package de.informatik.game.object.map;

import de.informatik.game.JumpAndRun;
import de.informatik.game.object.graphic.gui.GameGui;

import java.awt.Color;
import java.awt.Graphics2D;

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
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die aktuelle absolute Position des Spielers. */
    private int absolutePositionX = MAX_LEFT_POINT_ON_SCREEN;
    /** Die aktuelle Position auf dem Bildschirm des Spielers. */
    private int screenPositionX = MAX_LEFT_POINT_ON_SCREEN;
    //</editor-fold>


    /**
     * Zeichnet diesen Spieler.
     *
     * @param g Das {@link Graphics2D Graphics-Objekt}, auf dessen Grundlage der Spieler gezeichnet wird.
     */
    public void drawPlayer(final Graphics2D g) {
        g.setColor(Color.RED);
        g.drawRect(screenPositionX, 310, 50, 50);
    }

    /**
     * Simuliert die Bewegung nach links.
     */
    public void moveLeft() {
        if (absolutePositionX - STEP_SIZE <= MAX_LEFT_POINT_ON_SCREEN) return;

        absolutePositionX -= STEP_SIZE;

        if (screenPositionX <= MAX_LEFT_POINT_ON_SCREEN) return;

        screenPositionX -= STEP_SIZE;
    }

    /**
     * Simuliert die Bewegung nach rechts.
     */
    public void moveRight() {
        if (absolutePositionX + STEP_SIZE >= JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getMapLength()) return;

        absolutePositionX += STEP_SIZE;

        if (screenPositionX >= MAX_RIGHT_POINT_ON_SCREEN) return;

        screenPositionX += STEP_SIZE;
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
    //</editor-fold>

}

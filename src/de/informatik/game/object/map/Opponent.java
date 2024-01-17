package de.informatik.game.object.map;

import java.awt.Graphics2D;

/**
 * Ein {@link Opponent Gegner} stellt eine Instanz jedes Gegners auf dem Spielfeld dar.
 */
public interface Opponent {

    /**
     * Zeichnet den Gegner auf das {@link Map Spielfeld}.
     *
     * @param g Das {@link Graphics2D Graphics-Objekt}, mit dem der Gegner auf das Spielfeld gezeichnet wird.
     */
    void drawOpponent(final Graphics2D g);

    /**
     * Das Event, welches getriggert wird, wenn sich der Spieler nach links bewegt.
     *
     * @param playerPosition     Die aktuelle absolute Position des Spielers.
     * @param isBackgroundMoving Der Zustand, ob sich der Hintergrund gerade bewegt oder nicht.
     */
    void playerMoveLeftEvent(final int playerPosition, final boolean isBackgroundMoving);

    /**
     * Das Event, welches getriggert wird, wenn sich der Spieler nach rechts bewegt.
     *
     * @param playerPosition     Die aktuelle absolute Position des Spielers.
     * @param isBackgroundMoving Der Zustand, ob sich der Hintergrund gerade bewegt oder nicht.
     */
    void playerMoveRightEvent(final int playerPosition, final boolean isBackgroundMoving);

    /**
     * Dieser Gegner wird hiermit initialisiert. Das heißt, dass diese Methode einzig und allein beim Laden des Spielers
     * auf der Map ausgeführt wird.
     *
     * @param startX Die Koordinate, an der der Gegner zu Beginn platziert werden soll, welche aus der jeweiligen Datei
     *               der Map ausgelesen wird.
     */
    void initializeOpponent(final int startX);

    /**
     * Soll die aktuelle Position des Gegners zurückgeben.
     *
     * @return Die aktuelle Position des Gegners.
     */
    int getPositionX();

}

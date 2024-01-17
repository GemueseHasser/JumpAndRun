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
     * Das Event, welches getriggert wird, wenn der Spieler mit diesem Gegner zusammenstößt.
     */
    void playerCollideOpponentEvent();

    /**
     * Dieser Gegner wird hiermit initialisiert. Das heißt, dass diese Methode einzig und allein beim Laden des Spielers
     * auf der Map ausgeführt wird.
     *
     * @param startX Die Koordinate, an der der Gegner zu Beginn platziert werden soll, welche aus der jeweiligen Datei
     *               der Map ausgelesen wird.
     */
    void initializeOpponent(final int startX);

    /**
     * Soll die aktuelle x-Koordinate des Gegners zurückgeben.
     *
     * @return Die aktuelle x-Koordinate des Gegners.
     */
    int getPositionX();

    /**
     * Soll die aktuelle y-Koordinate des Gegners zurückgeben.
     *
     * @return Die aktuelle y-Koordinate des Gegners.
     */
    int getPositionY();

    /**
     * Soll die tatsächliche Größe des Gegners zurückgeben, also die Größe des sichtbaren Gegners und nicht die des
     * Bildes.
     *
     * @return Die Größe des Gegners, also die Größe des sichtbaren Gegners und nicht die des Bildes.
     */
    int getSize();

}

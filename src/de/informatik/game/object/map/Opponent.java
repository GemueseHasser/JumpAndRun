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
     * @param playerPosition      Die aktuelle absolute Position des Spielers.
     * @param isBackgroundMovable Der Zustand, ob der Hintergrund aktuell bewegbar ist.
     */
    void playerMoveLeftEvent(final int playerPosition, final boolean isBackgroundMovable);

    /**
     * Das Event, welches getriggert wird, wenn sich der Spieler nach rechts bewegt.
     *
     * @param playerPosition      Die aktuelle absolute Position des Spielers.
     * @param isBackgroundMovable Der Zustand, ob der Hintergrund gerade bewegbar ist.
     */
    void playerMoveRightEvent(final int playerPosition, final boolean isBackgroundMovable);

    /**
     * Das Event, welches getriggert wird, wenn der Spieler mit diesem Gegner zusammenstößt.
     */
    void playerCollideOpponentEvent();

    /**
     * Dieser Gegner wird hiermit initialisiert. Das heißt, dass diese Methode einzig und allein beim Laden des Spielers
     * auf der Map ausgeführt wird.
     *
     * @param startX      Die x-Koordinate, an der der Gegner zu Beginn platziert werden soll, welche aus der jeweiligen
     *                    Datei der Map ausgelesen wird.
     * @param startY      Die y-Koordinate, an der der Gegner zu Beginn platziert werden soll, welche aus der jeweiligen
     *                    Datei der Map ausgelesen wird.
     * @param startWidth  Die Breite, die der Gegner zu Beginn haben soll, welche aus der jeweiligen Datei der Map
     *                    ausgelesen wird.
     * @param startHeight Die Höhe, die der Gegner zu Beginn haben soll, welche aus der jeweiligen Datei der Map
     *                    ausgelesen wird.
     */
    void initializeOpponent(final int startX, final int startY, final int startWidth, final int startHeight);

    /**
     * Der Zustand, ob der Gegner von der Oberseite aus durchlässig sein soll.
     *
     * @return Wenn der Gegner von der Oberseite aus durchlässig sein soll {@code true}, ansonsten {@code false}.
     */
    boolean isPermeable();

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
     * Soll die Breite des Gegners im Spiel zurückgeben.
     *
     * @return Die Breite des Gegners im Spiel.
     */
    int getWidth();

    /**
     * Soll die Höhe des Gegners im Spiel zurückgeben.
     *
     * @return Die Höhe des Gegners im Spiel.
     */
    int getHeight();

}

package de.informatik.game.object.map;

import de.informatik.game.constant.OpponentType;

/**
 * Mithilfe des {@link OpponentLoader} wird für den jeweiligen {@link OpponentType} für den dieser Loader angelegt
 * wird ein Gegner charakterisiert mithilfe einer x- und einer y-Koordinate bzw. einer Breite und einer Höhe.
 */
public final class OpponentLoader {

    //<editor-fold desc="LOCAL FIELDS">
    /** Der {@link OpponentType Typ} des Gegners, der geladen werden soll. */
    private OpponentType type;
    /** Die x-Koordinate, an der der Gegner initialisiert werden soll. */
    private int x;
    /** Die y-Koordinate, an der der Gegner initialisiert werden soll. */
    private int y;
    /** Die Breite, mit der der Gegner initialisiert werden soll. */
    private int width;
    /** Die Höhe, mit der der Gegner initialisiert werden soll. */
    private int height;
    //</editor-fold>


    //<editor-fold desc="Getter">

    /**
     * Gibt den Typ des Gegners zurück, der geladen werden soll.
     *
     * @return Der {@link OpponentType Typ} des Gegners, der geladen werden soll.
     */
    public OpponentType getType() {
        return type;
    }

    /**
     * Gibt die x-Koordinate zurück, an der dieser Gegner initialisiert werden soll.
     *
     * @return Die x-Koordinate, an der dieser Gegner initialisiert werden soll.
     */
    public int getX() {
        return this.x + Player.MAX_LEFT_POINT_ON_SCREEN;
    }

    /**
     * Gibt die y-Koordinate zurück, an der dieser Gegner initialisiert werden soll.
     *
     * @return Die y-Koordinate, an der dieser Gegner initialisiert werden soll.
     */
    public int getY() {
        return Player.START_POSITION_Y + Player.PLAYER_SIZE - height - y;
    }

    /**
     * Gibt die Breite zurück, mit der dieser Gegner initialisiert werden soll.
     *
     * @return Die Breite, mit der dieser Gegner initialisiert werden soll.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gibt die Höhe zurück, mit der dieser Gegner initialisiert werden soll.
     *
     * @return Die Höhe, mit der dieser Gegner initialisiert werden soll.
     */
    public int getHeight() {
        return height;
    }
    //</editor-fold>

    //<editor-fold desc="Setter">

    /**
     * Setzt den Typen des Gegners neu, der geladen werden soll.
     *
     * @param type Der {@link OpponentType Typ} des Gegners, der geladen werden soll.
     */
    public void setType(final OpponentType type) {
        this.type = type;
    }

    /**
     * Setzt die x-Koordinate, mit der dieser Gegner initialisiert werden soll.
     *
     * @param x Die x-Koordinate, mit der dieser Gegner initialisiert werden soll.
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Setzt die y-Koordinate, mit der dieser Gegner initialisiert werden soll.
     *
     * @param y Die y-Koordinate, mit der dieser Gegner initialisiert werden soll.
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * Setzt die Breite, mit der dieser Gegner initialisiert werden soll.
     *
     * @param width Die Breite, mit der dieser Gegner initialisiert werden soll.
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * Setzt die Höhe, mit der dieser Gegner initialisiert werden soll.
     *
     * @param height Die Höhe, mit der dieser Gegner initialisiert werden soll.
     */
    public void setHeight(final int height) {
        this.height = height;
    }
    //</editor-fold>

}

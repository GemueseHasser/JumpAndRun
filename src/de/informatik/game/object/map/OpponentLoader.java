package de.informatik.game.object.map;

import de.informatik.game.constant.OpponentType;

import java.util.List;

/**
 * Mithilfe des {@link OpponentLoader} wird für jeden {@link OpponentType Typ} aus allen Koordinaten, die in der
 * Map-Datei hinterlegt wurden, jeder einzelne {@link Opponent Gegner} erzeugt, wobei der {@link OpponentLoader}
 * eigentlich nur eine Data-Klasse darstellt, mit dessen Hilfe diese Gegner geladen werden.
 */
public final class OpponentLoader {

    //<editor-fold desc="LOCAL FIELDS">
    /** Der {@link OpponentType Typ} des Gegners, welcher an verschiedenen Koordinaten geladen werden soll. */
    private OpponentType type;
    /** Alle Positionen, an denen dieser Typ eines Gegners geladen werden soll. */
    private List<Integer> positions;
    //</editor-fold>


    //<editor-fold desc="Getter">

    /**
     * Gibt den Typ des Gegners zurück, welcher an verschiedenen Koordinaten geladen werden soll.
     *
     * @return Der {@link OpponentType Typ} des Gegners, welcher an verschiedenen Koordinaten geladen werden soll.
     */
    public OpponentType getType() {
        return type;
    }

    /**
     * Gibt alle Positionen zurück, an denen dieser Typ eines Gegners geladen werden soll.
     *
     * @return Alle Positionen, an denen dieser Typ eines Gegners geladen werden soll.
     */
    public List<Integer> getPositions() {
        return positions;
    }
    //</editor-fold>

    //<editor-fold desc="Setter">

    /**
     * Setzt den Typen des Gegners neu, welcher an verschiedenen Koordinaten geladen werden soll.
     *
     * @param type Der {@link OpponentType Typ} des Gegners, welcher an verschiedenen Koordinaten geladen werden soll.
     */
    public void setType(final OpponentType type) {
        this.type = type;
    }

    /**
     * Setzt alle Positionen neu, an denen dieser Typ eines Gegners geladen werden soll.
     *
     * @param positions Alle Positionen, an denen dieser Typ eines Gegners geladen werden soll.
     */
    public void setPositions(final List<Integer> positions) {
        this.positions = positions;
    }
    //</editor-fold>
}

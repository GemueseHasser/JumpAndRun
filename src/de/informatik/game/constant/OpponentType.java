package de.informatik.game.constant;

import de.informatik.game.object.map.opponent.Barrier;
import de.informatik.game.object.map.opponent.Heal;
import de.informatik.game.object.map.opponent.Jumppad;
import de.informatik.game.object.map.opponent.Scroll;
import de.informatik.game.object.map.opponent.Sting;

/**
 * Ein {@link OpponentType} stellt einen Typen eines Gegners dar, welcher für jeden Typen eines Gegners angelegt werden
 * muss, um festzulegen, auf welcher Klasse dieser Gegner basiert.
 */
public enum OpponentType {

    //<editor-fold desc="VALUES">
    /** Der Typ einer einfachen Barriere. */
    BARRIER(Barrier.class),
    /** Der Typ eines Sprungbretts. */
    JUMPPAD(Jumppad.class),
    /** Der Typ eines Stachels. */
    STING(Sting.class),
    /** Der Typ einer Heilung. */
    HEAL(Heal.class),
    /** Der Typ einer Schriftrolle. */
    SCROLL(Scroll.class);
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die Klasse, auf der der Gegner basiert. */
    private final Class<?> opponentClass;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue und vollständig unabhängige Instanz eines {@link OpponentType}. Ein {@link OpponentType} stellt
     * einen Typen eines Gegners dar, welcher für jeden Typen eines Gegners angelegt werden muss, um festzulegen, auf
     * welcher Klasse dieser Gegner basiert.
     *
     * @param opponentClass Die Klasse, auf der der Gegner basiert.
     */
    OpponentType(final Class<?> opponentClass) {
        this.opponentClass = opponentClass;
    }
    //</editor-fold>


    //<editor-fold desc="Getter">

    /**
     * Gibt die Klasse zurück, auf der der Gegner basiert.
     *
     * @return Die Klasse, auf der der Gegner basiert.
     */
    public Class<?> getOpponentClass() {
        return opponentClass;
    }
    //</editor-fold>

}

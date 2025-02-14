package de.informatik.game.object.map;

import de.informatik.game.JumpAndRun;

/**
 * Ein {@link StaticOpponentMovement} ist ein Objekt, welches man für einen bestimmten Gegner erzeugen kann, welcher
 * sich statisch mit dem Hintergrund der aktuellen {@link Map} mitbewegen soll. Es wird also durch Aufrufen der
 * Methoden des {@link StaticOpponentMovement} in den jeweiligen implementierten Methoden in der jeweiligen
 * Gegner-Klasse die Berechnung der aktuellen x-Koordinate des Gegners übernommen.
 */
public final class StaticOpponentMovement {

    //<editor-fold desc="LOCAL FIELDS">
    /** Die x-Koordinate, mit der der Gegner initialisiert wurde. */
    private final int initialStartingX;
    /** Die aktuelle x-Koordinate des Gegners. */
    private int currentX;
    /** Die Menge an x-Koordinaten, die der Hintergrund sich verschoben hat. */
    private int backgroundCounterX;
    /** Die letzte x-Koordinate des Hintergrundes. */
    private int lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue und vollständig unabhängige Instanz eines {@link StaticOpponentMovement}. Ein
     * {@link StaticOpponentMovement} ist ein Objekt, welches man für einen bestimmten Gegner erzeugen kann, welcher
     * sich statisch mit dem Hintergrund der aktuellen {@link Map} mitbewegen soll. Es wird also durch Aufrufen der
     * Methoden des {@link StaticOpponentMovement} in den jeweiligen implementierten Methoden in der jeweiligen
     * Gegner-Klasse die Berechnung der aktuellen x-Koordinate des Gegners übernommen.
     *
     * @param initialStartingX Die x-Koordinate, mit der der Gegner initialisiert wurde.
     */
    public StaticOpponentMovement(final int initialStartingX) {
        this.initialStartingX = initialStartingX;
        this.currentX = initialStartingX;
    }
    //</editor-fold>


    /**
     * Simuliert die Bewegung nach links.
     */
    public void simulateLeftMovement() {
        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != lastBackgroundCentreX) {
            backgroundCounterX += Player.STEP_SIZE;
        }

        currentX = backgroundCounterX + initialStartingX;
        lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    }

    /**
     * Simuliert die Bewegung nach rechts.
     */
    public void simulateRightMovement() {
        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != lastBackgroundCentreX) {
            backgroundCounterX -= Player.STEP_SIZE;
        }

        currentX = backgroundCounterX + initialStartingX;
        lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    }


    //<editor-fold desc="Getter">

    /**
     * Gibt die aktuelle x-Koordinate des Gegners zurück.
     *
     * @return Die aktuelle x-Koordinate des Gegners.
     */
    public int getCurrentX() {
        return this.currentX;
    }
    //</editor-fold>

}

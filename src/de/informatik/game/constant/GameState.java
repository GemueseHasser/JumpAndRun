package de.informatik.game.constant;

/**
 * Ein {@link GameState} stellt den Status eines Spiels dar, also ob das Spiel läuft, gewonnen ist oder verloren wurde.
 */
public enum GameState {

    //<editor-fold desc="VALUES">
    /** Der Status für ein gerade laufendes Spiel. */
    RUNNING(null),
    /** Der Status für ein gewonnenes Spiel. */
    WIN("Gewonnen!"),
    /** Der Status für ein verlorenes Spiel. */
    LOSE("Verloren!");
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Der Text, welcher auf dem Bildschirm angezeigt werden soll, wenn dieser Status erreicht wird. */
    private final String displayText;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue und vollständig unabhängige Instanz eines {@link GameState Status}. Ein {@link GameState}
     * stellt den Status eines Spiels dar, also ob das Spiel läuft, gewonnen ist oder verloren wurde.
     *
     * @param displayText Der Text, welcher auf dem Bildschirm angezeigt werden soll, wenn dieser Status erreicht wird.
     */
    GameState(final String displayText) {
        this.displayText = displayText;
    }
    //</editor-fold>


    //<editor-fold desc="Getter">

    /**
     * Gibt den Text wieder, welcher auf dem Bildschirm angezeigt werden soll, wenn dieser Status erreicht wird.
     *
     * @return Der Text, welcher auf dem Bildschirm angezeigt werden soll, wenn dieser Status erreicht wird.
     */
    public String getDisplayText() {
        return this.displayText;
    }
    //</editor-fold>

}

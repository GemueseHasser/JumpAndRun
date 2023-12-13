package de.informatik.game.handler;

import de.informatik.game.object.graphic.Gui;

/**
 * Der {@link GameHandler} stellt die nächsthöhere Instanz des Spiels - nach der Haupt- und Main-Klasse - dar und in
 * dieser Klasse werden alle Angelegenheiten, die während des Spielbetriebs geregelt werden müssen, geregelt.
 */
public final class GameHandler {

    //<editor-fold desc="LOCAL FIELDS">
    /** Die aktuelle Position des Hintergrundes. */
    private int backgroundPosition;
    //</editor-fold>


    /**
     * Aktualisiert die aktuelle Position des Hintergrundes, um die Bewegung zu simulieren.
     */
    public void updateBackgroundPosition() {
        backgroundPosition = (backgroundPosition <= -Gui.WIDTH) ? 0 : backgroundPosition - 1;
    }

    //<editor-fold desc="Getter">

    /**
     * Gibt die aktuelle Position des Hintergrundes zurück.
     *
     * @return Die aktuelle Position des Hintergrundes.
     */
    public int getBackgroundPosition() {
        return this.backgroundPosition;
    }
    //</editor-fold>

}

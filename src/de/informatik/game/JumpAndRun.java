package de.informatik.game;

import de.informatik.game.constant.ImageType;
import de.informatik.game.object.graphic.Gui;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Die Haupt- und Main-Klasse dieses Spiels. In dieser Klasse wird das Spiel sowohl instanziiert als auch
 * initialisiert, weshalb sich die Main-Methode dieser Anwendung in der Klasse befindet, die als erstes von der JRE
 * automatisch aufgerufen wird.</p>
 * <p>Das Prinzip des Spiels gleicht dem eines gewöhnlichen Jump-and-Run spiels, bei dem man - abstrakt betrachtet -
 * eine Figur hat, mit der man durch eine 2-Dimensionale Spielwelt läuft und verschiedenen statischen oder flexiblen
 * Hindernissen ausweichen muss.</p>
 */
public class JumpAndRun {

    //<editor-fold desc="CONSTANTS">
    /** Die oberste Instanz dieses {@link JumpAndRun Spiels}. */
    public static final JumpAndRun GAME_INSTANCE = new JumpAndRun();
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Alle geladenen Bilder, welche als {@link ImageType Typ} hinterlegt wurden. */
    private final Map<ImageType, BufferedImage> loadedImages = new HashMap<>();
    //</editor-fold>


    //<editor-fold desc="main">

    /**
     * Die Main-Methode dieses {@link JumpAndRun Spiels}, welches als erstes von der JRE aufgerufen wird, um das
     * Programm zu initialisieren.
     *
     * @param args Die Argumente, die von der JRE beim Start des Programms übergeben werden.
     */
    public static void main(final String[] args) {
        // load images
        GAME_INSTANCE.loadImages();

        // create and open new gui-instance
        final Gui gui = new Gui();
        gui.open();
    }
    //</editor-fold>


    /**
     * Lädt alle Bilder, die als {@link ImageType Typ} hinterlegt wurden und speichert diese in einer {@link Map} ab.
     */
    private void loadImages() {
        for (final ImageType type : ImageType.values()) {
            loadedImages.put(type, type.getImage());
        }
    }

    //<editor-fold desc="Getter">

    /**
     * Gibt eine {@link Map} zurück, welche alle geladenen Bilder enthält, die als {@link ImageType Typ} hinterlegt
     * sind.
     *
     * @return Eine {@link Map}, welche alle geladenen Bilder enthält, die als {@link ImageType Typ} hinterlegt sind.
     */
    public Map<ImageType, BufferedImage> getLoadedImages() {
        return this.loadedImages;
    }
    //</editor-fold>

}

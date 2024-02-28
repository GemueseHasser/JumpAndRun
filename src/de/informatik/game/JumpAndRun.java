package de.informatik.game;

import de.informatik.game.constant.ImageType;
import de.informatik.game.handler.GameHandler;
import de.informatik.game.object.graphic.gui.GameGui;
import de.informatik.game.object.graphic.gui.MenuGui;
import de.informatik.game.task.KeyboardTask;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    /** Die Anzahl an Level, die in dem Spiel angeboten werden sollen. */
    public static final int LEVEL_AMOUNT = 15;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Der {@link GameHandler} des Spiels, welcher alle Angelegenheiten während das Spiel läuft, regelt. */
    private final GameHandler gameHandler = new GameHandler();
    /** Alle geladenen Bilder, welche als {@link ImageType Typ} hinterlegt wurden. */
    private final Map<ImageType, BufferedImage> loadedImages = new HashMap<>();
    /** Das Fenster, in dem das Menü in diesem Spiel angezeigt wird. */
    private final MenuGui menuGui = new MenuGui();
    /** Der Task, welcher die Tastatur überwacht. */
    private final KeyboardTask keyboardTask = new KeyboardTask();
    /** Die Instanz des aktuellen {@link GameGui} ({@code null}, wenn kein Gui geöffnet ist). */
    private GameGui currentGameGui;
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

        // copy default map file
        GAME_INSTANCE.copyDefaultMapFiles();

        // open menu-gui
        GAME_INSTANCE.getMenuGui().open();
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

    /**
     * Kopiert alle Maps, die in diesem Spiel angeboten werden sollen in einen automatisch erzeugten Ordner - zufalls
     * dieser nicht schon vorhanden ist. Maps, die bereits vorhanden sind, werden übersprungen.
     */
    private void copyDefaultMapFiles() {
        try {
            new File("JumpAndRun/").mkdirs();

            for (int i = 1; i < LEVEL_AMOUNT + 1; i++) {
                Files.copy(
                    Objects.requireNonNull(getClass().getResourceAsStream("/de/informatik/resources/maps/map" + i + ".yml")),
                    Paths.get("JumpAndRun/map" + i + ".yml")
                );
            }
        } catch (IOException ignored) {
        }
    }

    //<editor-fold desc="Getter">

    /**
     * Gibt den {@link GameHandler} dieses Spiels zurück, welcher alle Angelegenheiten regelt, während das Spiel läuft.
     *
     * @return Der {@link GameHandler} dieses Spiels, welcher alle Angelegenheiten regelt, während das Spiel läuft.
     */
    public GameHandler getGameHandler() {
        return this.gameHandler;
    }

    /**
     * Gibt eine {@link Map} zurück, welche alle geladenen Bilder enthält, die als {@link ImageType Typ} hinterlegt
     * sind.
     *
     * @return Eine {@link Map}, welche alle geladenen Bilder enthält, die als {@link ImageType Typ} hinterlegt sind.
     */
    public Map<ImageType, BufferedImage> getLoadedImages() {
        return this.loadedImages;
    }

    /**
     * Gibt das {@link de.informatik.game.object.graphic.Gui} zurück, in dem das Menü dieses Spiels angezeigt wird.
     *
     * @return Das {@link de.informatik.game.object.graphic.Gui}, in dem das Menü dieses Spiels angezeigt wird.
     */
    public MenuGui getMenuGui() {
        return this.menuGui;
    }

    /**
     * Gibt den Task zurück, welcher die Tastatur überwacht.
     *
     * @return Der Task, der die Tastatur überwacht.
     */
    public KeyboardTask getKeyboardTask() {
        return keyboardTask;
    }

    /**
     * Gibt die Instanz des aktuellen {@link GameGui} zurück.
     *
     * @return Die Instanz des aktuellen {@link GameGui} ({@code null}, wenn kein {@link GameGui} geöffnet ist).
     */
    public GameGui getCurrentGameGui() {
        return currentGameGui;
    }
    //</editor-fold>


    //<editor-fold desc="Setter">

    /**
     * Setzt die aktuelle Instanz des {@link GameGui}. Wenn das aktuelle {@link GameGui} geschlossen wird, wird diese
     * Instanz auf {@code null} gesetzt.
     *
     * @param currentGameGui Die neue aktuelle Instanz des {@link GameGui}. Wenn das aktuelle {@link GameGui}
     *                       geschlossen wird, wird diese Instanz auf {@code null} gesetzt.
     */
    public void setCurrentGameGui(final GameGui currentGameGui) {
        this.currentGameGui = currentGameGui;
    }
    //</editor-fold>
}

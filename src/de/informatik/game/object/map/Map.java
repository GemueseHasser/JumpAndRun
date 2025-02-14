package de.informatik.game.object.map;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.handler.MapHandler;
import de.informatik.game.object.graphic.gui.GameGui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Eine {@link Map} stellt eine Spielumgebung dar, in welcher sich der {@link Player Spieler} dieses Spiels befindet und
 * in welcher er sich bewegen kann.
 */
public final class Map {

    //<editor-fold desc="LOCAL FIELDS">
    /** Alle geladenen Gegner in dieser Umgebung. */
    private final List<Opponent> loadedOpponents = new ArrayList<>();
    /** Die Größe der Welt. */
    private int mapLength;
    /** Der Name der Welt. */
    private String name;
    /** Der {@link ImageType Typ} des Hintergrundbildes. */
    private ImageType backgroundImageType;
    /** Alle {@link OpponentLoader}, mit deren Hilfe die Gegner in dieser Map geladen werden. */
    private List<OpponentLoader> opponentLoader;
    /** Die letzte mittlere x-Koordinate des Hintergrundes in dieser Map. */
    private int lastMiddleBackgroundX;
    //</editor-fold>


    /**
     * Erzeugt eine neue und vollständig unabhängige Instanz einer {@link Map}. Eine {@link Map} stellt eine
     * Spielumgebung dar, in welcher sich der {@link Player Spieler} dieses Spiels befindet und in welcher er sich
     * bewegen kann.
     */
    public Map() {
        updateLastMiddleBackgroundX();
    }


    /**
     * Lädt mithilfe der {@link OpponentLoader} alle Gegner in dieser Umgebung und speichert diese in der Liste
     * {@code loadedOpponents}.
     */
    public void loadOpponents() {
        for (final OpponentLoader loader : opponentLoader) {
            if (!Arrays.asList(loader.getType().getOpponentClass().getInterfaces()).contains(Opponent.class)) {
                System.out.println("No-Opponent-Class:" + loader.getType().name());
                continue;
            }

            try {
                final Opponent opponent = (Opponent) loader.getType().getOpponentClass().getDeclaredConstructor().newInstance();
                opponent.initializeOpponent(loader.getX(), loader.getY(), loader.getWidth(), loader.getHeight());
                loadedOpponents.add(opponent);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Aktualisiert die Position des mittleren Hintergrundbildes dieser Map.
     */
    public void updateLastMiddleBackgroundX() {
        if (!MapHandler.isBackgroundMovable()) return;

        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        final int leftMargin = Player.MAX_LEFT_POINT_ON_SCREEN;
        final int rightMargin = GameGui.WIDTH - Player.MAX_RIGHT_POINT_ON_SCREEN;

        final int widthCounter = player.getAbsolutePositionX() / GameGui.WIDTH;

        switch (player.getCurrentMovementState()) {
            case LEFT:
                if (player.getScreenPositionX() > Player.MAX_LEFT_POINT_ON_SCREEN) return;

                lastMiddleBackgroundX = (widthCounter * GameGui.WIDTH) - (player.getAbsolutePositionX() - leftMargin);
                break;
            case RIGHT:
                if (player.getScreenPositionX() < Player.MAX_RIGHT_POINT_ON_SCREEN) return;

                lastMiddleBackgroundX = ((widthCounter + 1) * GameGui.WIDTH) - (player.getAbsolutePositionX() + rightMargin);
                break;
        }
    }


    //<editor-fold desc="Getter">

    /**
     * Gibt den Namen der Welt zurück.
     *
     * @return Der Name der Welt.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Größe der Welt zurück.
     *
     * @return Die Größe der Welt.
     */
    public int getMapLength() {
        return mapLength;
    }

    /**
     * Gibt die letzte mittlere x-Koordinate des Hintergrundes in dieser Map zurück.
     *
     * @return Die letzte mittlere x-Koordinate des Hintergrundes in dieser Map.
     */
    public int getLastMiddleBackgroundX() {
        return lastMiddleBackgroundX;
    }

    /**
     * Gibt den {@link ImageType Typ} des Hintergrundbildes zurück.
     *
     * @return Der {@link ImageType Typ} des Hintergrundbildes.
     */
    public ImageType getBackgroundImageType() {
        return backgroundImageType;
    }

    /**
     * Gibt alle geladenen Gegner in dieser Umgebung zurück.
     *
     * @return Alle geladenen Gegner in dieser Umgebung.
     */
    public List<Opponent> getLoadedOpponents() {
        return loadedOpponents;
    }
    //</editor-fold>

    //<editor-fold desc="Setter">

    /**
     * Setzt den Namen dieser Umgebung neu.
     *
     * @param name Der neue Name dieser Umgebung.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Setzt die Größe dieser Umgebung neu.
     *
     * @param mapLength Die neue Größe dieser Umgebung.
     */
    public void setMapLength(final int mapLength) {
        this.mapLength = mapLength + Player.MAX_LEFT_POINT_ON_SCREEN;
    }

    /**
     * Setzt den {@link ImageType Typen} des Hintergrundbildes neu.
     *
     * @param backgroundImageType Der neue {@link ImageType Typ} des Hintergrundbildes.
     */
    public void setBackgroundImageType(final ImageType backgroundImageType) {
        this.backgroundImageType = backgroundImageType;
    }

    /**
     * Setzt alle {@link OpponentLoader}, mit deren Hilfe die Gegner in dieser Map geladen werden, neu.
     *
     * @param opponentLoader Alle {@link OpponentLoader}, mit deren Hilfe die Gegner in dieser Map geladen werden.
     */
    public void setOpponentLoader(final List<OpponentLoader> opponentLoader) {
        this.opponentLoader = opponentLoader;
    }
    //</editor-fold>

}

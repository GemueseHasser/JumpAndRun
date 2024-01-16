package de.informatik.game.object.map;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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


    public Map() {
        final int playerPosition = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().getAbsolutePositionX();
        final int width = Player.MAX_RIGHT_POINT_ON_SCREEN - Player.MAX_LEFT_POINT_ON_SCREEN;
        lastMiddleBackgroundX = ((playerPosition / width) * width) - playerPosition;
    }


    /**
     * Lädt mithilfe der {@link OpponentLoader} alle Gegner in dieser Umgebung und speichert diese in der Liste
     * {@code loadedOpponents}.
     */
    public void loadOpponents() {
        for (final OpponentLoader loader : opponentLoader) {
            if (!loader.getType().getOpponentClass().getSuperclass().isInstance(Opponent.class)) {
                System.out.println("No-Opponent-Class:" + loader.getType().name());
                continue;
            }

            try {
                for (final int x : loader.getPositions()) {
                    final Opponent opponent = (Opponent) loader.getType().getOpponentClass().getConstructor(
                        Integer.class
                    ).newInstance(x);
                    loadedOpponents.add(opponent);
                }
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                System.out.println(
                    "Error while loading opponents; has every opponent a constructor opponent(Integer startX)?"
                );
                throw new RuntimeException(e);
            }
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
        this.mapLength = mapLength;
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
     * Setzt die letzte mittlere x-Koordinate des Hintergrundes in dieser Map neu.
     *
     * @param x Die letzte mittlere x-Koordinate des Hintergrundes in dieser Map.
     */
    public void setLastMiddleBackgroundX(final int x) {
        this.lastMiddleBackgroundX = x;
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
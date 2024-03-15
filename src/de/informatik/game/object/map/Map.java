package de.informatik.game.object.map;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.constant.OpponentType;
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


    //<editor-fold desc="OpponentLoader">

    /**
     * Mithilfe des {@link OpponentLoader} wird für den jeweiligen {@link OpponentType} für den dieser Loader angelegt
     * wird ein Gegner charakterisiert mithilfe einer x- und einer y-Koordinate bzw. einer Breite und einer Höhe.
     */
    public static final class OpponentLoader {

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
    //</editor-fold>

}

package de.informatik.game.handler;

import de.informatik.game.constant.GameState;
import de.informatik.game.object.map.Map;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;

import java.io.FileNotFoundException;

/**
 * Der {@link GameHandler} stellt die nächsthöhere Instanz des Spiels - nach der Haupt- und Main-Klasse - dar und in
 * dieser Klasse werden alle Angelegenheiten, die während des Spielbetriebs geregelt werden müssen, geregelt.
 */
public final class GameHandler {

    //<editor-fold desc="LOCAL FIELDS">
    /** Der Instanz des Spielers, mit dem der Nutzer dieses Spiel spielt. */
    private final Player player = new Player();
    /** Die Map dieses Spiels, in welcher sich der Nutzer während des Spiels befindet. */
    private Map map;
    /** Der aktuelle Level, der gespielt wird. */
    private int currentLevel;
    /** Der aktuelle Status dieses Spiels. */
    private GameState gameState;
    //</editor-fold>


    /**
     * Initialisiert dieses Spiel bzw. diese Runde dieses Spiels.
     */
    public void initialize(final int level) {
        // set current level
        currentLevel = level;

        // initialize player
        player.initialize();

        try {
            // load map
            map = MapHandler.loadMap(level);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // load opponents
        map.loadOpponents();

        // initialize game-state
        gameState = GameState.RUNNING;
    }

    /**
     * Die Aktion, die ausgeführt wird, wenn der Nutzer die Taste nach links drückt. Diese Methode wird ausgeführt, wenn
     * der {@link de.informatik.game.listener.KeyListener} getriggert wird.
     */
    public void moveLeft() {
        player.moveLeft();

        map.updateLastMiddleBackgroundX();

        for (final Opponent opponent : map.getLoadedOpponents()) {
            opponent.playerMoveLeftEvent();
        }
    }

    /**
     * Die Aktion, die ausgeführt wird, wenn der Nutzer die Taste nach rechts drückt. Diese Methode wird ausgeführt,
     * wenn der {@link de.informatik.game.listener.KeyListener} getriggert wird.
     */
    public void moveRight() {
        player.moveRight();

        map.updateLastMiddleBackgroundX();

        for (final Opponent opponent : map.getLoadedOpponents()) {
            opponent.playerMoveRightEvent();
        }
    }

    //<editor-fold desc="Getter">

    /**
     * Gibt die Map dieses Spiels zurück, in welcher sich der Nutzer während des Spiels befindet.
     *
     * @return Die Map dieses Spiels, in welcher sich der Nutzer während des Spiels befindet.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Gibt die Instanz des Spielers zurück, mit dem der Nutzer dieses Spiel spielt.
     *
     * @return Der Instanz des Spielers, mit dem der Nutzer dieses Spiel spielt.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gibt den aktuellen Level zurück, welcher gespielt wird.
     *
     * @return Der aktuelle Level, der gespielt wird.
     */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * Gibt den aktuellen Status dieses Spiels zurück.
     *
     * @return Der aktuelle Status dieses Spiels.
     */
    public GameState getGameState() {
        return this.gameState;
    }
    //</editor-fold>

    //<editor-fold desc="Setter">

    /**
     * Aktualisiert den aktuellen Status dieses Spiels.
     *
     * @param gameState Der neue Status, den dieses Spiel erhalten soll.
     */
    public void updateGameState(final GameState gameState) {
        this.gameState = gameState;
    }
    //</editor-fold>

}

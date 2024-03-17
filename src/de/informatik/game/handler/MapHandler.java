package de.informatik.game.handler;

import de.informatik.game.JumpAndRun;
import de.informatik.game.object.graphic.gui.GameGui;
import de.informatik.game.object.map.Map;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Mithilfe des {@link MapHandler} lässt sich die Map laden, die sich aktuell im Ordner ./JumpAndRun befindet; sollte
 * sich dort keine Map befinden, entsteht eine Fehlermeldung. Außerdem lässt sich eine fertig gerenderte Map erzeugen in
 * Form eines {@link BufferedImage Bildes}, die aus dem Hintergrund und den Gegnern besteht. Diese Map aktualisiert sich
 * auch immer wieder auf der Grundlage der Position des Spielers.
 */
public final class MapHandler {

    //<editor-fold desc="utility">

    /**
     * Lädt die Map, die sich im Ordner ./JumpAndRun befindet.
     *
     * @return Die geladene Map.
     *
     * @throws FileNotFoundException Die Fehlermeldung, die entsteht, wenn sich keine Map im Ordner ./JumpAndRun
     *                               befindet.
     */
    public static Map loadMap(final int mapCount) throws FileNotFoundException {
        final Yaml yaml = new Yaml(new Constructor(Map.class, new LoaderOptions()));
        try {
            return yaml.load(Files.newInputStream(Paths.get("JumpAndRun/map" + mapCount + ".yml")));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Erzeugt eine fertig gerenderte Map in Form eines {@link BufferedImage Bildes}, die aus dem Hintergrund und den
     * Gegnern besteht. Diese Map aktualisiert sich auch immer wieder auf der Grundlage der Position des Spielers.
     *
     * @param map            Die Map, auf dessen Grundlage ein Bild erzeugt werden soll.
     *
     * @return Die fertig gerenderte Map in Form eines {@link BufferedImage Bildes}.
     */
    public static BufferedImage getRenderedMap(final Map map) {
        final BufferedImage image = new BufferedImage(GameGui.WIDTH, GameGui.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        // draw background
        g.drawImage(
            map.getBackgroundImageType().getImage(),
            map.getLastMiddleBackgroundX() - GameGui.WIDTH + 2,
            0,
            GameGui.WIDTH,
            GameGui.HEIGHT,
            null
        );
        g.drawImage(
            map.getBackgroundImageType().getImage(),
            map.getLastMiddleBackgroundX(),
            0,
            GameGui.WIDTH,
            GameGui.HEIGHT,
            null
        );
        g.drawImage(
            map.getBackgroundImageType().getImage(),
            map.getLastMiddleBackgroundX() + GameGui.WIDTH - 2,
            0,
            GameGui.WIDTH,
            GameGui.HEIGHT,
            null
        );

        // draw all opponents
        for (final Opponent opponent : map.getLoadedOpponents()) {
            opponent.drawOpponent(g);
        }

        // dispose graphics object
        g.dispose();

        return image;
    }

    /**
     * Der Zustand, ob sich der Hintergrund aktuell bewegt oder nicht.
     *
     * @return Wenn sich der Hintergrund aktuell bewegt {@code true}, ansonsten {@code false}.
     */
    public static boolean isBackgroundMovable() {
        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();
        final int screenX = player.getScreenPositionX();
        final int absoluteX = player.getAbsolutePositionX();

        if (screenX == absoluteX) return false;

        return screenX <= Player.MAX_LEFT_POINT_ON_SCREEN || screenX >= Player.MAX_RIGHT_POINT_ON_SCREEN;
    }
    //</editor-fold>
}

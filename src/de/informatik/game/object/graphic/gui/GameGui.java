package de.informatik.game.object.graphic.gui;

import de.informatik.game.JumpAndRun;
import de.informatik.game.handler.MapHandler;
import de.informatik.game.listener.KeyListener;
import de.informatik.game.object.graphic.Gui;

import javax.swing.JFrame;

import java.awt.Graphics2D;

/**
 * Das Haupt-Fenster des {@link de.informatik.game.JumpAndRun Spiels}, welches auch als erstes geöffnet wird und worin
 * eigentlich das gesamte Spiel stattfindet.
 */
public final class GameGui extends Gui {

    //<editor-fold desc="CONSTANTS">
    /** Die Breite des Fensters. */
    public static final int WIDTH = 700;
    /** Die Höhe des Fensters. */
    public static final int HEIGHT = 500;
    /** Der Titel des Fensters. */
    private static final String TITLE = "Jump-and-Run";
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue Instanz eines {@link GameGui Fensters}, welches ein {@link JFrame} darstellt. Dieses
     * {@link GameGui} ist das Haupt-Fenster des {@link de.informatik.game.JumpAndRun Spiels}, welches auch als erstes
     * geöffnet wird und worin eigentlich das gesamte Spiel stattfindet.
     */
    public GameGui() {
        // create gui instance
        super(TITLE, WIDTH, HEIGHT);
        super.addKeyListener(new KeyListener());
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void draw(final Graphics2D g) {
        // draw map (background and opponents)
        g.drawImage(MapHandler.getRenderedMap(
            JumpAndRun.GAME_INSTANCE.getGameHandler().getMap(),
            JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().getAbsolutePositionX()
        ), 0, 0, null);

        // draw player
        JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().drawPlayer(g);

        repaint();
    }

    @Override
    public void dispose() {
        super.dispose();

        JumpAndRun.GAME_INSTANCE.getMenuGui().open();
    }
    //</editor-fold>

}

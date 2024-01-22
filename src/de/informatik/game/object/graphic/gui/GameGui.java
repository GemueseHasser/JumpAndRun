package de.informatik.game.object.graphic.gui;

import de.informatik.game.JumpAndRun;
import de.informatik.game.handler.MapHandler;
import de.informatik.game.listener.KeyListener;
import de.informatik.game.object.graphic.Gui;

import javax.swing.JFrame;

import java.awt.Graphics2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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


    //<editor-fold desc="LOCAL FIELDS">
    /** Der {@link ScheduledExecutorService}, womit Threads in einem bestimmten Pool ausgeführt werden können. */
    private final ScheduledExecutorService taskExecutor = Executors.newScheduledThreadPool(1);
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue Instanz eines {@link GameGui Fensters}, welches ein {@link JFrame} darstellt. Dieses
     * {@link GameGui} ist das Haupt-Fenster des {@link de.informatik.game.JumpAndRun Spiels}, welches auch als erstes
     * geöffnet wird und worin eigentlich das gesamte Spiel stattfindet.
     */
    public GameGui() {
        // create gui instance
        super(TITLE + " -- Level " + JumpAndRun.GAME_INSTANCE.getGameHandler().getCurrentLevel(), WIDTH, HEIGHT);
        super.addKeyListener(new KeyListener());
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        taskExecutor.scheduleAtFixedRate(JumpAndRun.GAME_INSTANCE.getKeyboardTask(), 0, 100, TimeUnit.MILLISECONDS);

        // register current gui in main-class
        JumpAndRun.GAME_INSTANCE.setCurrentGameGui(this);
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
        taskExecutor.close();

        // unregister current gui instance in main-class
        JumpAndRun.GAME_INSTANCE.setCurrentGameGui(null);
    }
    //</editor-fold>

}

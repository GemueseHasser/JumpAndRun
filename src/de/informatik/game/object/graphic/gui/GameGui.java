package de.informatik.game.object.graphic.gui;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.GameState;
import de.informatik.game.constant.SoundType;
import de.informatik.game.handler.MapHandler;
import de.informatik.game.listener.KeyListener;
import de.informatik.game.object.graphic.Gui;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import java.awt.Color;
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
    /** Die Farbe, in der der Schriftzug für ein gewonnenes oder verlorenes Spiel angezeigt wird. */
    private static final Color GAME_WIN_LOSE_COLOR = Color.RED;
    /** Die Schriftgröße der Schriftart für die Darstellung des Gewinnens oder Verlierens. */
    private static final float WIN_LOSE_FONT_SIZE = 60;
    /** Der Text, der unter dem Text für das Gewinnen oder Verlieren steht. */
    private static final String WIN_LOSE_SUBTEXT = "Press Enter";
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

        taskExecutor.scheduleAtFixedRate(JumpAndRun.GAME_INSTANCE.getGameTask(), 0, 24, TimeUnit.MILLISECONDS);

        // register current gui in main-class
        JumpAndRun.GAME_INSTANCE.setCurrentGameGui(this);
    }
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void draw(final Graphics2D g) {
        // draw map (background and opponents)
        g.drawImage(MapHandler.getRenderedMap(JumpAndRun.GAME_INSTANCE.getGameHandler().getMap()), 0, 0, null);

        // draw player
        JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().drawPlayer(g);

        // check if game is running
        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getGameState() != GameState.RUNNING) {
            g.setFont(JumpAndRun.GAME_INSTANCE.getGameFont().deriveFont(WIN_LOSE_FONT_SIZE));

            final String displayText = JumpAndRun.GAME_INSTANCE.getGameHandler().getGameState().getDisplayText();
            final int textWidth = g.getFontMetrics().stringWidth(displayText);
            final int textHeight = g.getFontMetrics().getHeight();

            g.setColor(GAME_WIN_LOSE_COLOR);
            g.drawString(displayText, (WIDTH / 2) - (textWidth / 2), (HEIGHT / 2) - textHeight);

            g.setFont(JumpAndRun.GAME_INSTANCE.getGameFont().deriveFont(WIN_LOSE_FONT_SIZE - 25));

            final int subtextWidth = g.getFontMetrics().stringWidth(WIN_LOSE_SUBTEXT);
            g.drawString(WIN_LOSE_SUBTEXT, (WIDTH / 2) - (subtextWidth / 2), (HEIGHT / 2) - (textHeight / 2) + 5);
        }

        repaint();
    }

    @Override
    public void open() {
        super.open();

        // start playing background music
        SoundType.GAME_BACKGROUND.play(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void dispose() {
        super.dispose();

        JumpAndRun.GAME_INSTANCE.getMenuGui().open();
        taskExecutor.shutdown();

        // unregister current gui instance in main-class
        JumpAndRun.GAME_INSTANCE.setCurrentGameGui(null);

        // stop playing background music
        SoundType.GAME_BACKGROUND.stop();
        SoundType.SAD_MUSIC.stop();
    }
    //</editor-fold>

}

package de.informatik.game.object.graphic.gui;

import de.informatik.game.object.graphic.Gui;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Das {@link MenuGui} stellt eine Instanz eines {@link Gui} dar. Das {@link MenuGui} wird dem Nutzer als erstes
 * geöffnet und stellt ein Menü dar, in welchem der Spieler sein Spiel beginnen oder beenden kann.
 */
public final class MenuGui extends Gui {

    //<editor-fold desc="CONSTANTS">
    /** Die Breite des Fensters. */
    private static final int WIDTH = 300;
    /** Die Höhe des Fensters. */
    private static final int HEIGHT = 400;
    /** Der Titel des Fensters. */
    private static final String TITLE = "Jump-and-Run";
    /** Die Hintergrundfarbe jedes Buttons. */
    private static final Color BUTTON_BACKGROUND = Color.GRAY;
    /** Die Farbe, zu der sich die Hintergrundfarbe ändert, wenn man den Button mit der Maus berührt. */
    private static final Color BUTTON_BACKGROUND_HOVER = Color.LIGHT_GRAY;
    /** Die Breite jedes Buttons. */
    private static final int BUTTON_WIDTH = 200;
    /** Die Höhe jedes Buttons. */
    private static final int BUTTON_HEIGHT = 40;
    /** Der Text, der auf dem Button angezeigt wird, um das Spiel zu starten. */
    private static final String PLAY_BUTTON_TEXT = "Spiel starten";
    /** Der Text, der auf dem Button angezeigt wird, um das Spiel zu beenden. */
    private static final String EXIT_BUTTON_TEXT = "Spiel beenden";
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue Instanz eines {@link MenuGui}. Das {@link MenuGui} stellt eine Instanz eines {@link Gui} dar.
     * Das {@link MenuGui} wird dem Nutzer als erstes geöffnet und stellt ein Menü dar, in welchem der Spieler sein
     * Spiel beginnen oder beenden kann.
     */
    public MenuGui() {
        super(TITLE, WIDTH, HEIGHT);
        super.setUndecorated(true);

        final JButton playButton = new JButton(PLAY_BUTTON_TEXT);
        playButton.addActionListener(e -> {
            new GameGui().open();
            this.dispose();
        });
        initializeButton(playButton, 180);

        final JButton exitButton = new JButton(EXIT_BUTTON_TEXT);
        exitButton.addActionListener(e -> System.exit(0));
        initializeButton(exitButton, 250);

        super.add(playButton);
        super.add(exitButton);
    }
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void draw(final Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Jump-And-Run", 40, 80);
    }
    //</editor-fold>


    //<editor-fold desc="utility">

    /**
     * Initialisiert einen bestimmten {@link JButton} mit den wichtigsten Eigenschaften.
     *
     * @param button Der Button, der die Eigenschaften bekommen soll.
     * @param y      Die y-Koordinate, an der sich der Button befinden soll.
     */
    private static void initializeButton(final JButton button, final int y) {
        button.setBounds(WIDTH / 2 - (BUTTON_WIDTH / 2), y, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.addMouseListener(new ButtonHoverListener(button));
        button.setFocusable(false);
        button.setBackground(BUTTON_BACKGROUND);
        button.setForeground(Color.WHITE);
    }
    //</editor-fold>


    /**
     * Der {@link ButtonHoverListener} sorgt dafür, dass sich die Hintergrundfarbe des jeweiligen Buttons, der übergeben
     * wird, verändert, wenn man diesen Mit der Maus berührt. Die Farbe ändert sich wieder zur ursprünglichen Farbe
     * zurück, wenn man wieder aufhört den Button mit der Maus zu berühren.
     */
    private static final class ButtonHoverListener implements MouseListener {

        //<editor-fold desc="LOCAL FIELDS">
        /** Der {@link java.awt.Button}, der von diesem Listener explizit überwacht werden soll. */
        private final JButton button;
        //</editor-fold>


        //<editor-fold desc="CONSTRUCTORS">

        /**
         * Erzeugt eine neue Instanz eines {@link ButtonHoverListener}. Der {@link ButtonHoverListener} sorgt dafür,
         * dass sich die Hintergrundfarbe des jeweiligen Buttons, der übergeben wird, verändert, wenn man diesen Mit der
         * Maus berührt. Die Farbe ändert sich wieder zur ursprünglichen Farbe zurück, wenn man wieder aufhört den
         * Button mit der Maus zu berühren.
         *
         * @param button Der {@link java.awt.Button}, der von diesem Listener explizit überwacht werden soll.
         */
        public ButtonHoverListener(final JButton button) {
            this.button = button;
        }
        //</editor-fold>


        //<editor-fold desc="implementation">
        @Override
        public void mouseClicked(final MouseEvent e) {

        }

        @Override
        public void mousePressed(final MouseEvent e) {

        }

        @Override
        public void mouseReleased(final MouseEvent e) {

        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            button.setBackground(BUTTON_BACKGROUND_HOVER);
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            button.setBackground(BUTTON_BACKGROUND);
        }
        //</editor-fold>
    }
}

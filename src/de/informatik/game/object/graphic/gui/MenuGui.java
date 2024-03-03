package de.informatik.game.object.graphic.gui;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.SoundType;
import de.informatik.game.object.graphic.Gui;

import javax.sound.sampled.Clip;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static de.informatik.game.JumpAndRun.GAME_INSTANCE;

/**
 * Das {@link MenuGui} stellt eine Instanz eines {@link Gui} dar. Das {@link MenuGui} wird dem Nutzer als erstes
 * geöffnet und stellt ein Menü dar, in welchem der Spieler sein Spiel beginnen oder beenden kann.
 */
public final class MenuGui extends Gui {

    //<editor-fold desc="CONSTANTS">
    /** Die Breite des Fensters. */
    private static final int WIDTH = 500;
    /** Die Höhe des Fensters. */
    private static final int HEIGHT = 650;
    /** Der Titel des Fensters. */
    private static final String TITLE = "Jump-and-Run";
    /** Die Schriftart, die standardmäßig für alle Schriften in diesem {@link Gui} verwendet wird. */
    private static final Font STANDARD_FONT = new Font("Arial", Font.BOLD, 25);
    /** Die standard Hintergrundfarbe jedes Buttons. */
    private static final Color BUTTON_BACKGROUND = Color.GRAY;
    /** Die Farbe, zu der sich die Hintergrundfarbe ändert, wenn man den Button mit der Maus berührt. */
    private static final Color BUTTON_BACKGROUND_HOVER = Color.LIGHT_GRAY;
    /** Die standard Breite eines Buttons. */
    private static final int BUTTON_WIDTH = 400;
    /** Die standard Höhe eines Buttons. */
    private static final int BUTTON_HEIGHT = 55;
    /** Der Text, der auf dem Button angezeigt wird, um das Spiel zu starten. */
    private static final String PLAY_BUTTON_TEXT = "Spiel starten";
    /** Der Text, der auf dem Button angezeigt wird, um das Spiel zu beenden. */
    private static final String EXIT_BUTTON_TEXT = "Spiel beenden";
    /** Die Größe jedes Buttons, mit dem man das Level wählen kann. */
    private static final int LEVEL_BUTTON_SIZE = 70;
    /** Die Farbe, die der Button des jeweiligen Levels erhält, wenn man dieses Level ausgewählt hat. */
    private static final Color LEVEL_BUTTON_SELECT_COLOR = Color.GREEN;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Der Button, mit dem man das Spiel startet. */
    private final JButton playButton;
    /** Der aktuelle Level, der ausgewählt wurde. */
    private int selectedLevel = 1;
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

        // create buttons for each level
        final JButton[] levelButtons = new JButton[JumpAndRun.LEVEL_AMOUNT];

        for (int i = 0; i < JumpAndRun.LEVEL_AMOUNT; i++) {
            // set current level
            final int currentLevel = i + 1;

            // create temp button with default properties
            final JButton button = new JButton("Lvl " + currentLevel);
            initializeButton(button, 0);

            button.setFont(STANDARD_FONT.deriveFont(12F));
            button.setBounds(
                53 + ((i % 5) * (LEVEL_BUTTON_SIZE + 10)),
                (i / 5) * (LEVEL_BUTTON_SIZE + 10) + 130,
                LEVEL_BUTTON_SIZE,
                LEVEL_BUTTON_SIZE
            );
            button.addActionListener(e -> {
                // play sound
                SoundType.MENU_HOVER.play(0);

                // set selected level
                selectedLevel = currentLevel;

                // color all buttons in the default color
                for (final JButton levelButton : levelButtons) {
                    if (levelButton == button) continue;

                    levelButton.setBackground(BUTTON_BACKGROUND);
                }

                // color the selected button specifically
                button.setBackground(LEVEL_BUTTON_SELECT_COLOR);
            });

            // add button to gui
            levelButtons[i] = button;
            super.add(levelButtons[i]);
        }

        // select first level automatically
        levelButtons[selectedLevel - 1].setBackground(LEVEL_BUTTON_SELECT_COLOR);

        // create play button with default properties
        playButton = new JButton(PLAY_BUTTON_TEXT);
        playButton.setFont(STANDARD_FONT);
        playButton.addActionListener(e -> {
            // play sound
            SoundType.START_GAME.play(0);

            // initialize game-handler
            GAME_INSTANCE.getGameHandler().initialize(selectedLevel);

            new GameGui().open();
            this.dispose();
        });
        initializeButton(playButton, 430);

        // create exit button with default properties
        final JButton exitButton = new JButton(EXIT_BUTTON_TEXT);
        exitButton.setFont(STANDARD_FONT);
        exitButton.addActionListener(e -> System.exit(0));
        initializeButton(exitButton, 520);

        // add components to gui
        super.add(playButton);
        super.add(exitButton);
    }
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void draw(final Graphics2D g) {
        // draw background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // draw frame-border
        g.setColor(Color.WHITE);
        g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);

        // draw heading
        g.setFont(STANDARD_FONT.deriveFont(45F));
        g.drawString("Jump-And-Run", 80, 80);
    }

    @Override
    public void open() {
        super.open();

        // start playing background music
        SoundType.MENU_BACKGROUND.play(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void dispose() {
        super.dispose();

        playButton.setBackground(BUTTON_BACKGROUND);

        // stop playing background music
        SoundType.MENU_BACKGROUND.stop();
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


    //<editor-fold desc="ButtonHoverListener">

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
            if (button.getBackground() != BUTTON_BACKGROUND) return;

            button.setBackground(BUTTON_BACKGROUND_HOVER);
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            if (button.getBackground() != BUTTON_BACKGROUND_HOVER) return;

            button.setBackground(BUTTON_BACKGROUND);
        }
        //</editor-fold>
    }
    //</editor-fold>
}

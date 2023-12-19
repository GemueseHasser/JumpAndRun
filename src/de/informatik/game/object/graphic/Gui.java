package de.informatik.game.object.graphic;

import de.informatik.game.listener.KeyListener;

import javax.swing.JFrame;

/**
 * Das Haupt-Fenster des {@link de.informatik.game.JumpAndRun Spiels}, welches auch als erstes geöffnet wird und worin
 * eigentlich das gesamte Spiel stattfindet.
 */
public final class Gui extends JFrame {

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
     * Erzeugt eine neue Instanz eines {@link Gui Fensters}, welches ein {@link JFrame} darstellt. Dieses {@link Gui}
     * ist das Haupt-Fenster des {@link de.informatik.game.JumpAndRun Spiels}, welches auch als erstes geöffnet wird und
     * worin eigentlich das gesamte Spiel stattfindet.
     */
    public Gui() {
        // create frame-instance
        super(TITLE);

        // set default properties
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setBounds(0, 0, WIDTH, HEIGHT);
        super.setLocationRelativeTo(null);
        super.setLayout(null);
        super.setResizable(false);
        super.addKeyListener(new KeyListener());

        // create basic draw-component
        final Draw draw = new Draw();
        draw.setBounds(0, 0, WIDTH, HEIGHT);
        draw.setVisible(true);

        // add components
        super.add(draw);
    }
    //</editor-fold>


    /**
     * Macht dieses {@link Gui} sichtbar.
     */
    public void open() {
        this.setVisible(true);
    }

}

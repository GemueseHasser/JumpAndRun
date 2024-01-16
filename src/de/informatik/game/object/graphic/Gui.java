package de.informatik.game.object.graphic;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Ein {@link Gui} stellt ein {@link JFrame Fenster} dar, welches bereits bestimmte Eigenschaften besitzt und welches
 * auch die Möglichkeit bietet, Grafiken einzuzeichnen.
 */
public abstract class Gui extends JFrame {

    //<editor-fold desc="LOCAL FIELDS">
    /** Das Objekt, welches genutzt wird, um auf diesem Fenster Grafiken einzuzeichnen. */
    private final Draw draw;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue Instanz eines {@link Gui}. Ein {@link Gui} stellt ein {@link JFrame Fenster} dar, welches
     * bereits bestimmte Eigenschaften besitzt und welches auch die Möglichkeit bietet, Grafiken einzuzeichnen.
     *
     * @param title  Der Titel des Fensters.
     * @param width  Die Breite des Fensters.
     * @param height Die Höhe des Fensters.
     */
    public Gui(
        final String title,
        final int width,
        final int height
    ) {
        // create frame-instance
        super(title);

        // set default properties
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setBounds(0, 0, width, height);
        super.setLocationRelativeTo(null);
        super.setLayout(null);
        super.setResizable(false);

        // create draw-component
        this.draw = new Draw();
        draw.setBounds(0, 0, width, height);
        draw.setVisible(true);
    }
    //</editor-fold>


    /**
     * Macht dieses {@link Gui} sichtbar.
     */
    public void open() {
        this.setVisible(true);

        // add draw-component
        super.add(draw);
    }

    /**
     * Mithilfe dieser Methode lassen sich Grafiken in dieses Fenster einzeichnen.
     *
     * @param g Das {@link Graphics2D Grafik-Objekt}, welches genutzt wird, um Grafiken darzustellen.
     */
    public abstract void draw(final Graphics2D g);


    //<editor-fold desc="Draw">

    /**
     * Das {@link JLabel Objekt}, welches genutzt wird, um auf diesem Fenster Grafiken einzuzeichnen.
     */
    private final class Draw extends JLabel {

        //<editor-fold desc="implementation">
        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);

            draw((Graphics2D) g);
        }
        //</editor-fold>
    }
    //</editor-fold>

}

package de.informatik.game.object.graphic;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Gui extends JFrame {

    //<editor-fold desc="CONSTRUCTORS">
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

        final Draw draw = new Draw();
        draw.setBounds(0, 0, width, height);
        draw.setVisible(true);

        super.add(draw);
    }
    //</editor-fold>


    /**
     * Macht dieses {@link Gui} sichtbar.
     */
    public void open() {
        this.setVisible(true);
    }

    public abstract void draw(final Graphics2D g);


    //<editor-fold desc="Draw">
    private final class Draw extends JLabel {

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);

            draw((Graphics2D) g);
        }
    }
    //</editor-fold>

}

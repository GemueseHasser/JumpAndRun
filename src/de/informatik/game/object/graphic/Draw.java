package de.informatik.game.object.graphic;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;

import javax.swing.JLabel;

import java.awt.Graphics;

/**
 * Die Zeichen-Komponente, mit der das {@link de.informatik.game.JumpAndRun Spiel} in dem {@link Gui Fenster} gezeichnet
 * wird.
 */
public final class Draw extends JLabel {

    //<editor-fold desc="implementation">
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        g.drawImage(
            JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.BACKGROUND),
            JumpAndRun.GAME_INSTANCE.getGameHandler().getBackgroundPosition(),
            0,
            this.getWidth(),
            this.getHeight(),
            null
        );
        g.drawImage(
            JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.BACKGROUND),
            JumpAndRun.GAME_INSTANCE.getGameHandler().getBackgroundPosition() + this.getWidth(),
            0,
            this.getWidth(),
            this.getHeight(),
            null
        );

        repaint();
    }
    //</editor-fold>
}

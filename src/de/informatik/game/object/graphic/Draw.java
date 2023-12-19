package de.informatik.game.object.graphic;

import de.informatik.game.JumpAndRun;
import de.informatik.game.handler.MapHandler;

import javax.swing.JLabel;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Die Zeichen-Komponente, mit der das {@link de.informatik.game.JumpAndRun Spiel} in dem {@link Gui Fenster} gezeichnet
 * wird.
 */
public final class Draw extends JLabel {

    //<editor-fold desc="implementation">
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        // draw map (background and opponents)
        g.drawImage(MapHandler.getRenderedMap(
            JumpAndRun.GAME_INSTANCE.getGameHandler().getMap(),
            JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().getAbsolutePositionX()
        ), 0, 0, null);

        // draw player
        JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer().drawPlayer((Graphics2D) g);

        repaint();
    }
    //</editor-fold>
}

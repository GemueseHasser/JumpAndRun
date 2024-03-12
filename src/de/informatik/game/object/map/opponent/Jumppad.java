package de.informatik.game.object.map.opponent;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.constant.MovementState;
import de.informatik.game.object.map.Opponent;
import de.informatik.game.object.map.Player;

import java.awt.Graphics2D;

public final class Jumppad implements Opponent  {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 60;
    private static final int Y_COORDINATE = 300;
    private int initialStartingX;
    private int currentX;
    private int backgroundCounterX;
    private static final boolean PERMEABLE = false;
    private int lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();

    @Override
    public void drawOpponent(final Graphics2D g){
        g.drawImage(
                JumpAndRun.GAME_INSTANCE.getLoadedImages().get(ImageType.JUMPPAD),
                currentX,
                Y_COORDINATE,
                WIDTH,
                HEIGHT,
                null
        );
    }

    @Override
    public void playerMoveLeftEvent(final int playerPosition, final boolean isBackgroundMoving){
        if (!isBackgroundMoving) return;

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != lastBackgroundCentreX){
            backgroundCounterX += Player.STEP_SIZE;
        }

        currentX = backgroundCounterX + initialStartingX;
        lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    }

    @Override
    public void playerMoveRightEvent(final int playerPosition, final boolean isBackgroundMoving){
        if (!isBackgroundMoving) return;

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX() != lastBackgroundCentreX){
            backgroundCounterX -= Player.STEP_SIZE;
        }

        currentX = backgroundCounterX + initialStartingX;
        lastBackgroundCentreX = JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getLastMiddleBackgroundX();
    }

    @Override
    public void playerCollideOpponentEvent(){
        final Player player = JumpAndRun.GAME_INSTANCE.getGameHandler().getPlayer();

        if (player.getLastMovementState() == MovementState.LEFT && player.getCurrentMovementState() == MovementState.RIGHT) return;
        if (player.getLastMovementState() == MovementState.RIGHT && player.getCurrentMovementState() == MovementState.LEFT) return;

        player.stay();
    }

    @Override
    public void initializeOpponent(final int startX){
        this.initialStartingX = startX;
        this.currentX = startX;
    }

    @Override
    public boolean isPermeable() {
        return PERMEABLE;
    }

    @Override
    public int getPositionX(){
        return currentX;
    }

    @Override
    public int getPositionY(){
        return Y_COORDINATE;
    }

    @Override
    public int getWidth(){
        return WIDTH;
    }

    @Override
    public int getHeight(){
        return HEIGHT;
    }

}

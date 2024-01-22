package de.informatik.game.object.map;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.ImageType;
import de.informatik.game.constant.MovementState;
import de.informatik.game.object.graphic.gui.GameGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Der Spieler, welcher vom Nutzer dieses Spiels gesteuert wird und welcher sich in der {@link Map Spielumgebung}
 * befindet.
 */
public final class Player {

    //<editor-fold desc="CONSTANTS">
    /** Der Punkt, den der Spieler maximal nach links gehen kann. */
    public static final int MAX_LEFT_POINT_ON_SCREEN = 10;
    /** Der Punkt, den der Spieler maximal nach rechts gehen kann. */
    public static final int MAX_RIGHT_POINT_ON_SCREEN = GameGui.WIDTH - 70;
    /** Die Größe jedes Schrittes des Spielers. */
    public static final int STEP_SIZE = 5;
    /** Die Größe des Spielers. */
    public static final int PLAYER_SIZE = 60;
    /** Die maximale Anzahl an Leben, die der Spieler haben kann. */
    public static final int MAX_HEALTH_AMOUNT = 50;
    /** Die y-Koordinate, an der der Spieler bei der Initialisierung platziert wird. */
    private static final int START_POSITION_Y = 300;
    /** Die Sprunghöhe des Spielers. */
    private static final int JUMP_HEIGHT = 60;
    /** Die Anzahl an einzelnen Animationen, die es für den Spieler gibt. */
    private static final int ANIMATION_SIZE = 4;
    /** Die Skalierung (wie breit) die Lebensanzeige des Spielers sein soll. */
    private static final int HEALTH_SCALE = 4;
    /** Die Höhe der Lebensanzeige des Spielers. */
    private static final int HEALTH_HEIGHT = 30;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die aktuelle absolute Position des Spielers. */
    private int absolutePositionX;
    /** Die aktuelle Position auf dem Bildschirm des Spielers. */
    private int screenPositionX;
    /** Die aktuelle y-Koordinate des Spielers. */
    private int positionY;
    /** Die Anzahl an Leben, die der Spieler aktuell hat. */
    private int health;
    /** Die Zählvariable für die Animation des Spielers. */
    private int currentAnimationCount = 1;
    /** Die aktuelle Animation des Spielers in Form eines Bildes. */
    private BufferedImage currentAnimation;
    /** Der aktuelle Status, in welche Richtung sich der Spieler bewegt. */
    private MovementState currentMovementState = MovementState.RIGHT;
    /** Der Zustand, ob der Spieler gerade hochspringt. */
    private boolean jumping;
    //</editor-fold>


    /**
     * Initialisiert diesen Spieler.
     */
    public void initialize() {
        // set players position
        absolutePositionX = MAX_LEFT_POINT_ON_SCREEN;
        screenPositionX = MAX_LEFT_POINT_ON_SCREEN;
        positionY = START_POSITION_Y;
        health = MAX_HEALTH_AMOUNT;

        // initialize movement
        currentMovementState = MovementState.RIGHT;
        JumpAndRun.GAME_INSTANCE.getKeyboardTask().setKeyCode(-1);

        // reset animation
        resetAnimationCount();
    }

    /**
     * Zeichnet diesen Spieler.
     *
     * @param g Das {@link Graphics2D Graphics-Objekt}, auf dessen Grundlage der Spieler gezeichnet wird.
     */
    public void drawPlayer(final Graphics2D g) {
        // draw health
        g.setColor(Color.RED);
        g.fillRect(20, 20, health * HEALTH_SCALE, HEALTH_HEIGHT);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(20 + (health * HEALTH_SCALE), 20, (MAX_HEALTH_AMOUNT - health) * HEALTH_SCALE, HEALTH_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(20, 20, MAX_HEALTH_AMOUNT * HEALTH_SCALE, HEALTH_HEIGHT);

        switch (currentMovementState) {
            case LEFT -> g.drawImage(
                currentAnimation,
                screenPositionX + PLAYER_SIZE,
                positionY,
                -PLAYER_SIZE,
                PLAYER_SIZE,
                null
            );
            case RIGHT -> g.drawImage(currentAnimation, screenPositionX, positionY, PLAYER_SIZE, PLAYER_SIZE, null);
        }
    }

    /**
     * Simuliert die Bewegung nach links.
     */
    public void moveLeft() {
        // check if player should overall move left
        if (absolutePositionX - STEP_SIZE <= MAX_LEFT_POINT_ON_SCREEN) return;

        // update current movement state
        currentMovementState = MovementState.LEFT;

        // update player animation
        updateAnimation();
        currentAnimationCount--;

        // decrement absolute position
        absolutePositionX -= STEP_SIZE;

        // check if player should move left on screen
        if (screenPositionX <= MAX_LEFT_POINT_ON_SCREEN) return;

        // update position on screen
        screenPositionX -= STEP_SIZE;
    }

    /**
     * Simuliert die Bewegung nach rechts.
     */
    public void moveRight() {
        // check if player should overall move right
        if (absolutePositionX + STEP_SIZE >= JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getMapLength()) return;

        // update current movement state
        currentMovementState = MovementState.RIGHT;

        // update player animation
        updateAnimation();
        currentAnimationCount++;

        // increment absolute position
        absolutePositionX += STEP_SIZE;

        // check if player should move right on screen
        if (screenPositionX >= MAX_RIGHT_POINT_ON_SCREEN) return;

        // update position on screen
        screenPositionX += STEP_SIZE;
    }

    /**
     * Lässt den Spieler hochspringen.
     */
    public void jump() {
        if (jumping) return;

        jumping = true;
        positionY -= JUMP_HEIGHT;

        final ScheduledExecutorService taskExecutor = Executors.newScheduledThreadPool(1);
        taskExecutor.schedule(() -> {
            positionY += JUMP_HEIGHT;
            jumping = false;
        }, 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * Setzt die Animation immer auf die Standard-Position zurück.
     */
    public void resetAnimationCount() {
        currentAnimationCount = 1;
        updateAnimation();
    }

    /**
     * Aktualisiert auf Grundlage der Zählvariable die aktuelle Animation des Spielers. Sollte sich die Zählvariable
     * außerhalb der festgelegten Grenzen befinden, wird diese wieder korrekt aktualisiert.
     */
    private void updateAnimation() {
        if (currentAnimationCount > ANIMATION_SIZE) currentAnimationCount = 1;
        if (currentAnimationCount < 1) currentAnimationCount = ANIMATION_SIZE;

        currentAnimation = ImageType.valueOf("UNC_" + currentAnimationCount).getImage();
    }

    //<editor-fold desc="Getter">

    /**
     * Gibt die aktuelle absolute Position des Spielers zurück.
     *
     * @return Die aktuelle absolute Position des Spielers.
     */
    public int getAbsolutePositionX() {
        return absolutePositionX;
    }

    /**
     * Gibt die aktuelle Position auf dem Bildschirm des Spielers zurück.
     *
     * @return Die aktuelle Position auf dem Bildschirm des Spielers.
     */
    public int getScreenPositionX() {
        return screenPositionX;
    }

    /**
     * Gibt die aktuelle y-Koordinate des Spielers zurück.
     *
     * @return Die aktuelle y-Koordinate des Spielers.
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Gibt den aktuellen Status, in welche Richtung sich der Spieler bewegt, zurück.
     *
     * @return Der aktuelle Status, in welche Richtung sich der Spieler bewegt.
     */
    public MovementState getCurrentMovementState() {
        return currentMovementState;
    }

    /**
     * Gibt den Zustand zurück, ob der Spieler gerade springt.
     *
     * @return Wenn der Spieler gerade springt {@code true}, ansonsten {@code false}.
     */
    public boolean isJumping() {
        return jumping;
    }

    /**
     * Gibt die Anzahl an Leben zurück, die der Spieler aktuell hat.
     *
     * @return Die Anzahl an Leben, die der Spieler aktuell hat.
     */
    public int getHealth() {
        return health;
    }
    //</editor-fold>


    //<editor-fold desc="Setter">

    /**
     * Setzt die Leben neu, die der Spieler aktuell hat. Wenn die angegebene Anzahl an Leben aber kleiner als 0 ist,
     * wird die Anzahl der Leben einfach auf 0 gesetzt und wenn die Anzahl der Leben größer ist, als die maximal
     * zulässige Anzahl an Leben, wird diese ebenfalls einfach auf die maximale Anzahl an Leben gesetzt.
     *
     * @param health Die Anzahl an Leben, die der Spieler neu gesetzt bekommen soll.
     */
    public void setHealth(final int health) {
        if (health > MAX_HEALTH_AMOUNT) {
            this.health = MAX_HEALTH_AMOUNT;
            return;
        }

        if (health < 0) {
            this.health = 0;
            return;
        }

        this.health = health;
    }
    //</editor-fold>

}

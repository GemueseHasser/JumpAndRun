package de.informatik.game.object.map;

import de.informatik.game.JumpAndRun;
import de.informatik.game.constant.GameState;
import de.informatik.game.constant.ImageType;
import de.informatik.game.constant.MovementState;
import de.informatik.game.constant.SoundType;
import de.informatik.game.object.graphic.gui.GameGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
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
    public static final int MAX_LEFT_POINT_ON_SCREEN = 100;
    /** Der Punkt, den der Spieler maximal nach rechts gehen kann. */
    public static final int MAX_RIGHT_POINT_ON_SCREEN = GameGui.WIDTH - 150;
    /** Die Größe jedes Schrittes des Spielers. */
    public static final int STEP_SIZE = 2;
    /** Die Größe des Spielers. */
    public static final int PLAYER_SIZE = 60;
    /** Die maximale Anzahl an Leben, die der Spieler haben kann. */
    public static final int MAX_HEALTH_AMOUNT = 50;
    /** Die y-Koordinate, an der der Spieler bei der Initialisierung platziert wird. */
    public static final int START_POSITION_Y = 300;
    /** Die Sprunghöhe des Spielers. */
    private static final int JUMP_HEIGHT = 80;
    /** Die Anzahl an einzelnen Animationen, die es für den Spieler gibt. */
    private static final int ANIMATION_SIZE = 4;
    /** Die zeitlichen Abstände, in Millisekunden, in denen die Animation aktualisiert werden soll. */
    private static final int ANIMATION_PERIOD_IN_MILLIS = 60;
    /** Die Zeit in Millisekunden, um die das Zurücksetzen der Animation verzögert werden kann. */
    private static final int ANIMATION_RESET_DELAY_IN_MILLIS = 50;
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
    /** Der Zeitpunkt, zu dem die Animation zuletzt aktualisiert wurde. */
    private Instant lastAnimationUpdateMoment = Instant.now();
    /** Der aktuelle Status, in welche Richtung sich der Spieler bewegt. */
    private MovementState currentMovementState;
    /** Nicht der aktuelle, sondern der vorherige Status der Bewegung des Spielers. */
    private MovementState lastMovementState;
    /** Der Zustand, ob der Spieler gerade hochspringt. */
    private boolean jumping;
    /** Der Zustand, ob Schwerkraft auf den Spieler wirken soll. */
    private boolean gravity = true;
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
        JumpAndRun.GAME_INSTANCE.getGameTask().setKeyCode(-1);
        currentMovementState = MovementState.STAY;
        lastMovementState = MovementState.RIGHT;

        // reset animation
        resetAnimation(false);
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

        if (JumpAndRun.GAME_INSTANCE.getGameHandler().getGameState() == GameState.LOSE) return;

        switch (currentMovementState) {
            case LEFT:
                drawLeftMovement(g);
                break;
            case RIGHT:
                drawRightMovement(g);
                break;
            case STAY:
                switch (lastMovementState) {
                    case LEFT:
                        drawLeftMovement(g);
                        break;
                    case RIGHT:
                        drawRightMovement(g);
                        break;
                }
        }
    }

    /**
     * Prüft, ob der Spieler aktuell mit einem bestimmten Gegner kollidiert.
     *
     * @param opponent Der Gegner, welcher auf eine Kollision mit dieser Instanz des Spielers überprüft wird.
     *
     * @return Wenn der Spieler mit diesem Gegner kollidiert {@code true}, ansonsten {@code false}.
     */
    public boolean collides(final Opponent opponent) {
        final int rightPlayerPosition = screenPositionX + PLAYER_SIZE;
        final int bottomPlayerPosition = positionY + PLAYER_SIZE;

        if (rightPlayerPosition >= opponent.getPositionX() && screenPositionX <= opponent.getPositionX() + opponent.getWidth()) {
            return bottomPlayerPosition >= opponent.getPositionY() && positionY <= opponent.getPositionY() + opponent.getHeight();
        }

        return false;
    }

    /**
     * Simuliert die Bewegung nach links.
     */
    public void moveLeft() {
        // update player animation
        if (Duration.between(lastAnimationUpdateMoment, Instant.now()).toMillis() >= ANIMATION_PERIOD_IN_MILLIS) {
            updateAnimation();
            currentAnimationCount--;

            lastAnimationUpdateMoment = Instant.now();
        }

        // update last movement state
        lastMovementState = MovementState.LEFT;

        // check if player should overall move left
        if (absolutePositionX <= MAX_LEFT_POINT_ON_SCREEN) return;

        // check if player should stay
        if (currentMovementState == MovementState.STAY) {
            currentMovementState = MovementState.LEFT;
            return;
        }

        // update current movement state
        currentMovementState = MovementState.LEFT;

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
        // update player animation
        if (Duration.between(lastAnimationUpdateMoment, Instant.now()).toMillis() >= ANIMATION_PERIOD_IN_MILLIS) {
            updateAnimation();
            currentAnimationCount++;

            lastAnimationUpdateMoment = Instant.now();
        }

        // update last movement state
        lastMovementState = MovementState.RIGHT;

        // check if player should overall move right
        if (absolutePositionX >= JumpAndRun.GAME_INSTANCE.getGameHandler().getMap().getMapLength()) return;

        // check if player should stay
        if (currentMovementState == MovementState.STAY) {
            currentMovementState = MovementState.RIGHT;
            return;
        }

        // update current movement state
        currentMovementState = MovementState.RIGHT;

        // increment absolute position
        absolutePositionX += STEP_SIZE;

        // check if player should move right on screen
        if (screenPositionX >= MAX_RIGHT_POINT_ON_SCREEN) return;

        // update position on screen
        screenPositionX += STEP_SIZE;
    }

    /**
     * Setzt den aktuellen Status der Bewegung des Spielers auf {@code STAY}.
     */
    public void stay() {
        this.currentMovementState = MovementState.STAY;
    }

    /**
     * Lässt den Spieler hochspringen.
     */
    public void jump() {
        if (jumping) return;
        if (gravity && positionY < START_POSITION_Y) return;

        // play sound
        SoundType.JUMP.play(0);

        jumping = true;
        final int startY = positionY;

        final ScheduledExecutorService jumpTask = Executors.newScheduledThreadPool(1);
        jumpTask.scheduleAtFixedRate(() -> {
            gravity = false;

            if (positionY < startY - JUMP_HEIGHT) {
                jumping = false;
                gravity = true;

                jumpTask.shutdown();
            }

            positionY -= (STEP_SIZE / 2);
            resetAnimation(false);
        }, 0, 6, TimeUnit.MILLISECONDS);
    }

    /**
     * Setzt die Animation immer auf die Standard-Position zurück.
     *
     * @param delay Der Zustand, ob das Zurücksetzen kurz verzögert werden soll.
     */
    public void resetAnimation(final boolean delay) {
        if (!delay) {
            currentAnimationCount = 1;
            updateAnimation();
            return;
        }

        switch (currentMovementState) {
            case LEFT:
                currentAnimationCount--;
                break;

            case RIGHT:
                currentAnimationCount++;
                break;
        }
        updateAnimation();

        final ScheduledExecutorService resetDelayTask = Executors.newScheduledThreadPool(1);
        resetDelayTask.schedule(() -> {
            currentAnimationCount = 1;
            updateAnimation();
        }, ANIMATION_RESET_DELAY_IN_MILLIS, TimeUnit.MILLISECONDS);
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

    /**
     * Zeichnet den Spieler ein, welcher nach links ausgerichtet ist.
     *
     * @param g Das Grafik-Objekt, mit dem der Spieler gezeichnet werden soll.
     */
    private void drawLeftMovement(final Graphics2D g) {
        g.drawImage(currentAnimation, screenPositionX + PLAYER_SIZE, positionY, -PLAYER_SIZE, PLAYER_SIZE, null);
    }

    /**
     * Zeichnet den Spieler ein, welcher nach rechts ausgerichtet ist.
     *
     * @param g Das Grafik-Objekt, mit dem der Spieler gezeichnet werden soll.
     */
    private void drawRightMovement(final Graphics2D g) {
        g.drawImage(currentAnimation, screenPositionX, positionY, PLAYER_SIZE, PLAYER_SIZE, null);
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
     * Gibt den Zustand zurück, ob Schwerkraft auf den Spieler wirken soll.
     *
     * @return Der Zustand, ob Schwerkraft auf den Spieler wirken soll.
     */
    public boolean hasGravity() {
        return gravity;
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
        if (health > this.health) {
            SoundType.HEAL.play(0);
        }

        if (health < this.health) {
            SoundType.DAMAGE.play(0);
        }

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

    /**
     * Legt die aktuelle y-Position des Spielers neu fest.
     *
     * @param y Die neue y-Position des Spielers.
     */
    public void setPositionY(final int y) {
        this.positionY = y;
    }

    /**
     * Legt den Zustand fest, ob Schwerkraft auf den Spieler wirken soll.
     *
     * @param gravity Der Zustand, ob Schwerkraft auf den Spieler wirken soll.
     */
    public void setGravity(final boolean gravity) {
        this.gravity = gravity;
    }
    //</editor-fold>

}

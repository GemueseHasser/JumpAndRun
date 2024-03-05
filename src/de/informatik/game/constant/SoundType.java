package de.informatik.game.constant;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * Ein {@link SoundType} stellt einen Typen eines Tons dar, welcher beliebig oft abgespielt werden kann und auch
 * beliebig oft wieder gestoppt werden kann. Der Ton wird außerdem mit einer Lautstärke von {@code 0} bis {@code 100}
 * versehen.
 */
public enum SoundType {

    //<editor-fold desc="VALUES">
    /** Die Hintergrundmusik für das eigentliche Spiel. */
    GAME_BACKGROUND("game_background.wav", 40),
    /** Der Ton, welcher bei einem Sprung gespielt wird. */
    JUMP("jump.wav", 60),
    /** Die Hintergrundmusik für das Menü, in dem man das Spiel starten kann. */
    MENU_BACKGROUND("menu_background.wav", 30),
    /** Der Ton, welcher gespielt wird, wenn man einen Level im Menü auswählt. */
    CHOOSE_LEVEL("choose_level.wav", 35),
    /** Der Ton, welcher gespielt wird, wenn man im Menü das Spiel startet. */
    START_GAME("start_game.wav", 50);
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Der Ton, welcher geladen wird und abgespielt oder gestoppt werden kann. */
    private Clip clip;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt mithilfe eines Dateinamens und einer Lautstärke von {@code 0} bis {@code 100} einen neuen und vollständig
     * unabhängigen {@link SoundType Typen}. Typ stellt einen Typen eines Tons dar, welcher beliebig oft abgespielt
     * werden kann und auch beliebig oft wieder gestoppt werden kann.
     *
     * @param soundName Der Name der Datei des Tons, welche sich unter resources/sounds befindet.
     * @param level     Die Lautstärke, mit der der jeweilige Ton abgespielt werden soll, von {@code 0} bis {@code 100}.
     */
    SoundType(final String soundName, final int level) {
        try {
            final AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    getClass().getResource("/de/informatik/resources/sounds/" + soundName)
            );
            final DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10((double) level / 100));
        } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }
    //</editor-fold>


    /**
     * Spielt den aktuellen Ton beliebig oft ab.
     *
     * @param loop Die Anzahl an Wiederholungen bei der Wiedergabe des Tons.
     */
    public void play(final int loop) {
        if (clip == null) return;

        if (clip.isRunning()) clip.stop();

        clip.loop(loop);
        clip.setMicrosecondPosition(0);

        clip.start();
    }

    /**
     * Stoppt die aktuelle Wiedergabe des Tons.
     */
    public void stop() {
        if (clip == null) return;

        clip.stop();
    }

}
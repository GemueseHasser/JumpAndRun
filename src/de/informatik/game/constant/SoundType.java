package de.informatik.game.constant;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;

public enum SoundType {

    GAME_BACKGROUND("game_background.wav", 40),
    JUMP("jump.wav", 60),
    MENU_BACKGROUND("menu_background.wav", 30),
    MENU_HOVER("menu_hover.wav", 35),
    START_GAME("start_game.wav", 50);


    private Clip clip;


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


    public void play(final int loop) {
        if (clip == null) return;

        if (clip.isRunning()) clip.stop();

        clip.loop(loop);
        clip.setMicrosecondPosition(0);

        clip.start();
    }

    public void stop() {
        if (clip == null) return;

        clip.stop();
    }

}

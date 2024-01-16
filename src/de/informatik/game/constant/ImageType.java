package de.informatik.game.constant;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Ein {@link ImageType} stellt einen Typen eines Bildes dar, welches beim Start der Anwendung geladen werden soll.
 * Jeder Typ besteht aus einem Namen des Bildes, welches sich im Package  {@link de.informatik.resources} befinden muss.
 * Dieses Bild wird dann geladen und als {@link BufferedImage} zurückgegeben.
 */
public enum ImageType {

    //<editor-fold desc="VALUES">
    /** Das Hintergrundbild. */
    BACKGROUND(
        "pre-dreadnough_french.jpg"
    ),
    /** Eine einfache Barriere. */
    BARRIER(
        "barrier.jpg"
    );
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Das mithilfe des Namens geladene {@link BufferedImage Bild}. */
    private final BufferedImage image;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt einen neuen und vollständig unabhängigen {@link ImageType}. Ein {@link ImageType} stellt einen Typen
     * eines Bildes dar, welches beim Start der Anwendung geladen werden soll. Jeder Typ besteht aus einem Namen des
     * Bildes, welches sich im Package  {@link de.informatik.resources} befinden muss. Dieses Bild wird dann geladen und
     * als {@link BufferedImage} zurückgegeben.
     *
     * @param name Der Name des Bildes, unter dem das Bild gefunden und somit geladen werden kann.
     */
    ImageType(final String name) {
        try {
            this.image = ImageIO.read(
                Objects.requireNonNull(getClass().getResource("/de/informatik/resources/textures/" + name))
            );
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    //</editor-fold>


    //<editor-fold desc="Getter">

    /**
     * Gibt das jeweilige mithilfe des Namens geladene {@link BufferedImage} zurück.
     *
     * @return Das jeweilige mithilfe des Namens geladene {@link BufferedImage}.
     */
    public BufferedImage getImage() {
        return this.image;
    }
    //</editor-fold>

}

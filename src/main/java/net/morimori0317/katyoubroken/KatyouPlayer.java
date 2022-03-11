package net.morimori0317.katyoubroken;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class KatyouPlayer {
    private static final Random rand = new Random();

    public static void playBroken(boolean brokend) {
        if (brokend) {
            play("broken" + (rand.nextBoolean() ? 1 : 2));
        } else {
            play("katyou" + (rand.nextInt(17) + 1));
        }
    }

    private static void play(String id) {
        try {
            Clip cl = createClip(id);
            if (cl != null)
                cl.start();
        } catch (Exception ex) {
        }
    }

    private static Clip createClip(String id) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        id = String.format("/sounds/%s.wav", id);
        InputStream stream = KatyouPlayer.class.getResourceAsStream(id);
        if (stream == null) return null;
        stream = new BufferedInputStream(stream);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(stream);
        AudioFormat af = audioInputStream.getFormat();
        DataLine.Info dataLine = new DataLine.Info(Clip.class, af);
        Clip c = (Clip) AudioSystem.getLine(dataLine);
        c.open(audioInputStream);
        return c;
    }
}

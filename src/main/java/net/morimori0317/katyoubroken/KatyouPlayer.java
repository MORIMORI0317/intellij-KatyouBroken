package net.morimori0317.katyoubroken;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class KatyouPlayer {
    private static final KatyouPlayer INSTANCE = new KatyouPlayer();
    private static int playerNumber;
    private final Random random = new Random();

    public static KatyouPlayer getInstance() {
        return INSTANCE;
    }

    public void playBroken(boolean broken) {
        if (broken) {
            play("broken" + (random.nextBoolean() ? 1 : 2));
        } else {
            play("katyou" + (random.nextInt(17) + 1));
        }
    }

    private void play(String id) {
        PlayerThread player = new PlayerThread(id);
        player.start();
    }

    private void playInternal(String id) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        id = String.format("/katyou_sounds/%s.wav", id);
        try (InputStream stream = resourceExtractor(KatyouPlayer.class, id)) {
            if (stream == null) return;
            try (BufferedInputStream bufstream = new BufferedInputStream(stream); AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufstream)) {
                AudioFormat af = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, af, AudioSystem.NOT_SPECIFIED);
                try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                    line.open(af, AudioSystem.NOT_SPECIFIED);
                    line.start();
                    int buffer_size = 128000;
                    int bytes_read = 0;
                    byte[] ab_data = new byte[buffer_size];
                    while (bytes_read != -1) {
                        bytes_read = audioInputStream.read(ab_data, 0, ab_data.length);
                        if (bytes_read >= 0) {
                            line.write(ab_data, 0, bytes_read);
                        }
                    }
                    line.drain();
                }
            }
        }
    }

    @Nullable
    private static InputStream resourceExtractor(@NotNull Class<?> clazz, @NotNull String path) {
        if (path.startsWith("/")) path = path.substring(1);

        InputStream stream = clazz.getResourceAsStream("/" + path);
        if (stream == null) stream = ClassLoader.getSystemResourceAsStream(path);
        return stream != null ? new BufferedInputStream(stream) : null;
    }

    private class PlayerThread extends Thread {
        private final String id;

        public PlayerThread(String id) {
            this.id = id;
            setName("katyou-sound-player-" + playerNumber++);
        }

        @Override
        public void run() {
            try {
                playInternal(id);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ignored) {
            }
        }
    }
}

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Rational;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;


public class VideoRenderRunner implements Runnable {
    private final Surface surface;
    private final int videoFramesPerSecond;
    private final int videoTotalLengthSeconds;
    private final String outputFilePath;
    private AtomicLong frameProgress = new AtomicLong(0);

    /**
     * Constructor to set up the GUI components
     */
    public VideoRenderRunner(Surface surface, int framesPerSecond, int videoLength, String outputFilePath) {
        this.surface = surface;
        this.videoFramesPerSecond = framesPerSecond;
        this.videoTotalLengthSeconds = videoLength;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void run() {
        // activate opengl
        System.setProperty("sun.java2d.opengl", "true");

        FileChannelWrapper out = null;
        try {
            out = NIOUtils.writableFileChannel(outputFilePath);
            // for Android use: AndroidSequenceEncoder
            AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(videoFramesPerSecond, 1));
            for (long i = 0; i < (long) videoFramesPerSecond * videoTotalLengthSeconds; ++i) {
                // Encode the image
                encoder.encodeImage(paintFrame());
                if (i % videoFramesPerSecond == 0) {
                    System.out.println("f: " + i + " / " + videoFramesPerSecond * videoTotalLengthSeconds);
                }
                frameProgress.set(i);
            }
            // Finalize the encoding, i.e. clear the buffers, write the header, etc.
            encoder.finish();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            NIOUtils.closeQuietly(out);
        }
    }

    public long getFrameProgress() {
        return frameProgress.get();
    }

    private BufferedImage paintFrame() {
        BufferedImage resultImage = surface.getFrame();

        surface.doCalculations();

        return resultImage;
    }
}
 import java.awt.*;

 import org.jcodec.api.awt.AWTSequenceEncoder;
 import org.jcodec.common.io.FileChannelWrapper;
 import org.jcodec.common.io.NIOUtils;
 import org.jcodec.common.model.Rational;

 import java.awt.image.BufferedImage;
 import java.io.IOException;

 
 public class AppVideo {
    public static final int CANVAS_WIDTH = 3840; // 1920;
    public static final int CANVAS_HEIGHT = 2160; //1080;
    public static final int INFLUENCE_LIMIT = 15;

    public static final int VIDEO_FRAMES_PER_SECOND = 25;
    public static final int VIDEO_TOTAL_LENGTH_SECONDS = 60;

    public Surface surface;
 
    /** Constructor to set up the GUI components */
    public AppVideo() {
       // setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
       surface = new Surface(CANVAS_WIDTH, CANVAS_HEIGHT, INFLUENCE_LIMIT);

       // setup some random particles
       int count = 10000;
       boolean bigBang = false;
       surface.addAtoms(Color.RED,         count, bigBang);
       surface.addAtoms(Color.BLUE,        count, bigBang);
       surface.addAtoms(Color.GREEN,       count, bigBang);
       surface.addAtoms(Color.YELLOW,      count, bigBang);
       surface.addAtoms(Color.WHITE,       count, bigBang);
       surface.addAtoms(Color.PINK,        count, bigBang);
       surface.addAtoms(Color.ORANGE,      count, bigBang);
       surface.addAtoms(Color.CYAN,        count, bigBang);
       surface.addAtoms(Color.LIGHT_GRAY,  count, bigBang);
       surface.addAtoms(Color.MAGENTA,     count, bigBang);
//       surface.addAtoms(Color.DARK_GRAY,   count, bigBang);

       surface.setgFactor(0.1);
       surface.setFriction(0.5);
       surface.setInfluenceLimitRadius(40);
    }

    /** The Entry main method */
    public static void main(String[] args) {
       // activate opengl
       System.setProperty("sun.java2d.opengl", "true");

       AppVideo app = new AppVideo();
       FileChannelWrapper out = null;
       try {
          out = NIOUtils.writableFileChannel("output.mp4");
          // for Android use: AndroidSequenceEncoder
          AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(VIDEO_FRAMES_PER_SECOND, 1));
          for (long i = 0; i < VIDEO_FRAMES_PER_SECOND * VIDEO_TOTAL_LENGTH_SECONDS; ++i) {
             // Encode the image
             encoder.encodeImage(app.paintFrame());
             if (i % VIDEO_FRAMES_PER_SECOND == 0) {
                System.out.println("f: " + i + " / " + VIDEO_FRAMES_PER_SECOND * VIDEO_TOTAL_LENGTH_SECONDS);
             }
          }
          // Finalize the encoding, i.e. clear the buffers, write the header, etc.
          encoder.finish();
       } catch (IOException e) {
          e.printStackTrace();
       } finally {
           NIOUtils.closeQuietly(out);
       }
    }
 
    public BufferedImage paintFrame() {
//       BufferedImage resultImage = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
//       Graphics2D g2d = resultImage.createGraphics();
//       g2d.setBackground(Color.BLACK);
//
//       surface.geAtoms().forEach((a) -> {
//          g2d.setColor(a.getColor());
//          int x = (int) Math.round(a.getX());
//          int y = (int) Math.round(a.getY());
//          g2d.fillOval(x, y, PARTICLE_SIZE, PARTICLE_SIZE);
//          g2d.drawOval(x, y, PARTICLE_SIZE, PARTICLE_SIZE);
//       });

       BufferedImage resultImage = surface.getFrame();

       surface.doCalculations();

       return resultImage;
    }
 }
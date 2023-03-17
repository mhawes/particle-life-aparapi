import java.awt.*;
import javax.swing.*;
 
public class AppPanel extends JPanel {
   // Named-constants for dimensions
   public static final int CANVAS_WIDTH = 1900;
   public static final int CANVAS_HEIGHT = 1020;
   public static final int INFLUENCE_LIMIT = 15;
   public static final int PARTICLE_SIZE = 1;

   public static final String TITLE = "Particle Life";
   public Surface surface;

   public AppPanel() {
      setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
      surface = new Surface(CANVAS_WIDTH, CANVAS_HEIGHT, INFLUENCE_LIMIT);

      // setup some random particles
      int count = 1000;
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
      surface.addAtoms(Color.DARK_GRAY,   count, bigBang);
   }

   @Override
   public void paint(Graphics g) {
      super.paintComponent(g);
      setBackground(Color.BLACK);
      Graphics2D g2d = (Graphics2D)g;

      surface.doCalculations();

      surface.geAtoms().parallelStream().forEach((a) -> {
         g2d.setColor(a.getColor());
         int x = (int) Math.round(a.getX());
         int y = (int) Math.round(a.getY());
         g2d.fillOval(x, y, PARTICLE_SIZE, PARTICLE_SIZE);
         g2d.drawOval(x, y, PARTICLE_SIZE, PARTICLE_SIZE);
      });
   }

   public static void main(String[] args) {
      // activate opengl
      System.setProperty("sun.java2d.opengl", "true");

      AppPanel appPanel = new AppPanel();

      // Run the GUI codes on the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            JFrame mainFrame = new JFrame(TITLE);
            mainFrame.setContentPane(appPanel);
            mainFrame.pack();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null); // center the application window
            mainFrame.setVisible(true);

            JFrame controlFrame = new JFrame (TITLE + " - controls");
            controlFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            controlFrame.getContentPane().add (new ControlPanel(appPanel.surface));
            controlFrame.pack();
            controlFrame.setVisible (true);
         }
      });
                 
      // basic bitch game loop
      while(true) {
         appPanel.repaint();
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }
}
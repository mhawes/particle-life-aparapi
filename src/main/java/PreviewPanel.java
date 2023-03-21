import java.awt.*;
import javax.swing.*;
 
public class PreviewPanel extends JPanel {
   public static final String TITLE = "Particle Life - preview";
   public Surface surface;

   public PreviewPanel(Surface surface) {
      this.surface = surface;
      setPreferredSize(new Dimension(this.surface.getWidth(), this.surface.getHeight()));
   }

   @Override
   public void paint(Graphics g) {
      super.paintComponent(g);
      setBackground(Color.BLACK);
      surface.renderFrame((Graphics2D)g);
      surface.doCalculations();
   }

   public void run() {
      // activate opengl
      System.setProperty("sun.java2d.opengl", "true");
      boolean quit = false;

      // Run the GUI codes on the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(() -> {
         JFrame mainFrame = new JFrame(TITLE);
         mainFrame.setContentPane(this);
         mainFrame.pack();
         mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         mainFrame.setLocationRelativeTo(null); // center the application window
         mainFrame.setVisible(true);
      });

      // basic bitch game loop
      while(!quit) {
         this.repaint();
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }
}
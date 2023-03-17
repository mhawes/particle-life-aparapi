import java.awt.*;
import javax.swing.*;

public class ControlPanel extends JPanel {
  private JButton btnRandomMatrix;
  private JButton btnPause;
  private JButton btnRestartSim;
  private JSlider sldGFactor;
  private JLabel lblGFactor;
  private JLabel lblFriction;
  private JSlider sldFriction;
  private JLabel lblInfluence;
  private JSlider sldInfluence;

  public ControlPanel(Surface surface) {

    //construct components
    btnRandomMatrix = new JButton ("Randomise Matrix");
    btnPause = new JButton ("Pause Sim");
    btnRestartSim = new JButton ("Restart Sim");
    sldGFactor = new JSlider (0, 100, 10);
    lblGFactor = new JLabel ("G Factor (0 to 1)");
    lblFriction = new JLabel ("Friction (0 to 1)");
    sldFriction = new JSlider (0, 100, 10);
    lblInfluence = new JLabel ("Influence (10 to 100)");
    sldInfluence = new JSlider (10, 100, 50);

    //set components properties
    sldGFactor.setOrientation (JSlider.HORIZONTAL);
    sldGFactor.setMinorTickSpacing (10);
    sldGFactor.setMajorTickSpacing (50);
    sldGFactor.setPaintTicks (true);
    sldGFactor.setPaintLabels (false);
    sldFriction.setOrientation (JSlider.HORIZONTAL);
    sldFriction.setMinorTickSpacing (10);
    sldFriction.setMajorTickSpacing (50);
    sldFriction.setPaintTicks (true);
    sldFriction.setPaintLabels (false);
    sldInfluence.setOrientation (JSlider.HORIZONTAL);
    sldInfluence.setMinorTickSpacing (10);
    sldInfluence.setMajorTickSpacing (50);
    sldInfluence.setPaintTicks (true);
    sldInfluence.setPaintLabels (false);

    //adjust size and set layout
    setPreferredSize (new Dimension (155, 293));
    setLayout (null);

    //add components
    add (btnRandomMatrix);
    add (btnPause);
    add (btnRestartSim);
    add (sldGFactor);
    add (lblGFactor);
    add (lblFriction);
    add (sldFriction);
    add (lblInfluence);
    add (sldInfluence);

    //set component bounds (only needed by Absolute Positioning)
    btnRandomMatrix.setBounds (5, 10, 145, 25);
    btnPause.setBounds (5, 70, 145, 25);
    btnRestartSim.setBounds (5, 40, 145, 25);
    sldGFactor.setBounds (5, 130, 145, 30);
    lblGFactor.setBounds (30, 105, 100, 25);
    lblFriction.setBounds (30, 160, 100, 25);
    sldFriction.setBounds (5, 180, 145, 30);
    lblInfluence.setBounds (15, 210, 125, 25);
    sldInfluence.setBounds (5, 230, 145, 30);

    // do event listeners 
    btnRandomMatrix.addActionListener(e -> surface.randomiseMatrix());
    btnPause.addActionListener(e -> surface.pauseSimulation());
    sldGFactor.addChangeListener(e -> surface.setgFactor((double) sldGFactor.getValue() * 0.01d));
    sldFriction.addChangeListener(e -> surface.setgFactor((double) sldFriction.getValue() * 0.01d));
    sldInfluence.addChangeListener(e -> surface.setInfluenceLimitRadius(sldInfluence.getValue()));
  }
}

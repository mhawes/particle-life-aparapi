import com.google.common.collect.ImmutableMap;
import javafx.util.Pair;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class SetupPanel extends JPanel {
    public static final int TOTAL_COLORS = 10;
    private final JComboBox resolution;
    private final JSlider influenceLimitSld;
    private final JLabel lblResolution;
    private final JLabel influenceLimitLbl;
    private final JLabel influenceLayersLbl;
    private final JSlider influenceLayersSld;
    private final JTextField influenceLimitTxt;
    private final JTextField influenceLayersTxt;
    private final JTextField fpsTxt;
    private final JLabel fpsLbl;
    private final JTextField secondsTxt;
    private final JLabel secondsLbl;
    private final JSlider gFactorSld;
    private final JTextField gFactorTxt;
    private final JLabel gFactorLbl;
    private final JSlider frictionSld;
    private final JTextField frictionTxt;
    private final JLabel frictionLbl;
    private final JButton renderBtn;
    private final JButton previewBtn;
    private final JTextField totalAtomsTxt;
    private final JLabel totalAtomsLbl;
    private final JCheckBox bigBangChk;
    private final JProgressBar progressBar;

    private static Map<String, Pair<Integer, Integer>> RESOLUTIONS = ImmutableMap.of(
            "480p   (720x480)", new Pair<>(720, 480),
            "576p   (720x576)", new Pair<>(720, 576),
            "720p   (1280x720)", new Pair<>(1280, 720),
            "1080p  (1920x1080)", new Pair<>(1920, 1080),
            "4K     (3840x2160)", new Pair<>(3840, 2160)
    );

    public SetupPanel() {
        //construct components
        resolution = new JComboBox (RESOLUTIONS.keySet().toArray(new String[0]));
        influenceLimitSld = new JSlider (0, 150);
        lblResolution = new JLabel ("Resolution");
        influenceLimitLbl = new JLabel ("Inf. limit");
        influenceLayersLbl = new JLabel ("Inf. layers");
        influenceLayersSld = new JSlider (1, 50);
        influenceLimitTxt = new JTextField (5);
        influenceLayersTxt = new JTextField (5);
        fpsTxt = new JTextField (5);
        fpsLbl = new JLabel ("Frames per second");
        secondsTxt = new JTextField (5);
        secondsLbl = new JLabel ("Video length (seconds)");
        gFactorSld = new JSlider (0, 100);
        gFactorTxt = new JTextField (5);
        gFactorLbl = new JLabel ("Gravity Factor");
        frictionSld = new JSlider (0, 100);
        frictionTxt = new JTextField (5);
        frictionLbl = new JLabel ("Friction");
        renderBtn = new JButton ("Render");
        previewBtn = new JButton ("Preview");
        totalAtomsTxt = new JTextField (5);
        totalAtomsLbl = new JLabel ("Total atoms");
        bigBangChk = new JCheckBox ("Big bang");
        progressBar = new JProgressBar(0, 100);

        //set components properties
        influenceLimitSld.setOrientation (JSlider.HORIZONTAL);
        influenceLimitSld.setMinorTickSpacing (25);
        influenceLimitSld.setMajorTickSpacing (75);
        influenceLimitSld.setPaintTicks (false);
        influenceLimitSld.setPaintLabels (false);
        influenceLayersSld.setOrientation (JSlider.HORIZONTAL);
        influenceLayersSld.setMinorTickSpacing (10);
        influenceLayersSld.setMajorTickSpacing (25);
        influenceLayersSld.setPaintTicks (false);
        influenceLayersSld.setPaintLabels (false);
        influenceLimitTxt.setEnabled (false);
        influenceLayersTxt.setEnabled (false);
        gFactorSld.setOrientation (JSlider.HORIZONTAL);
        gFactorSld.setMinorTickSpacing (0);
        gFactorSld.setMajorTickSpacing (0);
        gFactorSld.setPaintTicks (false);
        gFactorSld.setPaintLabels (false);
        gFactorTxt.setEnabled (false);
        frictionSld.setOrientation (JSlider.HORIZONTAL);
        frictionSld.setMinorTickSpacing (1);
        frictionSld.setMajorTickSpacing (5);
        frictionSld.setPaintTicks (false);
        frictionSld.setPaintLabels (false);
        frictionTxt.setEnabled (false);

        //adjust size and set layout
        setPreferredSize (new Dimension (437, 341));
        setLayout (null);

        //add components
        add (resolution);
        add (influenceLimitSld);
        add (lblResolution);
        add (influenceLimitLbl);
        add (influenceLayersLbl);
        add (influenceLayersSld);
        add (influenceLimitTxt);
        add (influenceLayersTxt);
        add (fpsTxt);
        add (fpsLbl);
        add (secondsTxt);
        add (secondsLbl);
        add (gFactorSld);
        add (gFactorTxt);
        add (gFactorLbl);
        add (frictionSld);
        add (frictionTxt);
        add (frictionLbl);
        add (renderBtn);
        add (previewBtn);
        add (totalAtomsTxt);
        add (totalAtomsLbl);
        add (bigBangChk);
        add (progressBar);

        //set component bounds (only needed by Absolute Positioning)
        resolution.setBounds (145, 205, 280, 25);
        influenceLimitSld.setBounds (110, 20, 275, 45);
        lblResolution.setBounds (10, 205, 100, 25);
        influenceLimitLbl.setBounds (10, 30, 100, 25);
        influenceLayersLbl.setBounds (10, 65, 100, 25);
        influenceLayersSld.setBounds (110, 55, 275, 45);
        influenceLimitTxt.setBounds (385, 30, 40, 25);
        influenceLayersTxt.setBounds (385, 65, 40, 25);
        fpsTxt.setBounds (145, 240, 100, 25);
        fpsLbl.setBounds (10, 240, 120, 25);
        secondsTxt.setBounds (145, 275, 100, 25);
        secondsLbl.setBounds (10, 275, 140, 25);
        gFactorSld.setBounds (105, 90, 280, 45);
        gFactorTxt.setBounds (385, 100, 40, 25);
        gFactorLbl.setBounds (10, 100, 100, 25);
        frictionSld.setBounds (105, 125, 280, 45);
        frictionTxt.setBounds (385, 135, 40, 25);
        frictionLbl.setBounds (10, 135, 100, 25);
        renderBtn.setBounds (255, 275, 170, 25);
        previewBtn.setBounds (255, 240, 170, 25);
        totalAtomsTxt.setBounds (145, 170, 100, 25);
        totalAtomsLbl.setBounds (10, 170, 100, 25);
        bigBangChk.setBounds (350, 170, 75, 25);
        progressBar.setBounds (10, 310, 415, 10);

        totalAtomsTxt.setText("20000");
        fpsTxt.setText("25");
        secondsTxt.setText("60");
        influenceLimitSld.addChangeListener(e -> influenceLimitTxt.setText(String.valueOf(influenceLimitSld.getValue())));
        influenceLayersSld.addChangeListener(e -> influenceLayersTxt.setText(String.valueOf(influenceLayersSld.getValue())));
        gFactorSld.addChangeListener(e -> gFactorTxt.setText(String.valueOf((double) gFactorSld.getValue() * 0.01d)));
        frictionSld.addChangeListener(e -> frictionTxt.setText(String.valueOf((double) frictionSld.getValue() * 0.01d)));
        influenceLimitSld.setValue(40);
        influenceLayersSld.setValue(15);
        gFactorSld.setValue(20);
        frictionSld.setValue(30);

        previewBtn.addActionListener(e -> startPreview());
        renderBtn.addActionListener(e -> startRender());
    }

    public static void createPanel() {
        JFrame frame = new JFrame ("Particle life - setup");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new SetupPanel());
        frame.pack();
        frame.setVisible (true);
    }

    public Surface generateSurface() {
        int width = RESOLUTIONS.get(resolution.getSelectedItem()).getKey();
        int height = RESOLUTIONS.get(resolution.getSelectedItem()).getValue();
        int infRad = Integer.parseInt(influenceLayersTxt.getText());
        int infLim = Integer.parseInt(influenceLimitTxt.getText());
        Surface surface = new Surface(width, height, infRad, infLim);
        surface.setFriction(Double.parseDouble(frictionTxt.getText()));
        surface.setgFactor(Double.parseDouble(gFactorTxt.getText()));

        int count = Integer.parseInt(totalAtomsTxt.getText()) / TOTAL_COLORS;
        boolean bigBang = bigBangChk.isSelected();
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

        return surface;
    }

    private void startPreview() {
        PreviewPanel panel = new PreviewPanel(generateSurface());
        panel.run();
    }

    private void startRender() {
        int fps = Integer.parseInt(fpsTxt.getText());
        int seconds = Integer.parseInt(secondsTxt.getText());
        long totalLength = (long) fps * seconds;
        VideoRenderRunner videoRenderRunner = new VideoRenderRunner(
                generateSurface(),
                fps,
                seconds,
                "output.mp4");
        Thread thread = new Thread(videoRenderRunner);
        thread.start();

        while(thread.isAlive()) {
            long progress = videoRenderRunner.getFrameProgress();
            int percentageDone = (int) (progress / totalLength) * 100;
            progressBar.setValue(percentageDone);
        }
    }
}

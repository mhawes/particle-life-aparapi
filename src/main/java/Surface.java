import com.aparapi.Kernel;
import com.aparapi.Range;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class Surface {
  public static final int PARTICLE_SIZE = 2;

  private AttractionMatrix attractionMatrix;
  private List<Atom> atoms;
  private int width, height, influenceLimitRadius;
  private double friction = 0.4d;
  private double gFactor = 0.2d;
  private boolean paused;
  private int nextTypeIndex = 0;

  private double[] atoms_x;
  private double[] atoms_y;
  private int[] atom_types;
  private double[] atoms_velocity_x;
  private double[] atoms_velocity_y;
  private Color[] atoms_colors;

  public Surface(int width, int height, int influenceLimitRadius) {
    this.attractionMatrix = new AttractionMatrix();
    this.atoms = new ArrayList<>();
    this.width = width;
    this.height = height;
    this.influenceLimitRadius = influenceLimitRadius;
    this.paused = false;
  }

  public void addAtoms(Color color, int count, boolean bigBang) {
    int type = nextTypeIndex++;
    attractionMatrix.addTypeToMatrix(type);
    Random rand = new Random();
    int midW = width / 2;
    int midH = height / 2;
    for (int i = 0; i < count; ++i) {
      if (bigBang) {
        int deviationX = rand.nextInt(10) - 5;
        int deviationY = rand.nextInt(10) - 5;
        atoms.add(new Atom(color, midW + deviationX, midH + deviationY, type));
      } else {
        atoms.add(new Atom(color, rand.nextInt(width), rand.nextInt(height), type));
      }
    }

    atoms_x = atoms.stream().mapToDouble(Atom::getX).toArray();
    atoms_y = atoms.stream().mapToDouble(Atom::getY).toArray();
    atom_types = atoms.stream().mapToInt(Atom::getType).toArray();
    atoms_velocity_x = atoms.stream().mapToDouble(Atom::getVelocityX).toArray();
    atoms_velocity_y = atoms.stream().mapToDouble(Atom::getVelocityY).toArray();
    atoms_colors = atoms.stream().map(Atom::getColor).toArray(Color[]::new);
    attractionMatrix.randomise();
  }

  public BufferedImage getFrame() {
    BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = resultImage.createGraphics();
    g2d.setBackground(Color.BLACK);

    for(int i = 0; i < atoms_x.length; ++i) {
      g2d.setColor(atoms_colors[i]);
      int x = (int) Math.round(atoms_x[i]);
      int y = (int) Math.round(atoms_y[i]);
      g2d.fillOval(x, y, PARTICLE_SIZE, PARTICLE_SIZE);
      g2d.drawOval(x, y, PARTICLE_SIZE, PARTICLE_SIZE);
    }

    return resultImage;
  }

  public void doCalculations() {
    if (paused) return;

    int size = atoms.size();
    double[] atoms_x = this.atoms_x;
    double[] atoms_y = this.atoms_y;
    int[] atom_types = this.atom_types;
    double[] atoms_velocity_x = this.atoms_velocity_x;
    double[] atoms_velocity_y = this.atoms_velocity_y;
    double[] results_x = new double[size];
    double[] results_y = new double[size];
    double[] results_velocity_x = new double[size];
    double[] results_velocity_y = new double[size];
    double[][] attraction_matrix = attractionMatrix.getAsLookupTable();
    double width_d = width;
    double height_d = height;
    double friction_d = friction;
    double gFactor_d = gFactor;
    double influenceLimitRadius_d = influenceLimitRadius;

    Kernel kernel = new Kernel(){
      @Override public void run() {
        int a = getGlobalId();
        double forceX = 0d;
        double forceY = 0d;
        double ax = atoms_x[a];
        double ay = atoms_y[a];
        for(int b = 0; b < size; ++b) {
          if (a != b) {
            double g = attraction_matrix[atom_types[a]][atom_types[b]] * gFactor_d;

            double bx = atoms_x[b];
            double by = atoms_y[b];

            double dx = getDistanceWithWrap(ax, bx, width_d);
            double dy = getDistanceWithWrap(ay, by, height_d);

            double distance = Math.sqrt(dx * dx + dy * dy);

            double gMult = 1d;
            for (int i = 0; i < 5; ++i) {
              if (distance > i * influenceLimitRadius_d &&
                      distance < (i + 1) * influenceLimitRadius_d) {
                g *= gMult;
                gMult *= 0.5d;
                double F = g * 1 / distance;
                forceX += (F * dx);
                forceY += (F * dy);
              }
            }
          }
        }
        results_velocity_x[a] = (atoms_velocity_x[a] * friction_d) + forceX;
        results_velocity_y[a] = (atoms_velocity_y[a] * friction_d) + forceY;
        results_x[a] = ax + atoms_velocity_x[a];
        results_y[a] = ay + atoms_velocity_y[a];

        // wrapping
        if (results_x[a] < 0) { results_x[a] = width_d; } else if (results_x[a] >= width_d){ results_x[a] = 0; }
        if (results_y[a] < 0) { results_y[a] = height_d; } else if (results_y[a] >= height_d){ results_y[a] = 0; }
      }

      double getDistanceWithWrap(double a, double b, double len) {
        double d = a - b;
        double dMax = a - (b + len);
        double dMin = a - (b - len);
        if (Math.abs(d) < Math.abs(dMax) && Math.abs(d) < Math.abs(dMin)) {
          return d;
        } else if (Math.abs(dMin) < Math.abs(dMax)) {
          return dMin;
        } else {
          return dMax;
        }
      }
    };

    kernel.execute(Range.create(size));

//    IntStream.range(0, size).parallel().forEachOrdered(i -> {
//      atoms.get(i).setPosition(results_x[i], results_y[i]);
//      atoms.get(i).setVelocity(results_velocity_x[i], results_velocity_y[i]);
//    });

    this.atoms_x = results_x;
    this.atoms_y = results_y;
    this.atoms_velocity_x = results_velocity_x;
    this.atoms_velocity_y = results_velocity_y;

    kernel.dispose();
  }

  public List<Atom> geAtoms() {
    return atoms;
  }

  public void printDebug() {
    attractionMatrix.printDebug();
  }

  public void randomiseMatrix() {
    attractionMatrix.randomise();
  }

  public void pauseSimulation() {
    this.paused = !this.paused;
  }
  
  public void setInfluenceLimitRadius(int influenceLimitRadius) {
    this.influenceLimitRadius = influenceLimitRadius;
  }

  public void setFriction(double friction) {
    this.friction = friction;
  }

  public void setgFactor(double gFactor) {
    this.gFactor = gFactor;
  }
}


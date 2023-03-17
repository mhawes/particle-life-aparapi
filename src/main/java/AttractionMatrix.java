import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AttractionMatrix {
  private Map<Integer, Map<Integer, Double>> attractions;

  public AttractionMatrix() {
    this.attractions = new HashMap<Integer, Map<Integer, Double>>();
  }

  public void addTypeToMatrix(int type) {
    Map<Integer, Double> newMap = new HashMap<>();
    for (Integer existingType : attractions.keySet()) {
      attractions.get(existingType).put(type, 0d);
      newMap.put(existingType, 0d);
    }
    newMap.put(type, 0d);
    attractions.put(type, newMap);

    // build it as an old school raw dog matrix
  }

  public double getAttractionForColors(int colorA, int colorB) {
    return attractions.get(colorA).get(colorB);
  }

  public void setAttractionForColors(int colorA, int colorB, double attraction) {
    attractions.get(colorA).put(colorB, attraction);
  }

  public void randomise() {
    Random rand = new Random();
    for (Integer i : attractions.keySet()) {
      for(Integer j : attractions.get(i).keySet()) {
        attractions.get(i).put(j, rand.nextDouble() - 0.5);
      }
    }
  }

  public double[][] getAsLookupTable() {
    double[][] result = new double[attractions.size()][attractions.size()];
    for (Integer i : attractions.keySet()) {
      for(Integer j : attractions.get(i).keySet()) {
        result[i][j] = attractions.get(i).get(j);
      }
    }
    return result;
  }

  public void printDebug() {
    for (Integer i : attractions.keySet()) {
      System.out.println("c: " + i);
      for(Integer j : attractions.get(i).keySet()) {
        System.out.println(" - " + j + ": " + attractions.get(i).getOrDefault(j, null));
      }
    }
  }
}

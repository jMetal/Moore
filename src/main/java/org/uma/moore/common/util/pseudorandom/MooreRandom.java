package org.uma.moore.common.util.pseudorandom;

import org.uma.moore.common.util.pseudorandom.impl.JavaRandomGenerator;

import java.io.Serializable;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class MooreRandom implements Serializable {
  private static MooreRandom instance ;
  private PseudoRandomGenerator randomGenerator ;

  private MooreRandom() {
    randomGenerator = new JavaRandomGenerator() ;
  }

  public static MooreRandom getInstance() {
    if (instance == null) {
      instance = new MooreRandom() ;
    }
    return instance ;
  }

  public void setRandomGenerator(PseudoRandomGenerator randomGenerator) {
    this.randomGenerator = randomGenerator;
  }

  public PseudoRandomGenerator getRandomGenerator() {
    return randomGenerator ;
  }

  public int nextInt(int lowerBound, int upperBound) {
    return randomGenerator.nextInt(lowerBound, upperBound) ;
  }

  public double nextDouble() {
    return randomGenerator.nextDouble() ;
  }

  public double nextDouble(double lowerBound, double upperBound) {
    return randomGenerator.nextDouble(lowerBound, upperBound) ;
  }

  public void setSeed(long seed) {
    randomGenerator.setSeed(seed);
  }

  public long getSeed() {
    return randomGenerator.getSeed() ;
  }

  public String getGeneratorName() {
    return randomGenerator.getName() ;
  }
}

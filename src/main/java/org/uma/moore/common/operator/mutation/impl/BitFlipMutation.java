package org.uma.moore.common.operator.mutation.impl;

import org.uma.moore.common.operator.mutation.Mutation;
import org.uma.moore.common.solution.impl.BinarySolution;
import org.uma.moore.common.util.MooreException;
import org.uma.moore.common.util.pseudorandom.MooreRandom;
import org.uma.moore.common.util.pseudorandom.RandomGenerator;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @version 1.0
 *
 * This class implements a bit flip mutation operator.
 */
@SuppressWarnings("serial")
public class BitFlipMutation implements Mutation<BinarySolution> {
  private double mutationProbability ;
  private RandomGenerator<Double> randomGenerator ;

  /** Constructor */
  public BitFlipMutation(double mutationProbability) {
	  this(mutationProbability, () -> MooreRandom.getInstance().nextDouble());
  }

  /** Constructor */
  public BitFlipMutation(double mutationProbability, RandomGenerator<Double> randomGenerator) {
    if (mutationProbability < 0) {
      throw new MooreException("Mutation probability is negative: " + mutationProbability) ;
    }
    this.mutationProbability = mutationProbability;
    this.randomGenerator = randomGenerator ;
  }

  /* Getter */
  @Override
  public double getMutationProbability() {
    return mutationProbability;
  }

  /** Execute() method */
  @Override
  public BinarySolution execute(BinarySolution solution) {
    if (null == solution) {
      throw new MooreException("Null parameter") ;
    }

    doMutation(mutationProbability, solution);
    return solution;
  }

  /**
   * Perform the mutation operation
   *
   * @param probability Mutation setProbability
   * @param solution    The solution to mutate
   */
  public void doMutation(double probability, BinarySolution solution)  {
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      for (int j = 0; j < solution.getVariable(i).getBinarySetLength(); j++) {
        if (randomGenerator.getRandomValue() <= probability) {
          solution.getVariable(i).flip(j);
        }
      }
    }
  }
}

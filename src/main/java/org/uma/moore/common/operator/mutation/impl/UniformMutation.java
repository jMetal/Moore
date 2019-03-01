package org.uma.moore.common.operator.mutation.impl;

import org.uma.moore.common.operator.mutation.Mutation;
import org.uma.moore.common.solution.impl.DoubleSolution;
import org.uma.moore.common.util.MooreException;
import org.uma.moore.common.util.pseudorandom.MooreRandom;
import org.uma.moore.common.util.pseudorandom.RandomGenerator;

/**
 * This class implements a uniform mutation operator.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @author Juan J. Durillo
 */
@SuppressWarnings("serial")
public class UniformMutation implements Mutation<DoubleSolution> {
  private double perturbation;
  private Double mutationProbability;
  private RandomGenerator<Double> randomGenenerator ;

  /** Constructor */
  public UniformMutation(double mutationProbability, double perturbation) {
	  this(mutationProbability, perturbation, () -> MooreRandom.getInstance().nextDouble());
  }

  /** Constructor */
  public UniformMutation(double mutationProbability, double perturbation, RandomGenerator<Double> randomGenenerator) {
    this.mutationProbability = mutationProbability ;
    this.perturbation = perturbation ;
    this.randomGenenerator = randomGenenerator ;
  }

  /* Getters */
  public double getPerturbation() {
    return perturbation;
  }

  @Override
  public double getMutationProbability() {
    return mutationProbability;
  }

  /* Setters */
  public void setPerturbation(Double perturbation) {
    this.perturbation = perturbation;
  }

  public void setMutationProbability(Double mutationProbability) {
    this.mutationProbability = mutationProbability;
  }

  /**
   * Perform the operation
   *
   * @param probability Mutation setProbability
   * @param solution    The solution to mutate
   */
  public void doMutation(double probability, DoubleSolution solution)  {
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      if (randomGenenerator.getRandomValue() < probability) {
        double rand = randomGenenerator.getRandomValue();
        double tmp = (rand - 0.5) * perturbation;

        tmp += solution.getVariable(i);

        if (tmp < solution.getLowerBound(i)) {
          tmp = solution.getLowerBound(i);
        } else if (tmp > solution.getUpperBound(i)) {
          tmp = solution.getUpperBound(i);
        }

        solution.setVariable(i, tmp);
      }
    }
  }

  /** Execute() method */
  @Override
  public DoubleSolution execute(DoubleSolution solution) {
    if (null == solution) {
      throw new MooreException("Null parameter");
    }

    doMutation(mutationProbability, solution);

    return solution;
  }
}

package org.uma.moore.common.operator.mutation.impl;


import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;
import org.uma.moore.common.operator.mutation.Mutation;
import org.uma.moore.common.solution.impl.DoubleSolution;
import org.uma.moore.common.solution.util.RepairDoubleSolution;
import org.uma.moore.common.solution.util.impl.RepairDoubleSolutionAtBounds;
import org.uma.moore.common.util.MooreException;

/**
 * This class implements a polynomial mutation operator
 *
 * The implementation is based on the NSGA-II code available in
 * http://www.iitk.ac.in/kangal/codes.shtml
 *
 * If the lower and upper bounds of a variable are the same, no mutation is carried out and the
 * bound value is returned.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @author Juan J. Durillo
 */
@SuppressWarnings("serial")
public class PolynomialMutation implements Mutation<DoubleSolution> {
  private static final double DEFAULT_PROBABILITY = 0.01 ;
  private static final double DEFAULT_DISTRIBUTION_INDEX = 20.0 ;
  private double distributionIndex ;
  private double mutationProbability ;
  private RepairDoubleSolution solutionRepair ;

  private RandomGenerator<Double> randomGenerator ;

  /** Constructor */
  public PolynomialMutation() {
    this(DEFAULT_PROBABILITY, DEFAULT_DISTRIBUTION_INDEX) ;
  }

  /** Constructor */
  public PolynomialMutation(DoubleProblem problem, double distributionIndex) {
    this(1.0/problem.getNumberOfVariables(), distributionIndex) ;
  }

  /** Constructor */
  public PolynomialMutation(DoubleProblem problem,
                            double distributionIndex,
                            RandomGenerator<Double> randomGenerator) {
    this(1.0/problem.getNumberOfVariables(), distributionIndex) ;
    this.randomGenerator = randomGenerator ;
  }

  /** Constructor */
  public PolynomialMutation(double mutationProbability, double distributionIndex) {
    this(mutationProbability, distributionIndex, new RepairDoubleSolutionAtBounds()) ;
  }

  /** Constructor */
  public PolynomialMutation(double mutationProbability,
                            double distributionIndex,
                            RandomGenerator<Double> randomGenerator) {
    this(mutationProbability, distributionIndex, new RepairDoubleSolutionAtBounds(), randomGenerator) ;
  }

  /** Constructor */
  public PolynomialMutation(double mutationProbability, double distributionIndex,
      RepairDoubleSolution solutionRepair) {
	  this(mutationProbability, distributionIndex, solutionRepair, () -> JMetalRandom.getInstance().nextDouble());
  }

  /** Constructor */
  public PolynomialMutation(double mutationProbability, double distributionIndex,
      RepairDoubleSolution solutionRepair, RandomGenerator<Double> randomGenerator) {
    if (mutationProbability < 0) {
      throw new MooreException("Mutation probability is negative: " + mutationProbability) ;
    } else if (distributionIndex < 0) {
      throw new MooreException("Distribution index is negative: " + distributionIndex) ;
    }
    this.mutationProbability = mutationProbability;
    this.distributionIndex = distributionIndex;
    this.solutionRepair = solutionRepair ;

    this.randomGenerator = randomGenerator ;
  }

  /* Getters */
  @Override
  public double getMutationProbability() {
    return mutationProbability;
  }

  public double getDistributionIndex() {
    return distributionIndex;
  }

  /** Execute() method */
  @Override
  public DoubleSolution execute(DoubleSolution solution) throws MooreException {
    if (null == solution) {
      throw new MooreException("Null parameter") ;
    }

    doMutation(mutationProbability, solution);
    return solution;
  }

  /** Perform the mutation operation */
  private void doMutation(double probability, DoubleSolution solution) {
    double rnd, delta1, delta2, mutPow, deltaq;
    double y, yl, yu, val, xy;

    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      if (randomGenerator.getRandomValue() <= probability) {
        y = solution.getVariable(i);
        yl = solution.getLowerBound(i) ;
        yu = solution.getUpperBound(i) ;
        if (yl == yu) {
          y = yl ;
        } else {
          delta1 = (y - yl) / (yu - yl);
          delta2 = (yu - y) / (yu - yl);
          rnd = randomGenerator.getRandomValue();
          mutPow = 1.0 / (distributionIndex + 1.0);
          if (rnd <= 0.5) {
            xy = 1.0 - delta1;
            val = 2.0 * rnd + (1.0 - 2.0 * rnd) * (Math.pow(xy, distributionIndex + 1.0));
            deltaq = Math.pow(val, mutPow) - 1.0;
          } else {
            xy = 1.0 - delta2;
            val = 2.0 * (1.0 - rnd) + 2.0 * (rnd - 0.5) * (Math.pow(xy, distributionIndex + 1.0));
            deltaq = 1.0 - Math.pow(val, mutPow);
          }
          y = y + deltaq * (yu - yl);
          y = solutionRepair.repairSolutionVariable(y, yl, yu);
        }
        solution.setVariable(i, y);
      }
    }
  }
}

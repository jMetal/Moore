package org.uma.moore.common.solution.impl;

import org.uma.moore.common.solution.Solution;
import org.uma.moore.common.util.MooreException;
import org.uma.moore.common.util.pseudorandom.MooreRandom;

import java.util.HashMap;
import java.util.List;

/**
 * Interface representing a double solutions
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class DoubleSolution extends AbstractSolution<Double> {
  protected List<Double> lowerBounds ;
  protected List<Double> upperBounds ;

  /** Constructor */
  public DoubleSolution(int numberOfVariables, int numberOfObjectives, List<Double> lowerBounds, List<Double> upperBounds) {
    super(numberOfVariables, numberOfObjectives) ;

    if (numberOfVariables != lowerBounds.size()) {
      throw new MooreException("The number of lower bounds is not equal to the number of variables: " +
          lowerBounds.size() + " -> " +  numberOfVariables) ;
    } else if (numberOfVariables != upperBounds.size()) {
      throw new MooreException("The number of upper bounds is not equal to the number of variables: " +
          upperBounds.size() + " -> " +  numberOfVariables) ;
    }

    this.lowerBounds = lowerBounds ;
    this.upperBounds = upperBounds ;

    for (int i = 0 ; i < numberOfVariables; i++) {
      Double value = MooreRandom.getInstance().nextDouble(lowerBounds.get(i), upperBounds.get(i)) ;
      setVariable(i, value) ;
    }
  }

  /** Copy constructor */
  public DoubleSolution(DoubleSolution solution) {
    super(solution.getNumberOfVariables(), solution.getNumberOfObjectives()) ;

    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      setVariable(i, solution.getVariable(i));
    }

    for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
      setObjective(i, solution.getObjective(i)) ;
    }

    lowerBounds = solution.lowerBounds ;
    upperBounds = solution.upperBounds ;

    attributes = new HashMap<Object, Object>(solution.attributes) ;
  }

  public double getLowerBound(int index) {
    return lowerBounds.get(index) ;
  }

  public double getUpperBound(int index) {
    return upperBounds.get(index) ;
  }

  @Override
  public String getVariableString(int index) {
    return getVariable(index).toString() ;
  }

  @Override
  public Solution<Double> copy() {
    return new DoubleSolution(this);
  }
}

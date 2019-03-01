package org.uma.moore.common.problem.impl;

import org.uma.moore.common.problem.DoubleProblem;
import org.uma.moore.common.problem.util.AbstractGenericProblem;
import org.uma.moore.common.solution.impl.DoubleSolution;

import java.util.List;

@SuppressWarnings("serial")
public abstract class AbstractDoubleProblem extends AbstractGenericProblem<DoubleSolution>
  implements DoubleProblem {

  protected List<Double> lowerBounds ;
  protected List<Double> upperBounds ;

  /* Getters */
	@Override
	public List<Double> getUpperBounds() {
		return upperBounds ;
	}

	@Override
	public List<Double> getLowerBounds() {
		return lowerBounds ;
	}

  /* Setters */
  protected void setLowerBound(List<Double> lowerBound) {
    this.lowerBounds = lowerBound;
  }

  protected void setUpperBound(List<Double> upperBound) {
    this.upperBounds = upperBound;
  }

  @Override
  public DoubleSolution createSolution() {
    return new DoubleSolution(this.getNumberOfVariables(), this.getNumberOfObjectives(),
        this.getLowerBounds(), this.getUpperBounds())  ;
  }
}

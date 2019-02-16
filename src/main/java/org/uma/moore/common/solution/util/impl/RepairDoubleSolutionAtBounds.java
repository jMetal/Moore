package org.uma.moore.common.solution.util.impl;


import org.uma.moore.common.solution.util.RepairDoubleSolution;
import org.uma.moore.common.util.MooreException;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RepairDoubleSolutionAtBounds implements RepairDoubleSolution {
  /**
   * Checks if the value is between its bounds; if not, the lower or upper bound is returned
   * @param value The value to be checked
   * @param lowerBound
   * @param upperBound
   * @return The same value if it is in the limits or a repaired value otherwise
   */
  public double repairSolutionVariable(double value, double lowerBound, double upperBound) {
    if (lowerBound > upperBound) {
      throw new MooreException("The lower bound (" + lowerBound + ") is greater than the "
          + "upper bound (" + upperBound+")") ;
    }

    double result = value ;
    if (value < lowerBound) {
      result = lowerBound ;
    }
    if (value > upperBound) {
      result = upperBound ;
    }

    return result ;
  }
}

package org.uma.moore.common.problem;

import org.uma.moore.common.solution.impl.DoubleSolution;

import java.util.List;

/**
 * Interface representing continuous problems
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface DoubleProblem extends Problem<DoubleSolution> {
  List<Double> getLowerBounds() ; ;
  List<Double> getUpperBounds() ;
}

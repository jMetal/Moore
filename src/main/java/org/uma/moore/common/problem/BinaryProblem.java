package org.uma.moore.common.problem;

import org.uma.moore.common.solution.impl.BinarySolution;

/**
 * Interface representing binary problems
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface BinaryProblem extends Problem<BinarySolution> {
  int getNumberOfBits(int index) ;
  int getTotalNumberOfBits() ;
}

package org.uma.moore.common.operator.crossover;

import org.uma.moore.common.operator.Operator;

import java.util.List;

/**
 * Interface representing crossover operators. They will receive a list of solutions and return
 * another list of solutions
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 *
 * @param <Source> The class of the solutions
 */
public interface Crossover<Source> extends Operator<List<Source>,List<Source>> {
  int getNumberOfRequiredParents() ;
  int getNumberOfGeneratedChildren() ;
  double getCrossoverProbability() ;
}
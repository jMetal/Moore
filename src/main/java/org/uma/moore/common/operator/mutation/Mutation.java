package org.uma.moore.common.operator.mutation;

import org.uma.moore.common.operator.Operator;

/**
 * Interface representing mutation operators
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 *
 * @param <Source> The solution class of the solution to be mutated
 */
public interface Mutation<Source> extends Operator<Source, Source> {
  double getMutationProbability() ;
}

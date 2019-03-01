package org.uma.moore.common.util.ranking;

import java.util.List;

/**
 * Ranks a list of solutions according to the dominance relationship
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface Ranking<S> {
  Ranking<S> computeRanking(List<S> solutionList) ;
  List<S> getSubfront(int rank) ;
  int getNumberOfSubfronts() ;
}

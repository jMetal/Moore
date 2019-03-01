package org.uma.moore.common.operator.selection.impl;

import org.uma.moore.common.operator.selection.Selection;
import org.uma.moore.common.solution.Solution;
import org.uma.moore.common.util.MooreException;
import org.uma.moore.common.util.SolutionListUtils;
import org.uma.moore.common.util.SolutionUtils;
import org.uma.moore.common.util.comparator.DominanceComparator;

import java.util.Comparator;
import java.util.List;

/**
 * @author Juanjo
 * @version 1.0
 *
 * Applies a n-ary tournament selection to return a solution from a list.
 */
@SuppressWarnings("serial")
public class TournamentSelection<S extends Solution<?>> implements Selection<List<S>,S> {
  private Comparator<S> comparator;

  private final int n_arity;

  /** Constructor */
  public TournamentSelection(int n_arity) {
    this(new DominanceComparator<S>(), n_arity) ;
  }

  /** Constructor */
  public TournamentSelection(Comparator<S> comparator, int n_arity) {
    this.n_arity = n_arity;
    this.comparator = comparator ;
  }

  @Override
  /** Execute() method */
  public S execute(List<S> solutionList) {
    if (null == solutionList) {
      throw new MooreException("The solution list is null") ;
    } else if (solutionList.isEmpty()) {
      throw new MooreException("The solution list is empty") ;
    }

    S result;
    if (solutionList.size() == 1) {
      result = solutionList.get(0);
    } else {
      result = SolutionListUtils.selectNRandomDifferentSolutions(1, solutionList).get(0);
      int count = 1; // at least 2 solutions are compared
      do {
        S candidate = SolutionListUtils.selectNRandomDifferentSolutions(1, solutionList).get(0);
        result = SolutionUtils.getBestSolution(result, candidate, comparator) ;
      } while (++count < this.n_arity);
    }

    return result;
  }
}

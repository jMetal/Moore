package org.uma.moore.common.operator.selection.impl;

import org.uma.moore.common.operator.selection.Selection;
import org.uma.moore.common.solution.Solution;
import org.uma.moore.common.util.MooreException;
import org.uma.moore.common.util.SolutionListUtils;
import org.uma.moore.common.util.comparator.DominanceComparator;

import java.util.Comparator;
import java.util.List;

/**
 * Applies a N-ary tournament selection to return the best solution between N that have been
 * chosen at random from a solution list.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class NaryTournamentSelection<S extends Solution<?>> implements Selection<List<S>, S> {
  private Comparator<S> comparator;
  private int numberOfSolutionsToBeReturned ;

  /** Constructor */
  public NaryTournamentSelection() {
    this(2, new DominanceComparator<S>()) ;
  }

  /** Constructor */
  public NaryTournamentSelection(int numberOfSolutionsToBeReturned, Comparator<S> comparator) {
    this.numberOfSolutionsToBeReturned = numberOfSolutionsToBeReturned ;
    this.comparator = comparator ;
  }

  @Override
  /** Execute() method */
  public S execute(List<S> solutionList) {
    if (null == solutionList) {
      throw new MooreException("The solution list is null") ;
    } else if (solutionList.isEmpty()) {
      throw new MooreException("The solution list is empty") ;
    } else if (solutionList.size() < numberOfSolutionsToBeReturned) {
      throw new MooreException("The solution list size (" + solutionList.size() +") is less than "
          + "the number of requested solutions ("+numberOfSolutionsToBeReturned+")") ;
    }

    S result ;
    if (solutionList.size() == 1) {
      result = solutionList.get(0) ;
    } else {
      List<S> selectedSolutions = SolutionListUtils.selectNRandomDifferentSolutions(
          numberOfSolutionsToBeReturned, solutionList) ;
      result = SolutionListUtils.findBestSolution(selectedSolutions, comparator) ;
    }

    return result;
  }
}

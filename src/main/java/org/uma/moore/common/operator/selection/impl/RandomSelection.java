package org.uma.moore.common.operator.selection.impl;

import org.uma.moore.common.operator.selection.Selection;
import org.uma.moore.common.util.MooreException;
import org.uma.moore.common.util.SolutionListUtils;

import java.util.List;

/**
 * This class implements a random selection operator used for selecting randomly a solution from a list
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class RandomSelection<S> implements Selection<List<S>, S> {

  /** Execute() method */
  public S execute(List<S> solutionList) {
    if (null == solutionList) {
      throw new MooreException("The solution list is null") ;
    } else if (solutionList.isEmpty()) {
      throw new MooreException("The solution list is empty") ;
    }

    List<S> list = SolutionListUtils.selectNRandomDifferentSolutions(1, solutionList);

    return list.get(0) ;
  }
}

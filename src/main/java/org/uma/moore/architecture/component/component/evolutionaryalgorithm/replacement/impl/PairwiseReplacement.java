package org.uma.moore.architecture.component.component.evolutionaryalgorithm.replacement.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.moore.common.util.Message;
import org.uma.moore.architecture.component.algorithm.impl.GDE3;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.replacement.Replacement;

/**
 *  * This class takes two populations of equal size, makes pairwise comparisons of their solutions,
 *  * select the non-dominated ones, and finally applies ranking and crowding to get the desired
 *  * number of solutions.
 *  *
 *  * Used in {@link GDE3}
 *
 * @param <S>
 */
public class PairwiseReplacement<S extends Solution<?>> extends Replacement<S> {

  private Comparator<S> dominanceComparator ;

  /**
   * Constructor
   */
  public PairwiseReplacement(Comparator<S> dominanceComparator) {
    super("Ranking and crowding replacement") ;
    this.dominanceComparator = dominanceComparator ;
  }

  /**
   * Constructor
   */
  public PairwiseReplacement() {
    this(new DominanceComparator<>()) ;
  }

  @Override
  public void onNext(Message message) {
    if (!(boolean)message.getAttribute("ALGORITHM_TERMINATED")) {
      List<S> population = (List<S>) message.getAttribute("POPULATION");
      List<S> offspringPopulation = ((List<S>) message.getAttribute("OFFSPRING_POPULATION"));
      int populationSize = population.size();

      if (populationSize != offspringPopulation.size()) {
        throw new JMetalException("The population size " + populationSize + " must be equal than"
            + "the offpsring population size " + offspringPopulation.size()) ;
      }

      List<S> temporaryPopulation = new ArrayList<>() ;
      for (int i = 0; i < populationSize; i++) {
        // Dominance test
        S child = offspringPopulation.get(i);
        int result;
        result = dominanceComparator.compare(population.get(i), child);
        if (result == -1) {
          // Solution i dominates child
          temporaryPopulation.add(population.get(i));
        } else if (result == 1) {
          // child dominates
          temporaryPopulation.add(child);
        } else {
          // the two solutions are non-dominated
          temporaryPopulation.add(child);
          temporaryPopulation.add(population.get(i));
        }
      }
      RankingAndCrowdingSelection<S> rankingAndCrowdingSelection;
      rankingAndCrowdingSelection = new RankingAndCrowdingSelection<S>(populationSize, dominanceComparator);

      List<S> newPopulation = new ArrayList<>(
          rankingAndCrowdingSelection.execute(temporaryPopulation));

      message.setAttribute("OFFSPRING_POPULATION", null);
      message.setAttribute("POPULATION", newPopulation);

      getObservable().setChanged();
      getObservable().notifyObservers(message);
    }
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public void onFinish(Message message) {

  }
}

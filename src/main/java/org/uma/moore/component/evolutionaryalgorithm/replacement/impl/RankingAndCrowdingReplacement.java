package org.uma.moore.component.evolutionaryalgorithm.replacement.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.moore.Population;
import org.uma.moore.component.evolutionaryalgorithm.replacement.Replacement;

public class RankingAndCrowdingReplacement<S extends Solution<?>> extends Replacement<S> {

  private Comparator<S> dominanceComparator ;

  /**
   * Constructor
   */
  public RankingAndCrowdingReplacement(Comparator<S> dominanceComparator) {
    super("Ranking and crowding replacement") ;
    this.dominanceComparator = dominanceComparator ;
  }

  /**
   * Constructor
   */
  public RankingAndCrowdingReplacement() {
    this(new DominanceComparator<>()) ;
  }

  @Override
  public void onNext(Population<S> population) {
    if (!(boolean)population.getAttribute("ALGORITHM_TERMINATED")) {

      int populationSize = population.size();

      List<S> jointPopulation = new ArrayList<>();
      jointPopulation.addAll(population);
      jointPopulation.addAll((Collection<? extends S>) population.getAttribute("OFFSPRING_POPULATION"));

      RankingAndCrowdingSelection<S> rankingAndCrowdingSelection;
      rankingAndCrowdingSelection = new RankingAndCrowdingSelection<S>(populationSize, dominanceComparator);

      Population<S> newPopulation = new Population<S>(
          rankingAndCrowdingSelection.execute(jointPopulation));

      population.setAttribute("OFFSPRING_POPULATION", null);
      newPopulation.setAttributes(population.getAttributes());

      getObservable().setChanged();
      getObservable().notifyObservers(newPopulation);
    } else {
      getObservable().setChanged();
      getObservable().notifyObservers(population);
    }
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public void onFinish(Population<S> population) {

  }
}

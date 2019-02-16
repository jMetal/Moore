package org.uma.moore.comp.component.evolutionaryalgorithm.replacement.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.moore.Message;
import org.uma.moore.comp.component.evolutionaryalgorithm.replacement.Replacement;

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
  public void onNext(Message message) {
    if (!(boolean)message.getAttribute("ALGORITHM_TERMINATED")) {
      List<S> population = (List<S>) message.getAttribute("POPULATION");

      int populationSize = population.size();

      List<S> jointPopulation = new ArrayList<>();
      jointPopulation.addAll(population);
      jointPopulation.addAll((Collection<? extends S>) message.getAttribute("OFFSPRING_POPULATION"));

      RankingAndCrowdingSelection<S> rankingAndCrowdingSelection;
      rankingAndCrowdingSelection = new RankingAndCrowdingSelection<S>(populationSize, dominanceComparator);

      List<S> newPopulation = new ArrayList<>(
          rankingAndCrowdingSelection.execute(jointPopulation));

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

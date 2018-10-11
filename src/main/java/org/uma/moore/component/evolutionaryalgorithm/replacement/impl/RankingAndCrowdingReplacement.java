package org.uma.moore.component.evolutionaryalgorithm.replacement.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.moore.Population;
import org.uma.moore.component.evolutionaryalgorithm.replacement.Replacement;
import org.uma.moore.observer.Observable;
import org.uma.moore.util.DataBuffer;

public class RankingAndCrowdingReplacement<S extends Solution<?>> extends Replacement<S> {
  private DataBuffer<Population<S>> buffer ;

  private Comparator<S> dominanceComparator ;

  /**
   * Constructor
   */
  public RankingAndCrowdingReplacement(Comparator<S> dominanceComparator) {
    buffer = new DataBuffer<>() ;
    this.dominanceComparator = dominanceComparator ;
  }

  /**
   * Constructor
   */
  public RankingAndCrowdingReplacement() {
    this(new DominanceComparator<>()) ;
  }

  @Override
  public void apply(Population<S> population) {
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

      observable.setChanged();
      observable.notifyObservers(newPopulation);
    } else {
      observable.setChanged();
      observable.notifyObservers(population);
    }
  }

  @Override
  public synchronized  void update(Observable<Population<S>> observable, Population<S> population) {
    try {
      buffer.put(new Population<>(population));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public void run() {
    try {
      while (true) {
        Population<S> population = buffer.get();
        apply(population);

        if ((boolean)population.getAttribute("ALGORITHM_TERMINATED")) {
          break ;
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

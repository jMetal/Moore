package org.uma.moore.component.evolutionaryalgorithm.selection.impl;

import java.util.Comparator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.moore.Population;
import org.uma.moore.component.evolutionaryalgorithm.selection.Selection;
import org.uma.moore.observer.Observable;
import org.uma.moore.util.DataBuffer;

/**
 * Applies a binary tournament selection to return the best solution between two that have been
 * chosen at random from a solution list.
 * Modified by Juanjo in 13.03.2015. A binary tournament is now a TournamenteSelection with 2 
 * tournaments
 *
 * @author Antonio J. Nebro, Juan J. Durillo
 */

public class BinaryTournamentSelection<S extends Solution<?>> extends Selection<S> {
  private org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection<S> binaryTournamentSelection ;
  private DataBuffer<Population<S>> buffer;
  private int matingPoolSize;

  public BinaryTournamentSelection(int offspringPopulationSize, Comparator<S> comparator ) {
    super("Binary tournament selection") ;
    this.binaryTournamentSelection =
        new org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection<>(comparator) ;
    buffer = new DataBuffer<>() ;
    this.matingPoolSize = offspringPopulationSize ;
  }

  public BinaryTournamentSelection(int offspringPopulationSize) {
    this(offspringPopulationSize, new DominanceComparator<S>());
  }

  @Override
  public void onNext(Population<S> population) {
    if (!(boolean)population.getAttribute("ALGORITHM_TERMINATED")) {
      Population<S> newPopulation = new Population<>(matingPoolSize);
      while (newPopulation.size() < matingPoolSize) {
        newPopulation.add(binaryTournamentSelection.execute(population));
      }

      population.setAttribute("MATING_POOL", newPopulation);
    }

    getObservable().setChanged();
    getObservable().notifyObservers(population);
  }

  @Override
  public void onFinish(Population<S> population) {

  }

  @Override
  public String getDescription() {
    return "Binary tournament selection object";
  }
}

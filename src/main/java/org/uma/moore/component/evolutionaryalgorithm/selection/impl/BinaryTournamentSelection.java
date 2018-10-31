package org.uma.moore.component.evolutionaryalgorithm.selection.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.moore.Message;
import org.uma.moore.component.evolutionaryalgorithm.selection.Selection;
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
  public void onNext(Message message) {
    if (!(boolean)message.getAttribute("ALGORITHM_TERMINATED")) {
      List<S> newPopulation = new ArrayList<>(matingPoolSize);
      List<S> population = (List<S>) message.getAttribute("POPULATION");
      while (newPopulation.size() < matingPoolSize) {
        newPopulation.add(binaryTournamentSelection.execute(population));
      }

      message.setAttribute("MATING_POOL", newPopulation);
    }

    getObservable().setChanged();
    getObservable().notifyObservers(message);
  }

  @Override
  public void onFinish(Message message) {
  }

  @Override
  public String getDescription() {
    return "Binary tournament selection object";
  }
}

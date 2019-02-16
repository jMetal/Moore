package org.uma.moore.architecture.component.component.evolutionaryalgorithm.selection.impl;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.moore.common.util.Message;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.selection.Selection;

/**
 * Applies a binary tournament selection to return the best solution between two that have been
 * chosen at random from a solution list.
 * Modified by Juanjo in 13.03.2015. A binary tournament is now a TournamenteSelection with 2 
 * tournaments
 *
 * @author Antonio J. Nebro, Juan J. Durillo
 */

public class DifferentialEvolutionSelection extends Selection<DoubleSolution> {
  private org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection differentialEvolutionSelection ;
  private int matingPoolSize;

  public DifferentialEvolutionSelection(int matingPoolSize) {
    super("Differential Evolution Selection") ;
    this.differentialEvolutionSelection =
        new org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection() ;
    this.matingPoolSize = matingPoolSize ;
  }

  @Override
  public void onNext(Message message) {
    if (!(boolean)message.getAttribute("ALGORITHM_TERMINATED")) {
      List<DoubleSolution> population = (List<DoubleSolution>) message.getAttribute("POPULATION");

      List<DoubleSolution> newPopulation = new ArrayList<>(matingPoolSize) ;
      for (int i = 0; i < population.size(); i++) {
        // Obtain parents. Two parameters are required: the population and the
        //                 index of the current individual
        differentialEvolutionSelection.setIndex(i);
        List<DoubleSolution> parents = differentialEvolutionSelection.execute(population);

        newPopulation.addAll(parents);
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
    return "Differential Evolution Selection object";
  }
}

package org.uma.moore.architecture.component.component.evolutionaryalgorithm.variation.impl;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.moore.common.solution.Solution;
import org.uma.moore.common.util.Message;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.variation.Variation;

public class CrossoverAndMutationVariation<S extends Solution<?>> extends Variation<S> {
  private CrossoverOperator<S> crossover ;
  private MutationOperator<S> mutation ;
  private int offspringPopulationSize;

  public CrossoverAndMutationVariation(
          CrossoverOperator<S> crossover,
          MutationOperator<S> mutation,
          int offspringPopulationSize) {
    super("Crossover and mutation variation") ;
    this.crossover = crossover ;
    this.mutation = mutation ;
    this.offspringPopulationSize = offspringPopulationSize;
  }
  
  @Override
  public void onNext(Message message) {
    if (!(boolean)message.getAttribute("ALGORITHM_TERMINATED")) {
      int numberOfParents = crossover.getNumberOfRequiredParents();
      List<S> population = (List<S>) message.getAttribute("POPULATION");

      checkNumberOfParents(population, numberOfParents);

      List<S> offspringPopulation = (List<S>) message.getAttribute("MATING_POOL");

      List<S> offspringList = new ArrayList<>();
      for (int i = 0; i < offspringPopulation.size(); i += numberOfParents) {
        List<S> parents = new ArrayList<>(numberOfParents);
        for (int j = 0; j < numberOfParents; j++) {
          parents.add(offspringPopulation.get(i + j));
        }

        List<S> offspring = crossover.execute(parents);

        for (S solution : offspring) {
          mutation.execute(solution);
          offspringList.add(solution);
          if (offspringList.size() >= offspringPopulationSize)
            break;
        }
      }

      message.setAttribute("MATING_POOL", null);
      message.setAttribute("OFFSPRING_POPULATION", new ArrayList<>(offspringList));
    }
    getObservable().setChanged() ;
    getObservable().notifyObservers(message);
  }


  private void checkNumberOfParents(List<S> population, int numberOfParentsForCrossover) {
    if ((population.size() % numberOfParentsForCrossover) != 0) {
      throw new JMetalException("Wrong number of parents: the remainder of the " +
              "population size (" + population.size() + ") is not divisible by " +
              numberOfParentsForCrossover) ;
    }
  }

  @Override
  public void onFinish(Message message) {
  }

  @Override
  public String getDescription() {
    return "Crossover and mutation variator object";
  }
}

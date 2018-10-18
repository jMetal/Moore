package org.uma.moore.component.evolutionaryalgorithm.variation.impl;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Population;
import org.uma.moore.component.evolutionaryalgorithm.variation.Variation;
import org.uma.moore.observer.Observable;
import org.uma.moore.util.DataBuffer;

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
  public void onNext(Population<S> population) {
    if (!(boolean)population.getAttribute("ALGORITHM_TERMINATED")) {
      int numberOfParents = crossover.getNumberOfRequiredParents();

      checkNumberOfParents(population, numberOfParents);

      Population<S> offspringPopulation = (Population<S>) population.getAttribute("MATING_POOL");

      List<S> offspringList = new Population<S>();
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

      population.setAttribute("MATING_POOL", null);
      population.setAttribute("OFFSPRING_POPULATION", new Population<>(offspringList));
    }
    getObservable().setChanged() ;
    getObservable().notifyObservers(population);
  }


  private void checkNumberOfParents(List<S> population, int numberOfParentsForCrossover) {
    if ((population.size() % numberOfParentsForCrossover) != 0) {
      throw new JMetalException("Wrong number of parents: the remainder of the " +
              "population size (" + population.size() + ") is not divisible by " +
              numberOfParentsForCrossover) ;
    }
  }

  @Override
  public void onFinish(Population<S> population) {
  }

  @Override
  public String getDescription() {
    return "Crossover and mutation variator object";
  }
}

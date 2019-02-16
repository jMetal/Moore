package org.uma.moore.architecture.component.component.evolutionaryalgorithm.variation.impl;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.moore.common.util.Message;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.variation.Variation;

public class DifferentialEvolutionVariation extends Variation<DoubleSolution> {

  private int offspringPopulationSize;
  private DifferentialEvolutionCrossover crossover;

  public DifferentialEvolutionVariation(
      int offspringPopulationSize, DifferentialEvolutionCrossover crossover) {
    super("Differential evoluion variation");
    this.offspringPopulationSize = offspringPopulationSize;
    this.crossover = crossover;
  }

  @Override
  public void onNext(Message message) {
    if (!(boolean) message.getAttribute("ALGORITHM_TERMINATED")) {
      int numberOfParents = crossover.getNumberOfRequiredParents();
      List<DoubleSolution> population = (List<DoubleSolution>) message.getAttribute("POPULATION");
      List<DoubleSolution> matingPool = (List<DoubleSolution>) message.getAttribute("MATING_POOL");

      checkNumberOfParents(matingPool, numberOfParents);

      List<DoubleSolution> offspringPopulation = new ArrayList<>();
      for (int i = 0; i < population.size(); i ++) {
        crossover.setCurrentSolution(population.get(i));
        List<DoubleSolution> parents = new ArrayList<>(3);
        for (int j = 0; j < 3; j++) {
          parents.add(matingPool.get(0));
          matingPool.remove(0);
        }

        List<DoubleSolution> offspring = crossover.execute(parents);

        offspringPopulation.add(offspring.get(0));
      }

      message.setAttribute("MATING_POOL", null);
      message.setAttribute("OFFSPRING_POPULATION", offspringPopulation);
    }
    getObservable().setChanged();
    getObservable().notifyObservers(message);
  }


  private void checkNumberOfParents(List<DoubleSolution> population,
      int numberOfParentsForCrossover) {
    if ((population.size() % numberOfParentsForCrossover) != 0) {
      throw new JMetalException("Wrong number of parents: the remainder of the " +
          "population size (" + population.size() + ") is not divisible by " +
          numberOfParentsForCrossover);
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

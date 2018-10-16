package org.uma.moore.component.common.createinitialpopulation.impl;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Population;
import org.uma.moore.component.common.createinitialpopulation.CreateInitialPopulation;
import org.uma.moore.util.DataBuffer;

public class RandomPopulationCreation<S extends Solution<?>> extends CreateInitialPopulation<S> {

  public RandomPopulationCreation(Problem<S> problem, int populationSize) {
    super(problem, populationSize) ;
  }

  @Override
	public void apply() {
    long initialComputingTime = System.currentTimeMillis() ;

		Population<S> initialPopulation = new Population<>(populationSize);
		for (int i = 0; i < populationSize; i++) {
			S newIndividual = problem.createSolution();
			initialPopulation.add(newIndividual);
		}

    initialPopulation.setAttribute("EVALUATIONS", 0);
    initialPopulation.setAttribute("ALGORITHM_TERMINATED", false);
    initialPopulation.setAttribute("INITIAL_COMPUTING_TIME", initialComputingTime);

    getObservable().setChanged() ;
    getObservable().notifyObservers(initialPopulation);

    try {
      buffer.put(true);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

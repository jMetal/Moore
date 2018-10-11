package org.uma.moore.component.common.createinitialpopulation.impl;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Population;
import org.uma.moore.component.common.createinitialpopulation.CreateInitialPopulation;
import org.uma.moore.util.DataBuffer;

public class RandomPopulationCreation<S extends Solution<?>> extends CreateInitialPopulation<S> {

  private DataBuffer<Boolean> applyHasBeenInvoked ;

  public RandomPopulationCreation(Problem<S> problem, int populationSize) {
    super(problem, populationSize) ;
    applyHasBeenInvoked = new DataBuffer<>();
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

    observable.setChanged() ;
		observable.notifyObservers(initialPopulation);

    try {
      applyHasBeenInvoked.put(true);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      applyHasBeenInvoked.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

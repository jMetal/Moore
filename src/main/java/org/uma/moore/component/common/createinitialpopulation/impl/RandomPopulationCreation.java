package org.uma.moore.component.common.createinitialpopulation.impl;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.moore.Message;
import org.uma.moore.component.common.createinitialpopulation.CreateInitialPopulation;

public class RandomPopulationCreation<S extends Solution<?>> extends CreateInitialPopulation<S> {

  public RandomPopulationCreation(Problem<S> problem, int populationSize) {
    super(problem, populationSize) ;
  }

  @Override
	public void apply() {
    long initialComputingTime = System.currentTimeMillis() ;

		List<S> initialPopulation = new ArrayList<>(populationSize);
		for (int i = 0; i < populationSize; i++) {
			S newIndividual = problem.createSolution();
			initialPopulation.add(newIndividual);
		}

    Message message = new Message("Population") ;
		message.setAttribute("POPULATION", initialPopulation);
    message.setAttribute("EVALUATIONS", 0);
    message.setAttribute("ALGORITHM_TERMINATED", false);
    message.setAttribute("INITIAL_COMPUTING_TIME", initialComputingTime);

    getObservable().setChanged() ;
    getObservable().notifyObservers(message);
  }
}

package org.uma.moore.component.common.evaluation.impl;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.moore.Population;
import org.uma.moore.component.common.evaluation.Evaluation;
import org.uma.moore.observer.Observable;

public class MultithreadedEvaluation<S extends Solution<?>> extends Evaluation<S> {

  public MultithreadedEvaluation(Problem<S> problem, String populationIdentifier) {
    super(problem, populationIdentifier);
  }

  @Override
  public void apply(Population<S> population) {
    if (!(boolean) population.getAttribute("ALGORITHM_TERMINATED")) {
      Population<S> populationToEvaluate;
      if (populationIdentifier.equals("CURRENT_POPULATION")) {
        populationToEvaluate = population;
      } else if (population.getAttribute(populationIdentifier) != null) {
        populationToEvaluate = (Population<S>) population.getAttribute(populationIdentifier);
      } else {
        throw new JMetalException("The population " + populationIdentifier + " does not exist");
      }

      populationToEvaluate.
          parallelStream().
          forEach(solution -> problem.evaluate(solution));
      population.setAttribute("EVALUATIONS",
          (int) population.getAttribute("EVALUATIONS") + populationToEvaluate.size());
    }
    observable.setChanged();
    observable.notifyObservers(population);
  }

  @Override
  public synchronized void update(Observable<Population<S>> observable, Population<S> population) {
    try {
      buffer.put(new Population<>(population));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getDescription() {
    return "Multithreaded evaluation object";
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

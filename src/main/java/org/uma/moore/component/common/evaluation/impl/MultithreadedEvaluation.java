package org.uma.moore.component.common.evaluation.impl;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.moore.Population;
import org.uma.moore.component.common.evaluation.Evaluation;
import org.uma.moore.observer.Observable;

public class MultithreadedEvaluation<S extends Solution<?>> extends Evaluation<S> {

  public MultithreadedEvaluation(Problem<S> problem, String populationIdentifier) {
    super("Multithreaded evaluation", problem, populationIdentifier);
  }

  @Override
  public void onNext(Population<S> population) {
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
    getObservable().setChanged();
    getObservable().notifyObservers(population);
  }

  @Override
  public void onFinish(Population<S> population) {
  }

  @Override
  public String getDescription() {
    return "Multithreaded evaluation object";
  }
}

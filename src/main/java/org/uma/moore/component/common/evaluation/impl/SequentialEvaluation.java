package org.uma.moore.component.common.evaluation.impl;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Population;
import org.uma.moore.component.common.evaluation.Evaluation;
import org.uma.moore.observer.Observable;
import org.uma.moore.util.DataBuffer;

public class SequentialEvaluation<S extends Solution<?>> extends Evaluation<S> {

  private Problem<S> problem;
  private DataBuffer<Population<S>> buffer;

  public SequentialEvaluation(Problem<S> problem) {
    this.problem = problem;
    buffer = new DataBuffer<>();
  }

  @Override
  public void apply(Problem<S> problem, Population<S> population) {
    if (!(boolean)population.getAttribute("ALGORITHM_TERMINATED")) {
      if (population.getAttribute("OFFSPRING_POPULATION") == null) {
        population.
            stream().
            forEach(solution -> problem.evaluate(solution));
        population.setAttribute("EVALUATIONS",
            (int) population.getAttribute("EVALUATIONS") + population.size());

      } else {
        Population<S> offspringPopulation = (Population<S>) population.getAttribute("OFFSPRING_POPULATION");
        offspringPopulation.
            stream().
            forEach(solution -> problem.evaluate(solution));
        population.setAttribute("EVALUATIONS",
            (int) population.getAttribute("EVALUATIONS") + offspringPopulation.size());
      }
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
    return "Sequential evaluation object";
  }

  @Override
  public void run() {
    try {
      while (true) {
        Population<S> population = buffer.get();
        JMetalLogger.logger.info("SEQUENTIAL EVALUATION OBSERVER: GET READY");
        apply(problem, population);

        if ((boolean)population.getAttribute("ALGORITHM_TERMINATED")) {
          break ;
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

package org.uma.moore.component.common.termination.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Population;
import org.uma.moore.component.common.termination.Termination;
import org.uma.moore.observer.Observable;
import org.uma.moore.util.DataBuffer;

public class TerminationByEvaluations<S extends Solution<?>> extends Termination<S> {
  private int totalNumberOfEvaluations ;

  public TerminationByEvaluations(int totalNumberOfEvaluations) {
    super("Termination by evaluations") ;
    this.totalNumberOfEvaluations = totalNumberOfEvaluations ;
  }

  @Override
  public void onNext(Population<S> population) {
    int evaluations =  (int)population.getAttribute("EVALUATIONS") ;

    if (evaluations >= totalNumberOfEvaluations) {
      long finalComputingTime = System.currentTimeMillis();

      JMetalLogger.logger.info("TERMINATION. ALGORITHM TERMINATED");
      population.setAttribute("ALGORITHM_TERMINATED", true);
      population.setAttribute("END_COMPUTING_TIME", finalComputingTime);
      System.out.println("COMPUTING time: " +
          (finalComputingTime - (long) population.getAttribute("INITIAL_COMPUTING_TIME")));
    }

    getObservable().setChanged();
    getObservable().notifyObservers(population);
  }

  @Override
  public void onFinish(Population<S> population) {
  }

  @Override
  public String getDescription() {
    return "Termination Evaluations object";
  }
}


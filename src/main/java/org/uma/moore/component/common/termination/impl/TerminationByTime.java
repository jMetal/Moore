package org.uma.moore.component.common.termination.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Population;
import org.uma.moore.component.common.termination.Termination;
import org.uma.moore.observer.Observable;
import org.uma.moore.util.DataBuffer;

public class TerminationByTime<S extends Solution<?>> extends Termination<S> {
  private long millisecondsToStop ;

  public TerminationByTime(long millisecondsToStop) {
    super("Termination by time") ;
    this.millisecondsToStop = millisecondsToStop ;
  }

  @Override
  public void onNext(Population<S> population) {
    long computingTime = (long) population.getAttribute("INITIAL_COMPUTING_TIME");
    long currentComputingTime = System.currentTimeMillis() - computingTime;

    if (currentComputingTime >= millisecondsToStop) {
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
    return "Termination by time object";
  }
}


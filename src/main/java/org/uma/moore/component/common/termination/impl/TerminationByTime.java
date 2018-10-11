package org.uma.moore.component.common.termination.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Population;
import org.uma.moore.component.common.termination.Termination;
import org.uma.moore.observer.Observable;
import org.uma.moore.util.DataBuffer;

public class TerminationByTime<S extends Solution<?>> extends Termination<S> {
  private long millisecondsToStop ;
  private boolean algorithmTerminated ;
  private DataBuffer<Population<S>> buffer ;

  public TerminationByTime(
      long millisecondsToStop) {
    this.millisecondsToStop = millisecondsToStop ;
    buffer = new DataBuffer<>() ;
  }

  @Override
  public void apply(Population<S> population) {
    int evaluations =  (int)population.getAttribute("EVALUATIONS") ;

    long computingTime =  (long)population.getAttribute("INITIAL_COMPUTING_TIME") ;
    long currentComputingTime = System.currentTimeMillis() - computingTime ;

    if (currentComputingTime >= millisecondsToStop) {
      long finalComputingTime = System.currentTimeMillis() ;

      algorithmTerminated = true ;
      JMetalLogger.logger.info("TERMINATION. ALGORITHM TERMINATED") ;
      population.setAttribute("ALGORITHM_TERMINATED", algorithmTerminated);
      population.setAttribute("END_COMPUTING_TIME", finalComputingTime);
      System.out.println("COMPUTING time: " +
          (finalComputingTime - (long)population.getAttribute("INITIAL_COMPUTING_TIME"))) ;

    } else {
      algorithmTerminated = false ;
      population.setAttribute("ALGORITHM_TERMINATED", algorithmTerminated);
    }

    observable.setChanged() ;
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
    return "Termination Evaluations object";
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


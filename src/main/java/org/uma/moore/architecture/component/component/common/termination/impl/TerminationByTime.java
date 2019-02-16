package org.uma.moore.architecture.component.component.common.termination.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.common.util.Message;
import org.uma.moore.architecture.component.component.common.termination.Termination;

public class TerminationByTime<S extends Solution<?>> extends Termination<S> {
  private long millisecondsToStop ;

  public TerminationByTime(long millisecondsToStop) {
    super("Termination by time") ;
    this.millisecondsToStop = millisecondsToStop ;
  }

  @Override
  public void onNext(Message message) {
    long computingTime = (long) message.getAttribute("INITIAL_COMPUTING_TIME");
    long currentComputingTime = System.currentTimeMillis() - computingTime;

    if (currentComputingTime >= millisecondsToStop) {
      long finalComputingTime = System.currentTimeMillis();

      JMetalLogger.logger.info("TERMINATION. ALGORITHM TERMINATED");
      message.setAttribute("ALGORITHM_TERMINATED", true);
      message.setAttribute("END_COMPUTING_TIME", finalComputingTime);
      System.out.println("COMPUTING time: " +
          (finalComputingTime - (long) message.getAttribute("INITIAL_COMPUTING_TIME")));
    }

    getObservable().setChanged();
    getObservable().notifyObservers(message);
  }

  @Override
  public void onFinish(Message message) {
  }

  @Override
  public String getDescription() {
    return "Termination by time object";
  }
}


package org.uma.moore.architecture.component.component.common.termination.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.common.util.Message;
import org.uma.moore.architecture.component.component.common.termination.Termination;

public class TerminationByEvaluations<S extends Solution<?>> extends Termination<S> {
  private int totalNumberOfEvaluations ;

  public TerminationByEvaluations(int totalNumberOfEvaluations) {
    super("Termination by evaluations") ;
    this.totalNumberOfEvaluations = totalNumberOfEvaluations ;
  }

  @Override
  public void onNext(Message message) {
    int evaluations =  (int)message.getAttribute("EVALUATIONS") ;

    if (evaluations >= totalNumberOfEvaluations) {
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
    return "Termination Evaluations object";
  }
}


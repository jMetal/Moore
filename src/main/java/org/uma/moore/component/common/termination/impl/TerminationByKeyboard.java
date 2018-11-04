package org.uma.moore.component.common.termination.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Message;
import org.uma.moore.ObservableComponent;
import org.uma.moore.component.common.termination.Termination;
import org.uma.moore.component.miscelanea.observable.KeyboardReader;

public class TerminationByKeyboard<S extends Solution<?>> extends Termination<S> {
  private ObservableComponent keyboardReader;
  private Message lastReceivedPopulationMessage = null ;
  private boolean computationFinished = false ;

  public TerminationByKeyboard(int totalNumberOfEvaluations) {
    super("Termination by keyboard") ;
    keyboardReader = new KeyboardReader<>() ;
    keyboardReader.getObservable().register(this);
    keyboardReader.start();
  }

  @Override
  public void onNext(Message message) {
    switch (message.type) {
      case "Population":
        lastReceivedPopulationMessage = new Message(message) ;
        if (computationFinished) {
          long finalComputingTime = System.currentTimeMillis();

          JMetalLogger.logger.info("TERMINATION. ALGORITHM TERMINATED");
          message.setAttribute("ALGORITHM_TERMINATED", true);
          message.setAttribute("END_COMPUTING_TIME", finalComputingTime);
          message.setAttribute("POPULATION", lastReceivedPopulationMessage.getAttribute("POPULATION"));
          message.setAttribute("EVALUATIONS", lastReceivedPopulationMessage.getAttribute("EVALUATIONS"));
          message.setAttribute("INITIAL_COMPUTING_TIME",
              lastReceivedPopulationMessage.getAttribute("INITIAL_COMPUTING_TIME"));
        }
        getObservable().setChanged();
        getObservable().notifyObservers(message);
        break;
      case "KeyboardText":
        computationFinished = true ;
        break ;
      default:
        throw new JMetalException("Message unknown: " + message.type) ;
    }
  }

  @Override
  public void onFinish(Message message) {
  }

  @Override
  public String getDescription() {
    return "Termination Evaluations object";
  }
}


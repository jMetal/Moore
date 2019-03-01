package org.uma.moore.component.common.evaluation.impl;

import java.util.List;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.moore.common.util.Message;
import org.uma.moore.component.common.evaluation.Evaluation;

public class MultithreadedEvaluation<S extends Solution<?>> extends Evaluation<S> {

  public MultithreadedEvaluation(Problem<S> problem, String populationIdentifier) {
    super("Multithreaded evaluation", problem, populationIdentifier);
  }

  @Override
  public void onNext(Message message) {
    if (!(boolean) message.getAttribute("ALGORITHM_TERMINATED")) {
      List<S> populationToEvaluate;

      if (message.getAttribute(populationIdentifier) != null) {
        populationToEvaluate = (List<S>) message.getAttribute(populationIdentifier);
      } else {
        throw new JMetalException("The population " + populationIdentifier + " does not exist");
      }

      populationToEvaluate.
          parallelStream().
          forEach(solution -> problem.evaluate(solution));

      message.setAttribute("EVALUATIONS",
          (int) message.getAttribute("EVALUATIONS") + populationToEvaluate.size());
    }
    getObservable().setChanged();
    getObservable().notifyObservers(message);
  }

  @Override
  public void onFinish(Message message) {
  }

  @Override
  public String getDescription() {
    return "Multithreaded evaluation object";
  }
}

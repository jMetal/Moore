package org.uma.moore.architecture.component.component.common.populationobserver;

import org.uma.moore.architecture.component.util.ObserverComponent;
import org.uma.moore.common.solution.Solution;
import org.uma.moore.common.util.Message;
import org.uma.moore.common.util.MooreLogger;

public class EvaluationObserver<S extends Solution<?>> extends ObserverComponent {
  private int maxEvaluations ;
  private int frequency ;

  public EvaluationObserver(int maxEvaluations, int frequency) {
    super("Evaluation observer") ;
    this.maxEvaluations = maxEvaluations ;
    this.frequency = frequency ;
  }

  @Override
  public void onNext(Message message) {
    int evaluations = (int)message.getAttribute("EVALUATIONS") ;
    if (evaluations % frequency == 0) {
      System.out.println("Evaluations: " + message.getAttribute("EVALUATIONS") + " of "
          + maxEvaluations);
    }
  }

  @Override
  public void onFinish(Message message) {
    MooreLogger.logger.info("Evaluation observed terminated");
  }

  @Override
  public String getDescription() {
    return "Evaluation observer object";
  }

}

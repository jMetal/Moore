package org.uma.moore.comp.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Message;
import org.uma.moore.comp.util.ObserverComponent;

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
    JMetalLogger.logger.info("Evaluation observed terminated");
  }

  @Override
  public String getDescription() {
    return "Evaluation observer object";
  }

}

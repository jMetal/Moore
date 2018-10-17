package org.uma.moore.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.ObserverComponent;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.util.DataBuffer;

public class EvaluationObserver<S extends Solution<?>> extends ObserverComponent<S> {
  private int maxEvaluations ;
  private int frequency ;

  public EvaluationObserver(int maxEvaluations, int frequency) {
    super("Evaluation observer") ;
    this.maxEvaluations = maxEvaluations ;
    this.frequency = frequency ;
  }

  @Override
  public void onNext(Population<S> population) {
    int evaluations = (int)population.getAttribute("EVALUATIONS") ;
    if (evaluations % frequency == 0) {
      System.out.println("Evaluations: " + (int) population.getAttribute("EVALUATIONS") + " of "
          + maxEvaluations);
    }
  }

  @Override
  public void onFinish(Population<S> population) {
    JMetalLogger.logger.info("Evaluation observed terminated");
  }

  @Override
  public String getDescription() {
    return "Evaluation observer object";
  }

}

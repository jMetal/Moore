package org.uma.moore.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.util.DataBuffer;

public class EvaluationObserver<S extends Solution<?>> extends Thread implements
    Observer<Population<S>> {

  private DataBuffer<Population<S>> buffer ;
  private int maxEvaluations ;
  private int frequency ;

  public EvaluationObserver(int maxEvaluations, int frequency) {
    buffer = new DataBuffer<>() ;
    this.maxEvaluations = maxEvaluations ;
    this.frequency = frequency ;
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
  public synchronized String getDescription() {
    return "Evaluation observer object";
  }

  @Override
  public void run() {
    boolean finishCondition = false ;

    Population<S> population = null ;
    while (!finishCondition) {
      try {
        population = buffer.get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if ((boolean) population.getAttribute("ALGORITHM_TERMINATED")) {
        finishCondition = true  ;
      } else {
        int evaluations = (int)population.getAttribute("EVALUATIONS") ;
        if (evaluations % frequency == 0) {
          System.out.println("Evaluations: " + (int) population.getAttribute("EVALUATIONS") + " of "
              + maxEvaluations);
        }
      }
    }
  }
}

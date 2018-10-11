package org.uma.moore.component.common.evaluation;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.moore.Component;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.observer.impl.DefaultObservable;

public abstract class Evaluation<S extends Solution<?>>
    extends Thread
    implements Observer<Population<S>>, Component<Population<S>> {
  protected Observable<Population<S>> observable ;

  public Evaluation() {
    this.observable = new DefaultObservable<>("evaluationObservable");
  }

  abstract public void apply(Problem<S> problem, Population<S> population) ;

  @Override
  public Observable<Population<S>> getObservable() {
    return observable;
  }
}

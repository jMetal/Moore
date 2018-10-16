package org.uma.moore.component.common.evaluation;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.moore.Component;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.observer.impl.DefaultObservable;
import org.uma.moore.util.DataBuffer;

public abstract class Evaluation<S extends Solution<?>>
    extends Thread
    implements Observer<Population<S>>, Component<Population<S>> {
  protected Observable<Population<S>> observable ;
  protected Problem<S> problem ;
  protected String populationIdentifier ;
  protected DataBuffer<Population<S>> buffer ;

  public Evaluation(Problem<S> problem, String populationIdentifier) {
    this.observable = new DefaultObservable<>("evaluationObservable");
    this.problem = problem ;
    this.populationIdentifier = populationIdentifier ;
    buffer = new DataBuffer<>();
  }

  abstract public void apply(Population<S> population) ;

  @Override
  public Observable<Population<S>> getObservable() {
    return observable;
  }
}

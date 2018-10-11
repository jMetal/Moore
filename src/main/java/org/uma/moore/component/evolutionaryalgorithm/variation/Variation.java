package org.uma.moore.component.evolutionaryalgorithm.variation;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.Component;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.observer.impl.DefaultObservable;

public abstract class Variation<S extends Solution<?>>
    extends Thread
    implements Observer<Population<S>>, Component<Population<S>> {
  protected Observable<Population<S>> observable ;

  public Variation() {
    this.observable = new DefaultObservable<>("variationObservable") ;
  }

	abstract public void apply(Population<S> population) ;

  @Override
  public Observable<Population<S>> getObservable() {
    return observable;
  }
}

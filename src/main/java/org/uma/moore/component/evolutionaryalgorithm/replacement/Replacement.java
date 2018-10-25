package org.uma.moore.component.evolutionaryalgorithm.replacement;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.Component;
import org.uma.moore.ObserverObservableComponent;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.observer.impl.DefaultObservable;

public abstract class Replacement<S extends Solution<?>>
    extends ObserverObservableComponent<S> {

  public Replacement(String componentName) {
    super(componentName) ;
  }
}

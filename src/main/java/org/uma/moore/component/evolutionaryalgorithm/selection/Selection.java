package org.uma.moore.component.evolutionaryalgorithm.selection;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.Component;
import org.uma.moore.ObserverObservableComponent;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.observer.impl.DefaultObservable;

public abstract class Selection<S extends Solution<?>>
    extends ObserverObservableComponent<S> {

  public Selection(String componentName) {
    super(componentName) ;
  }
}

package org.uma.moore.component.evolutionaryalgorithm.variation;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.ObserverObservableComponent;

public abstract class Variation<S extends Solution<?>>
    extends ObserverObservableComponent<S> {

  public Variation(String componentName) {
    super(componentName) ;
  }
}

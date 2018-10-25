package org.uma.moore.component.evolutionaryalgorithm.selection;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.ObserverObservableComponent;

public abstract class Selection<S extends Solution<?>>
    extends ObserverObservableComponent<S> {

  public Selection(String componentName) {
    super(componentName) ;
  }
}

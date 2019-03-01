package org.uma.moore.architecture.component.component.evolutionaryalgorithm.variation;

import org.uma.moore.architecture.component.util.ObserverObservableComponent;
import org.uma.moore.common.solution.Solution;

public abstract class Variation<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Variation(String componentName) {
    super(componentName) ;
  }
}

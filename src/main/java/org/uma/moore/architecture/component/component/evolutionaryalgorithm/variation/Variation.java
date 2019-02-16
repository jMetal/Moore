package org.uma.moore.architecture.component.component.evolutionaryalgorithm.variation;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.architecture.component.util.ObserverObservableComponent;

public abstract class Variation<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Variation(String componentName) {
    super(componentName) ;
  }
}

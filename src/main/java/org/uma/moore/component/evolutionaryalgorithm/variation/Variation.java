package org.uma.moore.component.evolutionaryalgorithm.variation;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.common.util.ObserverObservableComponent;

public abstract class Variation<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Variation(String componentName) {
    super(componentName) ;
  }
}

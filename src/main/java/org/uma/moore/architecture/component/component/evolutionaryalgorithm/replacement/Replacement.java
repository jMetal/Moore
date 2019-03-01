package org.uma.moore.architecture.component.component.evolutionaryalgorithm.replacement;

import org.uma.moore.architecture.component.util.ObserverObservableComponent;
import org.uma.moore.common.solution.Solution;

public abstract class Replacement<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Replacement(String componentName) {
    super(componentName) ;
  }
}

package org.uma.moore.architecture.component.component.evolutionaryalgorithm.replacement;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.architecture.component.util.ObserverObservableComponent;

public abstract class Replacement<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Replacement(String componentName) {
    super(componentName) ;
  }
}

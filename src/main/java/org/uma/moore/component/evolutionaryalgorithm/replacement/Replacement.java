package org.uma.moore.component.evolutionaryalgorithm.replacement;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.common.util.ObserverObservableComponent;

public abstract class Replacement<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Replacement(String componentName) {
    super(componentName) ;
  }
}

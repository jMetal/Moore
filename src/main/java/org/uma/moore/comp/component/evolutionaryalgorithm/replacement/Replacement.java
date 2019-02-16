package org.uma.moore.comp.component.evolutionaryalgorithm.replacement;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.comp.util.ObserverObservableComponent;

public abstract class Replacement<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Replacement(String componentName) {
    super(componentName) ;
  }
}

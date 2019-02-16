package org.uma.moore.architecture.component.component.evolutionaryalgorithm.selection;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.architecture.component.util.ObserverObservableComponent;

public abstract class Selection<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Selection(String componentName) {
    super(componentName) ;
  }
}

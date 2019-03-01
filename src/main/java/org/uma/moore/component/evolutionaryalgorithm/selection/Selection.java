package org.uma.moore.component.evolutionaryalgorithm.selection;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.common.util.ObserverObservableComponent;

public abstract class Selection<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Selection(String componentName) {
    super(componentName) ;
  }
}

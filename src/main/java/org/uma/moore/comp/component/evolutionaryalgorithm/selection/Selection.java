package org.uma.moore.comp.component.evolutionaryalgorithm.selection;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.comp.util.ObserverObservableComponent;

public abstract class Selection<S extends Solution<?>>
    extends ObserverObservableComponent {

  public Selection(String componentName) {
    super(componentName) ;
  }
}

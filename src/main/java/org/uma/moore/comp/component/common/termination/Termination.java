package org.uma.moore.comp.component.common.termination;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.comp.util.ObserverObservableComponent;

public abstract class Termination<S extends Solution<?>>
    extends ObserverObservableComponent<S> {

  public Termination(String componentName) {
    super(componentName);
  }
}
package org.uma.moore.architecture.component.component.common.termination;

import org.uma.moore.architecture.component.util.ObserverObservableComponent;
import org.uma.moore.common.solution.Solution;

public abstract class Termination<S extends Solution<?>>
    extends ObserverObservableComponent<S> {

  public Termination(String componentName) {
    super(componentName);
  }
}
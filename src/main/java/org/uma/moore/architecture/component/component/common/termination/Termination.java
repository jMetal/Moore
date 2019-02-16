package org.uma.moore.architecture.component.component.common.termination;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.architecture.component.util.ObserverObservableComponent;

public abstract class Termination<S extends Solution<?>>
    extends ObserverObservableComponent<S> {

  public Termination(String componentName) {
    super(componentName);
  }
}
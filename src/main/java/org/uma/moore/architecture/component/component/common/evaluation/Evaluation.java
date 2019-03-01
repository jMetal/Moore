package org.uma.moore.architecture.component.component.common.evaluation;

import org.uma.moore.architecture.component.util.ObserverObservableComponent;
import org.uma.moore.common.problem.Problem;
import org.uma.moore.common.solution.Solution;

public abstract class Evaluation<S extends Solution<?>>
    extends ObserverObservableComponent<S> {
  protected Problem<S> problem ;
  protected String populationIdentifier ;

  public Evaluation(String componentName, Problem<S> problem, String populationIdentifier) {
    super(componentName);
    this.problem = problem ;
    this.populationIdentifier = populationIdentifier ;
  }
}

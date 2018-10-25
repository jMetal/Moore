package org.uma.moore.component.common.evaluation;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.moore.ObserverObservableComponent;

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

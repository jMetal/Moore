package org.uma.moore.architecture.component.component.common.createinitialpopulation;

import org.uma.moore.architecture.component.util.ObservableComponent;
import org.uma.moore.common.problem.Problem;
import org.uma.moore.common.solution.Solution;

public abstract class CreateInitialPopulation<S extends Solution<?>>
    extends ObservableComponent {

  protected Problem<S> problem ;
  protected int populationSize ;

  public CreateInitialPopulation(Problem<S> problem, int populationSize) {
    super("initialPopulationObservable") ;
    this.problem = problem ;
    this.populationSize = populationSize ;
  }
}

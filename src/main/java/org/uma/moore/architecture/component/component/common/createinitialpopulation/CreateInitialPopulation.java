package org.uma.moore.architecture.component.component.common.createinitialpopulation;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.moore.architecture.component.util.ObservableComponent;

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

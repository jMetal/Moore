package org.uma.moore.component.common.createinitialpopulation;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.moore.Component;
import org.uma.moore.ObservableComponent;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.impl.DefaultObservable;

public abstract class CreateInitialPopulation<S extends Solution<?>>
    extends ObservableComponent<Population<S>> {
  protected Observable<Population<S>> observable ;

  protected Problem<S> problem ;
  protected int populationSize ;

  public CreateInitialPopulation(Problem<S> problem, int populationSize) {
    super("initialPopulationObservable") ;
    this.problem = problem ;
    this.populationSize = populationSize ;
  }
}

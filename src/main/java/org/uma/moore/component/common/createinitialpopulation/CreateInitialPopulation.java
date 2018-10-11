package org.uma.moore.component.common.createinitialpopulation;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.moore.Component;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.impl.DefaultObservable;

public abstract class CreateInitialPopulation<S extends Solution<?>>
    extends Thread
    implements Component<Population<S>> {
  protected Observable<Population<S>> observable ;

  protected Problem<S> problem ;
  protected int populationSize ;

  public CreateInitialPopulation(Problem<S> problem, int populationSize) {
    this.problem = problem ;
    this.populationSize = populationSize ;
    this.observable = new DefaultObservable<>("initialPopulationObservable")  ;
  }

  public abstract void apply() ;

  @Override
  public Observable<Population<S>> getObservable() {
    return observable;
  }
}

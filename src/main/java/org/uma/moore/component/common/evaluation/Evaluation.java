package org.uma.moore.component.common.evaluation;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.moore.Component;
import org.uma.moore.ObserverObservableComponent;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.observer.impl.DefaultObservable;
import org.uma.moore.util.DataBuffer;

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

package org.uma.moore;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.impl.DefaultObservable;

public abstract class ObserverObservableComponent<S extends Solution<?>> extends
    ObserverComponent<S> {
  private Observable<Population <S>> observable ;

  public ObserverObservableComponent(Observable<Population<S>> observable) {
    super(observable.getName()) ;
    this.observable = observable ;
  }

  public ObserverObservableComponent(String componentName) {
    super(componentName) ;
    observable = new DefaultObservable<>(componentName) ;
  }

  public abstract void onNext(Population<S> population) ;

  public abstract void onFinish(Population<S> population);

  public Observable<Population<S>> getObservable() {
    return this.observable ;
  }

  @Override
  public void update(Observable<Population<S>> observable, Population<S> population) {
    try {
      buffer.put(new Population<>(population));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

package org.uma.moore.common.util;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.common.observable.Observable;
import org.uma.moore.common.observable.impl.DefaultObservable;

public abstract class ObserverObservableComponent<S extends Solution<?>> extends
    ObserverComponent {
  private Observable observable ;

  public ObserverObservableComponent(Observable observable) {
    super(observable.getName()) ;
    this.observable = observable ;
  }

  public ObserverObservableComponent(String componentName) {
    super(componentName) ;
    observable = new DefaultObservable(componentName) ;
  }

  public abstract void onNext(Message message) ;

  public abstract void onFinish(Message message);

  public Observable getObservable() {
    return this.observable ;
  }

  @Override
  public void update(Observable observable, Message message) {
    try {
      buffer.put(new Message(message));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

package org.uma.moore;

import org.uma.moore.observer.Observable;
import org.uma.moore.observer.impl.DefaultObservable;

public abstract class ObservableComponent extends Thread {
  private Observable observable ;

  public ObservableComponent(Observable observable) {
    this.observable = observable ;
  }

  public ObservableComponent(String componentName) {
    observable = new DefaultObservable(componentName) ;
  }

  public Observable getObservable() {
    return observable ;
  }

  public abstract void apply() ;

  @Override
  public void run() {
    apply();
  }
}

package org.uma.moore.comp.util;

import org.uma.moore.common.observable.Observable;
import org.uma.moore.common.observable.impl.DefaultObservable;

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

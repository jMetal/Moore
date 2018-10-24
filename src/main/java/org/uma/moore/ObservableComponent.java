package org.uma.moore;

import org.uma.moore.observer.Observable;
import org.uma.moore.observer.impl.DefaultObservable;
import org.uma.moore.util.DataBuffer;

public abstract class ObservableComponent<S> extends Thread {
  private Observable<S> observable ;

  public ObservableComponent(Observable<S> observable) {
    this.observable = observable ;
  }

  public ObservableComponent(String componentName) {
    observable = new DefaultObservable<>(componentName) ;
  }

  public Observable<S> getObservable() {
    return observable ;
  }

  public abstract void apply() ;

  @Override
  public void run() {
    apply();
  }
}

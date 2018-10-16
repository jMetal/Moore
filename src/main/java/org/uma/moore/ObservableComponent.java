package org.uma.moore;

import org.uma.moore.observer.Observable;
import org.uma.moore.observer.impl.DefaultObservable;
import org.uma.moore.util.DataBuffer;

public abstract class ObservableComponent<S> extends Thread {
  private Observable<S> observable ;
  protected DataBuffer<Boolean> buffer ;

  public ObservableComponent(String componentName) {
    observable = new DefaultObservable<>(componentName) ;
    buffer = new DataBuffer<>();
  }

  public Observable<S> getObservable() {
    return observable ;
  }

  public abstract void apply() ;

  @Override
  public void run() {
    try {
      buffer.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

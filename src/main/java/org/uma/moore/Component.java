package org.uma.moore;

import org.uma.moore.observer.Observable;

public interface Component<S> {
  Observable<S> getObservable() ;
}

package org.uma.moore.util;

public class DataBuffer<S> {

  private S data ;
  private boolean thereIsData = false ;

  public synchronized void put(S newData) throws InterruptedException {
    while (thereIsData) {
      this.wait();
    }
    data = newData;
    thereIsData = true ;
    this.notifyAll();
  }

  public synchronized S get() throws InterruptedException {
    while (!thereIsData) {
      wait();
    }

    thereIsData = false ;
    this.notifyAll();

    return data ;
  }
}

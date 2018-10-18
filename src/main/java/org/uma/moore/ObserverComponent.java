package org.uma.moore;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.util.DataBuffer;

public abstract class ObserverComponent<S extends Solution<?>> extends Thread implements
    Observer<Population<S>> {

  protected DataBuffer<Population<S>> buffer;
  public String componentName;

  public ObserverComponent(String componentName) {
    this.componentName = componentName;
    buffer = new DataBuffer<Population<S>>();
  }

  public abstract void onNext(Population<S> population) ;

  public abstract void onFinish(Population<S> population);

  @Override
  public void update(Observable<Population<S>> observable, Population<S> population) {
    try {
      buffer.put(new Population<>(population));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public synchronized void run() {
    boolean finishCondition = false;

    Population<S> population = null;

    while (!finishCondition) {
      try {
        population = buffer.get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if ((boolean) population.getAttribute("ALGORITHM_TERMINATED")) {
        this.onFinish(population);
        finishCondition = true;
      } else {
        this.onNext(population);
      }
    }
  }
}

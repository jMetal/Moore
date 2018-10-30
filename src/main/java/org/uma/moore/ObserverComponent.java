package org.uma.moore;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.util.DataBuffer;

public abstract class ObserverComponent<S extends Solution<?>> extends Thread implements
    Observer {

  protected DataBuffer<Message> buffer;
  public String componentName;

  public ObserverComponent(String componentName) {
    this.componentName = componentName;
    buffer = new DataBuffer<Message>();
  }

  public abstract void onNext(Message message) ;

  public abstract void onFinish(Message message);

  @Override
  public void update(Observable observable, Message message) {
    try {
      buffer.put(new Message(message));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public synchronized void run() {
    boolean finishCondition = false;

    Message message = null ;
    while (!finishCondition) {
      try {
        message = buffer.get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if ((boolean) message.getAttribute("ALGORITHM_TERMINATED")) {
        this.onFinish(message);
        finishCondition = true;
      } else {
        this.onNext(message);
      }
    }
  }
}

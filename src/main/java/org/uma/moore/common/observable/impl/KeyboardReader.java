package org.uma.moore.common.observable.impl;

import java.util.Scanner;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.common.util.Message;
import org.uma.moore.common.util.ObservableComponent;

public class KeyboardReader<S extends Solution<?>>
    extends ObservableComponent {

  public KeyboardReader() {
    super("Keyboard reader") ;
  }

  @Override
  public void apply() {
    System.out.println("Press any key and hit return") ;
    Scanner scanner = new Scanner(System.in);

    String text = scanner.nextLine() ;

    Message message = new Message("KeyboardText") ;
    message.setAttribute("text", text);
    System.out.println("Keyboard Reader: " + text) ;
    message.setAttribute("ALGORITHM_TERMINATED", false);

    getObservable().setChanged() ;
    getObservable().notifyObservers(message);
  }
}

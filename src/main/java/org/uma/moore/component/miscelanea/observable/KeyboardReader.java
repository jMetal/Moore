package org.uma.moore.component.miscelanea.observable;

import java.util.Scanner;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.moore.Message;
import org.uma.moore.ObservableComponent;

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

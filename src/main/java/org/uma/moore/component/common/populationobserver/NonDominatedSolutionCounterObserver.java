package org.uma.moore.component.common.populationobserver;

import java.util.List;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;
import org.uma.moore.common.util.Message;
import org.uma.moore.common.util.ObserverComponent;

public class NonDominatedSolutionCounterObserver<S extends Solution<?>> extends
    ObserverComponent {

  public NonDominatedSolutionCounterObserver() {
    super("Non-dominated solution counter observer") ;
  }

  @Override
  public void onNext(Message message) {
    Ranking<S> ranking = new DominanceRanking<>(new DominanceComparator<>()) ;
    List<S> population = (List<S>) message.getAttribute("POPULATION");
    ranking.computeRanking(population) ;
    int numberOfNonDominatedSolutions ;
    numberOfNonDominatedSolutions = ranking.getSubfront(0).size() ;
    int evaluations = (int) message.getAttribute("EVALUATIONS") ;
    System.out.println("Evaluations: " + evaluations +
        ". Non dominated solutions: " + numberOfNonDominatedSolutions) ;
  }

  @Override
  public void onFinish(Message message) {
  }

  @Override
  public String getDescription() {
    return "NonDominatedSolutionCounterObserver observer object";
  }
}
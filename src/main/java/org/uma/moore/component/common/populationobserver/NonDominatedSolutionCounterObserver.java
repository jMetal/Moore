package org.uma.moore.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;
import org.uma.moore.Message;
import org.uma.moore.ObserverComponent;
import org.uma.moore.Population;

public class NonDominatedSolutionCounterObserver<S extends Solution<?>> extends
    ObserverComponent {

  public NonDominatedSolutionCounterObserver() {
    super("Non-dominated solution counter observer") ;
  }

  @Override
  public void onNext(Message message) {
    Ranking<S> ranking = new DominanceRanking<>(new DominanceComparator<>()) ;
    Population<S> population = (Population<S>) message.getAttribute("POPULATION");
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

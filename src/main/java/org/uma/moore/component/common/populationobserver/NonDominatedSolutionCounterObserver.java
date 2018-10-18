package org.uma.moore.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;
import org.uma.moore.ObserverComponent;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.util.DataBuffer;

public class NonDominatedSolutionCounterObserver<S extends Solution<?>> extends
    ObserverComponent<S> {

  public NonDominatedSolutionCounterObserver() {
    super("Non-dominated solution counter observer") ;
  }

  @Override
  public void onNext(Population<S> population) {
    Ranking<S> ranking = new DominanceRanking<>(new DominanceComparator<>()) ;
    ranking.computeRanking(population) ;
    int numberOfNonDominatedSolutions ;
    numberOfNonDominatedSolutions = ranking.getSubfront(0).size() ;
    int evaluations = (int) population.getAttribute("EVALUATIONS") ;
    System.out.println("Evaluations: " + evaluations +
        ". Non dominated solutions: " + numberOfNonDominatedSolutions) ;
  }

  @Override
  public void onFinish(Population<S> population) {
  }

  @Override
  public synchronized String getDescription() {
    return "NonDominatedSolutionCounterObserver observer object";
  }
}

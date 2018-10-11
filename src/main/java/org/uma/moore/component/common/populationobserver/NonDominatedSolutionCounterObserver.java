package org.uma.moore.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.util.DataBuffer;

public class NonDominatedSolutionCounterObserver<S extends Solution<?>> extends Thread implements
    Observer<Population<S>> {

  private DataBuffer<Population<S>> buffer ;

  public NonDominatedSolutionCounterObserver() {
    buffer = new DataBuffer<>() ;
  }

  @Override
  public synchronized void update(Observable<Population<S>> observable, Population<S> population) {
    try {
      buffer.put(new Population<>(population));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public synchronized String getDescription() {
    return "NonDominatedSolutionCounterObserver observer object";
  }

  @Override
  public void run() {
    boolean finishCondition = false ;

    Population<S> population = null ;
    while (!finishCondition) {
      try {
        population = buffer.get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if ((boolean) population.getAttribute("ALGORITHM_TERMINATED")) {
        finishCondition = true  ;
      } else {
        Ranking<S> ranking = new DominanceRanking<>(new DominanceComparator<>()) ;
        ranking.computeRanking(population) ;
      }
    }
  }
}

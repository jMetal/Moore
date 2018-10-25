package org.uma.moore.component.evolutionaryalgorithm.replacement.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;
import org.uma.moore.Population;
import org.uma.moore.component.evolutionaryalgorithm.replacement.Replacement;

public class RankingAndHypervolumeContributionReplacement<S extends Solution<?>> extends
    Replacement<S> {

  private Comparator<S> dominanceComparator ;
  private Hypervolume<S> hypervolume;

  /**
   * Constructor
   */
  public RankingAndHypervolumeContributionReplacement(Comparator<S> dominanceComparator) {
    super("Ranking and hypervolume contribution replacement") ;

    hypervolume = new PISAHypervolume<>() ;
  }

  /**
   * Constructor
   */
  public RankingAndHypervolumeContributionReplacement() {
    this(new DominanceComparator<>()) ;
  }

  @Override
  public void onNext(Population<S> population) {
    if (!(boolean)population.getAttribute("ALGORITHM_TERMINATED")) {

      List<S> jointPopulation = new ArrayList<>();
      jointPopulation.addAll(population);
      jointPopulation.addAll((Collection<? extends S>) population.getAttribute("OFFSPRING_POPULATION"));

      Ranking<S> ranking = new DominanceRanking<S>(dominanceComparator);
      ranking.computeRanking(jointPopulation);

      List<S> lastSubfront = ranking.getSubfront(ranking.getNumberOfSubfronts()-1) ;

      lastSubfront = hypervolume.computeHypervolumeContribution(lastSubfront, jointPopulation) ;

      List<S> resultPopulation = new ArrayList<>() ;
      for (int i = 0; i < ranking.getNumberOfSubfronts()-1; i++) {
        for (S solution : ranking.getSubfront(i)) {
          resultPopulation.add(solution);
        }
      }

      for (int i = 0; i < lastSubfront.size()-1; i++) {
        resultPopulation.add(lastSubfront.get(i)) ;
      }

      Population<S> newPopulation = new Population<S>(
          resultPopulation);
      newPopulation.setAttributes(population.getAttributes());

      population.setAttribute("OFFSPRING_POPULATION", null);
      newPopulation.setAttributes(population.getAttributes());

      getObservable().setChanged();
      getObservable().notifyObservers(newPopulation);
    } else {
      getObservable().setChanged();
      getObservable().notifyObservers(population);
    }
  }

  @Override
  public void onFinish(Population<S> population) {

  }
  @Override
  public String getDescription() {
    return null;
  }
}

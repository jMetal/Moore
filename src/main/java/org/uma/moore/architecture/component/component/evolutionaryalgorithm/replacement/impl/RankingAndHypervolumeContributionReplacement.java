package org.uma.moore.architecture.component.component.evolutionaryalgorithm.replacement.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.moore.common.solution.Solution;
import org.uma.moore.common.util.Message;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.replacement.Replacement;
import org.uma.moore.common.util.comparator.DominanceComparator;
import org.uma.moore.common.util.ranking.Ranking;
import org.uma.moore.common.util.ranking.impl.DominanceRanking;

public class RankingAndHypervolumeContributionReplacement<S extends Solution<?>> extends
    Replacement<S> {

  private Comparator<S> dominanceComparator ;
  private Hypervolume<S> hypervolume;

  /**
   * Constructor
   */
  public RankingAndHypervolumeContributionReplacement(Comparator<S> dominanceComparator) {
    super("Ranking and hypervolume contribution replacement") ;
    this.dominanceComparator = dominanceComparator ;
    hypervolume = new PISAHypervolume<>() ;
  }

  /**
   * Constructor
   */
  public RankingAndHypervolumeContributionReplacement() {
    this(new DominanceComparator<>()) ;
  }

  @Override
  public void onNext(Message message) {
    if (!(boolean)message.getAttribute("ALGORITHM_TERMINATED")) {
      List<S> population = (List<S>) message.getAttribute("POPULATION");

      List<S> jointPopulation = new ArrayList<>();
      jointPopulation.addAll(population);
      jointPopulation.addAll((Collection<? extends S>) message.getAttribute("OFFSPRING_POPULATION"));

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

      List<S> newPopulation = new ArrayList<>(resultPopulation);

      message.setAttribute("OFFSPRING_POPULATION", null);
      message.setAttribute("POPULATION", newPopulation);

      getObservable().setChanged();
      getObservable().notifyObservers(message);
    }
  }

  @Override
  public void onFinish(Message message) {

  }
  @Override
  public String getDescription() {
    return null;
  }
}

package org.uma.moore.comp.algorithm;

import org.uma.jmetal.solution.Solution;
import org.uma.moore.comp.component.common.createinitialpopulation.CreateInitialPopulation;
import org.uma.moore.comp.component.common.evaluation.Evaluation;
import org.uma.moore.comp.component.common.termination.Termination;
import org.uma.moore.comp.component.evolutionaryalgorithm.replacement.Replacement;
import org.uma.moore.comp.component.evolutionaryalgorithm.selection.Selection;
import org.uma.moore.comp.component.evolutionaryalgorithm.variation.Variation;

public class EvolutionaryAlgorithm<S extends Solution<?>> {

  private Evaluation<S> initialPopulationEvaluation;
  private Evaluation<S> offspringPopulationEvaluation;
  private CreateInitialPopulation<S> createInitialPopulation;
  private Termination<S> termination;
  private Selection<S> selection;
  private Variation<S> variation;
  private Replacement<S> replacement;

  public EvolutionaryAlgorithm(
      CreateInitialPopulation<S> createInitialPopulation,
      Evaluation<S> initialPopulationEvaluation,
      Evaluation<S> offspringPopulationEvaluation,
      Termination<S> termination,
      Selection<S> selection,
      Variation<S> variation,
      Replacement<S> replacement) {

    this.createInitialPopulation = createInitialPopulation ;
    this.initialPopulationEvaluation = initialPopulationEvaluation ;
    this.offspringPopulationEvaluation = offspringPopulationEvaluation ;
    this.termination = termination ;
    this.replacement = replacement ;
    this.variation = variation ;
    this.selection = selection ;

    createInitialPopulation.getObservable().register(initialPopulationEvaluation);
    initialPopulationEvaluation.getObservable().register(termination);
    termination.getObservable().register(selection);
    selection.getObservable().register(variation);
    variation.getObservable().register(offspringPopulationEvaluation);
    offspringPopulationEvaluation.getObservable().register(replacement);
    replacement.getObservable().register(termination);
  }

  public void run() {
    createInitialPopulation.start();
    initialPopulationEvaluation.start();
    termination.start();
    selection.start();
    variation.start();
    offspringPopulationEvaluation.start();
    replacement.start();
  }

  public Evaluation<S> getInitialPopulationEvaluation() {
    return initialPopulationEvaluation;
  }

  public Evaluation<S> getOffspringPopulationEvaluation() {
    return offspringPopulationEvaluation;
  }

  public CreateInitialPopulation<S> getCreateInitialPopulation() {
    return createInitialPopulation;
  }

  public Termination<S> getTermination() {
    return termination;
  }

  public Selection<S> getSelection() {
    return selection;
  }

  public Variation<S> getVariation() {
    return variation;
  }

  public Replacement<S> getReplacement() {
    return replacement;
  }
}

package org.uma.moore.architecture.component.algorithm.impl;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT4;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.architecture.component.algorithm.EvolutionaryAlgorithm;
import org.uma.moore.architecture.component.component.common.createinitialpopulation.impl.RandomPopulationCreation;
import org.uma.moore.architecture.component.component.common.evaluation.impl.SequentialEvaluation;
import org.uma.moore.architecture.component.component.common.populationobserver.EvaluationObserver;
import org.uma.moore.architecture.component.component.common.populationobserver.PopulationToFilesWriterObserver;
import org.uma.moore.architecture.component.component.common.termination.impl.TerminationByTime;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.replacement.impl.RankingAndHypervolumeContributionReplacement;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.selection.impl.BinaryTournamentSelection;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.variation.impl.CrossoverAndMutationVariation;
import org.uma.moore.common.operator.crossover.imp.SBXCrossover;
import org.uma.moore.common.operator.mutation.impl.PolynomialMutation;
import org.uma.moore.common.problem.DoubleProblem;
import org.uma.moore.common.solution.impl.DoubleSolution;
import org.uma.moore.common.util.comparator.DominanceComparator;

public class SMSEMOA {
  public static void main(String[] args) {
    DoubleProblem problem = new ZDT4();
    int populationSize = 100;
    int offspringPopulationSize = 1;
    int matingPoolSize = 2;
    int totalNumberOfEvaluations = 25000;

    JMetalLogger.logger.setUseParentHandlers(false);

    double crossoverProbability = 0.9;
    double crossoverDistributionIndex = 20.0;
    CrossoverOperator<DoubleSolution> crossover =
        new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

    double mutationProbability = 1.0 / problem.getNumberOfVariables();
    double mutationDistributionIndex = 20.0;
    MutationOperator<DoubleSolution> mutation =
        new PolynomialMutation(mutationProbability, mutationDistributionIndex);

    PopulationToFilesWriterObserver<DoubleSolution> populationObserver;
    populationObserver = new PopulationToFilesWriterObserver<>();
    EvolutionaryAlgorithm<DoubleSolution> algorithm = new EvolutionaryAlgorithm<>(
        new RandomPopulationCreation<>(problem, populationSize),
        new SequentialEvaluation<>(problem, "POPULATION"),
        new SequentialEvaluation<>(problem, "OFFSPRING_POPULATION"),
        new TerminationByTime<>(20000),
        new BinaryTournamentSelection<>(matingPoolSize, new DominanceComparator<>()),
        new CrossoverAndMutationVariation<>(crossover, mutation, offspringPopulationSize),
        new RankingAndHypervolumeContributionReplacement<>(new DominanceComparator<>()));

    algorithm.getTermination().getObservable().register(populationObserver);
    populationObserver.start();

    EvaluationObserver<DoubleSolution> evaluationObserver = new EvaluationObserver(totalNumberOfEvaluations, 100) ;
    algorithm.getReplacement().getObservable().register(evaluationObserver);
    evaluationObserver.start();

    algorithm.run();
  }
}
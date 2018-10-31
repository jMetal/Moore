package org.uma.moore.algorithm.impl;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT1;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.moore.algorithm.EvolutionaryAlgorithm;
import org.uma.moore.component.common.createinitialpopulation.impl.RandomPopulationCreation;
import org.uma.moore.component.common.evaluation.impl.SequentialEvaluation;
import org.uma.moore.component.common.populationobserver.EvaluationObserver;
import org.uma.moore.component.common.populationobserver.PopulationToFilesWriterObserver;
import org.uma.moore.component.common.populationobserver.RealTimeChartObserver;
import org.uma.moore.component.common.termination.impl.TerminationByEvaluations;
import org.uma.moore.component.evolutionaryalgorithm.replacement.impl.RankingAndCrowdingReplacement;
import org.uma.moore.component.evolutionaryalgorithm.selection.impl.BinaryTournamentSelection;
import org.uma.moore.component.evolutionaryalgorithm.variation.impl.CrossoverAndMutationVariation;

public class NSGAII {
  public static void main (String[] args) {
    DoubleProblem problem = new ZDT1() ;
    int populationSize = 100 ;
    int offspringPopulationSize = 100 ;
    int matingPoolSize = 100 ;
    int maxNumberOfEvaluations = 25000 ;

    JMetalLogger.logger.setUseParentHandlers(false);

    double crossoverProbability = 0.9 ;
    double crossoverDistributionIndex = 20.0 ;
    CrossoverOperator<DoubleSolution> crossover =
        new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
    double mutationDistributionIndex = 20.0 ;
    MutationOperator<DoubleSolution> mutation =
        new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;

    PopulationToFilesWriterObserver<DoubleSolution> populationObserver;
    populationObserver = new PopulationToFilesWriterObserver<>();

    EvolutionaryAlgorithm<DoubleSolution> algorithm = new EvolutionaryAlgorithm<>(
        new RandomPopulationCreation<>(problem, populationSize),
        new SequentialEvaluation<>(problem, "POPULATION"),
        new SequentialEvaluation<>(problem, "OFFSPRING_POPULATION"),
        new TerminationByEvaluations<>(maxNumberOfEvaluations),
        new BinaryTournamentSelection<>(matingPoolSize, new DominanceComparator<>()),
        new CrossoverAndMutationVariation<>(crossover, mutation, offspringPopulationSize),
        new RankingAndCrowdingReplacement<>(new DominanceComparator<>())) ;

    algorithm.getTermination().getObservable().register(populationObserver);
    populationObserver.start();

    // Examples of observers

    EvaluationObserver<DoubleSolution> evaluationObserver =
        new EvaluationObserver<>(maxNumberOfEvaluations, 100) ;

    algorithm.getTermination().getObservable().register(evaluationObserver);
    evaluationObserver.start();
    /*
    NonDominatedSolutionCounterObserver<DoubleSolution> nonDominatedSolutionCounterObserver =
        new NonDominatedSolutionCounterObserver<>() ;

    algorithm.getTermination().getObservable().register(nonDominatedSolutionCounterObserver);
    nonDominatedSolutionCounterObserver.start();

    ExternalArchiveObserver<DoubleSolution> externalBoundedArchive;
    ExternalArchiveObserver<DoubleSolution> externalUnboundedArchive;

    externalBoundedArchive = new ExternalArchiveObserver<>(
        new CrowdingDistanceArchive<>(populationSize), "BoundedArchive");
    externalUnboundedArchive = new ExternalArchiveObserver<>(
        new NonDominatedSolutionListArchive<>(), "UnboundedArchive");

    algorithm.getOffspringPopulationEvaluation().getObservable().register(externalBoundedArchive);
    algorithm.getOffspringPopulationEvaluation().getObservable().register(externalUnboundedArchive);
    externalBoundedArchive.start();
    externalUnboundedArchive.start();
    */
    RealTimeChartObserver<DoubleSolution> realTimeChartObserver
            = new RealTimeChartObserver<>("NSGA-II", "/paretoFronts/ZDT1.pf") ;
    algorithm.getTermination().getObservable().register(realTimeChartObserver);
    realTimeChartObserver.start();

    algorithm.run();
  }
}

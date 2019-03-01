package org.uma.moore.architecture.component.algorithm.impl;

import org.uma.jmetal.problem.multiobjective.zdt.ZDT1;
import org.uma.moore.architecture.component.algorithm.EvolutionaryAlgorithm;
import org.uma.moore.architecture.component.component.common.createinitialpopulation.impl.RandomPopulationCreation;
import org.uma.moore.architecture.component.component.common.evaluation.impl.SequentialEvaluation;
import org.uma.moore.architecture.component.component.common.populationobserver.PopulationToFilesWriterObserver;
import org.uma.moore.architecture.component.component.common.populationobserver.RealTimeChartObserver;
import org.uma.moore.architecture.component.component.common.termination.impl.TerminationByKeyboard;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.replacement.impl.RankingAndCrowdingReplacement;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.selection.impl.BinaryTournamentSelection;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.variation.impl.CrossoverAndMutationVariation;
import org.uma.moore.common.operator.crossover.Crossover;
import org.uma.moore.common.operator.crossover.imp.SBXCrossover;
import org.uma.moore.common.operator.mutation.Mutation;
import org.uma.moore.common.operator.mutation.impl.PolynomialMutation;
import org.uma.moore.common.problem.DoubleProblem;
import org.uma.moore.common.solution.impl.DoubleSolution;
import org.uma.moore.common.util.MooreLogger;
import org.uma.moore.common.util.comparator.DominanceComparator;

public class NSGAII {
  public static void main (String[] args) {
    DoubleProblem problem = new ZDT1() ;
    int populationSize = 100 ;
    int offspringPopulationSize = 100 ;
    int matingPoolSize = 100 ;
    int maxNumberOfEvaluations = 25000 ;

    MooreLogger.logger.setUseParentHandlers(false);

    double crossoverProbability = 0.9 ;
    double crossoverDistributionIndex = 20.0 ;
    Crossover<DoubleSolution> crossover =
        new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
    double mutationDistributionIndex = 20.0 ;
    Mutation<DoubleSolution> mutation =
        new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;

    PopulationToFilesWriterObserver<DoubleSolution> populationObserver;
    populationObserver = new PopulationToFilesWriterObserver<>();

    EvolutionaryAlgorithm<DoubleSolution> algorithm = new EvolutionaryAlgorithm<>(
        new RandomPopulationCreation<>(problem, populationSize),
        new SequentialEvaluation<>(problem, "POPULATION"),
        new SequentialEvaluation<>(problem, "OFFSPRING_POPULATION"),
        new TerminationByKeyboard<>(maxNumberOfEvaluations),
        new BinaryTournamentSelection<>(matingPoolSize, new DominanceComparator<>()),
        new CrossoverAndMutationVariation<>(crossover, mutation, offspringPopulationSize),
        new RankingAndCrowdingReplacement<>(new DominanceComparator<>())) ;

    algorithm.getTermination().getObservable().register(populationObserver);
    populationObserver.start();

    // Examples of observers
    /*
    EvaluationObserver<DoubleSolution> evaluationObserver =
        new EvaluationObserver<>(maxNumberOfEvaluations, 100) ;

    algorithm.getTermination().getObservable().register(evaluationObserver);
    evaluationObserver.start();
    */
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

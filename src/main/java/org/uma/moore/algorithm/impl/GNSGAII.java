package org.uma.moore.algorithm.impl;

import java.util.Arrays;
import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT1;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.archive.impl.NonDominatedSolutionListArchive;
import org.uma.jmetal.util.comparator.GDominanceComparator;
import org.uma.moore.algorithm.EvolutionaryAlgorithm;
import org.uma.moore.component.common.createinitialpopulation.impl.RandomPopulationCreation;
import org.uma.moore.component.common.evaluation.impl.SequentialEvaluation;
import org.uma.moore.component.common.populationobserver.EvaluationObserver;
import org.uma.moore.component.common.populationobserver.ExternalArchiveObserver;
import org.uma.moore.component.common.populationobserver.PopulationToFilesWriterObserver;
import org.uma.moore.component.common.termination.impl.TerminationByEvaluations;
import org.uma.moore.component.evolutionaryalgorithm.replacement.impl.RankingAndCrowdingReplacement;
import org.uma.moore.component.evolutionaryalgorithm.selection.impl.BinaryTournamentSelection;
import org.uma.moore.component.evolutionaryalgorithm.variation.impl.CrossoverAndMutationVariation;

public class GNSGAII {
  public static void main (String[] args) {
    DoubleProblem problem = new ZDT1() ;
    int populationSize = 100 ;
    int offspringPopulationSize = 100 ;
    int matingPoolSize = 100 ;
    int totalNumberOfEvaluations = 25000 ;

    //JMetalLogger.logger.setUseParentHandlers(false);

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

    List<Double> referencePoint = Arrays.asList(0.2, 0.2) ;

    EvolutionaryAlgorithm<DoubleSolution> algorithm = new EvolutionaryAlgorithm<>(
        new RandomPopulationCreation<>(problem, populationSize),
        new SequentialEvaluation<>(problem),
        new SequentialEvaluation<>(problem),
        new TerminationByEvaluations<>(totalNumberOfEvaluations),
        new BinaryTournamentSelection<>(matingPoolSize, new GDominanceComparator<>(referencePoint)),
        new CrossoverAndMutationVariation<>(crossover, mutation, offspringPopulationSize),
        new RankingAndCrowdingReplacement<>(new GDominanceComparator<>(referencePoint))) ;

    algorithm.getTermination().getObservable().register(populationObserver);
    populationObserver.start();

    // Examples of observers

    /* Prints the number of evaluations */
    EvaluationObserver<DoubleSolution> evaluationObserver = new EvaluationObserver(totalNumberOfEvaluations, 100) ;
    algorithm.getTermination().getObservable().register(evaluationObserver);
    evaluationObserver.start();

    /* Stores the evaluated solutions in bounded and unbounded archives */
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

    algorithm.run();
  }
}

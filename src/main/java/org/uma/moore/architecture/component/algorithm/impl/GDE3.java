package org.uma.moore.architecture.component.algorithm.impl;

import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT1;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.architecture.component.algorithm.EvolutionaryAlgorithm;
import org.uma.moore.architecture.component.component.common.createinitialpopulation.impl.RandomPopulationCreation;
import org.uma.moore.architecture.component.component.common.evaluation.impl.SequentialEvaluation;
import org.uma.moore.architecture.component.component.common.populationobserver.PopulationToFilesWriterObserver;
import org.uma.moore.architecture.component.component.common.populationobserver.RealTimeChartObserver;
import org.uma.moore.architecture.component.component.common.termination.impl.TerminationByEvaluations;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.replacement.impl.PairwiseReplacement;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.selection.impl.DifferentialEvolutionSelection;
import org.uma.moore.architecture.component.component.evolutionaryalgorithm.variation.impl.DifferentialEvolutionVariation;
import org.uma.moore.common.problem.DoubleProblem;
import org.uma.moore.common.solution.impl.DoubleSolution;
import org.uma.moore.common.util.comparator.DominanceComparator;

import java.util.Comparator;

public class GDE3 {
  public static void main (String[] args) {
    DoubleProblem problem = new ZDT1() ;
    int populationSize = 100 ;
    int offspringPopulationSize = 100 ;
    int matingPoolSize = 300 ;
    int maxNumberOfEvaluations = 25000 ;

    JMetalLogger.logger.setUseParentHandlers(false);

    DifferentialEvolutionCrossover crossover;

    double cr = 0.5;
    double f = 0.5;
    crossover = new DifferentialEvolutionCrossover(cr, f, "rand/1/bin");

    PopulationToFilesWriterObserver<DoubleSolution> populationObserver;
    populationObserver = new PopulationToFilesWriterObserver<>();

    //List<Double> referencePoint = Arrays.asList(0.1, 1.0) ;
    //Comparator<DoubleSolution> dominanceComparator = new GDominanceComparator<>(referencePoint) ;
    Comparator<DoubleSolution> dominanceComparator = new DominanceComparator<>() ;

    EvolutionaryAlgorithm<DoubleSolution> algorithm = new EvolutionaryAlgorithm<>(
        new RandomPopulationCreation<>(problem, populationSize),
        new SequentialEvaluation<>(problem, "POPULATION"),
        new SequentialEvaluation<>(problem, "OFFSPRING_POPULATION"),
        new TerminationByEvaluations<>(maxNumberOfEvaluations),
        new DifferentialEvolutionSelection(matingPoolSize),
        new DifferentialEvolutionVariation(offspringPopulationSize, crossover),
        new PairwiseReplacement<>(dominanceComparator)) ;

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
            = new RealTimeChartObserver<>("GDE3", "/paretoFronts/ZDT1.pf") ;
    algorithm.getTermination().getObservable().register(realTimeChartObserver);
    realTimeChartObserver.start();

    algorithm.run();
  }
}

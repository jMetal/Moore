package org.uma.moore.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.moore.ObserverComponent;
import org.uma.moore.Population;

public class PopulationToFilesWriterObserver<S extends Solution<?>> extends
    ObserverComponent<S> {

  public PopulationToFilesWriterObserver() {
    super("Population to files writer observer") ;
  }

  @Override
  public void onNext(Population<S> population) {
  }

  @Override
  public void onFinish(Population<S> population) {
    JMetalLogger.logger.info("POPULATION OBSERVER: ALGORITHM_TERMINATED");
    JMetalLogger.logger.info("POPULATION OBSERVER: WRITING RESULT TO FILES");

    JMetalLogger.logger.info("> Size = " + population.size());
    JMetalLogger.logger
        .info("Termination = " + population.getAttribute("ALGORITHM_TERMINATED"));
    JMetalLogger.logger.info("Evaluations = " + population.getAttribute("EVALUATIONS"));

    new SolutionListOutput(population)
        .setSeparator("\t")
        .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
        .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
        .print();

    JMetalLogger.logger.info("Population to files archive writer observer");
  }

  @Override
  public synchronized String getDescription() {
    return "Population observer object";
  }
}

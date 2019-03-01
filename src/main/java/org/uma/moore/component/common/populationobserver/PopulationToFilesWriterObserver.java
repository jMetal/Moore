package org.uma.moore.component.common.populationobserver;

import java.util.List;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.moore.common.util.Message;
import org.uma.moore.common.util.ObserverComponent;

public class PopulationToFilesWriterObserver<S extends Solution<?>> extends
    ObserverComponent {

  public PopulationToFilesWriterObserver() {
    super("Population to files writer observer") ;
  }

  @Override
  public void onNext(Message message) {
  }

  @Override
  public void onFinish(Message message) {
    List<S> population = (List<S>) message.getAttribute("POPULATION");

    JMetalLogger.logger.info("POPULATION OBSERVER: ALGORITHM_TERMINATED");
    JMetalLogger.logger.info("POPULATION OBSERVER: WRITING RESULT TO FILES");

    JMetalLogger.logger.info("> Size = " + population.size());
    JMetalLogger.logger
        .info("Termination = " + message.getAttribute("ALGORITHM_TERMINATED"));
    JMetalLogger.logger.info("Evaluations = " + message.getAttribute("EVALUATIONS"));

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

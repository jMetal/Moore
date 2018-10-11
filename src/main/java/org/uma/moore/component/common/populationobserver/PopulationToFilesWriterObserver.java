package org.uma.moore.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.util.DataBuffer;

public class PopulationToFilesWriterObserver<S extends Solution<?>> extends Thread implements
    Observer<Population<S>> {

  private DataBuffer<Population<S>> buffer ;

  public PopulationToFilesWriterObserver() {
    buffer = new DataBuffer<>() ;
  }

  @Override
  public synchronized void update(Observable<Population<S>> observable, Population<S> population) {
    JMetalLogger.logger.info("POPULATION OBSERVER: update invoked by " + observable.getName()) ;
    try {
      buffer.put(new Population<>(population));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public synchronized String getDescription() {
    return "Population observer object";
  }

  @Override
  public void run() {
    boolean finishCondition = false ;

    Population<S> population = null ;
    while (!finishCondition) {
      try {
        population = buffer.get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      boolean s = (boolean) population.getAttribute("ALGORITHM_TERMINATED");
      if ((boolean) population.getAttribute("ALGORITHM_TERMINATED")) {
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

        finishCondition = true  ;

      }
    }
  }
}

package org.uma.moore.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.util.DataBuffer;

public class ExternalArchiveObserver<S extends Solution<?>>
    extends Thread
    implements Observer<Population<S>> {
  private DataBuffer<Population<S>> buffer ;
  private Archive<S> archive ;
  private String archiveName ;

  public ExternalArchiveObserver(Archive<S> archive, String archiveName) {
    buffer = new DataBuffer<>() ;
    this.archive = archive ;
    this.archiveName = archiveName ;
  }

  @Override
  public synchronized void update(Observable<Population<S>> observable, Population<S> population) {
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

      if ((boolean) population.getAttribute("ALGORITHM_TERMINATED")) {
        new SolutionListOutput(archive.getSolutionList())
            .setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext(archiveName + "_VAR.tsv"))
            .setFunFileOutputContext(new DefaultFileOutputContext(archiveName + "_FUN.tsv"))
            .print();

        finishCondition = true  ;
      } else {
        for (S solution: population) {
          archive.add((S) solution.copy()) ;
        }
      }
    }
  }
}

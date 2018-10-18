package org.uma.moore.component.common.populationobserver;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.moore.ObserverComponent;
import org.uma.moore.Population;

public class ExternalArchiveObserver<S extends Solution<?>>
    extends ObserverComponent<S> {
  private Archive<S> archive ;
  private String archiveName ;

  public ExternalArchiveObserver(Archive<S> archive, String archiveName) {
    super("External archive observer") ;
    this.archive = archive ;
    this.archiveName = archiveName ;
  }

  @Override
  public void onNext(Population<S> population) {
    for (S solution: population) {
      archive.add((S) solution.copy()) ;
    }
  }

  @Override
  public void onFinish(Population<S> population) {
    new SolutionListOutput(archive.getSolutionList())
        .setSeparator("\t")
        .setVarFileOutputContext(new DefaultFileOutputContext(archiveName + "_VAR.tsv"))
        .setFunFileOutputContext(new DefaultFileOutputContext(archiveName + "_FUN.tsv"))
        .print();
    JMetalLogger.logger.info("External archive observer");
  }


  @Override
  public synchronized String getDescription() {
    return "Population observer object";
  }
}

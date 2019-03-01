package org.uma.moore.architecture.component.component.common.populationobserver;

import org.uma.moore.architecture.component.util.ObserverComponent;
import org.uma.moore.common.solution.Solution;
import org.uma.moore.common.util.Message;
import org.uma.moore.common.util.MooreLogger;
import org.uma.moore.common.util.archive.Archive;
import org.uma.moore.common.util.fileoutput.SolutionListOutput;
import org.uma.moore.common.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.List;

public class ExternalArchiveObserver<S extends Solution<?>>
    extends ObserverComponent {
  private Archive<S> archive ;
  private String archiveName ;

  public ExternalArchiveObserver(Archive<S> archive, String archiveName) {
    super("External archive observer") ;
    this.archive = archive ;
    this.archiveName = archiveName ;
  }

  @Override
  public void onNext(Message message) {
    List<S> population = (List<S>) message.getAttribute("POPULATION");
    for (S solution: population) {
      archive.add((S) solution.copy()) ;
    }
  }

  @Override
  public void onFinish(Message message) {
    new SolutionListOutput(archive.getSolutionList())
        .setSeparator("\t")
        .setVarFileOutputContext(new DefaultFileOutputContext(archiveName + "_VAR.tsv"))
        .setFunFileOutputContext(new DefaultFileOutputContext(archiveName + "_FUN.tsv"))
        .print();
    MooreLogger.logger.info("External archive observer");
  }


  @Override
  public synchronized String getDescription() {
    return "Population observer object";
  }
}

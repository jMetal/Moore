package org.uma.moore.component.common.populationobserver;

import java.io.FileNotFoundException;
import java.util.List;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Message;
import org.uma.moore.ObserverComponent;
import org.uma.moore.util.ChartContainer;

public class RealTimeChartObserver<S extends Solution<?>> extends ObserverComponent {

  private ChartContainer<S> chart ;

  public RealTimeChartObserver(String algorithmName) {
    this(algorithmName, null ) ;
  }

  @Override
  public void onNext(Message message
  ) {
    int evaluations = (int)message.getAttribute("EVALUATIONS") ;
    List<S> population = (List<S>) message.getAttribute("POPULATION");

    if (this.chart != null) {
      this.chart.getFrontChart().setTitle("Iteration: " + evaluations);
      this.chart.updateFrontCharts(population);
      this.chart.refreshCharts();
    }
  }

  @Override
  public void onFinish(Message message) {
    JMetalLogger.logger.info("Real time chart observer terminated");
  }

  public RealTimeChartObserver(String algorithmName, String referenceFrontName) {
    super("Real time chart observer") ;
    chart = new ChartContainer<>(algorithmName, 80);
    try {
      chart.setFrontChart(0, 1, referenceFrontName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    chart.initChart();
  }

  @Override
  public String getDescription() {
    return "Evaluation observer object";
  }

}

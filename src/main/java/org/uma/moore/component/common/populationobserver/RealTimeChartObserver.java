package org.uma.moore.component.common.populationobserver;

import java.io.FileNotFoundException;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.moore.Population;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;
import org.uma.moore.util.ChartContainer;
import org.uma.moore.util.DataBuffer;

public class RealTimeChartObserver<S extends Solution<?>> extends Thread implements
    Observer<Population<S>> {

  private DataBuffer<Population<S>> buffer ;
  private ChartContainer<S> chart ;

  public RealTimeChartObserver(String algorithmName) {
    this(algorithmName, null ) ;
  }

  public RealTimeChartObserver(String algorithmName, String referenceFrontName) {
    buffer = new DataBuffer<>() ;
    chart = new ChartContainer<>(algorithmName, 80);
    try {
      chart.setFrontChart(0, 1, referenceFrontName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    chart.initChart();
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
    return "Evaluation observer object";
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
        finishCondition = true  ;
      } else {

        int evaluations = (int)population.getAttribute("EVALUATIONS") ;
        if (this.chart != null) {
          this.chart.getFrontChart().setTitle("Iteration: " + evaluations);
          this.chart.updateFrontCharts(population);
          this.chart.refreshCharts();
        }
      }
    }
  }
}

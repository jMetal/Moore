package org.uma.moore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.uma.jmetal.solution.Solution;

/**
 * A Population is a list of {@link Solution} objects with attributes
 * @param <S>
 */
public class Population<S extends Solution<?>> extends ArrayList<S> {
	public Map<String, Object> attributes;

	public Population() {
		super() ;
		attributes = new HashMap<>() ;
		}
	
	public Population(int capacity) {
		super(capacity) ;
		attributes = new HashMap<>() ;
	}

	public Population(Population<S> otherPopulation) {
		super(otherPopulation);
		attributes = new HashMap<>(otherPopulation.getAttributes()) ;
	}

  public Population(List<S> otherPopulation) {
    super(otherPopulation);
    attributes = new HashMap<>() ;
  }

  public Object getAttribute(String attributeName) {
		return attributes.getOrDefault(attributeName, null);
	}

	public void setAttribute(String attributeName, Object value) {
		attributes.put(attributeName, value);
	}

	public Map<String, Object> getAttributes() {
	  return attributes ;
  }

  public void setAttributes(Map<String, Object> attributes) {
	  this.attributes = attributes ;
  }

  public String toString() {
	  String result = "" ;
    result += "POPOPULATION: EVALS = " + (int)getAttribute("EVALUATIONS")  ;
    result += ". POP: TERMINATION = " + getAttribute("ALGORITHM_TERMINATED") + "\n" ;

	  return result ;
  }
}

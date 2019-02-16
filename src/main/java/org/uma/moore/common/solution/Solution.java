package org.uma.moore.common.solution;

import java.io.Serializable;
import java.util.List;

/**
 * Interface representing a Solution
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @param <T> Type (Double, Integer, etc.)
 */
public interface Solution<T> extends Serializable {
  void setObjective(int index, double value) ;
  double getObjective(int index) ;
  double[] getObjectives() ;

  T getVariable(int index) ;
  List<T> getVariables() ;
  void setVariable(int index, T value) ;
  String getVariableString(int index) ;

  int getNumberOfVariables() ;
  int getNumberOfObjectives() ;

  Solution<T> copy() ;

  void setAttribute(Object id, Object value) ;
  Object getAttribute(Object id) ;
}

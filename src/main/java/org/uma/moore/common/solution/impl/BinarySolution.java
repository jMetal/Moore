package org.uma.moore.common.solution.impl;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.moore.common.solution.util.BinarySet;
import org.uma.moore.common.util.MooreException;

import java.util.HashMap;

/**
 * Interface representing a binary (bitset) solutions
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class BinarySolution extends AbstractSolution<BinarySet> {

  /** Constructor */
  public BinarySolution(int numberOfVariables, int numberOfObjectives, int[] numberOfBitsPerVariable) {
    super(numberOfVariables, numberOfObjectives) ;

    if (numberOfVariables != numberOfBitsPerVariable.length) {
      throw new MooreException("The lenght of the parameter numberOfBitsPerVariable (" +
          numberOfBitsPerVariable.length + " is not equal to the number of variables: " + numberOfVariables) ;
    }

    for (int i = 0; i < numberOfVariables; i++) {
      setVariable(i, createNewBitSet(numberOfBitsPerVariable[i]));
    }
  }

  /** Copy constructor */
  public BinarySolution(BinarySolution solution) {
    super(solution.getNumberOfVariables(), solution.getNumberOfObjectives());

    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      setVariable(i, (BinarySet) solution.getVariable(i).clone());
    }

    for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
      setObjective(i, solution.getObjective(i)) ;
    }

    attributes = new HashMap<Object, Object>(solution.attributes) ;
  }

  private BinarySet createNewBitSet(int numberOfBits) {
    BinarySet bitSet = new BinarySet(numberOfBits) ;

    for (int i = 0; i < numberOfBits; i++) {
      double rnd = JMetalRandom.getInstance().nextDouble() ;
      if (rnd < 0.5) {
        bitSet.set(i);
      } else {
        bitSet.clear(i);
      }
    }
    return bitSet ;
  }
  public int getNumberOfBits(int index) {
    return getVariable(index).getBinarySetLength() ;
  }

  @Override
  public BinarySolution copy() {
    return new BinarySolution(this);
  }

  public int getTotalNumberOfBits() {
    int sum = 0 ;
    for (int i = 0; i < getNumberOfVariables(); i++) {
      sum += getVariable(i).getBinarySetLength() ;
    }

    return sum ;
  }

  @Override
  public String getVariableString(int index) {
    String result = "" ;
    for (int i = 0; i < getVariable(index).getBinarySetLength() ; i++) {
      if (getVariable(index).get(i)) {
        result += "1" ;
      }
      else {
        result+= "0" ;
      }
    }
    return result ;
  }
}

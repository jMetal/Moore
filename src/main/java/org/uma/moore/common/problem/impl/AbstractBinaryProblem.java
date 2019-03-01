package org.uma.moore.common.problem.impl;

import org.uma.moore.common.problem.BinaryProblem;
import org.uma.moore.common.problem.util.AbstractGenericProblem;
import org.uma.moore.common.solution.impl.BinarySolution;

@SuppressWarnings("serial")
public abstract class AbstractBinaryProblem extends AbstractGenericProblem<BinarySolution>
  implements BinaryProblem {

  protected int[] bitsPerVariable ;

  protected void setNumberOfBitsPerVariable(int[] bitsPerVariable) {
    this.bitsPerVariable = bitsPerVariable ;
  }

  @Override
  public int getNumberOfBits(int index) {
    return bitsPerVariable[index] ;
  }
  
  @Override
  public int getTotalNumberOfBits() {
  	int count = 0 ;
  	for (int i = 0; i < this.getNumberOfVariables(); i++) {
  		count += this.bitsPerVariable[i] ;
  	}
  	
  	return count ;
  }

  @Override
  public BinarySolution createSolution() {
    return new BinarySolution(getNumberOfVariables(), getNumberOfObjectives(), bitsPerVariable)  ;
  }
}

package org.uma.moore.common.operator.selection;

import org.uma.moore.common.operator.Operator;

/**
 * Interface representing selection operators
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @param <Source> Class of the source object (typically, a list of solutions)
 * @param <Result> Class of the result of applying the operator
 */
public interface Selection<Source, Result> extends Operator<Source,Result> {
}

package org.uma.moore.observer;

import org.uma.jmetal.util.naming.DescribedEntity;

/**
 * Interface for observer components
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface Observer<D> extends DescribedEntity {
	void update(Observable<D> observable, D data) ;
}

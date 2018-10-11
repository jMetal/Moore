package org.uma.moore.observer;

import org.uma.jmetal.util.naming.DescribedEntity;

/**
 * Interface for observable components
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface Observable<D> extends DescribedEntity, Runnable {
	void register(Observer<D> observer) ;
	void unregister(Observer<D> observer) ;

	void notifyObservers(D data);
	int numberOfRegisteredObservers() ;
	void setChanged() ;
	boolean hasChanged() ;
	void clearChanged() ;
}

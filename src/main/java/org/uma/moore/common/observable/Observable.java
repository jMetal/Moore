package org.uma.moore.common.observable;

import org.uma.jmetal.util.naming.DescribedEntity;
import org.uma.moore.common.observer.Observer;
import org.uma.moore.common.util.Message;

/**
 * Interface for observable components
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface Observable extends DescribedEntity {
	void register(Observer observer) ;
	void unregister(Observer observer) ;

	void notifyObservers(Message data);
	int numberOfRegisteredObservers() ;
	void setChanged() ;
	boolean hasChanged() ;
	void clearChanged() ;
}

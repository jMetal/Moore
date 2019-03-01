package org.uma.moore.common.observer;

import org.uma.moore.common.observable.Observable;
import org.uma.moore.common.util.DescribedEntity;
import org.uma.moore.common.util.Message;

/**
 * Interface for observer components
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface Observer extends DescribedEntity {
	void update(Observable observable, Message data) ;
}

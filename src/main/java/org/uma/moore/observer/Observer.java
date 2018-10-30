package org.uma.moore.observer;

import org.uma.jmetal.util.naming.DescribedEntity;
import org.uma.moore.Message;

/**
 * Interface for observer components
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface Observer extends DescribedEntity {
	void update(Observable observable, Message data) ;
}

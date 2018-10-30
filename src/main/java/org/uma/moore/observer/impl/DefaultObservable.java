package org.uma.moore.observer.impl;

import java.util.HashSet;
import java.util.Set;
import org.uma.moore.Message;
import org.uma.moore.observer.Observable;
import org.uma.moore.observer.Observer;

/**
 * Default implementation of the {@link Observable} interface
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class DefaultObservable implements Observable {
	private Set<Observer> observers ;
	private boolean dataHasChanged ;
	private String name ;

	public DefaultObservable(String name) {
		observers = new HashSet<>() ;
		dataHasChanged = false ;
		this.name = name ;
	}

	@Override
	public synchronized void register(Observer observer) {
		observers.add(observer) ;
	}

	@Override
	public synchronized void unregister(Observer observer) {
		observers.remove(observer) ;
	}

	@Override
	public synchronized void notifyObservers(Message data) {
		if (dataHasChanged) {
      observers.stream().forEach(observer -> observer.update(this, data));
		}
		clearChanged();
	}

	@Override
	public synchronized int numberOfRegisteredObservers() {
		return observers.size();
	}

	@Override
	public synchronized void setChanged() {
		dataHasChanged = true ;
	}

	@Override
	public synchronized boolean hasChanged() {
		return dataHasChanged ;
	}

	@Override
	public synchronized void clearChanged() {
		dataHasChanged = false ;
	}

	@Override
	public synchronized String getName() {
		return name;
	}

	@Override
	public synchronized String getDescription() {
		return name;
	}
}

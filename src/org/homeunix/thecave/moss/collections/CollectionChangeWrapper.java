/*
 * Created on Sep 15, 2007 by wyatt
 */
package org.homeunix.thecave.moss.collections;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.event.EventListenerList;

/**
 * A class which adds data change notificiations to a collection.
 * 
 * @author wyatt
 *
 */
public class CollectionChangeWrapper<T> extends AbstractCollection<T> implements Collection<T> {
	private EventListenerList listenerList = new EventListenerList();
	protected Collection<T> backingCollection;
	
//	public Collection<T> getBackingCollection() {
//		return backingCollection;
//	}
//	public void setBackingCollection(Collection<T> backingCollection) {
//		this.backingCollection = backingCollection;
//	}
	
	public CollectionChangeWrapper() {
		this.backingCollection = new ArrayList<T>();
	}
	
	public CollectionChangeWrapper(Collection<T> backingCollection) {
		this.backingCollection = backingCollection;
	}

	public boolean add(T o) {
		boolean r = backingCollection.add(o);
		fireCollectionChangeEvent();
		return r;
	}

	public boolean addAll(Collection<? extends T> c) {
		boolean r = backingCollection.addAll(c);
		fireCollectionChangeEvent();
		return r;
	}

	public void clear() {
		backingCollection.clear();
		fireCollectionChangeEvent();
	}

	public boolean contains(Object o) {
		return backingCollection.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return backingCollection.containsAll(c);
	}

	public boolean isEmpty() {
		return backingCollection.isEmpty();
	}

	public Iterator<T> iterator() {
		return backingCollection.iterator();
	}

	public boolean remove(Object o) {
		boolean r = backingCollection.remove(o);
		fireCollectionChangeEvent();
		return r;
	}

	public boolean removeAll(Collection<?> c) {
		boolean r = backingCollection.removeAll(c);
		fireCollectionChangeEvent();
		return r;
	}

	public boolean retainAll(Collection<?> c) {
		boolean r = backingCollection.retainAll(c);
		fireCollectionChangeEvent();
		return r;
	}

	public int size() {
		return backingCollection.size();
	}

	public Object[] toArray() {
		return backingCollection.toArray();
	}

	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return backingCollection.toArray(a);
	}

	/**
	 * Registers this listener to recieve events when the list changes.
	 * @param listener
	 */
	public void addCollectionChangeListener(CollectionChangeListener listener){
		listenerList.add(CollectionChangeListener.class, listener);
	}

	/**
	 * Unregisters this listener from recieving events when the collection changes.
	 * @param listener
	 */
	public void removeCollectionChangeListener(CollectionChangeListener listener){
		listenerList.remove(CollectionChangeListener.class, listener);
	}

	/**
	 * Fires a collection change event.  This can happen at most once per second; if we
	 * try to fire more than that, we just ignore them.
	 */
	protected void fireCollectionChangeEvent(){
		CollectionChangeEvent event = new CollectionChangeEvent(this);

		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i=0; i<listeners.length; i+=2) {
			if (listeners[i] == CollectionChangeListener.class) {
				((CollectionChangeListener) listeners[i+1]).collectionChange(event);
			}
		}
	}
}

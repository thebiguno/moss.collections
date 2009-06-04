package org.homeunix.thecave.moss.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A thread safe implementation of a history set, which does not use blocking to
 * provide thread safety.  A history set follows the contract of a set (no duplicate
 * items), maintains items in their added order, and moves any re-added objects from
 * their position back to the end of the set.  Thus, it is suitable for algorithms
 * such as working sets.
 * 
 * @author wyatt
 *
 * @param <E>
 */
public class ConcurrentHistorySet<E> extends ConcurrentLinkedQueue<E> implements Set<E>{

	private static final long serialVersionUID = 1L;
	
	public boolean add(E o) {
		boolean ret = true;
		if (this.contains(o)){
			this.remove(o);
			ret = false;
		}
		super.add(o);			
		
		return ret;
	};
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean ret = false;
		for (E e : c) {
			if (this.add(e));
				ret = true;
		}
		return ret;
	}
	
	/**
	 * Trims the set down to the given capacity, removing elements from oldest
	 * to youngest.
	 * @param capacity
	 * @return
	 */
	public Collection<E> trim(int capacity){
		Collection<E> removedItems = new ArrayList<E>();
		while (this.size() > capacity){
			removedItems.add(this.poll());
		}
		this.removeAll(removedItems);
		return removedItems;
	}
}

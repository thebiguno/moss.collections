package org.homeunix.thecave.moss.collections;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

/*
 * Created on Apr 8, 2005 by wyatt
 */

/**
 * A set of a specified size, which contains only the last X 
 * recently added elements.  Originally created for 
 * CPSC 457 A4 (Operating Systems), to describe a working set.
 * 
 * @author wyatt
 */
public class HistorySet<T> implements Set<T>{
	private LinkedList<T> internalList;
	private int size;
	
	/**
	 * Creates a new set, of specified size 
	 * @param size
	 */
	public HistorySet(int size){
		internalList = new LinkedList<T>();
		this.size = size;
	}
	
	/**
	 * Returns the set of X most recently added elements
	 * @return
	 */
	private Set<T> getSet(){
		TreeSet<T> returnSet = new TreeSet<T>();
		//Log.debug(internalList);
		//Keep on adding integers to the set until our target size is reached.
		for (T t : internalList){
			returnSet.add(t);
			if (returnSet.size() >= size)
				break;
		}
		
		return returnSet;
	}
	

	/* (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(T obj){
		boolean flag = true;
		if (internalList.contains(obj))
			flag = false;
		
		internalList.addFirst(obj);
		
		return flag;
	}

	
	public boolean addAll(Collection<? extends T> c) {
		internalList.addAll(c);
		
		return true;
	}

	
	public boolean contains(Object o) {
		Set<?> s = getSet();
		return s.contains(o);
	}
	
	/**
	 * @return The most recent object that was removed from the set.
	 */
	public T getLastRemoved(){
		Set<T> set = getSet();

		for (T t : internalList){
			if (!set.contains(t))
				return t;
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getSet().toString();
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	public void clear() {
		internalList.clear();
	}

	public boolean containsAll(Collection<?> arg0) {
		Set<T> set = getSet();
		for (Iterator<?> i = arg0.iterator(); i.hasNext();){
			if(!set.contains(i.next()))
				return false;
		}
		return true;
	}
		
	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty() {
		if (internalList.size() == 0)
			return true;
		else
			return false;
	}
	/* (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	public Iterator<T> iterator() {
		return getSet().iterator();
	}
	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object arg0) {
		boolean flag = false;
		if (internalList.contains(arg0))
			flag = true;
		
		//Remove all items
		while(internalList.remove(arg0));
		
		return flag;
	}
	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> arg0) {
		boolean flag = false;
		
		for (Iterator<?> i = arg0.iterator(); i.hasNext();){
			if (remove(i.next()))
				flag = true;
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	public int size() {
		Set<?> set = getSet();
		
		return set.size();
	}
	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		return getSet().toArray();
	}
	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public Object[] toArray(Object[] arg0) {
		return getSet().toArray(arg0);
	}
}

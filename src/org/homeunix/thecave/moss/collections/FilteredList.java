/*
 * Created on Jul 31, 2007 by wyatt
 */
package org.homeunix.thecave.moss.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.homeunix.thecave.moss.collections.exception.UnmodifiableObjectException;

/**
 * @author wyatt
 * 
 * Returns an immutable list of type T, which contains only elements which
 * match the isIncluded() method.  You must implement this method yourself.
 */
public abstract class FilteredList<T> implements List<T> {

	private List<T> filteredList = null;
	protected final List<T> filteredListSource;
	
	public FilteredList(List<T> filteredListSource) {
		this.filteredListSource = filteredListSource;
	}
	
	public void add(int arg0, T arg1) {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public boolean add(T arg0) {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public boolean addAll(Collection<? extends T> arg0) {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public void clear() {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public boolean contains(Object arg0) {
		return getFilteredList().contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return getFilteredList().containsAll(arg0);
	}

	public T get(int arg0) {
		if (arg0 < size())
			return getFilteredList().get(arg0);
		return null;
	}

	public int indexOf(Object arg0) {
		return getFilteredList().indexOf(arg0);
	}

	public boolean isEmpty() {
		return getFilteredList().isEmpty();
	}

	public Iterator<T> iterator() {
		return getFilteredList().iterator();
	}

	public int lastIndexOf(Object arg0) {
		return getFilteredList().lastIndexOf(arg0);
	}

	public ListIterator<T> listIterator() {
		return getFilteredList().listIterator();
	}

	public ListIterator<T> listIterator(int arg0) {
		return getFilteredList().listIterator(arg0);
	}

	public T remove(int arg0) {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public boolean remove(Object arg0) {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public boolean removeAll(Collection<?> arg0) {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public boolean retainAll(Collection<?> arg0) {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public T set(int arg0, T arg1) {
		throw new UnmodifiableObjectException("Cannot modify FilterList");
	}

	public int size() {
		return getFilteredList().size();
	}

	public List<T> subList(int arg0, int arg1) {
		return getFilteredList().subList(arg0, arg1);
	}

	public Object[] toArray() {
		return getFilteredList().toArray();
	}

	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] arg0) {
		return getFilteredList().toArray(arg0);
	}
	
	@Override
	public String toString() {
		return getFilteredList().toString();
	}
	
	private List<T> getFilteredList(){
		if (filteredList == null)
			updateFilteredList();
		
		return filteredList;
	}
	
	@SuppressWarnings("unchecked")
	//TODO Should this really be synchonized?  Probably, but it could make things slower... 
	public synchronized void updateFilteredList(){
		if (filteredList == null)
			filteredList = new LinkedList<T>();

		filteredList.clear();
		for (T t : filteredListSource) {
			if (isIncluded(t))
				filteredList.add(t);
		}

		Collections.sort(filteredList, new Comparator<T>(){
			public int compare(T o1, T o2) {
				if (o1 == null && o2 == null)
					return 0;
				if (o1 == null && o2 != null)
					return -1;
				if (o1 != null && o2 == null)
					return 1;
				if (o1 instanceof Comparable && o2 instanceof Comparable)
					return ((Comparable) o1).compareTo(o2);
				return o1.toString().compareTo(o2.toString());
			}
		});
	}
	
	public abstract boolean isIncluded(T object);
}

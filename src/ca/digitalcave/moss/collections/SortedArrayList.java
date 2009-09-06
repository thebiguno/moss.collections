/*
 * Created on Dec 6, 2007 by wyatt
 */
package ca.digitalcave.moss.collections;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A List implementation which will remain sorted, regardless of operations
 * performed on it.  Backed by an ArrayList; this implementation is not
 * synchronized.
 * 
 * This extends AbstractList to ensure correct XML Serialization
 * 
 * @author wyatt
 *
 */
public class SortedArrayList<T extends Comparable<? super T>> extends AbstractList<T> {
	private ArrayList<T> backingList;

	public SortedArrayList() {
		backingList = new ArrayList<T>();
	}
	
	public SortedArrayList(int initialCapacity) {
		backingList = new ArrayList<T>(initialCapacity);
	}
	
	public SortedArrayList(Collection<T> arg0) {
		backingList = new ArrayList<T>(arg0);
		Collections.sort(backingList);
	}
	
	public void add(int arg0, T arg1) {
		//We don't care about what position you try for, foo!
		add(arg1);
	}

	public boolean add(T arg0) {
		int index = Collections.binarySearch(backingList, arg0);
		if (index < 0)
			index = (index + 1) * -1;
		backingList.add(index, arg0);
		return true;
	}

	public boolean addAll(Collection<? extends T> arg0) {
		boolean r = backingList.addAll(arg0);
		Collections.sort(backingList);
		return r;
	}

	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		return addAll(arg1);
	}

	public void clear() {
		backingList.clear();	
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new SortedArrayList<T>(backingList);
	}

	@SuppressWarnings("unchecked")
	public boolean contains(Object arg0) {
		return Collections.binarySearch(backingList, (T) arg0) >= 0;
	}

	public boolean containsAll(Collection<?> arg0) {
		return backingList.containsAll(arg0);
	}

	public T get(int arg0) {
		return backingList.get(arg0);
	}

	@SuppressWarnings("unchecked")
	public int indexOf(Object arg0) {
		int index = Collections.binarySearch(backingList, (T) arg0);
		if (index < 0)
			return -1;
		return index;
	}

	public boolean isEmpty() {
		return backingList.isEmpty();
	}

	public Iterator<T> iterator() {
		return backingList.iterator();
	}

	public int lastIndexOf(Object arg0) {
		int i;
		for (i = indexOf(arg0); backingList.get(i).equals(arg0); i++);
		return i;
	}

	public ListIterator<T> listIterator() {
		return backingList.listIterator();
	}

	public ListIterator<T> listIterator(int arg0) {
		return backingList.listIterator(arg0);
	}

	public T remove(int arg0) {
		return backingList.remove(arg0);
	}

	public boolean remove(Object arg0) {
		int i = indexOf(arg0);
		if (i < 0)
			return false;
		backingList.remove(i);
		return true;
	}

	public boolean removeAll(Collection<?> arg0) {
		return backingList.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return backingList.retainAll(arg0);
	}

	public T set(int arg0, T arg1) {
		return backingList.set(arg0, arg1);
	}

	public int size() {
		return backingList.size();
	}

	public List<T> subList(int arg0, int arg1) {
		return backingList.subList(arg0, arg1);
	}

	public Object[] toArray() {
		return backingList.toArray();
	}

	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] arg0) {
		return backingList.toArray(arg0);
	}
	
	@Override
	public String toString() {
		return backingList.toString();
	}
}

/*
 * Created on Sep 15, 2007 by wyatt
 */
package ca.digitalcave.moss.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("unchecked")
public class ListChangeWrapper<T> extends CollectionChangeWrapper<T> implements List<T> {

	private List backingList;
	
	public ListChangeWrapper() {
		super(new ArrayList<T>());
		this.backingList = (List<T>) super.backingCollection;
	}
	
//	public void setBackingCollection(List<T> backingCollection) {
//		super.setBackingCollection(backingCollection);
//		this.backingList = backingCollection;
//	}
	
	public ListChangeWrapper(List<T> backingList) {
		super(backingList);
		this.backingList = backingList;
	}
	
	public void add(int arg0, T arg1) {
		backingList.add(arg0, arg1);
		fireCollectionChangeEvent();
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		fireCollectionChangeEvent();
		return backingList.addAll(index, c);
	}

	public T get(int index) {
		return (T) backingList.get(index);
	}

	public int indexOf(Object arg0) {
		return backingList.indexOf(arg0);
	}

	public int lastIndexOf(Object arg0) {
		return backingList.lastIndexOf(arg0);
	}

	public ListIterator<T> listIterator() {
		return backingList.listIterator();
	}

	public ListIterator<T> listIterator(int arg0) {
		return backingList.listIterator(arg0);
	}

	public T remove(int arg0) {
		fireCollectionChangeEvent();
		return (T) backingList.remove(arg0);
	}

	public T set(int index, T element) {
		fireCollectionChangeEvent();
		return (T) backingList.set(index, element);
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return backingList.subList(fromIndex, toIndex);
	}
}

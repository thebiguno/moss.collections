/*
 * Created on Jul 31, 2007 by wyatt
 */
package ca.digitalcave.moss.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import ca.digitalcave.moss.collections.exception.UnmodifiableObjectException;

/**
 * @author wyatt
 * 
 * Returns an immutable list of type T, which consists of a list of wrapped type
 * W.  You must implement the conversion method yourself.
 */
public abstract class WrapperList<T, W> implements List<T> {

	private final List<W> wrappedList;
	private List<T> wrapperList;
	private final boolean sorted;
	
	public WrapperList(List<W> wrappedList, boolean sorted) {
		this.wrappedList = wrappedList;
		this.sorted = sorted;
	}
	
	public void add(int arg0, T arg1) {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public boolean add(T arg0) {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public boolean addAll(Collection<? extends T> arg0) {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public void clear() {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public boolean contains(Object arg0) {
		return getWrapperList().contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return getWrapperList().containsAll(arg0);
	}

	public T get(int arg0) {
		return getWrapperList().get(arg0);
	}

	public int indexOf(Object arg0) {
		return getWrapperList().indexOf(arg0);
	}

	public boolean isEmpty() {
		return getWrapperList().isEmpty();
	}

	public Iterator<T> iterator() {
		return getWrapperList().iterator();
	}

	public int lastIndexOf(Object arg0) {
		return getWrapperList().lastIndexOf(arg0);
	}

	public ListIterator<T> listIterator() {
		return getWrapperList().listIterator();
	}

	public ListIterator<T> listIterator(int arg0) {
		return getWrapperList().listIterator(arg0);
	}

	public T remove(int arg0) {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public boolean remove(Object arg0) {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public boolean removeAll(Collection<?> arg0) {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public boolean retainAll(Collection<?> arg0) {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public T set(int arg0, T arg1) {
		throw new UnmodifiableObjectException("Cannot modify WrapperList");
	}

	public int size() {
		return getWrapperList().size();
	}

	public List<T> subList(int arg0, int arg1) {
		return getWrapperList().subList(arg0, arg1);
	}

	public Object[] toArray() {
		return getWrapperList().toArray();
	}

	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] arg0) {
		return getWrapperList().toArray(arg0);
	}
	
	private List<T> getWrapperList(){
		if (wrapperList == null)
			updateWrapperList();
		return wrapperList;
	}
	
	@SuppressWarnings("unchecked")
	public void updateWrapperList(){
		List<T> ts = new LinkedList<T>();
		for (W w : wrappedList) {
			ts.add(getWrapperObject(w));
		}
		
		if (sorted){
			try{
				Collections.sort((List<? extends Comparable>) ts);
			}
			catch (ClassCastException cce){}
			catch (NullPointerException npe){}
		}
		
		wrapperList = ts;
	}
	
	@Override
	public String toString() {
		return getWrapperList().toString();
	}

	public abstract W getWrappedObject(T object);
	
	public abstract T getWrapperObject(W object);
}

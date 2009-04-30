package org.homeunix.thecave.moss.collections;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A fairly robust Heap implementation using Java 5 generics.  Allows for either Min or Max heap.
 * Does not allow for duplicate entries (since it implements the Set interface).
 * 
 * @author wyatt
 */
public class HeapSet<Type extends Comparable<Type>> implements Set<Type>, Serializable {
	public static final long serialVersionUID = 0;
	public enum HeapType {MIN, MAX}
	
	private ArrayList<Type> data = new ArrayList<Type>();
	private Map<Object, Integer> dataMap = new HashMap<Object, Integer>();
	private HeapType heapType = HeapType.MIN; 
	
	/**
	 * Creates a new min heap.
	 */
	public HeapSet() {
	}

	/**
	 * Creates a new min heap containing the specified items
	 * @param arg0
	 */
	public HeapSet(Collection<Type> arg0) {
		this.addAll(arg0);
	}
	
	/**
	 * Creates a new heap of the specified heap type.
	 * @param arg0
	 */
	public HeapSet(HeapType arg0){
		this.heapType = arg0;
	}
	
	/**
	 * Creates a new heap containing the specified items, with the specified heap type.
	 * @param arg0
	 * @param arg1
	 */
	public HeapSet(Collection<Type> arg0, HeapType arg1){
		this.heapType = arg1;
	}

	/**
	 * Adds an object to the heap
	 * @param arg0
	 * @return
	 */
	public boolean add(Type arg0) {		
		if (dataMap.get(arg0) != null){
			return false;
		}

		int pi, i = data.size();

		// This should be at location pi
		data.add(arg0);
		while (i != 0 && addCompareTo(i)) {  //addCompareTo changes depending on heap type.
			// Swap this element with it's parent
			pi = getParentIndex(i);
			swap(i, pi);
			i = pi;
		}

		//Makes for easy lookup later.
		dataMap.put(arg0, i);
		
		return true;
	}

	/**
	 * Add all the objects in the collection to the heap
	 * @param arg0
	 * @return
	 */
	public boolean addAll(Collection<? extends Type> arg0) {
		boolean r = false;

		data.ensureCapacity(data.size() + arg0.size());
		for (Type type : arg0) {
			if (this.add(type))
				r = true;
		}

		return r;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	public void clear() {
		data.clear();
		dataMap.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object arg0) {
		return (dataMap.get(arg0) != null);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> arg0) {
		for (Object object : arg0) {
			if (!contains(object))
				return false;
		}
		return true;
	}

	/**
	 * Removes and returns the top element in the heap
	 * @return The top element in the heap
	 */
	public Type dequeue() {
		Type r = data.get(0);
		remove(r);
		return r;
	}
	
	/**
	 * Adds the specified element to the heap.
	 * @param arg0 The element to add.
	 * @return <code>true</code> if the heap changed as a result of this add.
	 */
	public boolean enqueue(Type arg0){
		return add(arg0);
	}

	/**
	 * Returns the top element in the heap without removing it.
	 * @return The top element in the heap, or null if there are no elements.
	 */
	public Type getTop(){
		if (data.size() > 0)
			return data.get(0);
		else
			return null;
	}

	/**
	 * Allows for random access of the heap data.  Used for GA's fSelect() fucntions.
	 * @param i
	 * @return
	 */
	public Type get(int i){
		if (i >= 0 && i < data.size())
			return data.get(i);
		else
			return null;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<Type> iterator() {
		return data.iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object arg0) {
		Integer i;

		if ((i = dataMap.get(arg0)) == null)
			return false;
		
		swap(i, data.size() - 1);
		data.remove(data.size() - 1);

		while (getChildIndex(i, 2) < data.size() && 
				(removeCompareTo(i, 1) || removeCompareTo(i, 2))){ //removeCompareTo changes with heap type.
			//Swap i with the smallest / largest child
			if (removeGetChild(i)){
				swap(i, getChildIndex(i, 1));
				i = getChildIndex(i, 1);
			}
			else{
				swap(i, getChildIndex(i, 2));
				i = getChildIndex(i, 2);
			}
		}
		
		dataMap.put(arg0, i);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> arg0) {
		boolean r = false;

		for (Object object : arg0) {
			if (this.remove(object))
				r = true;
		}
		return r;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> arg0) {
		boolean r = false;
		
		for (Type type : data) {
			if (!arg0.contains(type)){
				remove(type);
				r = true;
			}
		}
		return r;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	public int size() {
		return data.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		return data.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(T[])
	 */
	public <T> T[] toArray(T[] arg0) {
		return data.toArray(arg0);
	}
	
	/**
	 * Get all of the items in the set.
	 * @return
	 */
	public Collection<Type> getAll(){
		return Collections.unmodifiableCollection(data);
	}

	/**
	 * Swaps the values at index arg0 and arg1.
	 * @param arg0
	 * @param arg1
	 */
	private void swap(int arg0, int arg1) {
		Type temp;
		temp = data.get(arg0);

		data.set(arg0, data.get(arg1));
		data.set(arg1, temp);
		dataMap.put(data.get(arg0), arg0);
		dataMap.put(data.get(arg1), arg1);
	}

	/**
	 * Returns the index of the childNum'th child of the specified index. 
	 * @param index
	 * @param childNum
	 * @return
	 */
	private int getChildIndex(int index, int childNum) {
		if (childNum != 1 && childNum != 2)
			throw new RuntimeException("HeapSet getChildIndex() - childNum should be either 1 or 2");
		
		return (2 * index + childNum);
	}

	/**
	 * Returns the parent index of the specified index.
	 * @param index
	 * @return
	 */
	private int getParentIndex(int index) {
		return (index - 1) / 2;
	}
	
	private boolean addCompareTo(int i){
		if (heapType == HeapType.MIN)
			return (data.get(i).compareTo(data.get(getParentIndex(i))) < 0);
		else
			return (data.get(i).compareTo(data.get(getParentIndex(i))) > 0);
	}
	
	private boolean removeCompareTo(int i, int childNum){
		if (heapType == HeapType.MIN)
			return (data.get(i).compareTo(data.get(getChildIndex(i, childNum))) > 0);
		else
			return (data.get(i).compareTo(data.get(getChildIndex(i, childNum))) < 0);
	}
	
	private boolean removeGetChild(int i){
		if (heapType == HeapType.MIN)
			return (getChildIndex(i, 2) >= data.size() || data.get(getChildIndex(i, 1)).compareTo(data.get(getChildIndex(i, 2))) < 0);
		else
			return (getChildIndex(i, 2) >= data.size() || data.get(getChildIndex(i, 1)).compareTo(data.get(getChildIndex(i, 2))) > 0);
	}
}

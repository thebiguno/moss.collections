package ca.digitalcave.moss.collections;
import java.util.Collection;
import java.util.LinkedHashSet;

/*
 * Created on Apr 8, 2005 by wyatt
 */

/**
 * A set of a specified size, which contains only the last X 
 * recently added elements.  Originally created for 
 * CPSC 457 A4 (Operating Systems), to describe a working set.
 * 
 * This set will iterate over elements within it in the order which
 * they were added; the oldest elements will be first, and the 
 * younger elements will be later.
 * 
 * @author wyatt
 */
public class HistorySet<T> extends LinkedHashSet<T>{
	
	private static final long serialVersionUID = 1L;
	private final int capacity;
	
	public HistorySet(int capacity) {
		this.capacity = capacity;
	}
	
	public boolean add(T o) {
		boolean ret = true;
		if (this.contains(o)){
			this.remove(o);
			ret = false;
		}
		super.add(o);
		
		while (this.size() > capacity){
			this.remove(this.iterator().next());
		}
			
		
		return ret;
	};
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean ret = false;
		for (T t : c) {
			if (this.add(t));
				ret = true;
		}
		return ret;
	}
	
	
}

package org.homeunix.thecave.moss.collections;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of a history map.  This map will retain the most recent X entries, 
 * and will list keys in added order (with oldest entries showing first).  It is
 * thread safe, and will not block on either retrieval or modification methods.
 *  
 * @author wyatt
 *
 * @param <K>
 * @param <V>
 */
public class ConcurrentHistoryMap<K, V> extends ConcurrentHashMap<K, V>{
	private final ConcurrentHistorySet<K> workingSet = new ConcurrentHistorySet<K>();
	
	private final static long serialVersionUID = 1L;
	private int capacity;	
	
	public ConcurrentHistoryMap() {
		this(32);
	}
	
	public ConcurrentHistoryMap(int capacity) {
		this.capacity = capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	@Override
	public V put(K key, V value) {
		V ret = null;
		if (this.containsKey(key)){
			ret = this.remove(key);
		}
		super.put(key, value);
		workingSet.add(key);
		
		Collection<K> itemsToRemove = workingSet.trim(capacity);
		for (K k : itemsToRemove) {
			this.remove(k);
		}
				
		return ret;
	};
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			this.put(key, m.get(key));
		}
	}
}

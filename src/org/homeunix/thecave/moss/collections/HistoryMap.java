package org.homeunix.thecave.moss.collections;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of a history map.  This map will retain the most recent X entries, 
 * and will list keys in added order (with oldest entries showing first).
 * 
 * Internally, this is an extension of LinkedHashMap, with the put / putAll methods
 * overridden to mark keys which were re-added as new entries; thus, this can easily
 * be used as a memory cache to store the most frequently accessed items.  The more a 
 * key is required, the higher priority it will keep in the map.
 *  
 * @author wyatt
 *
 * @param <K>
 * @param <V>
 */
public class HistoryMap<K, V> extends LinkedHashMap<K, V>{
	private final static long serialVersionUID = 1L;
	private int capacity;
	
	public HistoryMap() {
		capacity = 32;
	}
	
	public HistoryMap(int capacity) {
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
				
		return ret;
	};
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			this.put(key, m.get(key));
		}
	}
	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return this.size() > capacity;
	}

}

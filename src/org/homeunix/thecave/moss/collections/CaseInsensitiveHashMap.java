/*
 * Created on May 28, 2008 by wyatt
 */
package org.homeunix.thecave.moss.collections;

import java.util.HashMap;
import java.util.Map;

public class CaseInsensitiveHashMap<V> extends HashMap<String, V>{
	private static final long serialVersionUID = 0l;

	@Override
	public V put(String key, V value) {
		return super.put(key.toLowerCase(), value);
	}
	
	@Override
	public boolean containsKey(Object key) {
		return super.containsKey(key.toString().toLowerCase());
	}
	
	@Override
	public void putAll(Map<? extends String, ? extends V> m) {
		for (String s : m.keySet()) {
			put(s, m.get(s));
		}
	}
	
	@Override
	public V get(Object key) {
		return super.get(key.toString().toLowerCase());
	}
}

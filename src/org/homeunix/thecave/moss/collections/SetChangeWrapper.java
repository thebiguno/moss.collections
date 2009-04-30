/*
 * Created on Sep 15, 2007 by wyatt
 */
package org.homeunix.thecave.moss.collections;

import java.util.HashSet;
import java.util.Set;

public class SetChangeWrapper<T> extends CollectionChangeWrapper<T> implements Set<T> {
	
	public SetChangeWrapper() {
		super(new HashSet<T>());
	}
	
	public SetChangeWrapper(Set<T> backingSet) {
		super(backingSet);
	}
}

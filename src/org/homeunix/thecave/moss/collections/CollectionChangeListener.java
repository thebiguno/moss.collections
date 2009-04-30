/*
 * Created on Aug 11, 2007 by wyatt
 */
package org.homeunix.thecave.moss.collections;

import java.util.EventListener;

public interface CollectionChangeListener extends EventListener {
	public void collectionChange(CollectionChangeEvent event);
}

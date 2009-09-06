/*
 * Created on Aug 11, 2007 by wyatt
 */
package ca.digitalcave.moss.collections;

import java.util.Collection;
import java.util.EventObject;

public class CollectionChangeEvent extends EventObject {
	public static final long serialVersionUID = 0;

	public CollectionChangeEvent(Collection<?> source) {
		super(source);
	}
}

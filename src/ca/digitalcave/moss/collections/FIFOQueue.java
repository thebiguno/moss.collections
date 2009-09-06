package ca.digitalcave.moss.collections;
import java.util.LinkedList;

/*
 * Created on Apr 8, 2005 by wyatt
 * 
 * Released as part of the Moss library under the LGPL.
 */

/**
 * TODO: Implement the Queue interface
 * @author Wyatt Olson
 * 
 */
public class FIFOQueue<T> {
	private LinkedList<T> queue = new LinkedList<T>();
	
	/**
	 * Creates a new FIFO Queue
	 */
	public FIFOQueue(){
		queue = new LinkedList<T>();
	}
	
	/**
	 * @param arg0 The object to add to the tail of the queue
	 */
	public void add(T arg0){
		queue.addLast(arg0);
	}
	
	/**
	 * @return The head of the queue
	 */
	public T remove(){
		if (queue.size() > 0){
			return queue.removeFirst();
		}
		return null;
	}
	
	/**
	 * @return The size of the queue
	 */
	public int size(){
		return queue.size();
	}	
}

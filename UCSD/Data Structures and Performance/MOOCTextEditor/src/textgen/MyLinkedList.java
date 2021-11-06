package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head; 
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException();
		}
		
		LLNode<E> newElement = new LLNode<E>(element);
		
		newElement.next = tail;
		newElement.prev = tail.prev;
		tail.prev.next = newElement;
		tail.prev = newElement;
		size += 1;
		
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> currentNode = this.head.next;
		
		for (int i = 0; currentNode.next != null; i++) {
			if (i == index) {
				return currentNode.data;
			}
			else {
				currentNode = currentNode.next;
			}
		}
		
		return null;
	}
	
	// Get the node at postition index
	public LLNode<E> getNode(int index) {
		
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> currentNode = this.head.next;
		
		for (int i = 0; currentNode.next != null; i++) {
			if (i == index) {
				return currentNode;
			}
			else {
				currentNode = currentNode.next;
			}
		}
		
		return null;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException();
		}
		
		LLNode<E> currentNode = getNode(index);
			
		LLNode<E> newNode = new LLNode<E>(element);
		
		newNode.next = currentNode;
		newNode.prev = currentNode.prev;
		currentNode.prev.next = newNode;
		currentNode.prev = newNode;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> current = this.getNode(index);
		current.prev.next = current.next;
		current.next.prev = current.prev;
		size -= 1;
		return current.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> current = getNode(index);
		E oldElement = current.data;
		current.data = element;
		return oldElement;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}

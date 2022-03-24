/**
 * Name: Anh Bach
 * Email: tbach@ucsd.edu
 * Sources used: Lecture Slides 
 * The class that extends Abstract List and using a doubly linked list.
 */

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** 
 * A class that extends Abstract List by using a chain of nodes
 * The class is a implemetation of doubly linked list
 * and has dummy head and tail nodes. 
 */

public class MyLinkedList<E> extends AbstractList<E> {

	int size;
	Node head;
	Node tail;

	// Constant values
	static final String OUTOFBOUND = "Illegal index!";
	static final String NULL = "Null exception is thrown!";

	/**
	 * A Node class that holds data and references to previous and next Nodes.
	 */
	protected class Node {
		E data;
		Node next;
		Node prev;

		/** 
		 * Constructor to create singleton Node 
		 * @param element Element to add, can be null	
		 */
		public Node(E element) {
			// Initialize the instance variables
			this.data = element;
			this.next = null;
			this.prev = null;
		}

		/** 
		 * Set the parameter prev as the previous node
		 * @param prev - new previous node
		 */
		public void setPrev(Node prev) {
			this.prev = prev;		
		}

		/** 
		 * Set the parameter next as the next node
		 * @param next - new next node
		 */
		public void setNext(Node next) {
			this.next = next;
		}

		/** 
		 * Set the parameter element as the node's data
		 * @param element - new element 
		 */
		public void setElement(E element) {
			this.data = element;
		}

		/** 
		 * Accessor to get the next Node in the list 
		 * @return the next node
		 */
		public Node getNext() {
			return this.next;
		}

		/** 
		 * Accessor to get the prev Node in the list
		 * @return the previous node  
		 */
		public Node getPrev() {
			return this.prev;
		}

		/** 
		 * Accessor to get the Nodes Element 
		 * @return this node's data
		 */
		public E getElement() {
			return this.data;
		}
	}

	//  Implementation of the MyLinkedList Class
	/** Only 0-argument constructor is defined */
	/** 
	* Constructor to create a doubly linked list
	*/
	public MyLinkedList() {
		// Create dummy nodes
		this.head = this.tail = new Node(null); 
		// Links dummy nodes together
		this.head.setNext(this.tail);
		this.tail.setPrev(this.head);
		// Set size of empty linked list 
		this.size = 0;
	}

	/**
    * Get the number of elements in the list
    * @return number of elements present in the list
    */
	@Override
	public int size() {
		return this.size;
	}

	/**
    * Get the element at the given index 
    * @param index - position in the linked list
    * @return element present in the given index
	* Precondition: List is not empty
	* Index >= 0 and index <= number of elements in the list
    */
	@Override
	public E get(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		return (E) getNth(index).getElement();
	}

	/**
     * Add an element at the specified index
     * @param index - position in the linked list to insert the element
     * @param data - the element to be inserted 
     */
	@Override
	public void add(int index, E data) {
		if (data == null) {
			throw new NullPointerException(NULL);
		}
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException(OUTOFBOUND);
		}
		Node newNode = new Node(data);
		Node currNode;
		// When the list is empty
		if (this.head.getNext().getElement() == null || this.size == index) {
            currNode = this.tail;
		}
		else {
			currNode = this.getNth(index);
		}
		Node nodeBefore = currNode.getPrev();
		// Connect new node with node before and after
		nodeBefore.setNext(newNode);
		newNode.setPrev(nodeBefore);
		currNode.setPrev(newNode);
		newNode.setNext(currNode);
		// Update size of the list
		this.size++;
	}

	/**
	* Add a node at the end into this list
	* @param data - the element to be inserted 
	*/
	public boolean add(E data) {
		if (data == null) {
			throw new NullPointerException(NULL);
		}
		Node newNode = new Node(data);
		if (isEmpty()) {
			newNode.setPrev(this.head);
			this.head.setNext(newNode);
			newNode.setNext(this.tail);
			this.tail.setPrev(newNode);
		}
		// When the list is empty
		else {
			Node last = getNth(this.size()-1);
			// Connect new node with node before and tail
			last.setNext(newNode);
			newNode.setPrev(last);
			newNode.setNext(this.tail); 
			this.tail.setPrev(newNode);
		}
		// Update size of the list
		this.size++;
		return true;
	}

	/**
    * Replaces a data at the specified index 
	* with a new data and return the original data
    * @param index - position of the element to be replaced
    * @param data - new data replacing the old element
    * @return original data present in the index before replacement
    */
	public E set(int index, E data) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException(OUTOFBOUND);
		}
		if (data == null) {
			throw new NullPointerException(NULL);
		}
		Node currNode = getNth(index);
		E originalData = getNth(index).getElement();
		currNode.setElement(data);
		return (E) originalData;
	}

	/**
    * Remove the node at the specified index and return the removed data
    * @param index - position of the node to be removed
    * @return data in that index
    */
	public E remove(int index) {
		Node removedNode = new Node(null);
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException(OUTOFBOUND);
		}
		if (!isEmpty()) {
		    // Remove 1st node
		    if (index == 0) {
			    Node firstNode = getNth(index);
                removedNode = firstNode;
				// First node becomes the node original in index 1
			    firstNode = firstNode.getNext(); 
			    // Connect head with node at index 1
			    this.head.setNext(firstNode);
			    firstNode.setPrev(this.head);
		    }
		    // Remove the last node
		    else if (index == this.size()-1) {
			    Node lastNode = getNth(index);
			    removedNode = lastNode;
			    lastNode = lastNode.getPrev();
			    // Connect tail with node before removed node
			    this.tail.setPrev(lastNode);
			    lastNode.setNext(this.tail);
		    }
		    // Remove node at index > 0 and index < number of elements in list
		    else {
			    Node before = getNth(index - 1);
			    removedNode = before.getNext();
			    Node after = removedNode.getNext();
			    // Connect before and after nodes together
			    before.setNext(after);
			    after.setPrev(before);
		    }
	    }
		// Update size of the list
		this.size--;
		return (E) removedNode.getElement();
	}

	/**
	 * Remove all nodes from the list
	 */
	public void clear() {
		Node currNode = this.head;
		Node tempNode = new Node(null);
		currNode = currNode.getNext();
		// When the first node is not null, 
		// removes each node until the list is empty
        while (currNode.getElement() != null) {
			// Assign tempNode to the 2nd node in list
            tempNode = currNode.getNext(); 
			this.remove(0); // Remove the first node in list
			currNode = tempNode; // Update currNode
		}
		// Links dummy nodes together
		this.head.setNext(this.tail);
		this.tail.setPrev(this.head);
		// Update size of the list 
		this.size = 0;
	}

	/**
	 * Determine if the list is empty
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (this.head.getNext() == null || this.tail.getPrev() == null 
		    || this.size() == 0) {
			return true;
		}
		return false;  
	}

	/**
    * Get the node at the given index 
    * @param index - position in the linked list
    * @return node present in the given index
	* Precondition: List is not empty
	* Index >= 0 and index < number of elements in the list
    */
	protected Node getNth(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException(OUTOFBOUND);
		}
		// Set current node at the first node in list
		Node currNode = this.head.getNext();
		// Traverse the list to find the specific node 
		for (int i = 0; i < index; i++) {
			if (currNode.getNext() != null) {
			    currNode = currNode.getNext();
			}
		}
		return (Node) currNode; 
	}

	/**
	 * Create a new MyListIterator called myList1
	 */
    public ListIterator<E> listIterator() {
        MyListIterator myList1 = new MyListIterator();
		return myList1;
	}

	/**
	 * Create a new MyListIterator called myList2
	 */
    public Iterator<E> iterator() {
        MyListIterator myList2 = new MyListIterator();
		return myList2;
	}

	/**
	 * A class that implements ListIterator interface
	 */
	protected class MyListIterator implements ListIterator<E> {
        
		// Instance Variable
		Node left;
		Node right;
		int idx; // Int value of the index of the next node
		boolean forward;
		boolean canRemoveOrSet;

		/**
		 * Constructor that is used to initialize the iterator.
		 */
		public MyListIterator() {
			// The node that's returned by previous()
			this.left = head;
			// The node that's returned by next()
			this.right = head.getNext();
			this.idx = 0;
			this.forward = true;
			this.canRemoveOrSet = false;
		}

		/**
		 * @return true if there is an element node when going in the forward
		 * (head to tail) direction from the current iterator position,
		 */
		public boolean hasNext() {
			return idx < size();
		}
        
		/**
		 * @return the next element in the list when going forward
		 * Move the iterator forward by one node.
		 */
		public E next() {
            if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E returnNode = right.getElement(); // Return element
			// Move the iterator forward by one node
			this.left = this.right;
			this.right = right.getNext();
			idx++;
			forward = true;
            canRemoveOrSet = true;
			return returnNode;
		}

		/**
		 * @return true if there is an element node when going in the backward
		 * (tail to head) direction from the current iterator position
		 */
		public boolean hasPrevious() {
            return idx > 0;
		}

		/**
		 * @return the next element in the list when going backward
		 * Move the iterator backward by one node.
		 */
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			this.right = right.getPrev();
			this.left = right;
			idx--;
			forward = false;
			canRemoveOrSet = true;
			return this.right.getElement();
		}

		/**
		 * @return the index of the element 
		 * that would be returned by a call to next()
		 * Return the list size if at the end of the list.
		 */
		public int nextIndex() {
		    return idx;
		}

		/**
		 * @return the index of the element 
		 * that would be returned by a call to previous()
		 * Return -1 if at the start of the list
		 */
		public int previousIndex() {
			return idx - 1;
		}

		/**
		 * Insert the given item into the list immediately 
		 * before the element that would be returned by next()
		 * Return newly added item if previous() is called immediately following add
		 * Current index of the list iterator is increased by one.
		 */
		public void add (E element) {
			if (element == null) {
				throw new NullPointerException(NULL);
			}
			Node newNode = new Node(element);
            Node nodeBefore = right.getPrev();
			Node currNode = this.right;
			nodeBefore.setNext(newNode);
			newNode.setNext(currNode);
			currNode.setPrev(newNode);
			newNode.setPrev(nodeBefore);
			idx++;
			size++;
			canRemoveOrSet = false;
			this.right = newNode.getNext();
			this.left = newNode;
		}

		/**
		 * For the node returned by the most recent next/previous call
		 * Replace its value with the new value element
		 */
		public void set (E element) {
			if (element == null) {
				throw new NullPointerException(NULL);
			}
			if (canRemoveOrSet == false) {
				throw new IllegalStateException();
			}
			// If next() was called
            if (forward == true) {
				this.left.setElement(element);
			}
			// If previous() was called
			if (forward == false) {
				this.right.setElement(element);
			}
		}

		/**
		 * Remove the last element node returned by the most recent next/previous call
		 */
		public void remove() {
			if (canRemoveOrSet == false) {
				throw new IllegalStateException();
			}
			Node nodeBefore;
			Node nodeAfter;
			// If next() was called
			if (forward == true) {
                nodeBefore = left.getPrev();
				nodeAfter = left.getNext();
				nodeBefore.setNext(nodeAfter);
				nodeAfter.setPrev(nodeBefore);
				this.left = nodeBefore;
				this.right = nodeAfter;
				idx--;
			}
			// If previous() was called
			if (forward == false) {
                nodeBefore = right.getPrev();
				nodeAfter = right.getNext();
				nodeBefore.setNext(nodeAfter);
				nodeAfter.setPrev(nodeBefore);
				this.left = nodeBefore;
				this.right = nodeAfter;
			}
			canRemoveOrSet = false;
            // Update size
			size--;
		}
	}
}
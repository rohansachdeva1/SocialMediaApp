/**
 * Defines a doubly-linked list class
 * @author Albert Tran
 * @author Daniel Wang
 */

import java.util.NoSuchElementException;

public class LinkedList<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private int length;
    private Node first;
    private Node last;
    private Node iterator;

    /**** CONSTRUCTORS ****/

    /**
     * Instantiates a new LinkedList with default values
     * @postcondition Create an empty linked list object
     */
    public LinkedList() {
    	first = null;
    	last = null;
    	iterator = null;
    	length = 0;
    }

    /**
     * Converts the given array into a LinkedList
     * @param array the array of values to insert into this LinkedList
     * @postcondition Create a Node for every element in the array
     * and add them into a linklist
     */
    public LinkedList(T[] array) {
    	if (array.length == 0 ) {
    		return;
    	}
    	for (int i = 0; i < array.length; i++) {
    		Node n = new Node(array[i]);
    		if (i == 0) {
    			n.prev = n.next = null;
    			first = last = n;
    		} else {
    			n.prev = last;
    			last.next = n;
    			last = n;
    		}
    		length++;
    	}
    }
    
    /**
     * Instantiates a new LinkedList by copying another List
     * @param original the LinkedList to copy
     * @postcondition a new List object, which is an identical,
     * but separate, copy of the LinkedList original
     */
    public LinkedList(LinkedList<T> original) {
    	if (original.getLength() == 0) {
    		return;
    	}
    	original.positionIterator();
    	while (original.iterator != original.last) {
    		Node n = new Node(original.getIterator());
    		if (original.iterator == original.first) {
    			n.prev = null;
    			n.next = null;
    			this.first = this.last = n;
    		} else {
    			n.prev = last;
    			this.last.next = n;
    			this.last = n;
    		}
    		original.iterator = original.iterator.next;
    		this.length++;
    	}
    	Node n = new Node(original.getIterator());
    	n.prev = last;
		this.last.next = n;
		this.last = n;
		this.length++;
    }
    

    /**** ACCESSORS ****/

    /**
     * Returns the value stored in the first node
     * @precondition length != 0
     * @return the value stored at node first
     * @throws NoSuchElementException when List is empty
     */
    public T getFirst() throws NoSuchElementException {
        if (length == 0) {
        	throw new NoSuchElementException("getFirst: " + "List is Empty. No data to access!");
        }
        return first.data;
    }

    /**
     * Returns the value stored in the last node
     * @precondition length != 0
     * @return the value stored in the node last
     * @throws NoSuchElementException when List is empty
     */
    public T getLast() throws NoSuchElementException {
        if (length == 0) {
        	throw new NoSuchElementException("getLast: " + "List is Empty. No data to access!");
        }
        return last.data;
    }

    /**
     * Returns the data stored in the iterator node
     * @precondition !offEnd()
     * @throw NullPointerException Iterator is null
     */
    public T getIterator() throws NullPointerException {
        if (offEnd()) {
        	throw new NullPointerException("getIterator: " + "Iterator is null. No data to access!");
        }
        return iterator.data;
    }

    /**
     * Returns the current length of the LinkedList
     * @return the length of the LinkedList from 0 to n
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns whether the LinkedList is currently empty
     * @return whether the LinkedList is empty
     */
    public boolean isEmpty() {
        return (length == 0);
    }

 

   /**
     * Returns whether the iterator is offEnd, i.e. null
     * @return whether the iterator is null
     */
    public boolean offEnd() {
        return (iterator == null);
    }

 

    /**** MUTATORS ****/

    /**
     * Creates a new first element
     * @param data the data to insert at the front of the LinkedList
     * @postcondition Add a new first element for the LinkedList
     */
    public void addFirst(T data) {
    	Node n = new Node(data);
    	if (length == 0) {
    		first = last = n;
    	} else {
    		n.next = first;
    		first.prev = n;
    		first = n;
    	}
    	length++;
    }

    /**
     * Creates a new last element
     * @param data the data to insert at the end of the LinkedList
     * @postcondition Add a new last element for the LinkedList
     */
    public void addLast(T data) {
        Node n = new Node(data);
        if (length == 0) {
        	first = last = n;
        } else {
        	last.next = n;
        	n.prev = last;
        	last = n;
        }
        length++;
    }
    
    /**
     * Inserts a new element after the iterator
     * @param data the data to insert
     * @precondition iterator != null
     * @throws NullPointerException when Iterator is null
     */
    public void addIterator(T data) throws NullPointerException{
        if (offEnd()) {
        	throw new NullPointerException("addIterator: " + "Iterator is null. Cannot add new node!");
        } else if (iterator == last) {
            addLast(data);
        } else {
        	Node n = new Node(data);
	        n.next = iterator.next;
	        n.prev = iterator;
	        iterator.next.prev = n;
	        iterator.next = n;
	        length++;
        }
    }

    /**
     * removes the element at the front of the LinkedList
     * @precondition length != 0
     * @postcondition element at the front of the LinkedList is removed
     * @throws NoSuchElementException when List is empty
     */
    public void removeFirst() throws NoSuchElementException {
        if (length == 0) {
        	throw new NoSuchElementException("removeFirst: " + "List is empty. No data to remove!");
        } else if (length == 1) {
        	first = last = iterator = null;
        } else {
        	if (iterator == first) {
        		iterator = null;
        	}
        	first = first.next;
        	first.prev = null;   	
        }
        length--;
    }

    /**
     * removes the element at the end of the LinkedList
     * @precondition length != 0
     * @postcondition element at the end of the LinkedList is removed
     * @throws NoSuchElementException when List is empty
     */
    public void removeLast() throws NoSuchElementException {
        if (length == 0) {
        	throw new NoSuchElementException("removeLast: " + "List is empty. No data to remove!");
        } else if (length == 1) {
        	first = last = iterator = null;
        } else {
        	if (iterator == last) {
        		iterator = null;
        	}
        	last = last.prev;
        	last.next = null;
        }
        length--;
    }
    
    /**
     * removes the element referenced by the iterator
     * @precondition !offEnd()
     * @postcondition element pointing at the iterator of the LinkedList is removed
     * @throws NullPointerException when iterator is null
     */
    public void removeIterator() throws NullPointerException {
        if (offEnd()) {
        	throw new NullPointerException("removeIterator: " + "Iterator is null. No data to remove!");
        } else if (length == 1) {
        	first = last = iterator = null;
        } else {
        	if (iterator == first) {
        		iterator.next.prev = null;
        		first = iterator.next;
	        } else if (iterator == last) {
	        	iterator.prev.next = null;
	        	last = iterator.prev;
	        } else {
	        	iterator.next.prev = iterator.prev;
	        	iterator.prev.next = iterator.next;
	        }
        	iterator = null;
        }
	    length--;
    }
    
    /**
     * places the iterator at the first node
     * @precondition !offEnd()
     * @postcondition iterator now points at the beginning of the list
     * @throws NullPointerException when iterator is null
     */
    public void positionIterator() throws NullPointerException {
        if (first == null) {
        	throw new NullPointerException("positionIterator: " + "First is null. No data to access!");
        }
        iterator = first;
    }
    
    /**
     * Moves the iterator one node towards the last
     * @precondition !offEnd()
     * @postcondition move the iterator up by one node towards the last node
     * @throws NullPointerException when iterator is null
     */
    public void advanceIterator() throws NullPointerException {
        if (offEnd()) {
        	throw new NullPointerException("advanceIterator: " + "Iterator is null. Cannot move iterator!");
        } else if (iterator == last) {
        	throw new NoSuchElementException("advanceIterator: " + "Iterator is already on the last node!");
        }
        iterator = iterator.next;
    }
    
    /**
     * Moves the iterator one node towards the first
     * @precondition !offEnd()
     * @postcondition moves the iterator back by one node towards the first node
     * @throws NullPointerException when iterator is null
     */
    public void reverseIterator() throws NullPointerException {
        if (offEnd()) {
        	throw new NullPointerException("reverseIterator: " + "Iterator is null. Cannot move iterator!");
        } else if (iterator == first) {
        	throw new NoSuchElementException("reverseIterator: " + "Iterator is already on the first node!");
        }
        iterator = iterator.prev;
    }

    /**** ADDITIONAL OPERATIONS ****/

    /**
     * Converts the LinkedList to a String, with each value separated by a blank
     * line At the end of the String, place a new line character
     * @return the LinkedList as a String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = first;
        while (temp != null) {
        	result.append(temp.data + " ");
        	temp = temp.next;
        }
        return result.toString() + "\n";
    }
    
    /**
     * Determines whether the given Object is
     * another LinkedList, containing
     * the same data in the same order
     * @param o another Object
     * @return whether there is equality
     */
    @SuppressWarnings("unchecked") //good practice to remove warning here
    @Override public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }
    
    /**CHALLENGE METHODS*/
    
    
    /**
     * Zippers two LinkedLists to create a third List
     * which contains alternating values from this list
     * and the given parameter
     * For example: [1,2,3] and [4,5,6] -> [1,4,2,5,3,6]
     * For example: [1, 2, 3, 4] and [5, 6] -> [1, 5, 2, 6, 3, 4]
     * For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
     * @param list the second LinkedList in the zipper
     * @return a new LinkedList, which is the result of
     * zippering this and list
     * @postcondition this and list are unchanged
     */
    public LinkedList<T> zipperLists(LinkedList<T> list) throws NullPointerException {
    	if (this == null || list == null) {
    		throw new NullPointerException("One or more list is null, Cannot combine");
    	}
    	LinkedList<T> newList = new LinkedList<>();
    	Node temp1 = this.first;
    	Node temp2 = list.first;
        int minLength = Math.min(this.length, list.length);
        for (int i = 0; i < minLength; i++) {
        	newList.addLast(temp1.data);
        	newList.addLast(temp2.data);
        	temp1 = temp1.next;
        	temp2 = temp2.next;   	
        }
        if (this.length < list.length) {
        	for (int i = 0; i < (list.length - minLength); i++) {
        		newList.addLast(temp2.data);
        		temp2 = temp2.next;
        	}
        } else {
        	for (int i = 0; i < (this.length - minLength); i++) {
        		newList.addLast(temp1.data);
        		temp1 = temp1.next;
        	}
        }
        return newList;
    }
    
    /**
     * Determines whether any of the next or prev links
     * in the List are broken, i.e. referencing
     * the wrong Node or null
     * Used by the programmer for error checking
     * @return whether a broken links exist
     */
    public boolean containsBrokenLinks() {
    	Node temp = this.first;
        if (this.length == 1) {
    		return false;
        }
        while(temp != last) {
        	if (temp == this.first) {
        		if (temp != temp.next.prev) {
        			return true;
        		}
        	} else {
        		if (temp != temp.next.prev || temp != temp.prev.next) {
        			return true;
        		}
        	}
        	temp = temp.next;
        }
        return false;
    }

    **
    * Points the iterator at first
    * and then advances it to the
    * specified index
    * @param index the index where
    * the iterator should be placed
    * @precondition 0 < index <= length
    * @throws IndexOutOfBoundsException
    * when precondition is violated
    */
    public void iteratorToIndex(int index) throws IndexOutOfBoundsException{
        //if index is not in bounds, throw IOBE
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }
        positionIterator(); //set position iterator
        
        //iterate through to index
        for (int i = 0; i < index; i++) { 
            advanceIterator();
        }
    }
    
    /**
     * Searches the List for the specified
     * value using the linear  search algorithm
     * @param value the value to search for
     * @return the location of value in the
     * List or -1 to indicate not found
     * Note that if the List is empty we will
     * consider the element to be not found
     * post: position of the iterator remains
     * unchanged
     */
    public int linearSearch(T value) {
        Node looper = first; //set the looper to the first location
        int count = 0; //set count to zero

        while (looper != null) { //iterate through list
            //if the data at the looper equals the passed in value, return the location 'count'
        	if (looper.data.equals(value) == true) {
                return count;
            }
        	// advance looper and increment count 
            looper = looper.next;
            count++;
        }
        //return -1 if value not found
        return -1;
    }
}
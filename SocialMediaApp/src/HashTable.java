/**
 * HashTable.java
 * @author Albert Tran
 * @author Daniel Wang
 * CIS 22C, Lab 5
 */
import java.util.ArrayList;
import java.util.NoSuchElementException;


public class HashTable<T extends Comparable<T>> {

	private ArrayList<LinkedList<T>> Table;
	private int size;

	/**
	 * Constructor for the HashTable class. Initializes the Table to be sized
	 * according to value passed in as a parameter Inserts size empty Lists into the
	 * table. Sets size to 0
	 * 
	 * @param size the table size
	 */
	public HashTable(int size) {
		Table = new ArrayList<LinkedList<T>>(size);
		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList());
		}
		this.size = 0;
	}

	/** Accessors */

	/**
	 * returns the absolute value of the hashCode for a given Object scaled to the
	 * size of the Table
	 * 
	 * @param t the Object
	 * @return the index in the Table
	 */
	public int hash(T t) {
		return Math.abs(t.hashCode()) % Table.size();
	}
	
	public int hash(Interest i) {
		return Math.abs(i.getName().hashCode()) % Table.size();
	}
	public int hash(User u) {
		return Math.abs((u.getUserName() + u.getPassword()).hashCode()) % Table.size();
	}

	/**
	 * counts the number of elements at this index
	 * 
	 * @param index the index in the Table
	 * @precondition 0 <= index < Table.length
	 * @return the count of elements at this index
	 * @throws IndexOutOfBoundsException
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= Table.size()) {
			throw new IndexOutOfBoundsException("index is out of bound");
		}
		return Table.get(index).getLength();
	}

	/**
	 * returns total number of elements in the Table
	 * 
	 * @return total number of elements
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Determines whether the table has any elements
	 * 
	 * @return whether the table is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * searches for a specified element in the Table
	 * 
	 * @param t the element to search for
	 * @return whether the specified element exists in the table
	 */
	public T search(T t) {
		if (t == null) {
			return null;
		}
		int bucket = hash(t);
		if (Table.get(bucket).getLength() == 0) {
			return null;
		}
		Table.get(bucket).positionIterator();
		for (int i = 0; i < Table.get(bucket).getLength(); i++) {
			if (Table.get(bucket).getIterator().equals(t)) {
				return Table.get(bucket).getIterator();
			}
			try {
				Table.get(bucket).advanceIterator();
			} catch (Exception e) {
			}
		}
		return null;
	}
	public T searchUser(User u) {
		if (u == null) {
			return null;
		}
		int bucket = hash(u);
		if (Table.get(bucket).getLength() == 0) {
			return null;
		}
		Table.get(bucket).positionIterator();
		for (int i = 0; i < Table.get(bucket).getLength(); i++) {
			User tempU = (User)Table.get(bucket).getIterator();
			if (tempU.getUserName() == u.getUserName() && tempU.getPassword() == u.getPassword()) {
				return Table.get(bucket).getIterator();
			}
			try {
				Table.get(bucket).advanceIterator();
			} catch (Exception e) {
			}
		}
		return null;
	}

	/** Manipulation Procedures */

	/**
	 * inserts a new element in the Table calls the hash method to determine
	 * placement
	 * 
	 * @param t the element to insert
	 */
	public void insert(T t) {
		if (t == null) {
			return;
		}
		int index = hash(t);
		Table.get(index).addLast(t);
		size++;
	}

	/**
	 * removes the element t from the Table calls the hash method on the key to
	 * determine correct placement has no effect if t is not in the Table
	 * 
	 * @param t the key to remove
	 * @precondition t must be in the table H the element is not in the table
	 */
	public void remove(T t) {
		if (t == null) {
			return;
		}
		int bucket = hash(t);
		if (Table.get(bucket).getLength() == 0) {
			return;
		}
		Table.get(bucket).positionIterator();
		for (int i = 0; i < Table.get(bucket).getLength(); i++) {
			if (Table.get(bucket).getIterator().equals(t)) {
				Table.get(bucket).removeIterator();
				size--;
				return;
			}
			Table.get(bucket).advanceIterator();
		}
	}

	/** Additional Methods */

	/**
	 * Resets the Table back to empty
	 */
	public void clear() {
		int size1 = 0;
		Table = new ArrayList<LinkedList<T>>(size1);
		for (int i = 0; i < size1; i++) {
			Table.set(i, new LinkedList());
		}
		this.size = 0;
	}

	/**
	 * Prints to the console all the keys at a specified bucket in the Table. Each
	 * element displayed on its own line, with a blank line separating each element
	 * Above the elements, prints the message "Printing bucket #<bucket>:" Note that
	 * there is no <> in the output
	 * 
	 * @param bucket the index in the Table
	 * @throws IndexOutOfBoundsException
	 */
	public void printBucket(int bucket) throws IndexOutOfBoundsException {
		if (bucket < 0 || bucket > Table.size()) {
			throw new IndexOutOfBoundsException("Index is out of bound");
		}
		System.out.println("Printing bucket #" + bucket + "\n");
		System.out.print(Table.get(bucket).toString());
	}

	/**
	 * Prints the first element at each bucket along with a count of the total
	 * elements with the message "+ <count> -1 more at this bucket." Each bucket
	 * separated with to blank lines. When the bucket is empty, prints the message
	 * "This bucket is empty." followed by two blank lines
	 */
	public void printTable() {
		for (int i = 0; i < Table.size(); i++) {
			System.out.println("Bucket: " + i);
			if (Table.get(i).getLength() == 0) {
				System.out.println("This bucket is empty." + "\n\n");
			} else {
				System.out.println(Table.get(i).getFirst());
				System.out.println("+ " + (Table.get(i).getLength() - 1) + " more at this bucket" + "\n\n");
			}
		}
	}

	/**
	 * Starting at the first bucket, and continuing in order until the last bucket,
	 * concatenates all elements at all buckets into one String
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < Table.size(); i++) {
			if (Table.get(i).getLength() == 0) {
				continue;
			}
			Table.get(i).positionIterator();
			for (int j = 0; j < Table.get(i).getLength(); j++) {
				result.append(Table.get(i).getIterator() + "\n");
				try {
					Table.get(i).advanceIterator();
				} catch (Exception e) {
				}
			}
		}
		return result.toString() + "\n";
	}

}
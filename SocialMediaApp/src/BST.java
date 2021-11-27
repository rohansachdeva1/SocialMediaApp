/**
 * BST.java
 * @author Sze Chung Wong
 * @author Sze Wan Wong
 * CIS 22C Lab 4
 */


import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> {
    private class Node {
        private T data;
        private Node left;
        private Node right;
        
        public Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }
    
    private Node root;
    
    /***CONSTRUCTORS***/
    
    /**
     * Default constructor for BST
     * sets root to null
     */
    public BST() {
    	root = null;
    }
   
    /**
     * Copy constructor for BST
     * @param bst the BST to make
     * a copy of 
     */
    public BST(BST<T> bst) {
    	if (bst != null) {
		   copyHelper(bst.root);
    	}
    }
    
    /**
     * Helper method for copy constructor
     * @param node the node containing
     * data to copy
     */
    private void copyHelper(Node node) {
    	if (node != null) { // if it's null, does nothing (base case)
	    	insert(node.data);
	    	copyHelper(node.left);
	    	copyHelper(node.right);
    	}
     }
    
    /***ACCESSORS***/
    
    /**
     * Returns the data stored in the root
     * @precondition !isEmpty()
     * @return the data stored in the root
     * @throws NoSuchElementException when
     * precondition is violated
     */
    public T getRoot() throws NoSuchElementException {
    	if (root == null) {
    		throw new NoSuchElementException();
    	}
    	return root.data;
    }
    
    /**
     * Determines whether the tree is empty
     * @return whether the tree is empty
     */
    public boolean isEmpty() {
    	if (root == null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * Returns the current size of the 
     * tree (number of nodes)
     * @return the size of the tree
     */
    public int getSize() {
    	return getSize(root);
    }
    
    /**
     * Helper method for the getSize method
     * @param node the current node to count
     * @return the size of the tree
     */
    private int getSize(Node node) {
    	if (node == null) { //base case
    		return 0;
    	} else {
    		return getSize(node.left) + getSize(node.right) + 1;
    	}
    }
    
    /**
     * Returns the height of tree by
     * counting edges.
     * @return the height of the tree
     */
    public int getHeight() {
        return getHeight(root);
    }
    
    /**
     * Helper method for getHeight method
     * @param node the current
     * node whose height to count
     * @return the height of the tree
     */
    private int getHeight(Node node) {
    	if (node == null) { //base case
    		return -1;
    	} else {
    		return Math.max(getHeight(node.left)+1, getHeight(node.right)+1);
    	}
    }
    
    /**
     * Returns the smallest value in the tree
     * @precondition !isEmpty()
     * @return the smallest value in the tree
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public T findMin() throws NoSuchElementException{
    	if (getSize() == 0) {
    		throw new NoSuchElementException();
    	}
        return findMin(root);
    }
    
    /**
     * Helper method to findMin method
     * @param node the current node to check
     * if it is the smallest
     * @return the smallest value in the tree
     */
    private T findMin(Node node) {
    	if (node.left == null) {
    		return node.data;
    	}
    	else {
    		return findMin(node.left);
    	}
        
    }
    
    /**
     * Returns the largest value in the tree
     * @precondition !isEmpty()
     * @return the largest value in the tree
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public T findMax() throws NoSuchElementException{
    	if (getSize() == 0) {
    		throw new NoSuchElementException();
    	}
        return findMax(root);
    }
    
    /**
     * Helper method to findMax method
     * @param node the current node to check
     * if it is the largest
     * @return the largest value in the tree
     */
    private T findMax(Node node) {
    	if (node.right == null) {
    		return node.data;
    	}
    	else {
    		return findMax(node.right);
    	}
        
    }
    
    /***MUTATORS***/
    
    /**
     * Inserts a new node in the tree
     * @param data the data to insert
     */
    public void insert(T data) {
    	if (root == null) {
    		root = new Node(data);
    	}
    	else {
    		insert(data, root);
    	}
    }
    
    /**
     * Helper method to insert
     * Inserts a new value in the tree
     * @param data the data to insert
     * @param node the current node in the
     * search for the correct location
     * in which to insert
     */
    private void insert(T data, Node node) {
    	Node newNode = new Node(data);
    	Node currentNode = node;
    	if ((data.compareTo(currentNode.data) <= 0)) { //This goes to the left
			if (currentNode.left == null) { //If left is empty, it becomes node.left
				currentNode.left = newNode; 
			}
			else {
				currentNode = currentNode.left; // if left is not empty, recursion
				insert(data, currentNode);
			}
    	} else {
    		if (currentNode.right == null) { // same as left comparison
				currentNode.right = newNode;
			}
			else {
				currentNode = currentNode.right; //same a left comparison
				insert(data, currentNode);
			}
    	}
		
    
    }
    
    /**
     * Removes a value from the BST
     * @param data the value to remove
     * @precondition !isEmpty()
     * @precondition the data is located in the tree
     * @throws IllegalStateException when BST is empty
     */
    public void remove(T data) throws IllegalStateException {
    	if (getSize() == 0) {
    		throw new IllegalStateException();
    	} else if (!search(data)) {
    		return;
    	} else {
    		root = remove(data, root);
    	}
    }
    
    /**
     * Helper method to the remove method //xx why do we return node here? what if you remove root??
     * @param data the data to remove
     * @param node the current node
     * @return an updated reference variable
     */
    private Node remove(T data, Node node) {
    	if (node == null) {
    		return node;
    	} else if (data.compareTo(node.data) < 0) {
    		node.left = remove(data, node.left);
    	} else if (data.compareTo(node.data) > 0) {
    		node.right = remove(data, node.right);
    	} else {
    		if (getHeight(node) == 0) {
    			node = null;
    		} else if ((node.left != null) && (node.right == null)) {
    			node = node.left;
    		} else if ((node.right != null) && (node.left == null)) {
    			node = node.right;
    		} else {
    			node.data = findMin(node.right);
    			node.right = remove(node.data, node.right);
    		}
    		
    	}
    	return node;
        
    }
        
    /***ADDITIONAL OPERATIONS***/
    
    /**
     * Searches for a specified value
     * in the tree
     * @param data the value to search for
     * @return whether the value is stored
     * in the tree
     */
    public boolean search(T data) {
    	if (root != null) {
    	}
    	return search(data, root);
    }
    
    /**
     * Helper method for the search method
     * @param data the data to search for
     * @param node the current node to check
     * @return whether the data is stored
     * in the tree
     */
    private boolean search(T data, Node node) {
    	if (node == null) { //this is a little bit different than the lecture but it works the same
    		return false;
    	}
    	else if (data.compareTo(node.data) == 0) {
    		return true;
    	}
    	else {
    		if (data.compareTo(node.data) < 0) {
    			return search(data, node.left);
    		}
    		else {
    			return search(data, node.right);
    		}
    	}
    }
    
    /**
     * Determines whether a BST is balanced
     * using the definition given in the course
     * lesson notes
     * Note that we will consider an empty tree
     * to be trivially balanced
     * @return whether the BST is balanced
     */
    public boolean isBalanced() {
        if(root == null) {
            return true;
        }
        return isBalanced(root);
    }
    
    /**
     * Helper method for isBalanced
     * to determine if a BST is balanced
     * @param n a Node in the tree
     * @return whether the BST is balanced
     * at the level of the given Node
     */
    private boolean isBalanced(Node n) {
        if(n != null) {
            if(Math.abs(getHeight(n.left) - getHeight(n.right)) > 1) {
                return false;
            }
            return isBalanced(n.left) && isBalanced(n.right);
        }
        return true;
    }
    
    /**
     * Returns a String containing the data
     * in post order
     * @return a String of data in post order
     */
    public String preOrderString() {
    	StringBuilder result = new StringBuilder();
    	preOrderString(root, result);
        return result.toString() + "\n";
    }
    
    /**
     * Helper method to preOrderString
     * Inserts the data in pre order into a String
     * @param node the current Node
     * @param preOrder a String containing the data
     */
    private void preOrderString(Node node, StringBuilder preOrder) {
    	if (node == null) {
    		return;
    	}
    	else {
    		preOrder.append(node.data + " ");
    		preOrderString(node.left, preOrder);
    		preOrderString(node.right, preOrder);
    	}
       
    }
    
    /**
     * Returns a String containing the data
     * in order
     * @return a String of data in order
     */
    public String inOrderString() {
    	StringBuilder result = new StringBuilder();
    	inOrderString(root, result);
        return result.toString() + "\n";
    }
    
    /**
     * Helper method to inOrderString
     * Inserts the data in order into a String
     * @param node the current Node
     * @param inOrder a String containing the data
     */
    private void inOrderString(Node node, StringBuilder inOrder) {
    	if (node == null) {
    		return;
    	}
    	else {
    		inOrderString(node.left, inOrder);
    		inOrder.append(node.data);
    		inOrderString(node.right, inOrder);
    	}
    }
    
    /**
     * Returns a String containing the data
     * in post order
     * @return a String of data in post order
     */
    public String postOrderString() {
    	StringBuilder result = new StringBuilder();
    	postOrderString(root, result);
        return result.toString() + "\n";
    }
    
    /**
     * Helper method to postOrderString
     * Inserts the data in post order into a String
     * @param node the current Node
     * @param postOrder a String containing the data
     */
    private void postOrderString(Node node, StringBuilder postOrder) {
    	if (node == null) {
    		return;
    	}
    	else {
    		postOrderString(node.left, postOrder);
    		postOrderString(node.right, postOrder);
    		postOrder.append(node.data + " ");
    	}
    }
    
    
    /**
     * Challenging Methods
     */
    
    /**
     * Creates a BST of minimal height given an array of values
     * @param array the list of values to insert
     * @precondition array must be sorted in ascending order
     * @throws IllegalArgumentException when the array is
     * unsorted
     */
	public BST(T[] array) throws IllegalArgumentException {
		if (array == null) {
			return;
		}
		if (!isSorted(array)) {
			throw new IllegalArgumentException();
		}
		helperBST(array, 0, array.length);
	}
	

	private void helperBST(T[] array, int beginning, int end) { //end is exclusive, will out of bound if array[end]
		if ((end-beginning) == 2) {
			insert(array[beginning]);
			insert(array[end-1]);
		}
		else if ((end-beginning) == 1) {
			insert(array[beginning]);
		}
		else {
			int centerIndex = ((end-beginning)/2)+beginning; //making a centerIndex. if length is even, it will load the node on the right.
			Node temp = new Node(array[centerIndex]);
			insert(temp.data);
			helperBST(array, beginning, centerIndex); //cutting the array into half, separated by centerIndex (left side)
			helperBST(array, centerIndex+1, end); //right side of centerIndex
		}
	}
	
	private boolean isSorted(T[] array) {
		for (int i = 0; i < array.length - 1; ++i) {
	        if (array[i].compareTo(array[i + 1]) > 0)
	            return false;
	    }
		return true;
	}
}
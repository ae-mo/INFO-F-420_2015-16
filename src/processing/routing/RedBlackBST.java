/******************************************************************************
 *  Compilation:  javac RedBlackBST.java
 *  Execution:    java RedBlackBST < input.txt
 *  Dependencies: StdIn.java StdOut.java  
 *  Data files:   http://algs4.cs.princeton.edu/33balanced/tinyST.txt  
 *    
 *  A symbol table implemented using a left-leaning red-black BST.
 *  This is the 2-3 version.
 *
 *  Note: commented out assertions because DrJava now enables assertions
 *        by default.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java RedBlackBST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 ******************************************************************************/

import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.lang.Comparable;

/**
 *  The <tt>BST</tt> class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>_size</em>, and <em>is-empty</em> methods.
 *  It also provides ordered methods for finding the <em>minimum</em>,
 *  <em>maximum</em>, <em>floor</em>, and <em>ceiling</em>.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be <tt>null</tt>&mdash;setting the
 *  value associated with a key to <tt>null</tt> is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a left-leaning red-black BST. It requires that
 *  the key type implements the <tt>Comparable</tt> interface and calls the
 *  <tt>compareTo()</tt> and method to compare two keys. It does not call either
 *  <tt>equals()</tt> or <tt>hashCode()</tt>.
 *  The <em>put</em>, <em>contains</em>, <em>remove</em>, <em>minimum</em>,
 *  <em>maximum</em>, <em>ceiling</em>, and <em>floor</em> operations each take
 *  logarithmic time in the worst case, if the tree becomes unbalanced.
 *  The <em>_size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/33balanced">Section 3.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For other implementations, see {@link ST}, {@link BinarySearchST},
 *  {@link SequentialSearchST}, {@link BST},
 *  {@link SeparateChainingHashST}, and {@link LinearProbingHashST},
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

public class RedBlackBST<Key extends Comparable<Key>, Value> {

	private static final boolean RED   = true;
	private static final boolean BLACK = false;
	Node lastInserted;

	private Node root;     // root of the BST

	// BST helper node data type
	final class Node {
		private Key _key;           // key
		Value val;         // associated data
		Node left, right;  // links to left and right subtrees
		private boolean colored;     // color of parent link
		private int N;             // subtree count

		public Node(Key _key, Value val, boolean colored, int N) {
			this._key = _key;
			this.val = val;
			this.colored = colored;
			this.N = N;
		}
	}

	/**
	 * Initializes an empty symbol table.
	 */
	public RedBlackBST() {

		this.lastInserted = null;
	}

	/***************************************************************************
	 *  Node helper methods.
	 ***************************************************************************/
	// is node x red; false if x is null ?
	private boolean isRed(Node x) {
		if (x == null) return false;
		return x.colored == RED;
	}

	// number of node in subtree rooted at x; 0 if x is null
	private int _size(Node x) {
		if (x == null) return 0;
		return x.N;
	} 


	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * @return the number of key-value pairs in this symbol table
	 */
	public int _size() {
		return _size(root);
	}

	/**
	 * Is this symbol table empty?
	 * @return <tt>true</tt> if this symbol table is empty and <tt>false</tt> otherwise
	 */
	public boolean isEmpty() {
		return root == null;
	}


	/***************************************************************************
	 *  Standard BST search.
	 ***************************************************************************/

	/**
	 * Returns the value associated with the given key.
	 * @param _key the key
	 * @return the value associated with the given key if the key is in the symbol table
	 *     and <tt>null</tt> if the key is not in the symbol table
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public Value getValue(Key _key) {
		if (_key == null) throw new NullPointerException("argument to getValue() is null");
		return getValue(root, _key);
	}

	// value associated with the given key in subtree rooted at x; null if no such key
	private Value getValue(Node x, Key _key) {
		while (x != null) {
			int cmp = _key.compareTo(x._key);
			System.out.println("Comparison: " + cmp);
			if      (cmp < 0) x = x.left;
			else if (cmp > 0) x = x.right;
			else              return x.val;
		}
		return null;
	}

	/**
	 * Does this symbol table contain the given key?
	 * @param key the key
	 * @return <tt>true</tt> if this symbol table contains <tt>_key</tt> and
	 *     <tt>false</tt> otherwise
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public boolean contains(Key _key) {
		return getValue(_key) != null;
	}

	/***************************************************************************
	 *  Red-black tree insertion.
	 ***************************************************************************/

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is <tt>null</tt>.
	 *
	 * @param _key the key
	 * @param val the value
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public void put(Key _key, Value val) {
		if (_key == null) throw new NullPointerException("first argument to put() is null");
		if (val == null) {
			delete(_key);
			return;
		}

		root = put(root, _key, val);
		root.colored = BLACK;
		// assert check();
	}

	// insert the key-value pair in the subtree rooted at h
	private Node put(Node h, Key _key, Value val) { 
		if (h == null) {

			this.lastInserted = new Node(_key, val, RED, 1);
			return lastInserted;

		}

		int cmp = _key.compareTo(h._key);
		if      (cmp < 0) h.left  = put(h.left,  _key, val); 
		else if (cmp > 0) h.right = put(h.right, _key, val); 
		else              h.val   = val;

		// fix-up any right-leaning links
		if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
		if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
		h.N = _size(h.left) + _size(h.right) + 1;

		return h;
	}

	/***************************************************************************
	 *  Red-black tree deletion.
	 ***************************************************************************/

	/**
	 * Removes the smallest key and associated value from the symbol table.
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("BST underflow");

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.colored = RED;

		root = deleteMin(root);
		if (!isEmpty()) root.colored = BLACK;
		// assert check();
	}

	// delete the key-value pair with the minimum key rooted at h
	private Node deleteMin(Node h) { 
		if (h.left == null)
			return null;

		if (!isRed(h.left) && !isRed(h.left.left))
			h = moveRedLeft(h);

		h.left = deleteMin(h.left);
		return balance(h);
	}


	/**
	 * Removes the largest key and associated value from the symbol table.
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("BST underflow");

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.colored = RED;

		root = deleteMax(root);
		if (!isEmpty()) root.colored = BLACK;
		// assert check();
	}

	// delete the key-value pair with the maximum key rooted at h
	private Node deleteMax(Node h) { 
		if (isRed(h.left))
			h = rotateRight(h);

		if (h.right == null)
			return null;

		if (!isRed(h.right) && !isRed(h.right.left))
			h = moveRedRight(h);

		h.right = deleteMax(h.right);

		return balance(h);
	}

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 *
	 * @param  _key the key
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public void delete(Key _key) { 
		if (_key == null) throw new NullPointerException("argument to delete() is null");
		if (!contains(_key)) return;
		System.out.println("ohyes");
		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.colored = RED;

		root = delete(root, _key);
		if (!isEmpty()) root.colored = BLACK;
		// assert check();
	}

	// delete the key-value pair with the given key rooted at h
	private Node delete(Node h, Key _key) { 
		// assert getValue(h, key) != null;

		if (_key.compareTo(h._key) < 0)  {
			if (!isRed(h.left) && !isRed(h.left.left))
				h = moveRedLeft(h);
			h.left = delete(h.left, _key);
		}
		else {
			if (isRed(h.left))
				h = rotateRight(h);
			if (_key.compareTo(h._key) == 0 && (h.right == null))
				return null;
			if (!isRed(h.right) && !isRed(h.right.left))
				h = moveRedRight(h);
			if (_key.compareTo(h._key) == 0) {
				Node x = _min(h.right);
				h._key = x._key;
				h.val = x.val;
				// h.val = getValue(h.right, _min(h.right)._key);
				// h._key = _min(h.right)._key;
				h.right = deleteMin(h.right);
			}
			else h.right = delete(h.right, _key);
		}
		return balance(h);
	}

	/***************************************************************************
	 *  Red-black tree helper functions.
	 ***************************************************************************/

	// make a left-leaning link lean to the right
	private Node rotateRight(Node h) {
		// assert (h != null) && isRed(h.left);
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.colored = x.right.colored;
		x.right.colored = RED;
		x.N = h.N;
		h.N = _size(h.left) + _size(h.right) + 1;
		return x;
	}

	// make a right-leaning link lean to the left
	private Node rotateLeft(Node h) {
		// assert (h != null) && isRed(h.right);
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.colored = x.left.colored;
		x.left.colored = RED;
		x.N = h.N;
		h.N = _size(h.left) + _size(h.right) + 1;
		return x;
	}

	// flip the colors of a node and its two children
	private void flipColors(Node h) {
		// h must have opposite color of its two children
		// assert (h != null) && (h.left != null) && (h.right != null);
		// assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
		//    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
		h.colored = !h.colored;
		h.left.colored = !h.left.colored;
		h.right.colored = !h.right.colored;
	}

	// Assuming that h is red and both h.left and h.left.left
	// are black, make h.left or one of its children red.
	private Node moveRedLeft(Node h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

		flipColors(h);
		if (isRed(h.right.left)) { 
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private Node moveRedRight(Node h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
		flipColors(h);
		if (isRed(h.left.left)) { 
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	// restore red-black tree invariant
	private Node balance(Node h) {
		// assert (h != null);

		if (isRed(h.right))                      h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))     flipColors(h);

		h.N = _size(h.left) + _size(h.right) + 1;
		return h;
	}


	/***************************************************************************
	 *  Utility functions.
	 ***************************************************************************/

	/**
	 * Returns the height of the BST (for debugging).
	 * @return the height of the BST (a 1-node tree has height 0)
	 */
	public int height() {
		return height(root);
	}
	private int height(Node x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	/***************************************************************************
	 *  Ordered symbol table methods.
	 ***************************************************************************/

	/**
	 * Returns the smallest key in the symbol table.
	 * @return the smallest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public Key _min() {
		if (isEmpty()) throw new NoSuchElementException("called _min() with empty symbol table");
		return _min(root)._key;
	} 

	// the smallest key in subtree rooted at x; null if no such key
	public Node _min(Node x) { 
		// assert x != null;
		if (x.left == null) return x; 
		else                return _min(x.left); 
	} 

	/**
	 * Returns the largest key in the symbol table.
	 * @return the largest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public Key _max() {
		if (isEmpty()) throw new NoSuchElementException("called _max() with empty symbol table");
		return _max(root)._key;
	} 

	// the largest key in the subtree rooted at x; null if no such key
	private Node _max(Node x) { 
		// assert x != null;
		if (x.right == null) return x; 
		else                 return _max(x.right); 
	} 


	/**
	 * Returns the largest key in the symbol table less than or equal to <tt>_key</tt>.
	 * @param _key the key
	 * @return the largest key in the symbol table less than or equal to <tt>_key</tt>
	 * @throws NoSuchElementException if there is no such key
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public Key _floor(Key _key) {
		if (_key == null) throw new NullPointerException("argument to _floor() is null");
		if (isEmpty()) throw new NoSuchElementException("called _floor() with empty symbol table");
		Node x = _floor(root, _key);
		if (x == null) return null;
		else           return x._key;
	}    

	// the largest key in the subtree rooted at x less than or equal to the given key
	private Node _floor(Node x, Key _key) {
		if (x == null) return null;
		int cmp = _key.compareTo(x._key);
		if (cmp == 0) return x;
		if (cmp < 0)  return _floor(x.left, _key);
		Node t = _floor(x.right, _key);
		if (t != null) return t; 
		else           return x;
	}

	/**
	 * Returns the smallest key in the symbol table greater than or equal to <tt>_key</tt>.
	 * @param _key the key
	 * @return the smallest key in the symbol table greater than or equal to <tt>_key</tt>
	 * @throws NoSuchElementException if there is no such key
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public Key ceiling(Key _key) {
		if (_key == null) throw new NullPointerException("argument to ceiling() is null");
		if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
		Node x = ceiling(root, _key);
		if (x == null) return null;
		else           return x._key;  
	}

	// the smallest key in the subtree rooted at x greater than or equal to the given key
	private Node ceiling(Node x, Key _key) {  
		if (x == null) return null;
		int cmp = _key.compareTo(x._key);
		if (cmp == 0) return x;
		if (cmp > 0)  return ceiling(x.right, _key);
		Node t = ceiling(x.left, _key);
		if (t != null) return t; 
		else           return x;
	}

	/**
	 * Return the kth smallest key in the symbol table.
	 * @param k the order statistic
	 * @return the kth smallest key in the symbol table
	 * @throws IllegalArgumentException unless <tt>k</tt> is between 0 and
	 *     <em>N</em> &minus; 1
	 */
	public Key select(int k) {
		if (k < 0 || k >= _size()) throw new IllegalArgumentException();
		Node x = select(root, k);
		return x._key;
	}

	// the key of rank k in the subtree rooted at x
	private Node select(Node x, int k) {
		// assert x != null;
		// assert k >= 0 && k < _size(x);
		int t = _size(x.left); 
		if      (t > k) return select(x.left,  k); 
		else if (t < k) return select(x.right, k-t-1); 
		else            return x; 
	} 

	/**
	 * Return the number of keys in the symbol table strictly less than <tt>_key</tt>.
	 * @param _key the key
	 * @return the number of keys in the symbol table strictly less than <tt>_key</tt>
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public int rank(Key _key) {
		if (_key == null) throw new NullPointerException("argument to rank() is null");
		return rank(_key, root);
	} 

	// number of keys less than key in the subtree rooted at x
	private int rank(Key _key, Node x) {
		if (x == null) return 0; 
		int cmp = _key.compareTo(x._key); 
		if      (cmp < 0) return rank(_key, x.left); 
		else if (cmp > 0) return 1 + _size(x.left) + rank(_key, x.right); 
		else              return _size(x.left); 
	} 

	/***************************************************************************
	 *  Range count and range search.
	 ***************************************************************************/

	/**
	 * Returns all keys in the symbol table as an <tt>Iterable</tt>.
	 * To iterate over all of the keys in the symbol table named <tt>st</tt>,
	 * use the foreach notation: <tt>for (Key _key : st._keys())</tt>.
	 * @return all keys in the sybol table as an <tt>Iterable</tt>
	 */
	public Iterable<Key> _keys() {
		if (isEmpty()) return new LinkedList<Key>();
		return _keys(_min(), _max());
	}

	/**
	 * Returns all keys in the symbol table in the given range,
	 * as an <tt>Iterable</tt>.
	 * @return all keys in the sybol table between <tt>lo</tt> 
	 *    (inclusive) and <tt>hi</tt> (exclusive) as an <tt>Iterable</tt>
	 * @throws NullPointerException if either <tt>lo</tt> or <tt>hi</tt>
	 *    is <tt>null</tt>
	 */
	public Iterable<Key> _keys(Key lo, Key hi) {
		if (lo == null) throw new NullPointerException("first argument to keys() is null");
		if (hi == null) throw new NullPointerException("second argument to keys() is null");

		LinkedList<Key> queue = new LinkedList<Key>();
		// if (isEmpty() || lo.compareTo(hi) > 0) return queue;
		_keys(root, queue, lo, hi);
		return queue;
	} 

	// add the keys between lo and hi in the subtree rooted at x
	// to the queue
	private void _keys(Node x, LinkedList<Key> queue, Key lo, Key hi) { 
		if (x == null) return; 
		int cmplo = lo.compareTo(x._key); 
		int cmphi = hi.compareTo(x._key); 
		if (cmplo < 0) _keys(x.left, queue, lo, hi); 
		if (cmplo <= 0 && cmphi >= 0) queue.add(x._key); 
		if (cmphi > 0) _keys(x.right, queue, lo, hi); 
	} 

	/**
	 * Returns the number of keys in the symbol table in the given range.
	 * @return the number of keys in the sybol table between <tt>lo</tt> 
	 *    (inclusive) and <tt>hi</tt> (exclusive)
	 * @throws NullPointerException if either <tt>lo</tt> or <tt>hi</tt>
	 *    is <tt>null</tt>
	 */
	public int _size(Key lo, Key hi) {
		if (lo == null) throw new NullPointerException("first argument to _size() is null");
		if (hi == null) throw new NullPointerException("second argument to _size() is null");

		if (lo.compareTo(hi) > 0) return 0;
		if (contains(hi)) return rank(hi) - rank(lo) + 1;
		else              return rank(hi) - rank(lo);
	}


	/***************************************************************************
	 *  Check integrity of red-black tree data structure.
	 ***************************************************************************/
	private boolean check() {
		if (!isBST())            System.out.println("Not in symmetric order");
		if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
		if (!isRankConsistent()) System.out.println("Ranks not consistent");
		if (!is23())             System.out.println("Not a 2-3 tree");
		if (!isBalanced())       System.out.println("Not balanced");
		return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
	}

	// does this binary tree satisfy symmetric order?
	// Note: this test also ensures that data structure is a binary tree since order is strict
	private boolean isBST() {
		return isBST(root, null, null);
	}

	// is the tree rooted at x a BST with all keys strictly between min and max
	// (if min or max is null, treat as empty constraint)
	// Credit: Bob Dondero's elegant solution
	private boolean isBST(Node x, Key min, Key max) {
		if (x == null) return true;
		if (min != null && x._key.compareTo(min) <= 0) return false;
		if (max != null && x._key.compareTo(max) >= 0) return false;
		return isBST(x.left, min, x._key) && isBST(x.right, x._key, max);
	} 

	// are the _size fields correct?
	private boolean isSizeConsistent() { return isSizeConsistent(root); }
	private boolean isSizeConsistent(Node x) {
		if (x == null) return true;
		if (x.N != _size(x.left) + _size(x.right) + 1) return false;
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	} 

	// check that ranks are consistent
	private boolean isRankConsistent() {
		for (int i = 0; i < _size(); i++)
			if (i != rank(select(i))) return false;
		for (Key _key : _keys())
			if (_key.compareTo(select(rank(_key))) != 0) return false;
		return true;
	}

	// Does the tree have no red right links, and at most one (left)
	// red links in a row on any path?
	private boolean is23() { return is23(root); }
	private boolean is23(Node x) {
		if (x == null) return true;
		if (isRed(x.right)) return false;
		if (x != root && isRed(x) && isRed(x.left))
			return false;
		return is23(x.left) && is23(x.right);
	} 

	// do all paths from root to leaf have same number of black edges?
	private boolean isBalanced() { 
		int black = 0;     // number of black links on path from root to min
		Node x = root;
		while (x != null) {
			if (!isRed(x)) black++;
			x = x.left;
		}
		return isBalanced(root, black);
	}

	// does every path from the root to a leaf have the given number of black links?
	private boolean isBalanced(Node x, int black) {
		if (x == null) return black == 0;
		if (!isRed(x)) black--;
		return isBalanced(x.left, black) && isBalanced(x.right, black);
	} 

}
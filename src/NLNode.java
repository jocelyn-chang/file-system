import java.util.Comparator;
import java.util.Iterator;

/**
 * NLNode represents a class that represents the nodes of the non-linear data structure
 * storing the information of the file system.
 * 
 * @author jocelynchang
 *
 */

public class NLNode<T> {
	/**
	 * Refers to the parent of this node.
	 */
	private NLNode<T> parent;
	/**
	 * Refers to the list storing the children of this node.
	 */
	private ListNodes<NLNode<T>> children;
	/**
	 * Refers to the data object stored in this node.
	 */
	//reference to the data object stored in this node
	private T data;
	
	/**
	 * Creates nodes with null parent and data, and an empty list of children.
	 */
	public NLNode() {
		parent = null;
		data = null;
		children = new ListNodes<NLNode<T>>();
	}
	
	/**
	 * Creates nodes with a set parent and data, and an empty list of children.
	 */
	public NLNode(T d, NLNode<T> p) {
		parent = p;
		data = d;
		children = new ListNodes<NLNode<T>>();
	}
	
	/**
	 * Sets the parent of this node with the given node.
	 * 
	 * @param p  node to be set as parent
	 */
	public void setParent(NLNode<T> p) {
		//sets parent of this node to p
		parent = p;
	}
	
	/**
	 * Returns the parent of this node.
	 * 
	 * @return  the parent of this node
	 */
	public NLNode<T> getParent() {
		//returns the parent of this node
		return parent;
	}
	
	/**
	 * Adds the given child node to the list of children of this node, and sets the new parent of the child node.
	 * 
	 * @param newChild  the child node to be added
	 */
	public void addChild(NLNode<T> newChild) {
		newChild.setParent(this);
		children.add(newChild);
	}
	
	/**
	 * Returns an iterator containing the children of this node.
	 * 
	 * @return  the iterator of the children of this node
	 */
	public Iterator<NLNode<T>> getChildren() {
		return children.getList();
	}
	
	/**
	 * Returns an iterator containing the children of this node sorted in specified order.
	 * 
	 * @param sorter  specified order to sort the children of this node
	 * @return  the iterator of the sorted list of children of this node
	 */
	public Iterator<NLNode<T>> getChildren(Comparator<NLNode<T>> sorter) {
		return children.sortedList(sorter);
	}
	
    /**
     * Returns the data stored in this node.
     * 
     * @return  the data of this node
     */
	public T getData() {
		return data;
	}
	
	/**
	 * Sets the data stored in this node with the given data.
	 * 
	 * @param d  the data to be stored in this node
	 */
	public void setData(T d) {
		data = d;
	}
}

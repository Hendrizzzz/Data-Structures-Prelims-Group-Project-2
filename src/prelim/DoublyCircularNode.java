package prelim;

/**
 * Represents a node in a doubly circular linked list.
 * Each node contains a data element, and references to both the next and previous nodes
 * in the circular list.
 *
 * @param <T> the type of data stored in the node
 */
public class DoublyCircularNode<T> {
    private T datum;
    private DoublyCircularNode<T> nextLink;
    private DoublyCircularNode<T> prevLink;

    /**
     * Constructs a new {@code DoublyCircularNode} with the specified data.
     * The next and previous links are initially set to {@code null}.
     *
     * @param datum the data to store in the node
     */
    public DoublyCircularNode(T datum) {
        this.datum = datum;
        this.nextLink = null;
        this.prevLink = null;
    }

    /**
     * Returns the data stored in this node.
     *
     * @return the data stored in this node
     */
    public T getData() {
        return datum;
    }

    /**
     * Sets the data for this node.
     *
     * @param data the data to set in this node
     */
    public void setData(T data) {
        this.datum = data;
    }

    /**
     * Returns the next node in the circular linked list.
     *
     * @return the next node in the list
     */
    public DoublyCircularNode<T> getNext() {
        return nextLink;
    }

    /**
     * Sets the next node in the circular linked list.
     *
     * @param next the next node to set
     */
    public void setNext(DoublyCircularNode<T> next) {
        this.nextLink = next;
    }

    /**
     * Returns the previous node in the circular linked list.
     *
     * @return the previous node in the list
     */
    public DoublyCircularNode<T> getPrev() {
        return prevLink;
    }

    /**
     * Sets the previous node in the circular linked list.
     *
     * @param prev the previous node to set
     */
    public void setPrev(DoublyCircularNode<T> prev) {
        this.prevLink = prev;
    }

    @Override
    public String toString() {
        return "DoublyCircularNode{" +
                "datum=" + datum +
                ", nextLink=" + nextLink +
                ", prevLink=" + prevLink +
                '}';
    }
}

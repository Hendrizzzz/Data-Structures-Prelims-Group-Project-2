package prelim;

import java.util.Objects;

/**
 * The {@code Node} class represents a node in a linked list.
 * <p>
 * Each node in this list contains a data element and a link to the next node in the list. The list is linear, with each node
 * pointing to the next node, and the last node pointing to {@code null}.
 * </p>
 *
 * @param <T> the type of data stored in the node
 */
public class Node<T> {

    private T datum;
    private Node<T> link;

    /**
     * Default constructor that initializes the node with {@code null} values for both the data and the link.
     */
    public Node() {
        this.datum = null;
        this.link = null;
    }

    /**
     * Constructs a node with the specified data and a {@code null} link.
     *
     * @param data the data to be stored in the node
     */
    public Node(T data) {
        this.datum = data;
        this.link = null;
    }

    /**
     * Constructs a node with the specified data and link.
     *
     * @param data the data to be stored in the node
     * @param link the next node in the list
     */
    public Node(T data, Node<T> link) {
        this.datum = data;
        this.link = link;
    }

    /**
     * Sets the data for this node.
     *
     * @param datum the new data to be stored in the node
     */
    public void setDatum(T datum) {
        this.datum = datum;
    }

    /**
     * Sets the link to the next node.
     *
     * @param link the next node in the list
     */
    public void setLink(Node<T> link) {
        this.link = link;
    }

    /**
     * @return the data stored in this node.
     */
    public T getDatum() {
        return datum;
    }

    /**
     * @return the link to the next node.
     */
    public Node<T> getLink() {
        return link;
    }

    /**
     * @return a string representation of this node, including the data and the link.
     */
    @Override
    public String toString() {
        return "SinglyNode{" +
                "datum=" + datum +
                ", link=" + link +
                '}';
    }
    /**
     * @return True/ False if a Node is equals another node.
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node<?> node = (Node<?>) o;

        return Objects.equals(datum, node.datum);
    }
    /**
     * @return An integer to represent a hash code for a node.
     * */
    @Override
    public int hashCode() {
        return datum != null ? datum.hashCode() : 0;
    }
}

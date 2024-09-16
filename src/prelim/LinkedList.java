package prelim;

import java.util.NoSuchElementException;

/**
 * Represents a singly linked list.
 * This implementation allows for the creation of a list where each node points to the next node,
 * providing methods to insert, retrieve, delete, and search for elements.
 * It also includes an additional method to insert elements in a sorted order.
 *
 * @param <T> the type of elements in the list
 */
public class LinkedList<T> implements MyList<T> {
    private Node<T> head;
    private int size;

    /**
     * Constructs a {@code MySinglyLinkedList} with an empty list.
     */
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Returns the number of elements currently in the list.
     *
     * @return the number of elements in the list
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Inserts a new element at the end of the list.
     * If the list is empty, the new node becomes the head of the list.
     *
     * @param data the data to insert into the list
     */
    @Override
    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        Node<T> currentNode = head;
        if (head == null) {
            head = newNode;
        } else {
            while (currentNode.getLink() != null) {
                currentNode = currentNode.getLink();
            }
            currentNode.setLink(newNode);
        }
        size++;
    }

    /**
     * Inserts a new element into the list while maintaining a sorted order.
     * The list is assumed to be sorted according to the natural ordering of the elements.
     *
     * @param data the data to insert into the list
     * @throws ListOverflowException if the list has reached its maximum capacity (if any)
     */
    public void insertSorted(T data) throws ListOverflowException {
        Node<T> newNode = new Node<>(data);
        Node<T> currentNode = head;
        if (head == null) {
            head = newNode;
        } else if (newNode.getDatum().toString().compareTo(currentNode.getDatum().toString()) < 0) {
            newNode.setLink(head);
            head = newNode;
        } else {
            while (currentNode.getLink() != null && newNode.getDatum().toString().compareTo(currentNode.getDatum().toString()) < 0) {
                currentNode = currentNode.getLink();
            }
            newNode.setLink(currentNode.getLink());
            currentNode.setLink(newNode);
        }
    }

    /**
     * Retrieves the element at the specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws NoSuchElementException if the index is out of bounds
     */
    @Override
    public T getElement(int index) throws NoSuchElementException {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("Index out of bounds");
        }

        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getLink();
        }

        return currentNode.getDatum();
    }

    /**
     * Deletes the first occurrence of the specified element from the list.
     * If the element is found, the node is removed and the list is adjusted accordingly.
     *
     * @param data the data to delete from the list
     * @return {@code true} if the element was found and removed, {@code false} otherwise
     */
    @Override
    public boolean delete(T data) {
        if (head == null) {
            return false;
        } else if (head.getDatum().equals(data)) {
            head = head.getLink();
            size--;
            return true;
        } else {
            Node<T> currentNode = head;
            Node<T> previousNode = null;
            while (currentNode != null) {
                if (currentNode.getDatum().equals(data)) {
                    if (previousNode != null) {
                        previousNode.setLink(currentNode.getLink());
                    }
                    size--;
                    return true;
                }
                previousNode = currentNode;
                currentNode = currentNode.getLink();
            }
        }
        return false;
    }

    /**
     * Searches for the first occurrence of the specified element in the list.
     *
     * @param data the data to search for
     * @return the index of the element if found, {@code -1} otherwise
     */
    @Override
    public int search(T data) {
        Node<T> currentNode = head;
        int index = 0;

        while (currentNode != null) {
            if (currentNode.getDatum().equals(data)) {
                return index;
            }
            index++;
            currentNode = currentNode.getLink();
        }
        return -1;
    }
}

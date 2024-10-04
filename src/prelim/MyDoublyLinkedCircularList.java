package prelim;

import java.util.NoSuchElementException;

/**
 * Represents a doubly linked circular list.
 * This implementation maintains references to both the next and previous nodes, forming a circular structure.
 *
 * @param <T> the type of elements in the list
 */
public class MyDoublyLinkedCircularList<T> implements MyList<T> {
    private DoublyCircularNode<T> head;
    private int size;

    /**
     * Constructs an empty {@code MyDoublyLinkedCircularList}.
     * Initially, the list is empty with {@code head} set to {@code null} and {@code size} set to 0.
     */
    public MyDoublyLinkedCircularList() {
        head = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Inserts a new element into the list. If the list is empty, the new node becomes the head and points to itself.
     *
     * @param data the data to insert into the list
     * @throws ListOverflowException if the list has reached its maximum capacity (if any)
     */
    @Override
    public void insert(T data) throws ListOverflowException {
        DoublyCircularNode<T> newNode = new DoublyCircularNode<>(data);

        if (head == null) {
            head = newNode;
            newNode.setNext(head);
            newNode.setPrev(head);
        } else {
            DoublyCircularNode<T> last = head.getPrev();

            last.setNext(newNode);
            newNode.setPrev(last);
            newNode.setNext(head);
            head.setPrev(newNode);
        }
        size++;
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

        DoublyCircularNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Deletes the first occurrence of the specified element from the list.
     *
     * @param data the data to delete from the list
     * @return {@code true} if the element was found and removed, {@code false} otherwise
     */
    @Override
    public boolean delete(T data) {
        if (head == null) {
            return false;
        }

        DoublyCircularNode<T> current = head;

        do {
            if (current.getData().equals(data)) {
                if (size == 1) {
                    // Only one node in the list
                    head = null;
                } else {
                    DoublyCircularNode<T> prevNode = current.getPrev();
                    DoublyCircularNode<T> nextNode = current.getNext();

                    prevNode.setNext(nextNode);
                    nextNode.setPrev(prevNode);

                    if (current == head) {
                        head = nextNode;
                    }
                }
                size--;
                return true;
            }
            current = current.getNext();
        } while (current != head);

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
        if (head == null) {
            return -1;
        }

        DoublyCircularNode<T> current = head;
        int index = 0;

        do {
            if (current.getData().equals(data)) {
                return index;
            }
            current = current.getNext();
            index++;
        } while (current != head);

        return -1;
    }
}

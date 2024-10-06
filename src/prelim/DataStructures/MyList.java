package prelim.DataStructures;

import prelim.Exceptions.ListOverflowException;

import java.util.NoSuchElementException;

/**
 * An interface for a list data structure.
 * This interface defines the common operations that can be performed on a list, including
 * getting the size of the list, inserting, retrieving, deleting, and searching for elements.
 *
 * @param <E> the type of elements in the list
 */
public interface MyList<E> {

    /**
     * Returns the number of elements currently in the list.
     *
     * @return the number of elements in the list
     */
    int getSize();

    /**
     * Inserts a new element into the list.
     *
     * @param data the data to insert into the list
     * @throws ListOverflowException if the list has reached its maximum capacity
     */
    void insert(E data) throws ListOverflowException;

    /**
     * Retrieves the element at the specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws NoSuchElementException if the index is out of bounds
     */
    E getElement(int index) throws NoSuchElementException;

    /**
     * Deletes the first occurrence of the specified element from the list.
     *
     * @param data the data to delete from the list
     * @return {@code true} if the element was found and removed, {@code false} otherwise
     */
    boolean delete(E data);

    /**
     * Searches for the first occurrence of the specified element in the list.
     *
     * @param data the data to search for
     * @return the index of the element if found, {@code -1} otherwise
     */
    int search(E data);
}

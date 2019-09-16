package edu.emory.cs.queue;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public abstract class AbstractPriorityQueue<T extends Comparable<T>> {
    Comparator<T> comparator;

    AbstractPriorityQueue(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Adds a comparable key to this queue.
     * @param key the comparable key.
     */
    abstract public void add(T key);

    /**
     * Removes the key with the highest priority if exists.
     * @return the key with the highest priority if exists; otherwise, {@code null}.
     */
    abstract public T remove();

    /**
     * @return the size of this queue.
     */
    abstract public int size();

    /**
     * @return {@code true} if the queue is empty; otherwise, {@code false}.
     */
    public boolean isEmpty() {
        return size() == 0;
    }
}

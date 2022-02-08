package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    int front;
    int size;
    E[] array;
    @SuppressWarnings("unchecked")
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.size = 0;
        this.front = 0;
        array = (E[]) new Comparable[capacity];

    }

    @Override
    public void add(E work) {
        if(isFull()) throw new IllegalStateException();
        array[(front+size) % capacity()] = work;
        size++;

    }

    @Override
    public E peek() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        return array[front];
    }

    @Override
    public E peek(int i) {
        if(!hasWork()){
            throw new NoSuchElementException();
        }if(i<0 || i >= size){
            throw new IndexOutOfBoundsException();
        }
        return array[(front + i) % capacity()];
    }

    @Override
    public E next() {
        E temp = this.peek();
        front = (front + 1) % capacity();
        size--;
        return temp;
    }

    @Override
    public void update(int i, E value) {
        if(!hasWork()){
            throw new NoSuchElementException();
        }if(i<0 || i >= size){
            throw new IndexOutOfBoundsException();
        }
        array[(front + i) % capacity()] = value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.front = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        Iterator<E> i  = this.iterator();
        Iterator<E> i2  = other.iterator();
        int i_size = this.size();
        int i2_size = other.size();

        int limit = Math.min(i_size,i2_size);


        for (int j = 0; j < limit; j++) {
            int diff = i.next().compareTo(i2.next());
            if (diff != 0) {
                return diff;
            }
        }
        return i_size - i2_size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            return this.compareTo(other) == 0;

        }
    }

    @Override
    public int hashCode() {
        int HC = 1;
        for (int i = 0; i < size; i++) {
            int elementHc = this.peek(i).hashCode();
            HC = 11*HC + elementHc;

        }
        return HC;

    }
}

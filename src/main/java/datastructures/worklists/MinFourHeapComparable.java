package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.lang.Math;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;

    public MinFourHeapComparable() {
        this.size=0;
        this.capacity=10;
        this.data = (E[]) new Object[capacity];


    }

    @Override
    public boolean hasWork() {

        if(size>=0){
            return true;
        }
        return false;
    }

    @Override
    public void add(E work) {
        if (size==capacity){
            this.data= Arrays.copyOf(data, capacity*2);
            this.capacity*=2;
        }
         this.data[size]=work;
        this.size++;
        int index = size-1;
        while (Math.ceil((index-4.0)/4.0) >=0 && data[(int) Math.ceil((index-4.0)/4.0)].compareTo(data[(int) index]) >0){
            E temp = data[index];
            this.data[index]= this.data[(int) Math.ceil((index-4.0)/4.0)];
            this.data[(int) Math.ceil((index-4.0)/4.0)] = temp;
            index =(int) Math.ceil((index-4.0)/4.0);
        }
    }

    @Override
    public E peek() {
        if (!hasWork()) throw new NoSuchElementException();
        E item= this.data[0];
        this.data[0]=data[size-1];
        this.data[size-1]=null;
        this.size--;
        return item;
    }

    @Override
    public E next() {
        throw new NotYetImplementedException();
    }

    @Override
    public int size() {
        throw new NotYetImplementedException();
    }

    @Override
    public void clear() {
        throw new NotYetImplementedException();
    }
}

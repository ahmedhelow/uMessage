package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int size;
    private E[] array;

    public ArrayStack() {
        this.size=0;
        this.array=(E[]) new Object[10];
    }

    @Override
    public void add(E work) {
        if ((size+1)> array.length){
            E[] temp=array;
            this.array = (E[]) new Object[array.length*2];
            for (int i = 0; i < temp.length; i++) {
                this.array[i] = temp[i];
            }
        }
        this.array[size]=work;
        size++;
    }

    @Override
    public E peek() {
        if (size==0) throw new NoSuchElementException();
        return array[size-1];
    }

    @Override
    public E next() {
        if (size==0) throw new NoSuchElementException();
        E data = array[size-1];
        array[size-1] =null;
        size--;
        return data;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        if (size==0) throw new NoSuchElementException();
        E[] temp= (E[]) new Object[array.length];
        this.array=temp;
        this.size=0;

    }
}


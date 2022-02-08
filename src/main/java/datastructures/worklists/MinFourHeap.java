package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;
    private Comparator comparator;

    public MinFourHeap(Comparator<E> c) {
        this.size=0;
        this.capacity=10;
        this.data = (E[]) new Object[capacity];
        this.comparator = c;
    }

    @Override
    public boolean hasWork() {

        if (size>0) return true;
        return false;
    }

    @Override
    public void add(E work) {
        if (size==capacity){
            E[] datatemp= (E[]) new Object[capacity*2];
            for (int i = 0; i <this.size ; i++) {
                datatemp[i] = data[i];
            }
            this.data = (E[]) new Object[capacity*2];
            this.data=datatemp;
            this.capacity*=2;
        }
        this.data[size]=work;
        this.size++;
        int index = size-1;
        while (Math.ceil((index-4.0)/4.0) >=0 && this.comparator.compare(data[(int) Math.ceil((index-4.0)/4.0)],data[(int) index]) >0){
            E temp = data[index];
            this.data[index]= this.data[(int) Math.ceil((index-4.0)/4.0)];
            this.data[(int) Math.ceil((index-4.0)/4.0)] = temp;
            index =(int) Math.ceil((index-4.0)/4.0);
        }

    }

    @Override
    public E peek() {
        if (!hasWork()) throw new NoSuchElementException();
        return this.data[0];
    }

    @Override
    public E next() {
        if (!hasWork()) throw new NoSuchElementException();
        E item= this.data[0];
        this.data[0]=data[size-1];
        this.data[size-1]=null;
        this.size--;
        int index = 0;
        while ((4*index)+1<size){
            int smallestIndex = (4*index)+1;
            if ((4*index)+2<size) {
                if (this.comparator.compare(data[(4*index)+2],data[smallestIndex]) <0) smallestIndex = (4 * index) + 2;
                if ((4*index)+3<size){
                    if (this.comparator.compare(data[(4*index)+3],data[smallestIndex] ) <0) smallestIndex = (4 * index) + 3;
                    if ((4*index)+4<size){
                        if (this.comparator.compare(data[(4*index)+4],data[smallestIndex] ) <0) smallestIndex=(4*index)+4;
                    }
                }
            }
            if (this.comparator.compare(data[index], data[smallestIndex] )<0) break;
            else {
                E temp = data[index];
                data[index] = data[smallestIndex];
                data[smallestIndex]=temp;
            }
            index=smallestIndex;
        }
        return item;

    }

    @Override
    public int size() {

        return this.size;
    }

    @Override
    public void clear() {
        E[] temp = (E[]) new Object[capacity];
        this.data= temp;
        this.size=0;
    }
}

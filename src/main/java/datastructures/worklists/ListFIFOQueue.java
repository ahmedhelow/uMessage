package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */


public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private int size;
    private ListNode<E> head, tail;

    public ListFIFOQueue() {
        this.size =0;
        this.head = new ListNode<>(null);
        this.tail = new ListNode<>(null);
        head=tail;
        tail=head;
    }
    public class ListNode<E> {
        public E data;
        public ListNode<E> next;
        ListNode(E data){
            this.data=data;
            this.next=null;
        }
    }

    @Override
    public void add(E work) {
        ListNode<E> temp = new ListNode<>(work);
        if(size == 0){
            head=tail=temp;
        }
        else {
            tail.next = temp;
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E peek() {
        if (size==0){
            throw new NoSuchElementException();
        }
        return head.data;
    }

    @Override
    public E next() {
        if (size==0){
            throw new NoSuchElementException();
        }
        E temp = head.data;
        head=head.next;
        size--;
        return temp;

    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        head= new ListNode<E>(null);
        tail=head;
        size=0;
    }

}


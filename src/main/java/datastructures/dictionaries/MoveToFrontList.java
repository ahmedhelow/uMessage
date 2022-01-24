package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;

import java.util.Iterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the
 * list. This means you remove the node from its current position
 * and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private int size;
    private ListNode head, tail;

    public MoveToFrontList(){
        this.size=0;
        this.head= new ListNode<>(null,null);
        this.tail=new ListNode(null,null);
    }

    public static class ListNode<K,V> {
        public K key;
        public V value;
        public ListNode<K,V> next;
        ListNode(K key, V value){
            this.key=key;
            this.value=value;
            this.next=null;
        }
    }

    @Override
    public V insert(K key, V value) {
        if (key==null || value==null) throw new IllegalArgumentException();
        ListNode<K,V> temp = new ListNode<>(key,value);
        if (size==0) {
            head = tail = temp;
            this.size++;
            return value;
        }
        else {
            V findValue= this.find(key);
            if (findValue ==null){
                temp.next=this.head;
                this.head=temp;
                this.size++;
                return null;
            }
            else {
                this.head.value=value;
                this.size++;
                return findValue;
            }
        }
    }

    @Override
    public V find(K key) {

        if (key==null) throw new IllegalArgumentException();

    }

    @Override
    public Iterator<Item<K, V>> iterator() {

        throw new NotYetImplementedException();
    }
}

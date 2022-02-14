package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;

import java.util.Iterator;
import java.util.List;

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
    private ListNode head;


    public MoveToFrontList(Item<K,V> item) {
        this.head = new ListNode(item);
        if (item ==null || item.key ==null || item.value ==null ){
            this.size=0;
        }
        else{
            this.size=1;
        }

    }
    public MoveToFrontList() {
        this(null);
    }

    private class ListNode {
        private Item<K,V> data;
        private ListNode next;

        public ListNode() {
            this(null,null);
        }
        public ListNode(Item<K, V> item) {
            this(item, null);
        }
        public ListNode (Item<K,V> item, ListNode next){
            this.data = item;
            this.next=next;
        }

    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();
        ListNode temp = new ListNode(new Item(key, value), this.head);

        V findValue = this.find(key);
        if (findValue == null) {
            this.head = temp;
            this.size++;
            return null;
        } else {
            this.head.data.value = value;
            return findValue;
        }

    }

    @Override
    public V find(K key) {

        if (key == null) throw new IllegalArgumentException();

        if (this.size == 0) return null;

        ListNode currentNode = this.head;
        V value = null;

        if (currentNode != null && currentNode.data !=null  ) {
            if (currentNode.data.key.equals(key))  return currentNode.data.value;

            while (currentNode.next!=null && currentNode.next.data!=null && !currentNode.next.data.key.equals(key) ){
                    currentNode = currentNode.next;
            }
            if (currentNode.next!=null && currentNode.next.data!=null){
                value = currentNode.next.data.value;
                ListNode preNode = currentNode.next;
                currentNode.next = preNode.next;
                preNode.next = this.head;
                this.head = preNode;
            }
        }
        return value;

    }

    @Override
    public Iterator<Item<K, V>> iterator() {

        return new Iterator<Item<K, V>>() {
            ListNode currentNode = head;
            @Override
            public boolean hasNext() {
                return (currentNode != null && currentNode.next!=null);
            }

            @Override
            public Item<K, V> next() {
                if(this.hasNext()){
                    Item item = currentNode.data;
                    currentNode = currentNode.next;
                    return item;
                }else
                    return null;
            }
        };
    }
}

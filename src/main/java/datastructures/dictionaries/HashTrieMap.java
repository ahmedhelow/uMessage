package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(() -> new MoveToFrontList<>());

            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator() {
            Iterator<Item<A, HashTrieNode>> it = pointers.iterator();
            Iterator<Entry<A, HashTrieNode>> it1 = new Iterator<Entry<A, HashTrieNode>>() {
                public boolean hasNext() {
                    return it.hasNext();
                }
                public Entry<A, HashTrieNode> next() {
                    Item<A, HashTrieNode> i = it.next();
                    K key = (K) i.key;
                    V value = (V) i.value;
                    Entry<A, HashTrieNode> e = new AbstractMap.SimpleEntry(key, value);
                    return e;
                }
            };
            return it1;
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null){
            throw new IllegalArgumentException();
        }

        HashTrieNode curr  = (HashTrieNode) this.root;


        for (A val: key ){
            if(curr.pointers.find(val) != null){
                curr = curr.pointers.find(val);
            }else{
                curr.pointers.insert(val, new HashTrieNode());
                curr = curr.pointers.find(val);
            }
        }
        V past = curr.value;
        if(past == null){
            size ++;
        }
        curr.value = value;
        return past;
    }

    @Override
    public V find(K key) {
        if(key == null){
            throw new IllegalArgumentException();
        }
        if(size == 0){
            return null;
        }
        HashTrieNode curr = (HashTrieNode) this.root;

        for(A value: key){
            if(curr.pointers.find(value)!= null){
                curr = curr.pointers.find(value);
            }
            else{
                return null;
            }
        }
        return curr.value;
    }

    @Override
    public boolean findPrefix(K key) {

        if(key == null){
            throw new IllegalArgumentException();
        }
        if(size == 0){
            return false;
        }
        HashTrieNode curr = (HashTrieNode) this.root;

        for(A value: key){
            if(curr.pointers.find(value)!= null){
                curr = curr.pointers.find(value);
            }
            else{
                return  false;
            }

        }
        return true;
    }

    @Override
    public void delete(K key) {
        throw new NotYetImplementedException();
    }

    @Override
    public void clear() {
        HashTrieNode curr = (HashTrieNode) root;
        curr.pointers.clear();
        this.size = 0;
    }
}

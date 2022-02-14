package datastructures.dictionaries;

import com.sun.source.tree.BreakTree;
import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private static final int[] DEFAULT_SIZES = {97,197,397,797,1579,3163,6311,12611,25219,50441,100907,201731,403483,806917,1299827};
    private Dictionary<K, V> [] table;
    private int sizeInd =0;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {

        this.newChain = newChain;
        this.size=0;
        this.table = (Dictionary<K,V>[]) new Dictionary[DEFAULT_SIZES[this.sizeInd]];

    }

    @Override
    public V insert(K key, V value) {
        if (value == null){
            throw new IllegalArgumentException();
        }
        int lFactor = this.size/this.table.length;
        if (lFactor >=2){
            resize();
        }
        Dictionary<K,V> temp = ValueAtIndex(key);
        V Return = temp.insert(key, value);
        if (Return==null) size++;
        return Return;
    }

    @Override
    public V find(K key) {
        Dictionary<K,V> temp = ValueAtIndex(key);
        return temp.find(key);

    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        if (table[0] == null) table[0] = newChain.get();
        Iterator<Item<K,V>> iterator = new Iterator<Item<K, V>>() {
            private int Index=0;


            public Iterator<Item<K,V>> getNextIte() {
                Dictionary<K,V> current = null;
                while ((current == null || current.size() == 0) && (Index < ChainingHashTable.this.table.length)) {
                    current = ChainingHashTable.this.table[this.Index];
                    Index++;
                }

                if (current != null) return current.iterator();

                return null;
            }

            Iterator<Item<K,V>> TempHash = getNextIte();
            @Override
            public boolean hasNext() {
                return TempHash!=null;
            }

            @Override
            public Item<K, V> next() {
                if (hasNext() == false){
                    throw new NoSuchElementException();
                }
                Item<K,V> nextItem = TempHash.next();
                if (TempHash.hasNext()==false) TempHash = getNextIte();
                return nextItem;
            }

        };
        return iterator;
    }

    private Dictionary<K, V> ValueAtIndex(K key){
        if (key == null) throw new IllegalArgumentException();
        int HC = key.hashCode();
        Dictionary<K, V> current = this.table[HC % table.length];

        if (current == null){
            current = newChain.get();
            table[HC % table.length] = current;
        }
        return current;
    }
    private void resize(){
        int size2;
        if (sizeInd < DEFAULT_SIZES.length-1){
            sizeInd++;
            size2= DEFAULT_SIZES[sizeInd];
        }
        else size2 = (2*(table.length-1));
        Dictionary<K,V>[] temp = table;
        table = new Dictionary[size2];
        this.size=0;
        for (int i = 0; i < temp.length ; i++) {
            if (temp[i] !=null){
                Iterator<Item<K,V>> iterator = temp[i].iterator();
                while (iterator.hasNext()){
                    Item<K,V> item = iterator.next();
                    insert(item.key, item.value);
                }

            }
        }
    }
}

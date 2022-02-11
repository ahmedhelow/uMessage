package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
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
    private Dictionary<K, V> [] table;
    private static final int[] DEFAULT_SIZES = {97,197,397,797,1579,3163,6311,12611,25219,50441,100907,201731,403483,806917,1299827};
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
        Dictionary<K,V> temp = DictionaryAtIndex(key);
        V Return = temp.insert(key, value);
        if (Return==null) size++;
        return Return;
    }

    @Override
    public V find(K key) {
        Dictionary<K,V> temp = DictionaryAtIndex(key);
        return temp.find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return (Iterator<Item<K, V>>) new TableIterator();
    }

    private class TableIterator extends SimpleIterator<Item<K, V>> {
        private int currIndex;
        private Iterator<Item<K,V>> currIterator;

        public TableIterator() {
            this.currIndex = -1;
            this.currIterator = getNextIterator();
        }

        /**
         * Find next valid iterator, or return null if we have exhausted the table
         *
         * @return The next iterator
         */
        public Iterator<Item<K,V>> getNextIterator() {
            Dictionary<K,V> currDict = null;
            while ((currDict == null || currDict.size() == 0) && ++this.currIndex < ChainingHashTable.this.table.length) { // Find next valid index in the table
                currDict = ChainingHashTable.this.table[this.currIndex];
            }

            if (currDict != null) {
                return currDict.iterator();
            }

            return null;
        }

        @Override
        public boolean hasNext() {
            return this.currIterator != null;
        }

        @Override
        public Item<K, V> next() {

            if (!this.hasNext()) throw new NoSuchElementException();

            Item<K,V> next = this.currIterator.next();

            if (!this.currIterator.hasNext()) {
                this.currIterator = this.getNextIterator();
            }

            return next;
        }
    }

    private Dictionary<K, V> DictionaryAtIndex(K key){
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
                    Item<K,V> e = iterator.next();
                    insert(e.key, e.value);
                }

            }
        }
    }


}

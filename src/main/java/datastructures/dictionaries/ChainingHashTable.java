package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
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
    private static final int[] DEFAULT_SIZES = {83,167,311,613,1259,2557,5273,10163,20753,40993,80713,160019,321247,630473,1240793};
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

        }
    }

    @Override
    public V find(K key) {
        Dictionary<K,V> temp = DictionaryAtIndex(key);
        return temp.find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        throw new NotYetImplementedException();
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
        else size2 = (17*(table.length-1))/8;
        Dictionary<K,V>[] temp = table;
        table = new Dictionary[size2];
        this.size=0;
        for (int i = 0; i < temp.length ; i++) {
            if (temp != null){

            }
        }
    }


}

package p2.wordcorrector;

import cse332.datastructures.containers.Item;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.HashTrieMap;

public class AutocompleteTrie extends HashTrieMap<Character, AlphabeticString, Integer> {

    public AutocompleteTrie() {
        super(AlphabeticString.class);
    }

    public String autocomplete(String key) {
        @SuppressWarnings("unchecked")
        HashTrieNode current = (HashTrieNode) this.root;
        for (Character item : key.toCharArray()) {
            if ((current.pointers.find(item) != null)) {
                current = current.pointers.find(item);

            }
            else {
                return null;            }
        }

        String result = key;

        while (current.pointers.size() == 1) {
            if (current.value == null) {
                Item<Character, HashTrieNode> Entry = current.pointers.iterator().next();
                current = Entry.value;
                result += Entry.key;
            }
            else if (current.value!=null){return null;}

        }

        if (current.pointers.size() != 0) return result;

        return result;
    }
}

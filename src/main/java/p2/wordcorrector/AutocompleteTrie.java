package p2.wordcorrector;

import cse332.types.AlphabeticString;
import datastructures.dictionaries.HashTrieMap;
public class AutocompleteTrie extends HashTrieMap<Character, AlphabeticString, Integer> {

    public AutocompleteTrie() {
        super(AlphabeticString.class);
    }

    public String autocomplete(String key) {
        @SuppressWarnings("unchecked")
        HashTrieNode curr = (HashTrieNode) this.root;
        for (Character item : key.toCharArray()) {
            if (curr.pointers.find(item) == null) {
                return null;
            }
            else {
                curr = curr.pointers.find(item);
            }
        }

        String result = key;

        while (curr.pointers.size() == 1) {
            if (curr.value != null) {
                return null;
            }
            result += (curr.pointers.iterator().next());
            curr = curr.pointers.iterator().next().value;
        }

        if (curr.pointers.size() != 0) {
            return result.toString();
        }
        return result.toString();
    }
}

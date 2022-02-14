package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;


/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    public AVLTree() {
        super();
    }
    public class AVLNode extends BSTNode {
        int height;
        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {throw new IllegalArgumentException();}
        V oldValue = find(key);
        this.root=insert((AVLNode)this.root, key, value);
        return oldValue;
    }
    public AVLNode insert(AVLNode node, K key, V value) {
        if (node == null) {
            this.size++;
            return new AVLNode(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.children[0] = insert((AVLNode) node.children[0], key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.children[1] = insert((AVLNode) node.children[1], key, value);
        } else {
            node.value = value;
            return node;
        }

        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;;
        int i = checkBalance(node);

        if (i > 1 && key.compareTo(node.children[0].key) < 0)
            return rightRotate(node);

        if (i < -1 && key.compareTo((node.children[1]).key) > 0)
            return leftRotate(node);

        if (i > 1 && key.compareTo((node.children[0]).key) > 0) {
            node.children[0] = leftRotate((AVLNode) node.children[0]);
            return rightRotate(node);
        }

        if (i < -1 && key.compareTo((node.children[1]).key) < 0) {
            node.children[1] = rightRotate((AVLNode) node.children[1]);
            return leftRotate(node);
        }

        return node;

    }
    public int height(AVLNode node) {
        if (node == null) {
            return -1;
        } else {
            return node.height;
        }
    }

    public int checkBalance(AVLNode node) {
        if (node == null) {
            return -1;
        } else {
            return height((AVLNode) node.children[0]) - height((AVLNode) node.children[1]);
        }

    }

    public AVLNode rightRotate(AVLNode node){
        AVLNode left = (AVLNode) node.children[0];
        AVLNode right = (AVLNode) left.children[1];

        left.children[1] = node;
        node.children[0] = right;

        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;
        left.height = Math.max(height((AVLNode) left.children[0]), height((AVLNode) left.children[1])) + 1;

        return left;
    }

    public AVLNode leftRotate(AVLNode node){
        AVLNode left = (AVLNode) node.children[1];
        AVLNode right = (AVLNode) left.children[0];

        left.children[0] = node;
        node.children[1] = right;

        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;
        left.height = Math.max(height((AVLNode) left.children[0]), height((AVLNode) left.children[1])) + 1;

        return left;
    }
}
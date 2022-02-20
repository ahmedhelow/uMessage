package experiments;

import com.sun.net.httpserver.Filter;
import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import java.util.Random;

public class ChainingHashTableExp {
    public static void main(String[] args) {
        int[] INPUTS = {10000, 20000, 40000, 80000, 160000, 320000, 500000, 700000, 1000000, 1500000};

        int findnum = 0;

        double mtfInsertStart, mtfFindStart, mtfInsertEnd, mtfFindEnd  ;
        double mtfInsert = 0;
        double avlInsertStart, avlInsertEnd, avlFindStart, avlFindEnd ;
        double avlInsert = 0;
        double bstInsertStart, bstInsertEnd, bstFindStart, bstFindEnd ;
        double bstInsert = 0;
        double mtfFind = 0;
        double avlFind = 0;
        double bstFind = 0;


        int input;
        for (int i = 0; i < INPUTS.length; i++) {
            input = INPUTS[i];
            for (int j = 0; j < 10; j++) {
                ChainingHashTable<Integer, Integer> AVL = new ChainingHashTable<>(() -> new AVLTree<Integer, Integer>());
                ChainingHashTable<Integer, Integer> MTF = new ChainingHashTable<>(() -> new MoveToFrontList<>());
                ChainingHashTable<Integer, Integer> BST = new ChainingHashTable<>(() -> new BinarySearchTree<Integer, Integer>());
                Random rand = new Random();
                for (int k = 0; k < input - 1; k++) {
                    int nextRandom = rand.nextInt(input * 5);
                    if (k == input / 2) {
                        findnum = nextRandom;
                    }
                    MTF.insert(nextRandom, nextRandom);
                    AVL.insert(nextRandom, nextRandom);
                    BST.insert(nextRandom, nextRandom);
                }
                int nextRandom = rand.nextInt(input * 5);
                mtfInsertStart = System.nanoTime();
                MTF.insert(nextRandom, nextRandom);
                mtfInsertEnd = System.nanoTime();

                avlInsertStart = System.nanoTime();
                AVL.insert(nextRandom, nextRandom);
                avlInsertEnd = System.nanoTime();

                bstInsertStart = System.nanoTime();
                BST.insert(nextRandom, nextRandom);
                bstInsertEnd = System.nanoTime();

                mtfInsert += (mtfInsertEnd - mtfInsertStart);
                avlInsert += (avlInsertEnd - avlInsertStart);
                bstInsert += (bstInsertEnd - bstInsertStart);

                mtfFindStart = System.nanoTime();
                MTF.find(findnum);
                mtfFindEnd = System.nanoTime();

                avlFindStart = System.nanoTime();
                AVL.find(findnum);
                avlFindEnd = System.nanoTime();

                bstFindStart = System.nanoTime();
                BST.find(findnum);
                bstFindEnd = System.nanoTime();

                mtfFind += (mtfFindEnd - mtfFindStart);
                avlFind += (avlFindEnd - avlFindStart);
                bstFind += (bstFindEnd - bstFindStart);
            }

            System.out.println("mtfFind " + input + ": " + (mtfFind / 10));
            System.out.println("avlFind " + input + ": " + (avlFind / 10));
            System.out.println("bstFind " + input + ": " + (bstFind / 10));

            System.out.println("mtfInsert " + input + ": " + (mtfInsert / 10));
            System.out.println("avlInsert " + input + ": " + (avlInsert / 10));
            System.out.println("bstInsert " + input + ": " + (bstInsert / 10));
        }
    }
}

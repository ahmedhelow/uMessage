package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, 0, array.length, comparator);

    }
    public static <E> void quickSort(E[] array, int low, int high, Comparator<E> comparator){
    if (low>= high) return;
    int pivotIndex=partition(array, low, high, comparator);
    quickSort(array, low, pivotIndex, comparator);
    quickSort(array, pivotIndex+1, high, comparator);
    }
    public static <E> int partition(E[] array, int low, int high, Comparator<E> comparator){
        E pivot = array[high-1];
        int pIndex = low-1;
        for (int i = low; i <high ; i++) {
            if (comparator.compare(array[i], pivot) <=0 ){
                pIndex = pIndex+1;
                E temp = array[i];
                array[i] = array[pIndex];
                array[pIndex]=temp;
            }
        }

//        E temp = array[pIndex];
//        array[pIndex]= array[high];
//        array[high]= temp;
        return pIndex;
    }

}

package homework6;

/**
 *
 * @author Thomas Benge 
 * CSCI 202 Homework 6 
 * Sorting Algorithms and Runtime Analysis 
 * 11/17/2012 
 * This assignment was to implement different sorting algorithms.
 *
 * A good deal of the code for these algorithms was inspired by the textbook.
 *
 */
public class SortingAlgorithms {

    public static void main(String[] args) {
        int arraySize = 10000;
        int[] array1 = new int[arraySize];
        int[] array2 = new int[arraySize];
        int[] tempArray = new int[arraySize]; //this is only used in mergesort.

        //Made array1 and array2 identical to accurately show the time 
        //difference between bubblesort and mergesort.
        for (int i = 0; i < arraySize; i++) {
            //Create an array full of random data.
            array1[i] = array2[i] = (int) (Math.random() * 1000000); 
        }

        //This print statement helps to see when the arrays are filled so you
        //know when it actually starts being sorted.
        System.out.println("Arrays are filled. About to sort.");

        try {
            mergesort(array1, tempArray, 0, arraySize - 1);
            System.out.println("Mergesort finished sorting the array.");
        } catch (ArrayOutOfBoundsException e) {
            System.out.println("first index can't be larger than last index.");
        }
        try {
            bubbleSort(array2, arraySize);
            System.out.println("Bubblesort finished sorting the array.");
        } catch (NegativePassesException e) {
            System.out.println("bubblesort can't have negative number of passes.");
        } 

        //This for loop tests whether an array is sorted correctly
        /*
        for (int j = 0; j < arraySize - 1; j++) {
            if (array1[j] > array1[j + 1]) {
                System.out.println("The array did not get sorted correctly.");
                break;
            }
        } */
    }

    public static void bubbleSort(int[] array, int n) throws NegativePassesException {
        if (n < 0) {
            throw new NegativePassesException();
        }
        boolean sorted = false;
        for (int pass = 1; (pass < n) && (!sorted); pass++) {
            sorted = true; // we’ll notice if it isn’t, below
            for (int i = 0; i < n - pass; i++) {
                // Note: each pass has fewer iters than the last
                if (array[i] > array[i + 1]) {
                    // adjacent items out of order, so swap them
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    sorted = false; //we swapped so it was unsorted
                }
            }
        }
    }

    public static void mergesort(int[] array, int[] tempArray, int first, int last) throws ArrayOutOfBoundsException {
        if (first > last) {
            throw new ArrayOutOfBoundsException();
        }

        if (first < last) {
            int mid = (first + last) / 2;
            mergesort(array, tempArray, first, mid);
            mergesort(array, tempArray, mid + 1, last);

            merge(array, tempArray, first, mid, last);
        }
    }

    public static void merge(int[] array, int[] tempArray, int first, int mid, int last) {
        int first1 = first;
        int last1 = mid;
        int first2 = mid + 1;
        int last2 = last;
        int index = first1;

        while ((first1 <= last1) && (first2 <= last2)) {
            if (array[first1] < array[first2]) {
                tempArray[index] = array[first1];
                first1++;
            } else {
                tempArray[index] = array[first2];
                first2++;
            }
            index++;
        }

        while (first1 <= last1) {
            tempArray[index] = array[first1];
            first1++;
            index++;
        }

        while (first2 <= last2) {
            tempArray[index] = array[first2];
            first2++;
            index++;
        }

        for (index = first; index <= last; ++index) {
            array[index] = tempArray[index];
        }
    }
}

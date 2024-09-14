package nl.saxion.cds.experimenting;

import java.util.Arrays;

public class Sorters {
    public static void main(String[] args) {
        new Sorters().run();
    }

    public void run(){
        //expected: [3, 4, 7, 9, 42, 69, 420]
        int[] arr = new int[]{9, 3, 7, 4, 69, 420, 42};

        quicksort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void quicksort(int[] arr){
        qs(arr, 0, arr.length - 1);
    }

    public void qs(int[] arr, int start, int end){
        if(end - start >= 1){
            int pivot = partition(arr, start, end);
            qs(arr, start, pivot - 1);
            qs(arr, pivot + 1, end);
        }
    }

    public int partition(int[] arr, int start, int end){
        // partition finding, if pivot is by default end
        int pivot = arr[end];

        int idx = start - 1;
        for (int j = start; j < end; j++){
            if(arr[j] < pivot){
                // swap current pivot j and idx
                idx++;
                swap(arr, j, idx);
            }
        }
        idx++;
        swap(arr, idx, end);

        return idx;
    }

    protected void swap(int[] elements, int index1, int index2) {
        if (index1 != index2) {
            var temp = elements[index1];
            elements[index1] = elements[index2];
            elements[index2] = temp;
        }
    }

}

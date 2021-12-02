public class QuickSort {
    public static void main(String[] args) {
        int[] array = new int[] { 64, 42, 73, 41, 32, 53, 16, 24, 57, 42, 74, 55, 36 };

        System.out.println(arrayToString(array));

        quickSort(array, 0, array.length - 1);

        System.out.println(arrayToString(array));
    }

    private static void quickSort(int[] arr, int from, int to) {
        if (from < to) {
            int divideIndex = partitioning(arr, from, to);

            quickSort(arr, from, divideIndex - 1);
            quickSort(arr, divideIndex, to);
        }
    }

    private static int partitioning(int[] arr, int from, int to) {
        int rightIndex = to;
        int leftIndex = from;

        // Choose a pivot value in the center of an array
        int pivot = arr[from + (to - from) / 2];
        while (leftIndex <= rightIndex) {
            while (arr[leftIndex] < pivot) {
                leftIndex++;
            }
            while (arr[rightIndex] > pivot) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                swap(arr, rightIndex, leftIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return leftIndex;
    }

    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}

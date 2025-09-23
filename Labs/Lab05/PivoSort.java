import java.time.LocalDateTime;

public class quick {
    
    private static int[] arr;
	private static int n;

	public quick(){
		arr =  new int[] {13, 25, 50, 90, 9, -1};
		n = arr.length;
	}

    public static void sort(){
        QuickSortFirstPivot(0, n-1, arr);
        // QuickSortLastPivot(0, n-1, arr);
        // QuickSortRandomPivot(0, n-1, arr);
        // QuickSortMedianOfThree(0, n-1, arr);
    }

    //int pivo arr[0]

    public static void QuickSortFirstPivot(int esq, int dir, int[] arr) {
        long startTime = System.nanoTime();

        int i = esq, j = dir;
        // int pivo = arr[(dir+esq)/2];
        int pivo = arr[0];
        while (i <= j) {
            while (arr[i] < pivo) i++;
            while (arr[j] > pivo) j--;
            if (i <= j) {
                int swapTemp = arr[i+1];
                arr[i+1] = arr[dir];
                arr[dir] = swapTemp;

            }
        }
        if (esq < j)  QuickSortFirstPivot(esq, j,arr);
        if (i < dir)  QuickSortFirstPivot(i, dir,arr);

        long endTime = System.nanoTime();
        long durationInNanos = endTime - startTime;
        System.out.println("Execution time: " + durationInNanos + " nanoseconds");
    }
    
    public static void main(String[] args) {
        sort();

    }
}

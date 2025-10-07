public class LastPivot {

    public static void QuickSortLastPivot(int esq, int dir, int[] arr){
        long startTime = 0;
        if (esq == 0 && dir == arr.length - 1) {
            startTime = System.nanoTime();
        }

        if (esq >= dir) return;

        int i = esq, j = dir;
        int pivo = arr[dir];

        while (i <= j) {
            while (arr[i] < pivo) i++;
            while (arr[j] > pivo) j--;
            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        if (esq < j) QuickSortLastPivot(esq, j, arr);
        if (i < dir) QuickSortLastPivot(i, dir, arr);

        if (esq == 0 && dir == arr.length - 1) {
            long endTime = System.nanoTime();
            long durationInNanos = endTime - startTime;

            for (int o = 0; o < arr.length; o++) {
                System.out.println("Array ordenada: " + arr[o]);
            }
            System.out.println("Tempo de execução com o LastPivot " + durationInNanos);
        }
    }
}

public class MedianOfThreePivot {

    public static void QuickSortMedianPivot(int esq, int dir, int[] arr){
        long startTime = 0;
        if (esq == 0 && dir == arr.length - 1) {
            startTime = System.nanoTime();
        }

        if (esq >= dir) return;

        int i = esq, j = dir;

        // Calcula mediana de três (início, meio e fim)
        int meio = (esq + dir) / 2;
        int a = arr[esq], b = arr[meio], c = arr[dir];
        int pivo = medianOfThree(a, b, c);

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

        if (esq < j) QuickSortMedianPivot(esq, j, arr);
        if (i < dir) QuickSortMedianPivot(i, dir, arr);

        if (esq == 0 && dir == arr.length - 1) {
            long endTime = System.nanoTime();
            long durationInNanos = endTime - startTime;

            for (int o = 0; o < arr.length; o++) {
                System.out.println("Array ordenada: " + arr[o]);
            }
            System.out.println("Tempo de execução com o Mediana de 3 numeros " + durationInNanos);
        }
    }

    private static int medianOfThree(int a, int b, int c) {
        if ((a > b) != (a > c)) return a;
        else if ((b > a) != (b > c)) return b;
        else return c;
    }
}

import java.util.Arrays;
import java.util.Random;

public class PivoSort {

    // ---------- GERADORES DE ARRAYS ----------
    public static int[] gerarArrayOrdenado(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    public static int[] gerarArrayQuaseOrdenado(int n) {
        int[] arr = gerarArrayOrdenado(n);
        Random rand = new Random();
        for (int i = 0; i < n / 100; i++) {
            int idx1 = rand.nextInt(n);
            int idx2 = rand.nextInt(n);
            int tmp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = tmp;
        }
        return arr;
    }

    public static int[] gerarArrayAleatorio(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(n * 10);
        return arr;
    }

    // ---------- BENCHMARK ----------
    public static void testarAlgoritmos(int[] arrOriginal, String tipoArray) {
        int[] arr;

        System.out.println("\n===== Testando com array " + tipoArray + " de tamanho " + arrOriginal.length + " =====");

        // First Pivot
        arr = Arrays.copyOf(arrOriginal, arrOriginal.length);
        long start = System.nanoTime();
        FirstPivot.QuickSortFirstPivot(0, arr.length - 1, arr);
        long end = System.nanoTime();
        System.out.println("FirstPivot: " + (end - start) + " ns");

        // Last Pivot
        arr = Arrays.copyOf(arrOriginal, arrOriginal.length);
        start = System.nanoTime();
        LastPivot.QuickSortLastPivot(0, arr.length - 1, arr);
        end = System.nanoTime();
        System.out.println("LastPivot: " + (end - start) + " ns");

        // Random Pivot
        arr = Arrays.copyOf(arrOriginal, arrOriginal.length);
        start = System.nanoTime();
        RandomPivot.QuickSortRandomPivot(0, arr.length - 1, arr);
        end = System.nanoTime();
        System.out.println("RandomPivot: " + (end - start) + " ns");

        // Median of Three
        arr = Arrays.copyOf(arrOriginal, arrOriginal.length);
        start = System.nanoTime();
        MedianOfThreePivot.QuickSortMedianPivot(0, arr.length - 1, arr);
        end = System.nanoTime();
        System.out.println("MedianOfThreePivot: " + (end - start) + " ns");
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        int[] tamanhos = {100, 1000, 10000};

        for (int n : tamanhos) {
            testarAlgoritmos(gerarArrayOrdenado(n), "ordenado");
            testarAlgoritmos(gerarArrayQuaseOrdenado(n), "quase ordenado");
            testarAlgoritmos(gerarArrayAleatorio(n), "aleatório");
        }
    }
}

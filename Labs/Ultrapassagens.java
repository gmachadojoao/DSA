import java.util.*;

public class Ultrapassagens {

    public static long mergeSort(int[] arr, int left, int right) {
        if (left >= right) return 0;

        int mid = (left + right) / 2;
        long invCount = 0;

        invCount += mergeSort(arr, left, mid);
        invCount += mergeSort(arr, mid + 1, right);

        // Fusão e contagem de inversões
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                invCount += (mid - i + 1); // todos à direita de i são inversões
            }
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        System.arraycopy(temp, 0, arr, left, temp.length);

        return invCount;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            int N = sc.nextInt();
            int[] start = new int[N];
            int[] finish = new int[N];

            for (int i = 0; i < N; i++) start[i] = sc.nextInt();
            for (int i = 0; i < N; i++) finish[i] = sc.nextInt();

            // Array para armazenar posições finais
            int[] posFinal = new int[N + 1]; 
            for (int i = 0; i < N; i++) posFinal[finish[i]] = i;

            // Transformar start em posições finais
            int[] startMapped = new int[N];
            for (int i = 0; i < N; i++) startMapped[i] = posFinal[start[i]];

            long inversions = mergeSort(startMapped, 0, N - 1);
            System.out.println(inversions);
        }
    }
}

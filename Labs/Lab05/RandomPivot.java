import java.util.Random;

public class RandomPivot {

    public static void QuickSortRandomPivot(int esq, int dir, int[] arr){
        if (esq >= dir) return;

        int i = esq, j = dir;

        Random rand = new Random();
        int randomIndex = esq + rand.nextInt(dir - esq + 1);
        int pivo = arr[randomIndex];

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

        if (esq < j) QuickSortRandomPivot(esq, j, arr);
        if (i < dir) QuickSortRandomPivot(i, dir, arr);
    }
}

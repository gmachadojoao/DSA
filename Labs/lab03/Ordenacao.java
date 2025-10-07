import java.util.Scanner;

public class Ordenacao {


    // Insertion Sort aplicando as regras
   public static void insertionSort(int[] ar, int M) {
    for (int i = 1; i < ar.length; i++) {
        int key = ar[i];
        int j = i - 1;

        while (j >= 0) {
            boolean troca = false;

            int modKey = key % M;
            int modJ = ar[j] % M;

            // Ajustar módulo negativo como em C
            if (modKey < 0) modKey += M;
            if (modJ < 0) modJ += M;

            if (modKey < modJ) {
                troca = true;
            } else if (modKey == modJ) {
                if ((key % 2 != 0) && (ar[j] % 2 == 0)) {
                    // ímpar antes de par
                    troca = true;
                } else if ((key % 2 != 0) && (ar[j] % 2 != 0)) {
                    // ímpares: maior antes do menor
                    if (key > ar[j]) troca = true;
                } else if ((key % 2 == 0) && (ar[j] % 2 == 0)) {
                    // pares: menor antes do maior
                    if (key < ar[j]) troca = true;
                }
            }

            if (troca) {
                ar[j + 1] = ar[j];
                j--;
            } else {
                break;
            }
        }
        ar[j + 1] = key;
    }
}


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

        while (N != 0 || M != 0) {
            int[] numeros = new int[N];
            for (int i = 0; i < N; i++) {
                numeros[i] = sc.nextInt();
            }

            insertionSort(numeros, M);

            for (int num : numeros) {
                System.out.println(num);
            }

            // ler o próximo caso
            N = sc.nextInt();
            M = sc.nextInt();

            if (N ==0 && M ==0){
                // imprimir o 0 0 final
                System.out.println("0 0");
            }
        }


    }
}

import java.util.Scanner;

public class Ordenacao {


    // Insertion Sort aplicando as regras
    public static void insertionSort(int[] ar, int M) {
        for (int i = 1; i < ar.length; i++) {

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

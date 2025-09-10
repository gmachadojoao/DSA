import java.io.IOException;
import java.util.Scanner;

public class bee_1173 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        int[] vetor = new int[10];
        vetor[0] = sc.nextInt();

        for (int i = 1; i < vetor.length; i++) {
            vetor[i] = vetor[i - 1] * 2;
        }

        for (int i = 0; i < vetor.length; i++) {
            System.out.printf("N[%d] = %d\n", i, vetor[i]);
        }

        sc.close();
    }
}
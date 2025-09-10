import java.io.IOException;
import java.util.Scanner;

public class bee_1174 {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        double[] vetor = new double[100];

        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = sc.nextDouble();
        }

        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] <= 10.0) {
                System.out.printf("A[%d] = %.1f\n", i, vetor[i]);
            }
        }

        sc.close();
    }
}

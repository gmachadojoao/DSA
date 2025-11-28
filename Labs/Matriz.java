import java.util.Scanner;

public class Matriz {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String token = sc.next();
            if (token.equals("FIM")) break;

            int N = sc.nextInt();
            int M = sc.nextInt();

            int[][] tabuleiro = new int[N][M];

            // Lê o tabuleiro
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    tabuleiro[i][j] = sc.nextInt();
                }
            }

            // Gera a saída
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (tabuleiro[i][j] == 1) {
                        System.out.print("9");
                    } else {
                        int cont = 0;
                        if (i > 0) cont += tabuleiro[i - 1][j];       // cima
                        if (i < N - 1) cont += tabuleiro[i + 1][j];   // baixo
                        if (j > 0) cont += tabuleiro[i][j - 1];       // esquerda
                        if (j < M - 1) cont += tabuleiro[i][j + 1];   // direita
                        System.out.print(cont);
                    }
                }
                System.out.println();
            }
        }

        sc.close();
    }
}

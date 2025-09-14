import java.io.IOException;
import java.util.Scanner;

public class bee_2479 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        String[] comportadas = new String[N];
        String[] malcriadas = new String[N];
        int compCount = 0;
        int malCount = 0;

        // Leitura dos sinais e nomes
        for (int i = 0; i < N; i++) {
            String sinal = sc.next();
            String nome = sc.next();

            // Comparação manual do sinal
            if (isPlus(sinal)) {
                comportadas[compCount++] = nome;
            } else {
                malcriadas[malCount++] = nome;
            }
        }

        // Ordenação por seleção manual
        selectionSortManual(comportadas, compCount);
        selectionSortManual(malcriadas, malCount);

        // Impressão
        for (int i = 0; i < compCount; i++) {
            System.out.println(comportadas[i]);
        }
        for (int i = 0; i < malCount; i++) {
            System.out.println(malcriadas[i]);
        }

        sc.close();
    }

    // Função para verificar se o sinal é '+'
    public static boolean isPlus(String s) {
        return s.length() == 1 && s.charAt(0) == '+';
    }

    // Selection Sort com comparação manual de strings
    public static void selectionSortManual(String[] arr, int length) {
        for (int i = 0; i < length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < length; j++) {
                if (isLess(arr[j], arr[minIndex])) {
                    minIndex = j;
                }
            }
            // Troca
            String temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // Comparação manual de duas strings: retorna true se a < b
   public static boolean isLess(String a, String b) {
    int i = 0;
    // percorre enquanto i for menor que ambos os comprimentos
    while (i < a.length() && i < b.length()) {
        if (a.charAt(i) < b.charAt(i)) return true;
        if (a.charAt(i) > b.charAt(i)) return false;
        i++;
    }
    // se chegaram até aqui, uma string pode ser prefixo da outra
    return a.length() < b.length(); // a menor vem primeiro
}

}

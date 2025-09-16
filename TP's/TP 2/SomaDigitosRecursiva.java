import java.util.Scanner;

public class SomaDigitosRecursiva {

    // Função recursiva que soma os dígitos de uma string
    public static int somaDigitos(String str, int index) {
        if (index == str.length()) {
            return 0; // caso base: fim da string
        }
        char c = str.charAt(index);
        int valor = (Character.isDigit(c)) ? (c - '0') : 0;
        return valor + somaDigitos(str, index + 1); // soma atual + resto
    }

    // Verifica se a string é "FIM"
    public static boolean theEnd(String str) {
        return str.length() == 3 &&
               str.charAt(0) == 'F' &&
               str.charAt(1) == 'I' &&
               str.charAt(2) == 'M';
    }

    public static void processar(Scanner sc) {
        if (!sc.hasNextLine()) return;

        String input = sc.nextLine();

        if (theEnd(input)) return; // caso base: encerra

        int soma = somaDigitos(input, 0);
        System.out.println(soma);

        processar(sc); // chamada recursiva
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        processar(sc); // inicia recursão de leitura
        sc.close();
    }
}

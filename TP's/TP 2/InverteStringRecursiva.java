import java.util.Scanner;

public class InverteStringRecursiva {

    public static boolean theEnd(String str) {
        return str.length() == 3 &&
               str.charAt(0) == 'F' &&
               str.charAt(1) == 'I' &&
               str.charAt(2) == 'M';
    }

    // Função recursiva para inverter string
    public static String inverte(String str) {
        if (str.length() <= 1) {
            return str; // caso base: string de 1 caractere (ou vazia)
        }
        // pega último caractere + inverte o resto
        return str.charAt(str.length() - 1) + inverte(str.substring(0, str.length() - 1));
    }

    // Loop recursivo de leitura
    public static void processar(Scanner sc) {
        String input = sc.nextLine();
        if (theEnd(input)) {
            return; // caso base: encerra
        }
        System.out.println(inverte(input));
        processar(sc); // chamada recursiva
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        processar(sc); // inicia o loop recursivo
        sc.close();
    }
}

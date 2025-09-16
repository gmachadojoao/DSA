import java.util.Scanner;

public class inverteString {
    public static boolean theEnd(String str) { /*
                                                * função para verificar se a palavra FIM foi digitada
                                                * ela recebe o mesmo input que a função cifra, caso seja TRUE, o codigo
                                                * termina
                                                */

        if (str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M') {
            return true;
        } else {
            return false;
        }
    }

    public static String inverte(String str) {
        String resultado = "";

        for (int i = str.length() - 1; i >= 0; i--) {
            resultado += str.charAt(i); // cocatena o caractere atual a string resultado
        }

        return resultado; // retorna a string invertida
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;

        input = sc.nextLine();

        while (!theEnd(input)) {
            System.out.println(inverte(input));

            input = sc.nextLine();

        }

        sc.close();

    }
}

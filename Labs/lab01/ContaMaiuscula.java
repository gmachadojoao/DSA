import java.util.Scanner;

public class ContaMaiuscula {

    // Método para verificar se a palavra é "FIM"
    public static boolean theEnd(String str) {
        if (str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M') {
            return true;
        } else {
            return false;
        }
    }

    public static int contaLetra(String palavra) {
        int count = 0;
        for (int i = 0; i < palavra.length(); i++) {
            if (Character.isUpperCase(palavra.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra = "";

        while (true) {
            palavra = sc.nextLine();

            if (theEnd(palavra)) {
                break;
            }

            System.out.println(contaLetra(palavra));
        }

        sc.close();
    }
}
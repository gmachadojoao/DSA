import java.util.Scanner;

public class Combinador {

    public static String combinar(String s1, String s2) {
        String resultado = "";
        int tamanhoMax = Math.max(s1.length(), s2.length());

        // Itera até o tamanho da maior string
        for (int i = 0; i < tamanhoMax; i++) {
            if (i < s1.length()) {
                resultado += s1.charAt(i);
            }
            if (i < s2.length()) {
                resultado += s2.charAt(i);
            }
        }

        return resultado;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s1 = sc.nextLine();

        String s2 = sc.nextLine();

        String resultado = combinar(s1, s2);
        System.out.println("" + resultado);

        sc.close();
    }
}

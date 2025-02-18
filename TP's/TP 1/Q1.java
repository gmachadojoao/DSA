package Q1;

import java.util.Scanner;

public class Q1 {
    public static boolean theEnd(String x) {
        // Verifica se a string tem pelo menos 3 caracteres antes de acessar
        if (x.length() == 3 && x.charAt(0) == 'F' && x.charAt(1) == 'I' && x.charAt(2) == 'M') {
            return true;
        }
        return false;
    }

    public static boolean ehPalin(String x) {
        int n = x.length();
        for (int i = 0; i < n / 2; i++) {
            if (x.charAt(i) != x.charAt(n - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        boolean status = true;

        while (status) {
            input = sc.nextLine();
            if (theEnd(input)) {
                status = false;
            } else if (ehPalin(input)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }

        sc.close();
    }
}

package Q1;

import java.util.Scanner;

public class Q1 {

    public static boolean ehPalin(String x) {
        int n = x.length();
        boolean aux = true;
        for (int i = 0; i < n / 2; i++) {
            if (x.charAt(i) != x.charAt(n - 1 - i)) {
                return aux = false;
            }
        }
        return aux;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        boolean status = true;

        while (status) {
            input = sc.nextLine();
            if (input.equals("FIM")) {
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

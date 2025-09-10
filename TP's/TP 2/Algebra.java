import java.util.Scanner;

public class Algebra {

    public static boolean theEnd(String str) {
        return str.length() == 3 &&
               str.charAt(0) == 'F' &&
               str.charAt(1) == 'I' &&
               str.charAt(2) == 'M';
    }

    public static boolean algebraBooleana(String expressao) {
        int abre = 0;
        int fecha = 0;

        for (int i = 0; i < expressao.length(); i++) {
            char c = expressao.charAt(i);
            if (c == '(') {
                abre++;
            } else if (c == ')') {
                fecha++;
            }
        }

        return abre == fecha;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expressao = "";

        if (sc.hasNextLine()) {
            expressao = sc.nextLine();
        }

        while (!theEnd(expressao)) {
            if (algebraBooleana(expressao)) {
                System.out.println("1");
            } else {
                System.out.println("0");
            }

            if (!sc.hasNextLine()) break;
            expressao = sc.nextLine();
        }

        sc.close();
    }
}

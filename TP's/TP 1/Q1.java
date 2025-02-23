import java.util.Scanner;

public class Q3 {
    public static boolean theEnd(String str) {

        if (str.length() == 3 || str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M') {
            return true;
        } else {
            return false;
        }
    }

    public static String cifra(String str) {
        String result = "";

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            c += 3;
            result += c;

        }
        return result;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String input;
        String cripto;

        input = sc.nextLine();

        while (!theEnd(input)) {
            cripto = cifra(input);
            System.out.println(cripto);

            input = sc.nextLine();

        }

        sc.close();
    }

}

import java.util.Scanner;

public class Anagrama {

    public static boolean saoAnagramas(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        int[] contagem = new int[256];

        for (int i = 0; i < s1.length(); i++) {
            contagem[s1.charAt(i)]++;
        }

        for (int i = 0; i < s2.length(); i++) {
            contagem[s2.charAt(i)]--;
        }

        for (int i = 0; i < contagem.length; i++) {
            if (contagem[i] != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean theEnd(String str) {
        return str.length() == 3 &&
               str.charAt(0) == 'F' &&
               str.charAt(1) == 'I' &&
               str.charAt(2) == 'M';
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNext()) return;
        String s1 = sc.next();

        while (!theEnd(s1)) {
            if (!sc.hasNext()) break; 
            String s2 = sc.next();

            if (saoAnagramas(s1, s2)) {
                System.out.println("NÃO");
            } else {
                System.out.println("SIM");
            }

            if (!sc.hasNext()) break;
            s1 = sc.next();
        }

        sc.close();
    }
}

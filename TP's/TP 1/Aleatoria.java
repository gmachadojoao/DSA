import java.util.Scanner;
import java.util.Random;

 class Aleatoria {

    public static boolean theEnd(String str) {

		if (str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M') {
			return true;
		} else {
			return false;
		}
	}
    public static String alteraString(String word, char letra1, char letra2) {
        String result = "";

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (c == letra1) {
                result += letra2;
            } else {
                result += c;
            }
        }

        return result;
    }

    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rd = new Random();
        rd.setSeed(4);

        String word = sc.nextLine();

        while (!theEnd(word)) {
            // Gera letras aleatórias
            char letra1 = (char) ('a' + (Math.abs(rd.nextInt())) % 26);
            char letra2 = (char) ('a' + (Math.abs(rd.nextInt())) % 26);

            System.out.println(alteraString(word, letra1, letra2));

            word = sc.nextLine();
        }

        sc.close();
    }
}

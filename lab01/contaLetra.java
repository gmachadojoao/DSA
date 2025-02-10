import java.util.Scanner;

public class contaLetra {
    public static int contaLetra(String world) {
        int count = 0;
        for (int i = 0; i < world.length(); i++) {
            if (Character.isUpperCase(world.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra = "";

        while (palavra != "FIM") {
            palavra = sc.nextLine();
            System.out.println(contaLetra(palavra));

        }

        sc.close();
    }
}

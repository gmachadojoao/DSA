import java.io.IOException;
import java.util.Scanner;

public class bee_1068 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        
        sc.nextLine(); 
        String expressao = sc.nextLine(); 

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

        if (abre == fecha) {
            System.out.println("Correto");
        } else {
            System.out.println("Incorreto");
        }

        sc.close();
    }
}

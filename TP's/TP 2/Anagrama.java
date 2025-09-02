import java.util.Scanner;

public class Anagrama {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String palavra = sc.nextLine();
        int[] palavraLen = new int[];

        for(int i = 0; palavra.length() !=0; i--){
            palavraLen += palavraLen;
        }
        for(int i =0; palavra.length()!=0; i--){
            for(int j=0; j<palavraLen; j++){
                if(palavra[i] == palavraLen[j]){

                }
            }
        }

    }
}

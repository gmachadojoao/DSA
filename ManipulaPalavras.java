import java.io.File;
import java.util.Scanner;

/*
 * Algoritmo para manipulação de palavras em um arquivo de texto (.txt).
 * A classe 'Palavra' é usada para representar uma palavra e sua linha correspondente.
 * O método 'totalLine' retorna a linha de uma palavra específica.
 */
class Palavra {
    public String palavra;
    public int linha;

    public int totalLine() {
        return linha; // Não precisamos incrementar linha aqui
    }

    public Palavra(String palavra, int linha) {
        this.palavra = palavra;
        this.linha = linha;
    }
}

public class contaPalavra {
    public static void main(String[] args) {

    }
}

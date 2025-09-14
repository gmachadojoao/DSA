import java.util.Scanner;

class Idioma {
    String nome;
    String saudacao;

    public Idioma(String nome, String saudacao) {
        this.nome = nome;
        this.saudacao = saudacao;
    }
}

class Crianca {
    String nome;
    String idioma;

    public Crianca(String nome, String idioma) {
        this.nome = nome;
        this.idioma = idioma;
    }
}



public class bee_2482 {
    // Função para verificar se duas strings são iguais (conteúdo)
public static boolean stringsIguais(String a, String b) {
    if (a.length() != b.length()) return false;
    for (int i = 0; i < a.length(); i++) {
        if (a.charAt(i) != b.charAt(i)) return false;
    }
    return true;
}
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();  // número de traduções
        sc.nextLine();          // consumir quebra de linha

        Idioma[] idiomas = new Idioma[N];

        // Lê os idiomas e suas traduções
        for (int i = 0; i < N; i++) {
            String nomeIdioma = sc.nextLine();
            String saudacao = sc.nextLine();
            idiomas[i] = new Idioma(nomeIdioma, saudacao);
        }

        int M = sc.nextInt();  // número de crianças
        sc.nextLine();          // consumir quebra de linha

        Crianca[] criancas = new Crianca[M];

        // Lê o nome e idioma de cada criança
        for (int i = 0; i < M; i++) {
            String nome = sc.nextLine();
            String idioma = sc.nextLine();
            criancas[i] = new Crianca(nome, idioma);
        }

        // Para cada criança, encontra a saudação correspondente
        for (int i = 0; i < M; i++) {
            String saudacao = "";
            boolean encontrado = false;
            for (int j = 0; j < N && !encontrado; j++) { // só continua enquanto não encontrou
                if (stringsIguais(idiomas[j].nome, criancas[i].idioma)) {
                saudacao = idiomas[j].saudacao;
                encontrado = true; // marca como encontrado
        }
    }
            

            // Impressão da etiqueta
            System.out.println(criancas[i].nome);
            System.out.println(saudacao);
        }

        sc.close();
    }
}

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Tp4 {
    private String appID;
    private String name;
    private String releaseDate;
    private String compradores;
    private String preco;
    private String score;
    private String userScore;
    private String nConquistas;
    private String supportedLanguages;
    private String publishers;
    private String developers;
    private String categorias;
    private String generos;
    private String tags;

    public Tp4() {}

    public void setAppID(String appID) { this.appID = appID.trim(); }
    public void setName(String name) { this.name = name.trim(); }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate.trim(); }
    public void setCompradores(String compradores) { this.compradores = compradores.trim(); }
    public void setPreco(String preco) { this.preco = preco.trim(); }
    public void setScore(String score) { this.score = score.trim(); }
    public void setUserScore(String userScore) { this.userScore = userScore.trim(); }
    public void setnConquistas(String nConquistas) { this.nConquistas = nConquistas.trim(); }
    public void setSupportedLanguages(String supportedLanguages) { this.supportedLanguages = supportedLanguages.trim(); }
    public void setPublishers(String publishers) { this.publishers = publishers.trim(); }
    public void setDevelopers(String developers) { this.developers = developers.trim(); }
    public void setCategorias(String categorias) { this.categorias = categorias.trim(); }
    public void setGeneros(String generos) { this.generos = generos.trim(); }
    public void setTags(String tags) { this.tags = tags.trim(); }

    public String getAppID() { return appID; }
    public String getName() { return name; }
    public String getPreco() { return preco; }

    @Override
    public String toString() {
        String dataFormatada = releaseDate;
        try {
            SimpleDateFormat entrada = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat saida = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada = saida.format(entrada.parse(releaseDate.replace("\"", "")));
        } catch (ParseException e) {}
        return "=> " + appID + " ## " +
               limpar(name) + " ## " +
               dataFormatada + " ## " +
               limpar(compradores) + " ## " +
               limpar(preco) + " ## " +
               "[" + limparLista(supportedLanguages) + "] ## " +
               limpar(score) + " ## " +
               limpar(userScore) + " ## " +
               limpar(nConquistas) + " ## " +
               "[" + limparLista(publishers) + "] ## " +
               "[" + limparLista(developers) + "] ## " +
               "[" + limparLista(categorias) + "] ## " +
               "[" + limparLista(generos) + "] ## " +
               "[" + limparLista(tags) + "] ##";
    }

    private String limpar(String campo) {
        if (campo == null) return "";
        return campo.replace("\"", "").replace("\\[", "").replace("]", "").trim();
    }

    private String limparLista(String campo) {
        campo = limpar(campo);
        String[] partes = campo.split(",");
        StringBuilder resultado = new StringBuilder();
        for (String parte : partes) {
            String item = parte.trim();
            if (!item.isEmpty()) {
                if (resultado.length() > 0) resultado.append(", ");
                resultado.append(item);
            }
        }
        return resultado.toString();
    }
}

class FilaGame {
    private Tp4[] fila;
    private int primeiro;
    private int ultimo;
    private int tamanho;
    private int capacidade;

    public FilaGame(int capacidade) {
        this.capacidade = capacidade;
        this.fila = new Tp4[capacidade];
        this.primeiro = 0;
        this.ultimo = 0;
        this.tamanho = 0;
    }

    public boolean vazia() {
        return tamanho == 0;
    }

    public boolean cheia() {
        return tamanho == capacidade;
    }

    public void enfileirar(Tp4 jogo) throws Exception {
        if (cheia()) {
            throw new Exception("Erro: Fila cheia!");
        }
        fila[ultimo] = jogo;
        ultimo = (ultimo + 1) % capacidade;
        tamanho++;
    }

    public Tp4 desenfileirar() throws Exception {
        if (vazia()) {
            throw new Exception("Erro: Fila vazia!");
        }
        Tp4 jogo = fila[primeiro];
        primeiro = (primeiro + 1) % capacidade;
        tamanho--;
        return jogo;
    }

    public void mostrar() {
        if (vazia()) {
            System.out.println("Fila vazia!");
            return;
        }
        int pos = 0;
        for (int i = 0; i < tamanho; i++) {
            int indice = (primeiro + i) % capacidade;
            System.out.println("[" + pos + "] " + fila[indice]);
            pos++;
        }
    }

    public int getTamanho() {
        return tamanho;
    }
}

public class Fila {
    public static void main(String[] args) {
        String path = "/tmp/games.csv";
        Map<String, Tp4> jogosMap = new HashMap<>();

        try (Scanner fileScanner = new Scanner(new File(path))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String linha = fileScanner.nextLine();
                String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (campos.length < 14) continue;

                Tp4 jogo = new Tp4();
                jogo.setAppID(campos[0]);
                jogo.setName(campos[1]);
                jogo.setReleaseDate(campos[2]);
                jogo.setCompradores(campos[3]);
                jogo.setPreco(campos[4]);
                jogo.setSupportedLanguages(campos[5]);
                jogo.setScore(campos[6]);
                jogo.setUserScore(campos[7]);
                jogo.setnConquistas(campos[8]);
                jogo.setPublishers(campos[9]);
                jogo.setDevelopers(campos[10]);
                jogo.setCategorias(campos[11]);
                jogo.setGeneros(campos[12]);
                jogo.setTags(campos[13]);

                jogosMap.put(jogo.getAppID(), jogo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
            return;
        }

        FilaGame fila = new FilaGame(100);
        Scanner input = new Scanner(System.in);

        while (input.hasNextLine()) {
            String linha = input.nextLine().trim();
            if (linha.equalsIgnoreCase("FIM")) break;

            Tp4 jogo = jogosMap.get(linha);
            if (jogo != null) {
                try {
                    fila.enfileirar(jogo);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        int numOperacoes = input.hasNextInt() ? input.nextInt() : 0;
        input.nextLine();

        for (int i = 0; i < numOperacoes; i++) {
            if (!input.hasNextLine()) break;
            String operacao = input.nextLine().trim();
            String[] partes = operacao.split(" ");

            try {
                if (partes[0].equals("I")) {
                    String id = partes[1];
                    Tp4 jogo = jogosMap.get(id);
                    if (jogo != null) {
                        fila.enfileirar(jogo);
                    }
                } else if (partes[0].equals("R")) {
                    Tp4 removido = fila.desenfileirar();
                    System.out.println("(R) " + removido.getName());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        fila.mostrar();
        input.close();
    }
}
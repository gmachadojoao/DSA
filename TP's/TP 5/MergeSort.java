import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Game {
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

    public Game() {}

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

    public double getPrecoNumerico() {
        if (preco == null) return 0.0;
        String precoLimpo = preco.replace("\"", "").replace("$", "").replace(",", ".").trim().toLowerCase();
        if (precoLimpo.isEmpty() || precoLimpo.equals("free") || precoLimpo.contains("free to play"))
            return 0.0;
        try { return Double.parseDouble(precoLimpo); } 
        catch (NumberFormatException e) { return 0.0; }
    }

    public int getAppIDNumerico() {
        try { return Integer.parseInt(appID); } 
        catch (NumberFormatException e) { return 0; }
    }

    @Override
    public String toString() {
        String dataFormatada = releaseDate;
        try {
            SimpleDateFormat entrada = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat saida = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada = saida.format(entrada.parse(releaseDate.replace("\"", "")));
        } catch (ParseException e) {}

        String scoreStr = score == null || score.isEmpty() ? "0.0" : limpar(score);
        String userScoreStr = userScore == null || userScore.isEmpty() ? "0.0" : limpar(userScore);

        return "=> " + appID + " ## " +
               limpar(name) + " ## " +
               dataFormatada + " ## " +
               limpar(compradores) + " ## " +
               limpar(preco) + " ## " +
               "[" + limparLista(supportedLanguages) + "] ## " +
               scoreStr + " ## " +
               userScoreStr + " ## " +
               limpar(nConquistas) + " ## " +
               "[" + limparLista(publishers) + "] ## " +
               "[" + limparLista(developers) + "] ## " +
               "[" + limparLista(categorias) + "] ## " +
               "[" + limparLista(generos) + "] ## " +
               "[" + limparLista(tags) + "] ##";
    }

    private String limparLista(String campo) {
        campo = limpar(campo).replace("'", "");
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

    private String limpar(String campo) {
        if (campo == null) return "";
        return campo.replace("\"", "").replace("[", "").replace("]", "").trim();
    }
}

abstract class Geracao {
    protected Game[] array;
    protected int n;
    protected int comparacoes;
    protected int movimentacoes;

    public Geracao() { this(100); }
    public Geracao(int tamanho) {
        this.array = new Game[tamanho];
        this.n = 0;
        this.comparacoes = 0;
        this.movimentacoes = 0;
    }

    public void setArray(Game[] array) {
        this.array = array;
        this.n = array.length;
    }

    public Game[] getArray() { return array; }
    public int getComparacoes() { return comparacoes; }
    public int getMovimentacoes() { return movimentacoes; }
    public abstract void sort();
}

class Mergesort extends Geracao {
    public Mergesort() { super(); }
    public Mergesort(int tamanho) { super(tamanho); }

    @Override
    public void sort() { mergesort(0, n - 1); }

    private void mergesort(int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(esq, meio);
            mergesort(meio + 1, dir);
            intercalar(esq, meio, dir);
        }
    }

    private void intercalar(int esq, int meio, int dir) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;
        Game[] a1 = new Game[n1];
        Game[] a2 = new Game[n2];
        for (int i = 0; i < n1; i++) a1[i] = array[esq + i];
        for (int j = 0; j < n2; j++) a2[j] = array[meio + j + 1];

        int i = 0, j = 0;
        for (int k = esq; k <= dir; k++) {
            comparacoes++;
            if (i < n1 && (j >= n2 || comparar(a1[i], a2[j]) <= 0)) {
                array[k] = a1[i++];
            } else {
                array[k] = a2[j++];
            }
            movimentacoes++;
        }
    }

    private int comparar(Game g1, Game g2) {
        double preco1 = g1.getPrecoNumerico();
        double preco2 = g2.getPrecoNumerico();
        comparacoes++;
        if (preco1 < preco2) return -1;
        if (preco1 > preco2) return 1;
        return Integer.compare(g1.getAppIDNumerico(), g2.getAppIDNumerico());
    }
}

public class MergeSort {
    public static void main(String[] args) {
        String path = "/tmp/games.csv";
        List<Game> jogos = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new File(path), "UTF-8")) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                String linha = fileScanner.nextLine();
                String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (campos.length < 14) {
                    continue;
                }

                Game jogo = new Game();
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
                jogos.add(jogo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado: " + e.getMessage());
            return;
        }

        Scanner input = new Scanner(System.in);
        List<String> idsBusca = new ArrayList<>();
        while (input.hasNextLine()) {
            String idBusca = input.nextLine().trim();
            if (idBusca.equalsIgnoreCase("Fim")) break;
            idsBusca.add(idBusca);
        }
        input.close();

        List<Game> jogosFiltrados = new ArrayList<>();
        for (String id : idsBusca) {
            for (Game jogo : jogos) {
                if (id.equals(jogo.getAppID())) {
                    jogosFiltrados.add(jogo);
                    break;
                }
            }
        }
        Set<String> vistos = new HashSet<>();
        jogosFiltrados.removeIf(g -> !vistos.add(g.getAppID()));

        Mergesort mergesort = new Mergesort(jogosFiltrados.size());
        mergesort.setArray(jogosFiltrados.toArray(new Game[0]));

        long inicio = System.nanoTime();
        mergesort.sort();
        long fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1000000.0;
        Game[] arrayOrdenado = mergesort.getArray();

        System.out.println("| 5 preços mais caros |");
        for (int i = arrayOrdenado.length - 1; i >= Math.max(0, arrayOrdenado.length - 5); i--)
            System.out.println(arrayOrdenado[i]);

        System.out.println();

        System.out.println("| 5 preços mais baratos |");
        for (int i = 0; i < Math.min(5, arrayOrdenado.length); i++)
            System.out.println(arrayOrdenado[i]);

        gerarLog("775080", mergesort.getComparacoes(), mergesort.getMovimentacoes(), tempoExecucao);
    }

    private static void gerarLog(String matricula, int comparacoes, int movimentacoes, double tempo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("775080_mergesort.txt"))) {
            writer.write(matricula + "\t" + comparacoes + "\t" + movimentacoes + "\t" + tempo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar log: " + e.getMessage());
        }
    }
}

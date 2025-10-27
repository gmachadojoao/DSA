import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class TP5 {
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

    public TP5() {}

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

    public String getName() { return name; }
    public String getAppID() { return appID; }

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
        return campo.replace("\"", "").replace("[", "").replace("]", "").trim();
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

public class BuscaBinaria {
    public static void main(String[] args) {
        String path = "/tmp/games.csv";
        Map<String, TP5> todosJogos = new HashMap<>();

        try (Scanner fileScanner = new Scanner(new File(path))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String linha = fileScanner.nextLine();
                String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (campos.length < 14) continue;

                TP5 jogo = new TP5();
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

                todosJogos.put(jogo.getAppID(), jogo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
            return;
        }

        Scanner input = new Scanner(System.in);
        List<TP5> jogosInseridos = new ArrayList<>();

        while (true) {
            if (!input.hasNextLine()) break;
            String linha = input.nextLine().trim();
            if (linha.equalsIgnoreCase("FIM")) break;
            
            TP5 jogo = todosJogos.get(linha);
            if (jogo != null) {
                jogosInseridos.add(jogo);
            }
        }

        TP5[] arrayJogos = jogosInseridos.toArray(new TP5[0]);
        Arrays.sort(arrayJogos, Comparator.comparing(j -> j.getName().toLowerCase().trim()));

        List<String> pesquisas = new ArrayList<>();
        while (true) {
            if (!input.hasNextLine()) break;
            String linha = input.nextLine().trim();
            if (linha.equalsIgnoreCase("FIM")) break;
            pesquisas.add(linha);
        }

        long tempoInicio = System.nanoTime();
        int totalComparacoes = 0;

        for (String nome : pesquisas) {
            ResultBusca resultado = buscaBinaria(arrayJogos, nome);
            totalComparacoes += resultado.comparacoes;
            System.out.println(resultado.encontrado ? " SIM" : " NAO");
        }

        long tempoFim = System.nanoTime();
        long tempoExecucao = (tempoFim - tempoInicio) / 1_000_000;

        try (PrintWriter pw = new PrintWriter("775080_binaria.txt")) {
            pw.println("775080\t" + tempoExecucao + "\t" + totalComparacoes);
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de log: " + e.getMessage());
        }

        input.close();
    }

    static class ResultBusca {
        boolean encontrado;
        int comparacoes;
        ResultBusca(boolean encontrado, int comparacoes) {
            this.encontrado = encontrado;
            this.comparacoes = comparacoes;
        }
    }

    static ResultBusca buscaBinaria(TP5[] array, String chave) {
        int left = 0, right = array.length - 1;
        int comparacoes = 0;
        String chaveNormalizada = chave.toLowerCase().trim();

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String nomeMid = array[mid].getName().toLowerCase().trim();
            
            comparacoes++;
            int cmp = chaveNormalizada.compareTo(nomeMid);
            
            if (cmp == 0) {
                return new ResultBusca(true, comparacoes);
            } else if (cmp < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return new ResultBusca(false, comparacoes);
    }
}
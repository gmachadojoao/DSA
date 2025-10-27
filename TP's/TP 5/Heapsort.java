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
    public String getCompradores() { return compradores; }

    public int getEstimatedOwnersMax() {
        try {
            String limpo = compradores.replace("\"", "").trim();
            
            // Se contém hífen, pegar o valor máximo
            if (limpo.contains("-")) {
                String[] partes = limpo.split("-");
                if (partes.length == 2) {
                    return Integer.parseInt(partes[1].trim());
                }
            }
            
            // Se não tem hífen, é um número direto
            return Integer.parseInt(limpo);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        String dataFormatada = releaseDate;
        try {
            SimpleDateFormat entrada = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat saida = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada = saida.format(entrada.parse(releaseDate.replace("\"", "")));
        } catch (ParseException e) {}
        
        // Garantir que userScore tenha .0 se for inteiro
        String userScoreFormatado = limpar(userScore);
        if (!userScoreFormatado.isEmpty() && !userScoreFormatado.contains(".")) {
            userScoreFormatado = userScoreFormatado + ".0";
        }
        
        return "=> " + appID + " ## " +
               limpar(name) + " ## " +
               dataFormatada + " ## " +
               limpar(compradores) + " ## " +
               limpar(preco) + " ## " +
               "[" + limparLista(supportedLanguages) + "] ## " +
               limpar(score) + " ## " +
               userScoreFormatado + " ## " +
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
        if (campo == null) return "";
        // Remover aspas duplas, colchetes E aspas simples
        campo = campo.replace("\"", "").replace("[", "").replace("]", "").replace("'", "").trim();
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

public class Heapsort {
    private static int comparacoes = 0;
    private static int movimentacoes = 0;

    public static void main(String[] args) {
        String path = "/tmp/games.csv";
        List<Tp4> todosJogos = new ArrayList<>();

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

                todosJogos.add(jogo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
            return;
        }

        Scanner input = new Scanner(System.in);
        List<Tp4> jogosInseridos = new ArrayList<>();

        while (true) {
            if (!input.hasNextLine()) break;
            String idBusca = input.nextLine().trim();
            if (idBusca.equalsIgnoreCase("FIM")) break;

            for (Tp4 jogo : todosJogos) {
                if (idBusca.equals(jogo.getAppID())) {
                    jogosInseridos.add(jogo);
                    break;
                }
            }
        }

        Tp4[] array = jogosInseridos.toArray(new Tp4[0]);
        int n = array.length;

        long tempoInicio = System.nanoTime();
        heapsort(array, n);
        long tempoFim = System.nanoTime();
        
        long tempoExecucao = (tempoFim - tempoInicio) / 1_000_000;

        for (Tp4 jogo : array) {
            System.out.println(jogo);
        }

        try (PrintWriter pw = new PrintWriter("775080_heapsort.txt")) {
            pw.println("775080\t" + comparacoes + "\t" + movimentacoes + "\t" + tempoExecucao);
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de log: " + e.getMessage());
        }

        input.close();
    }

    private static void heapsort(Tp4[] array, int n) {
        comparacoes = 0;
        movimentacoes = 0;

        // Alterar vetor para ignorar posição zero
        Tp4[] tmp = new Tp4[n + 1];
        for (int i = 0; i < n; i++) {
            tmp[i + 1] = array[i];
        }

        // Construção do heap
        for (int tamHeap = 2; tamHeap <= n; tamHeap++) {
            construir(tmp, tamHeap);
        }

        // Ordenação propriamente dita
        int tamHeap = n;
        while (tamHeap > 1) {
            swap(tmp, 1, tamHeap--);
            reconstruir(tmp, tamHeap);
        }

        // Voltar para posição zero
        for (int i = 0; i < n; i++) {
            array[i] = tmp[i + 1];
        }
    }

    private static void construir(Tp4[] array, int tamHeap) {
        int i = tamHeap;
        while (i > 1) {
            comparacoes++;
            if (comparar(array[i], array[i / 2]) > 0) {
                swap(array, i, i / 2);
                i = i / 2;
            } else {
                break;
            }
        }
    }

    private static void reconstruir(Tp4[] array, int tamHeap) {
        int i = 1;
        while (i <= (tamHeap / 2)) {
            int filho = getMaiorFilho(array, i, tamHeap);
            comparacoes++;
            if (comparar(array[i], array[filho]) < 0) {
                swap(array, i, filho);
                i = filho;
            } else {
                break;
            }
        }
    }

    private static int getMaiorFilho(Tp4[] array, int i, int tamHeap) {
        int filho;
        if (2 * i == tamHeap) {
            filho = 2 * i;
        } else {
            comparacoes++;
            if (comparar(array[2 * i], array[2 * i + 1]) > 0) {
                filho = 2 * i;
            } else {
                filho = 2 * i + 1;
            }
        }
        return filho;
    }

    private static void swap(Tp4[] array, int i, int j) {
        Tp4 temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        movimentacoes += 3;
    }

    private static int comparar(Tp4 jogo1, Tp4 jogo2) {
        int owners1 = jogo1.getEstimatedOwnersMax();
        int owners2 = jogo2.getEstimatedOwnersMax();

        if (owners1 != owners2) {
            return Integer.compare(owners1, owners2);
        }

        // CORREÇÃO: Desempate por AppID NUMÉRICO (não lexicográfico)
        try {
            int appID1 = Integer.parseInt(jogo1.getAppID());
            int appID2 = Integer.parseInt(jogo2.getAppID());
            return Integer.compare(appID1, appID2);
        } catch (NumberFormatException e) {
            // Fallback para comparação lexicográfica se houver erro
            return jogo1.getAppID().compareTo(jogo2.getAppID());
        }
    }
}
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

class ListaGames {
    private Game[] array;
    private int n;

    public ListaGames() {
        this(100);
    }

    public ListaGames(int tamanho) {
        array = new Game[tamanho];
        n = 0;
    }

    public void inserirInicio(Game game) throws Exception {
        if (n >= array.length) {
            throw new Exception("Erro! Lista cheia.");
        }
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = game;
        n++;
    }

    public void inserir(Game game, int posicao) throws Exception {
        if (n >= array.length) {
            throw new Exception("Erro! Lista cheia.");
        }
        if (posicao < 0 || posicao > n) {
            throw new Exception("Erro! Posição inválida.");
        }
        for (int i = n; i > posicao; i--) {
            array[i] = array[i - 1];
        }
        array[posicao] = game;
        n++;
    }

    public void inserirFim(Game game) throws Exception {
        if (n >= array.length) {
            throw new Exception("Erro! Lista cheia.");
        }
        array[n] = game;
        n++;
    }

    public Game removerInicio() throws Exception {
        if (n == 0) {
            throw new Exception("Erro! Lista vazia.");
        }
        Game removido = array[0];
        n--;
        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
        }
        return removido;
    }

    public Game remover(int posicao) throws Exception {
        if (n == 0) {
            throw new Exception("Erro! Lista vazia.");
        }
        if (posicao < 0 || posicao >= n) {
            throw new Exception("Erro! Posição inválida.");
        }
        Game removido = array[posicao];
        n--;
        for (int i = posicao; i < n; i++) {
            array[i] = array[i + 1];
        }
        return removido;
    }

    public Game removerFim() throws Exception {
        if (n == 0) {
            throw new Exception("Erro! Lista vazia.");
        }
        return array[--n];
    }

    public void mostrar() {
        for (int i = 0; i < n; i++) {
            System.out.println(array[i]);
        }
    }
}

public class Lista {
    public static void main(String[] args) {
        String path = "/tmp/games.csv";
        Map<String, Game> jogosMap = new HashMap<>();

        try (Scanner fileScanner = new Scanner(new File(path))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String linha = fileScanner.nextLine();
                String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (campos.length < 14) continue;

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

                jogosMap.put(jogo.getAppID(), jogo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
            return;
        }

        ListaGames lista = new ListaGames(1000);
        Scanner input = new Scanner(System.in);
        String idBusca;

        while (true) {
            if (!input.hasNextLine()) break;
            idBusca = input.nextLine().trim();
            if (idBusca.equalsIgnoreCase("Fim")) break;

            Game jogo = jogosMap.get(idBusca);
            if (jogo != null) {
                try {
                    lista.inserirFim(jogo);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        if (!input.hasNextLine()) {
            input.close();
            return;
        }

        int numOperacoes = Integer.parseInt(input.nextLine().trim());

        for (int i = 0; i < numOperacoes; i++) {
            if (!input.hasNextLine()) break;
            String comando = input.nextLine().trim();
            String[] partes = comando.split(" ");

            try {
                switch (partes[0]) {
                    case "II":
                        Game jogoII = jogosMap.get(partes[1]);
                        if (jogoII != null) {
                            lista.inserirInicio(jogoII);
                        }
                        break;
                    case "I*":
                        int posicaoI = Integer.parseInt(partes[1]);
                        Game jogoI = jogosMap.get(partes[2]);
                        if (jogoI != null) {
                            lista.inserir(jogoI, posicaoI);
                        }
                        break;
                    case "IF":
                        Game jogoIF = jogosMap.get(partes[1]);
                        if (jogoIF != null) {
                            lista.inserirFim(jogoIF);
                        }
                        break;
                    case "RI":
                        Game removidoI = lista.removerInicio();
                        System.out.println("(R) " + removidoI.getName());
                        break;
                    case "R*":
                        int posicaoR = Integer.parseInt(partes[1]);
                        Game removidoP = lista.remover(posicaoR);
                        System.out.println("(R) " + removidoP.getName());
                        break;
                    case "RF":
                        Game removidoF = lista.removerFim();
                        System.out.println("(R) " + removidoF.getName());
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        lista.mostrar();
        input.close();
    }
}
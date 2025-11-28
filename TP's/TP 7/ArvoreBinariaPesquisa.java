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

class No {
    Tp4 jogo;
    No esq, dir;

    public No(Tp4 jogo) {
        this.jogo = jogo;
        this.esq = null;
        this.dir = null;
    }
}

class ArvoreBinaria {
    private No raiz;
    private int comparacoes;

    public ArvoreBinaria() {
        this.raiz = null;
        this.comparacoes = 0;
    }

    public void inserir(Tp4 jogo) {
        raiz = inserir(jogo, raiz);
    }

    private No inserir(Tp4 jogo, No i) {
        if (i == null) {
            i = new No(jogo);
        } else if (jogo.getName().compareTo(i.jogo.getName()) < 0) {
            i.esq = inserir(jogo, i.esq);
        } else if (jogo.getName().compareTo(i.jogo.getName()) > 0) {
            i.dir = inserir(jogo, i.dir);
        }
        return i;
    }

    public boolean pesquisar(String nome) {
        comparacoes = 0;
        System.out.print(nome + ": =>raiz");
        return pesquisar(nome, raiz);
    }

    private boolean pesquisar(String nome, No i) {
        boolean resp;
        if (i == null) {
            resp = false;
        } else if (nome.equals(i.jogo.getName())) {
            comparacoes++;
            resp = true;
        } else if (nome.compareTo(i.jogo.getName()) < 0) {
            comparacoes++;
            System.out.print(" esq");
            resp = pesquisar(nome, i.esq);
        } else {
            comparacoes++;
            System.out.print(" dir");
            resp = pesquisar(nome, i.dir);
        }
        return resp;
    }

    public int getComparacoes() {
        return comparacoes;
    }
}

public class ArvoreBinariaPesquisa {
    public static void main(String[] args) {
        String path = "/tmp/games.csv";
        Map<String, Tp4> mapaJogos = new HashMap<>();

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

                mapaJogos.put(jogo.getAppID(), jogo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
            return;
        }

        ArvoreBinaria arvore = new ArvoreBinaria();
        Scanner input = new Scanner(System.in);
        String idBusca;

        while (true) {
            if (!input.hasNextLine()) break;
            idBusca = input.nextLine().trim();
            if (idBusca.equalsIgnoreCase("FIM")) break;

            Tp4 jogo = mapaJogos.get(idBusca);
            if (jogo != null) {
                arvore.inserir(jogo);
            }
        }

        long inicio = System.currentTimeMillis();

        List<String> resultados = new ArrayList<>();
        while (true) {
            if (!input.hasNextLine()) break;
            String nomeBusca = input.nextLine().trim();
            if (nomeBusca.equalsIgnoreCase("FIM")) break;

            boolean encontrado = arvore.pesquisar(nomeBusca);
            System.out.println(encontrado ? " SIM" : " NAO");
            
            resultados.add("Comparações: " + arvore.getComparacoes());
        }

        long fim = System.currentTimeMillis();
        input.close();

        try (PrintWriter writer = new PrintWriter(new FileWriter("775080_arvoreBinaria.txt"))) {
            writer.println("775080\t" + (fim - inicio) + "ms\t" + resultados.size() + " pesquisas");
            for (String resultado : resultados) {
                writer.println(resultado);
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de log: " + e.getMessage());
        }
    }
}
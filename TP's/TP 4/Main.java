import java.io.File;
import java.io.FileNotFoundException;
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

    @Override
    public String toString() {
        String dataFormatada = releaseDate;
        try {
            SimpleDateFormat entrada = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat saida = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada = saida.format(entrada.parse(releaseDate.replace("\"", "")));
        } catch (ParseException e) {}

        return "=> "+ appID + " ## " +
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
        String resultado = "";
        for (int i = 0; i < partes.length; i++) {
            String item = partes[i].trim();
            if (!item.isEmpty()) {
                if (!resultado.isEmpty()) resultado += ", ";
                resultado += item;
            }
        }
        return resultado;
    }
}

public class Main {
    public static void main(String[] args) {
        String path = "/tmp/games.csv";
        List<Tp4> jogos = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNextLine()) scanner.nextLine();

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

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

                jogos.add(jogo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
            return;
        }

        Scanner input = new Scanner(System.in);
        String idBusca;
        while (true) {
            idBusca = input.nextLine().trim();
            if (idBusca.equalsIgnoreCase("Fim")) break;

            boolean encontrado = false;
            for (Tp4 jogo : jogos) {
                if (idBusca.equals(jogo.getAppID())) {
                    System.out.println(jogo);
                    encontrado = true;
                }
            }
            if (!encontrado) System.out.println("Jogo com ID " + idBusca + " não encontrado.");
        }
    }
}

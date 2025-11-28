import java.io.*;
import java.util.*;

class Tp4 {
    private String appID;
    private String name;
    private String compradores;

    public Tp4() {
    }

    public void setAppID(String appID) {
        this.appID = appID == null ? null : appID.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setCompradores(String compradores) {
        this.compradores = compradores == null ? null : compradores.trim();
    }

    public String getAppID() {
        return appID;
    }

    public String getName() {
        return name;
    }

    public String getCompradores() {
        return compradores;
    }

    public int getEstimatedOwnersMod15() {
        if (compradores == null)
            return 0;
        String owners = compradores.replace("\"", "").trim();
        if (owners.contains("..")) {
            String[] partes = owners.split("\\.\\.");
            if (partes.length == 2) {
                try {
                    long min = Long.parseLong(partes[0].trim());
                    long max = Long.parseLong(partes[1].trim());
                    long media = (min + max) / 2;
                    return (int) (media % 15);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            try {
                long v = Long.parseLong(owners.replaceAll("[^0-9]", ""));
                return (int) (v % 15);
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }

    private String limpar(String campo) {
        if (campo == null)
            return "";
        return campo.replace("\"", "").replace("[", "").replace("]", "").trim();
    }

    private String limparLista(String campo) {
        campo = limpar(campo);
        if (campo.isEmpty())
            return "";
        String[] partes = campo.split(",");
        StringBuilder resultado = new StringBuilder();
        for (String parte : partes) {
            String item = parte.trim();
            if (!item.isEmpty()) {
                if (resultado.length() > 0)
                    resultado.append(", ");
                resultado.append(item);
            }
        }
        return resultado.toString();
    }
}

class No2 {
    Tp4 jogo;
    No2 esq, dir;

    public No2(Tp4 jogo) {
        this.jogo = jogo;
    }
}

class ArvoreSecundaria {
    private No2 raiz;

    public void inserir(Tp4 jogo) {
        raiz = inserir(jogo, raiz);
    }

    private No2 inserir(Tp4 jogo, No2 i) {
        if (i == null)
            return new No2(jogo);
        String a = jogo.getName();
        String b = i.jogo.getName();
        if (a.compareToIgnoreCase(b) < 0)
            i.esq = inserir(jogo, i.esq);
        else if (a.compareToIgnoreCase(b) > 0)
            i.dir = inserir(jogo, i.dir);
        return i;
    }

    public boolean pesquisar(String nome) {
        return pesquisar(nome, raiz);
    }

    private boolean pesquisar(String nome, No2 i) {
        if (i == null)
            return false;
        int cmp = nome.compareToIgnoreCase(i.jogo.getName());
        if (cmp == 0)
            return true;
        if (cmp < 0) {
            System.out.print(" esq");
            return pesquisar(nome, i.esq);
        } else {
            System.out.print(" dir");
            return pesquisar(nome, i.dir);
        }
    }
}

class No1 {
    int chave;
    No1 esq, dir;
    ArvoreSecundaria arv;

    public No1(int chave) {
        this.chave = chave;
        this.arv = new ArvoreSecundaria();
    }
}

class ArvorePrimaria {
    private No1 raiz;

    public void construirEstrutura() {
        int[] ordem = { 7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14 };
        for (int chave : ordem)
            inserir(chave);
    }

    private void inserir(int chave) {
        raiz = inserir(chave, raiz);
    }

    private No1 inserir(int chave, No1 i) {
        if (i == null)
            return new No1(chave);
        if (chave < i.chave)
            i.esq = inserir(chave, i.esq);
        else if (chave > i.chave)
            i.dir = inserir(chave, i.dir);
        return i;
    }

    public void inserirJogo(Tp4 jogo) {
        int chave = jogo.getEstimatedOwnersMod15();
        No1 no = buscar(chave, raiz);
        if (no != null)
            no.arv.inserir(jogo);
    }

    private No1 buscar(int chave, No1 i) {
        if (i == null)
            return null;
        if (chave == i.chave)
            return i;
        if (chave < i.chave)
            return buscar(chave, i.esq);
        return buscar(chave, i.dir);
    }

    public boolean pesquisar(String nome) {
        System.out.print("=> " + nome + " => raiz");
        boolean resp = pesquisarTodos(nome, raiz);
        System.out.println(resp ? " SIM" : " NAO");
        return resp;
    }

    private boolean pesquisarTodos(String nome, No1 i) {
        if (i == null)
            return false;

        if (i.arv.pesquisar(nome))
            return true;

        System.out.print(" ESQ");
        if (pesquisarTodos(nome, i.esq))
            return true;

        System.out.print(" DIR");
        return pesquisarTodos(nome, i.dir);
    }
}

public class ArvoreArvore {
    public static void main(String[] args) {
        String path = "/tmp/games.csv";
        Map<String, Tp4> mapaJogos = new HashMap<>();

        try (Scanner s = new Scanner(new File(path))) {
            if (s.hasNextLine())
                s.nextLine();
            while (s.hasNextLine()) {
                String linha = s.nextLine();
                String[] c = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (c.length < 14)
                    continue;

                Tp4 j = new Tp4();
                j.setAppID(c[0]);
                j.setName(c[1]);
                j.setCompradores(c[3]);
                mapaJogos.put(j.getAppID(), j);
            }
        } catch (Exception e) {
            return;
        }

        ArvorePrimaria arv = new ArvorePrimaria();
        arv.construirEstrutura();

        Scanner in = new Scanner(System.in);

        while (true) {
            if (!in.hasNextLine())
                break;
            String id = in.nextLine().trim();
            if (id.equals("FIM"))
                break;
            Tp4 jogo = mapaJogos.get(id);
            if (jogo != null)
                arv.inserirJogo(jogo);
        }

        while (true) {
            if (!in.hasNextLine())
                break;
            String nome = in.nextLine().trim();
            if (nome.equals("FIM"))
                break;
            arv.pesquisar(nome);
        }

        in.close();
    }
}

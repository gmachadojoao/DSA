class Celula {
    public char elemento;
    public Celula prox;

    public Celula() {
        this(' ');
    }

    public Celula(char elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

class Lista {
    private Celula primeiro;
    private Celula ultimo;

    public Lista() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserirInicio(char x) {
        Celula tmp = new Celula(x);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
    }

    public void inserirFim(char x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public char removerInicio() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");
        }

        Celula tmp = primeiro.prox;
        char resp = tmp.elemento;
        primeiro.prox = tmp.prox;
        if (tmp == ultimo) {
            ultimo = primeiro;
        }
        tmp = null;
        return resp;
    }

    public char removerFim() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");
        }

        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox);
        char resp = ultimo.elemento;
        ultimo = i;
        ultimo.prox = null;
        return resp;
    }

    public void inserir(char x, int pos) throws Exception {
        int tamanho = tamanho();

        if (pos < 0 || pos > tamanho) {
            throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
        } else if (pos == 0) {
            inserirInicio(x);
        } else if (pos == tamanho) {
            inserirFim(x);
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);

            Celula tmp = new Celula(x);
            tmp.prox = i.prox;
            i.prox = tmp;
        }
    }

    public char remover(int pos) throws Exception {
        int tamanho = tamanho();
        char resp;

        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");
        } else if (pos < 0 || pos >= tamanho) {
            throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + ") invalida!");
        } else if (pos == 0) {
            resp = removerInicio();
        } else if (pos == tamanho - 1) {
            resp = removerFim();
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);

            Celula tmp = i.prox;
            resp = tmp.elemento;
            i.prox = tmp.prox;
            tmp = null;
        }
        return resp;
    }

    public void mostrar() {
        System.out.print("[ ");
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public boolean pesquisar(char x) {
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            if (i.elemento == x) {
                return true;
            }
        }
        return false;
    }

    public int tamanho() {
        int tamanho = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox, tamanho++);
        return tamanho;
    }

    public void inserirString(String str) {
        for (int i = 0; i < str.length(); i++) {
            inserirFim(str.charAt(i));
        }
    }

    public String obterString() {
        String s = "";
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            s += i.elemento;
        }
        return s;
    }
}

class StringToList {
    public static void main(String[] args) {
        try {
            System.out.println("=== LISTA ENCADEADA COM CHAR ===");
            Lista lista = new Lista();

            lista.inserirString("HELLO WORLD");
            
            System.out.print("Lista: ");
            lista.mostrar();
            
            System.out.println("Como string: " + lista.obterString());
            System.out.println("Tamanho: " + lista.tamanho());
            
            System.out.println("\n--- Testando operacoes ---");
            lista.inserirInicio('!');
            lista.inserirFim('!');
            lista.inserir('_', 6);
            
            System.out.print("Apos insercoes: ");
            lista.mostrar();
            System.out.println("String resultante: " + lista.obterString());
            
            char removido1 = lista.removerInicio();
            char removido2 = lista.removerFim();
            
            System.out.println("\nRemovidos: '" + removido1 + "' e '" + removido2 + "'");
            System.out.print("Lista final: ");
            lista.mostrar();
            System.out.println("String final: " + lista.obterString());
            
            System.out.println("\nPesquisando 'L': " + lista.pesquisar('L'));
            System.out.println("Pesquisando 'Z': " + lista.pesquisar('Z'));

        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }
    }
}
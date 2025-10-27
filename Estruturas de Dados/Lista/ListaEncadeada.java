class Celula {
    public int elemento;
    public Celula prox;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
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

    public void inserirInicio(int x) {
        Celula tmp = new Celula(x);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
    }

    public void inserirFim(int x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public int removerInicio() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");
        }

        Celula tmp = primeiro.prox;
        int resp = tmp.elemento;
        primeiro.prox = tmp.prox;
        if (tmp == ultimo) {
            ultimo = primeiro;
        }
        tmp = null;
        return resp;
    }

    public int removerFim() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");
        }

        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox);
        int resp = ultimo.elemento;
        ultimo = i;
        ultimo.prox = null;
        return resp;
    }

    public void inserir(int x, int pos) throws Exception {
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

    public int remover(int pos) throws Exception {
        int tamanho = tamanho();
        int resp;

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

    public boolean pesquisar(int x) {
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
}

class ListaEncadeada {
    public static void main(String[] args) {
        try {
            System.out.println("=== LISTA ENCADEADA ===");
            Lista lista = new Lista();

            lista.inserirInicio(1);
            lista.inserirInicio(0);
            lista.inserirFim(4);
            lista.inserirFim(5);
            lista.inserir(2, 2);
            lista.inserir(3, 3);
            lista.inserir(6, 6);
            lista.inserir(-1, 0);
            lista.inserirFim(7);
            lista.inserirFim(8);

            System.out.print("Apos insercoes: ");
            lista.mostrar();

            int x1 = lista.remover(3);
            int x2 = lista.remover(2);
            int x3 = lista.removerFim();
            int x4 = lista.removerInicio();
            int x5 = lista.remover(0);
            int x6 = lista.remover(4);
            lista.inserirFim(9);

            System.out.print("Apos remocoes (" + x1 + ", " + x2 + ", " + x3 + ", " + x4 + ", " + x5 + ", " + x6 + "): ");
            lista.mostrar();
        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }
    }
}

class Fila {

    // Classe interna que representa um nó da fila
    class Celula {
        public int elemento; // Valor armazenado na célula
        public Celula prox;  // Referência para a próxima célula

        // Construtor padrão, inicializa com 0
        public Celula() {
            this(0);
        }

        // Construtor que recebe um valor para armazenar
        public Celula(int elemento) {
            this.elemento = elemento;
            this.prox = null;
        }
    }

    // Referências para o início e fim da fila
    private Celula primeiro; // Nó cabeça (não contém elemento válido)
    private Celula ultimo;   // Último elemento real da fila

    /**
     * Construtor da fila vazia (apenas nó cabeça)
     */
    public Fila() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    /**
     * Insere um elemento no final da fila (FIFO)
     * @param x elemento a ser inserido
     */
    public void inserir(int x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    /**
     * Remove e retorna o elemento do início da fila (FIFO)
     * @return elemento removido
     * @throws Exception se a fila estiver vazia
     */
    public int remover() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover! Fila vazia.");
        }

        Celula tmp = primeiro.prox;
        int resp = tmp.elemento;
        primeiro.prox = tmp.prox;

        // Se o elemento removido era o último, atualiza ponteiro 'ultimo'
        if (tmp == ultimo) {
            ultimo = primeiro;
        }

        tmp.prox = null; // ajuda o garbage collector
        return resp;
    }

    /**
     * Verifica se a fila está vazia
     * @return true se vazia, false caso contrário
     */
    public boolean taVazia() {
        return primeiro == ultimo;
    }

    /**
     * Mostra os elementos da fila na tela, separados por espaços
     */
    public void mostrar() {
        System.out.print("[ ");
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    /**
     * Retorna o tamanho da fila
     * @return quantidade de elementos
     */
    public int tamanho() {
        int conta = 0;
        for (Celula c = primeiro.prox; c != null; c = c.prox) {
            conta++;
        }
        return conta;
    }

    /**
     * Retorna o primeiro elemento da fila sem removê-lo
     * @return primeiro elemento
     * @throws Exception se a fila estiver vazia
     */
    public int getPrimeiro() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro: fila vazia, nenhum primeiro elemento disponível.");
        }
        return primeiro.prox.elemento;
    }

    /**
     * Retorna o último elemento da fila sem removê-lo
     * @return último elemento
     * @throws Exception se a fila estiver vazia
     */
    public int getUltimo() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro: fila vazia, nenhum último elemento disponível.");
        }
        return ultimo.elemento;
    }

    /**
     * Inverte a ordem dos elementos da fila
     */
    public void inverteFila() {
        Celula anterior = null;          // Nó anterior (já invertido)
        Celula atual = primeiro.prox;    // Nó atual sendo processado
        Celula proxima;                  // Próximo nó antes de quebrar o link

        ultimo = atual; // O primeiro elemento se tornará o último

        while (atual != null) {
            proxima = atual.prox;       // Salva referência do próximo
            atual.prox = anterior;      // Inverte a ligação
            anterior = atual;           // Avança anterior
            atual = proxima;            // Avança atual
        }

        primeiro.prox = anterior; // Atualiza ponteiro do nó cabeça
    }

    // Método principal para testes
    public static void main(String[] args) throws Exception {
        System.out.println("==== FILA FLEXÍVEL ====");
        Fila fila = new Fila();

        fila.inserir(5);
        fila.inserir(7);
        fila.inserir(8);
        fila.inserir(9);

        System.out.println("Após inserções (5, 7, 8, 9): ");
        System.out.println("Tamanho: " + fila.tamanho());
        System.out.println("Primeiro elemento: " + fila.getPrimeiro() + " Último elemento: " + fila.getUltimo());
        fila.mostrar();

        int x1 = fila.remover();
        int x2 = fila.remover();

        System.out.println("Após remoções (" + x1 + ", " + x2 + "):");
        System.out.println("Tamanho: " + fila.tamanho());
        System.out.println("Primeiro elemento: " + fila.getPrimeiro() + " Último elemento: " + fila.getUltimo());
        fila.mostrar();
    }
}

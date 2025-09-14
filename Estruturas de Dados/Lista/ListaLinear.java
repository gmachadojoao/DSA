public class ListaLinear {
    public static final int MAX_ELEMENTOS = 6;

    private int[] array;
    private int posInicial;

    public ListaLinear() {
        array = new int[MAX_ELEMENTOS];
        posInicial = 0;
    }

    public void inserirInicio(int x) throws Exception {
        if (posInicial >= array.length) {
            throw new Exception("Erro ao inserir!");
        }
        for (int i = posInicial; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = x;
        posInicial++;
    }

    public void inserirFim(int x) throws Exception {
        if (posInicial >= array.length) {
            throw new Exception("Erro ao inserir!");
        }
        array[posInicial] = x;
        posInicial++;
    }

    public void inserir(int x, int pos) throws Exception {
        if (posInicial >= array.length || pos < 0 || pos > posInicial) {
            throw new Exception("Erro ao inserir!");
        }
        for (int i = posInicial; i > pos; i--) {
            array[i] = array[i - 1];
        }
        array[pos] = x;
        posInicial++;
    }

    public int removerInicio() throws Exception {
        if (posInicial == 0) {
            throw new Exception("Erro ao remover!");
        }
        int resp = array[0];
        posInicial--;
        for (int i = 0; i < posInicial; i++) {
            array[i] = array[i + 1];
        }
        return resp;
    }

    public int removerFim() throws Exception {
        if (posInicial == 0) {
            throw new Exception("Erro ao remover!");
        }
        return array[--posInicial];
    }

    public int remover(int pos) throws Exception {
        if (posInicial == 0 || pos < 0 || pos >= posInicial) {
            throw new Exception("Erro ao remover!");
        }
        int resp = array[pos];
        posInicial--;
        for (int i = pos; i < posInicial; i++) {
            array[i] = array[i + 1];
        }
        return resp;
    }

    public void mostrar() {
        System.out.print("[ ");
        for (int i = 0; i < posInicial; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("]");
    }

    public boolean pesquisar(int x) {
        boolean retorno = false;
        for (int i = 0; i < posInicial && retorno == false; i++) {
            retorno = (array[i] == x);
        }
        return retorno;
    }

    // 🔹 Método Bubble Sort
    public void bubbleSort() {
        for (int i = 0; i < posInicial - 1; i++) {
            for (int j = 0; j < posInicial - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    // 🔹 Método Insertion Sort
    public void insertionSort() {
        for (int i = 1; i < posInicial; i++) {
            int chave = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > chave) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = chave;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("==== LISTA ESTATICA ====");
        ListaLinear lista = new ListaLinear();

        lista.inserirFim(5);
        lista.inserirFim(1);
        lista.inserirFim(4);
        lista.inserirFim(2);
        lista.inserirFim(3);

        System.out.print("Lista original: ");
        lista.mostrar();

        // Teste Bubble Sort
        lista.bubbleSort();
        System.out.print("Lista ordenada (Bubble Sort): ");
        lista.mostrar();

        // Reinserindo desordenada
        lista = new ListaLinear();
        lista.inserirFim(5);
        lista.inserirFim(1);
        lista.inserirFim(4);
        lista.inserirFim(2);
        lista.inserirFim(3);

        // Teste Insertion Sort
        lista.insertionSort();
        System.out.print("Lista ordenada (Insertion Sort): ");
        lista.mostrar();
    }
}

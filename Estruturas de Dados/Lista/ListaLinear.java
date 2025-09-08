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

    public static void main(String[] args) throws Exception {
        System.out.println("==== LISTA ESTATICA ====");
        ListaLinear lista = new ListaLinear();
        int x1, x2, x3;

        lista.inserirInicio(1);
        lista.inserirInicio(0);
        lista.inserirFim(2);
        lista.inserirFim(3);
        System.out.println("Após insercoes no Inicio e Fim:");
        lista.mostrar();

        lista.inserir(4, 3);
        lista.inserir(5, 2);
        System.out.print("Lista completa apos insercoes nas posições: ");
        lista.mostrar();

        x1 = lista.removerInicio();
        x2 = lista.removerFim();
        x3 = lista.remover(2);

        System.out.print("Apos remocoes (" + x1 + ", " + x2 + ", " + x3 + "): ");
        lista.mostrar();
    }
}

public class Pilha {
    private int[] array;
    private int topo;

    public Pilha() {
        this(6);
    }

    public Pilha(int tamanho) {
        array = new int[tamanho];
        topo = 0;
    }

    public void empilhar(int elemento) throws Exception {
        if (topo >= array.length) {
            throw new Exception("Erro: Pilha cheia!");
        }
        array[topo] = elemento;
        topo++;
    }

    // desempilhar = pop
    public int desempilhar() throws Exception {
        if (isVazia()) {
            throw new Exception("Erro: Pilha vazia!");
        }
        topo--;
        return array[topo];
    }

    // Ver topo
    public int peek() throws Exception {
        if (isVazia()) {
            throw new Exception("Erro: Pilha vazia!");
        }
        return array[topo - 1];
    }

    // Mostrar elementos
    public void mostrar() {
        System.out.print("Pilha: ");
        for (int i = topo - 1; i >= 0; i--) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    // Verifica se está vazia
    public boolean isVazia() {
        return topo == 0;
    }

    // Verifica se está cheia
    public boolean isCheia() {
        return topo == array.length;
    }

    // Retorna quantidade de elementos
    public int tamanho() {
        return topo;
    }

    // Teste da pilha
    public static void main(String[] args) throws Exception {
        Pilha p1 = new Pilha(6);

        p1.empilhar(10);
        p1.empilhar(11);
        p1.empilhar(15);
        p1.empilhar(20);

        p1.mostrar(); // deve mostrar: 20 15 11 10

        System.out.println("Topo: " + p1.peek()); // 20

        System.out.println("Desempilhando: " + p1.desempilhar()); // 20
        p1.mostrar(); // deve mostrar: 15 11 10
    }
}

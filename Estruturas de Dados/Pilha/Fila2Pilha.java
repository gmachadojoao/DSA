public class Fila2Pilha {

    private Fila f1, f2;

    public Fila() {
        f1 = new Fila(6);
        f2 = new Fila(6);
    }

    public Fila2Pilha(int tamanho) {
        f1 = new Fila(tamanho);
        f2 = new Fila(tamanho);
    }

    // empilhar = push
    public void empilhar(int elemento) throws Exception {
        // Insere primeiro na fila auxiliar
        f2.inserir(elemento);

        // Transfere todos os elementos de f1 para f2
        while (!f1.isVazia()) {
            f2.inserir(f1.remover());
        }

        // Troca as filas (f1 será sempre a principal)
        Fila temp = f1;
        f1 = f2;
        f2 = temp;
    }

    // desempilhar = pop
    public int desempilhar() throws Exception {
        if (f1.isVazia()) {
            throw new Exception("Erro: Pilha vazia!");
        }
        return f1.remover(); // sempre remove o mais recente
    }

    public void mostrar() {
        f1.mostrar();
    }

    public boolean isVazia() {
        return f1.isVazia();
    }

    public static void main(String[] args) throws Exception {
        Fila2Pilha p1 = new Pilha();

        p1.empilhar(10);
        p1.empilhar(11);
        p1.empilhar(15);
        p1.empilhar(20);

        p1.mostrar(); // deve mostrar: 20, 15, 11, 10

        System.out.println("Desempilhando: " + p1.desempilhar()); // 20
        p1.mostrar(); // deve mostrar: 15, 11, 10
    }
}

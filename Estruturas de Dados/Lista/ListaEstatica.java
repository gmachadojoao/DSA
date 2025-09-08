class Lista {
    private int[] array;
    private int n; // Quantidade de elementos na lista

    // Construtor com tamanho
    public Lista(int tamanho) {
        array = new int[tamanho];
        n = 0;
        }

        // Inserir no início da lista
       public String inserirInicio(int x) {
        if (n >= array.length) {
            return "Erro: lista cheia!";
        }
        // Desloca elementos para a direita
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = x;
        n++;
        return "Elemento inserido com sucesso";
    }


    // Mostrar a lista
    public void mostrar() {
        for (int i = 0; i < n; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
  	System.out.println("Lista Simples");
  	System.out.println("Adicionando no começo");
            Lista lista = new Lista(5);
            lista.inserirInicio(10);
            lista.inserirInicio(20);
            lista.inserirInicio(30);

            lista.mostrar();
    }
}

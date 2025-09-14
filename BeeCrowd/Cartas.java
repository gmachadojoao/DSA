import java.util.Scanner;

public class Cartas {

    public static class ListaLinear {
        private int[] array;
        private int posInicial;

        public ListaLinear(int tamanho) {
            array = new int[tamanho];
            posInicial = 0;
        }

        public void inserirFim(int x) throws Exception {
            if (posInicial >= array.length) {
                throw new Exception("Erro ao inserir!");
            }
            array[posInicial++] = x;
        }

        public int removerInicio() throws Exception {
            if (posInicial == 0) {
                throw new Exception("Erro ao remover!");
            }
            int resp = array[0];
            for (int i = 0; i < posInicial - 1; i++) {
                array[i] = array[i + 1];
            }
            posInicial--;
            return resp;
        }

        public int tamanho() {
            return posInicial;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // primeira leitura
        while (n != 0) {      // loop controlado pela condição
            ListaLinear pilha = new ListaLinear(n);
            ListaLinear descartadas = new ListaLinear(n);

            // Inicializa pilha com 1 até n
            for (int i = 1; i <= n; i++) {
                pilha.inserirFim(i);
            }

            // Processa cartas
            while (pilha.tamanho() >= 2) {
                // Descartar topo
                int cartaDescartada = pilha.removerInicio();
                descartadas.inserirFim(cartaDescartada);

                // Mover próximo para a base
                int cartaMover = pilha.removerInicio();
                pilha.inserirFim(cartaMover);
            }

            // Mostrar resultado
            System.out.print("Cartas descartadas: ");
            for (int i = 0; i < descartadas.tamanho(); i++) {
                System.out.print(descartadas.array[i]);
                if (i != descartadas.tamanho() - 1) System.out.print(", ");
            }
            System.out.println();

            System.out.println("Carta restante: " + pilha.removerInicio());

            n = sc.nextInt(); // próxima leitura
        }

        sc.close();
    }
}

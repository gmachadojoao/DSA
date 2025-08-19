class SelectionSort {
    public static void main(String[] args) {
        int vetor[] = {13, 3, 9, 7, 19};

        // Imprime vetor original
        System.out.println("Vetor original:");
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i] + " ");
        }
        System.out.println();

        // Selection Sort
        for (int i = 0; i < vetor.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < vetor.length; j++) {
                if (vetor[menor] > vetor[j]) {
                    menor = j;
                }
            }
            // troca
            int temp = vetor[i];
            vetor[i] = vetor[menor];
            vetor[menor] = temp;
        }

        // Imprime vetor ordenado
        System.out.println("Vetor ordenado:");
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i] + " ");
        }
    }
}

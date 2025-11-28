class Celula {
    int elemento;
    Celula prox;

    Celula(int x) {
        this.elemento = x;
        this.prox = null;
    }
}

class Lista {
    Celula primeiro;
    Celula ultimo;

    public Lista() {
        primeiro = ultimo = new Celula(-1); // nó sentinela
    }

    public void inserirFim(int x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public void mostrar() {
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println();
    }

    public static Lista merge(int[] a, int[] b) {
        Lista resultado = new Lista();
        int i = 0, j = 0;

        // intercalar enquanto houver elementos em ambos os arrays
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                resultado.inserirFim(a[i]);
                i++;
            } else {
                resultado.inserirFim(b[j]);
                j++;
            }
        }

        // adicionar o restante de a[]
        while (i < a.length) {
            resultado.inserirFim(a[i]);
            i++;
        }

        // adicionar o restante de b[]
        while (j < b.length) {
            resultado.inserirFim(b[j]);
            j++;
        }

        return resultado;
    }
}
public class MergeTwoList {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = {1, 3, 5};

        Lista l3 = Lista.merge(a, b);

        l3.mostrar();
    }
}

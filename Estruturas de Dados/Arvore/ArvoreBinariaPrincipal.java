import java.util.*;


class No {
    public int elemento;
    public No esq, dir;

    public No(int elemento) {
        this(elemento, null, null);
    }

    public No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

/**
 * Arvore Binaria de Pesquisa
 */
class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    public boolean pesquisar(int x) {
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(int x, No i) {
        if (i == null) return false;
        if (x == i.elemento) return true;
        if (x < i.elemento) return pesquisar(x, i.esq);
        return pesquisar(x, i.dir);
    }

    public void inserir(int x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(int x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        } else {
            throw new Exception("Erro ao inserir!");
        }
        return i;
    }

    public void remover(int x) throws Exception {
        raiz = remover(x, raiz);
    }

    private No remover(int x, No i) throws Exception {
        if (i == null) throw new Exception("Erro ao remover!");
        if (x < i.elemento) i.esq = remover(x, i.esq);
        else if (x > i.elemento) i.dir = remover(x, i.dir);
        else if (i.dir == null) i = i.esq;
        else if (i.esq == null) i = i.dir;
        else i.esq = maiorEsq(i, i.esq);
        return i;
    }

    private No maiorEsq(No i, No j) {
        if (j.dir == null) {
            i.elemento = j.elemento;
            j = j.esq;
        } else {
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    public int getRaiz() throws Exception {
        if (raiz == null) throw new Exception("Árvore vazia!");
        return raiz.elemento;
    }

    public int getAltura() {
        return getAltura(raiz, 0);
    }

    private int getAltura(No i, int altura) {
        if (i == null) return altura - 1;
        int esq = getAltura(i.esq, altura + 1);
        int dir = getAltura(i.dir, altura + 1);
        return Math.max(esq, dir);
    }

    public static boolean igual(ArvoreBinaria a1, ArvoreBinaria a2) {
        return igual(a1.raiz, a2.raiz);
    }

    private static boolean igual(No i1, No i2) {
        if (i1 != null && i2 != null)
            return (i1.elemento == i2.elemento)
                    && igual(i1.esq, i2.esq)
                    && igual(i1.dir, i2.dir);
        return i1 == null && i2 == null;
    }
}

/**
 * Classe TreeSort (usando árvore binária para ordenar)
 */
class TreeSort {
    private No raiz;
    private int n;

    public TreeSort() {
        raiz = null;
        n = 0;
    }

    public void inserir(int x) {
        n++;
        raiz = inserir(x, raiz);
    }

    private No inserir(int x, No i) {
        if (i == null) i = new No(x);
        else if (x < i.elemento) i.esq = inserir(x, i.esq);
        else i.dir = inserir(x, i.dir);
        return i;
    }

    public int[] sort() {
        int[] array = new int[n];
        n = 0;
        sort(raiz, array);
        return array;
    }

    private void sort(No i, int[] array) {
        if (i != null) {
            sort(i.esq, array);
            array[n++] = i.elemento;
            sort(i.dir, array);
        }
    }
}


public class ArvoreBinariaPrincipal {
    public static void main(String[] args) throws Exception {
        // PARTE 1: árvore crescente com altura e log2
        System.out.println("=== PARTE 1: CRESCENTE ===");
        ArvoreBinaria arvore = new ArvoreBinaria();
        for (int i = 1; i <= 30; i++) {
            arvore.inserir(i);
            System.out.printf("Nó: %d\tAltura: %d\tlog2(n): %.2f%n", i, arvore.getAltura(), Math.log(i) / Math.log(2));
        }

        // PARTE 2: árvores aleatórias com comparação
        System.out.println("\n=== PARTE 2: ALEATÓRIA ===");
        ArvoreBinaria a1 = new ArvoreBinaria();
        ArvoreBinaria a2 = new ArvoreBinaria();
        Random gerador = new Random(4);

        for (int i = 1; i <= 5000; i++) {
            int valor = Math.abs(gerador.nextInt() % 10000);
            if (!a1.pesquisar(valor)) {
                a1.inserir(valor);
                a2.inserir(valor);
            }

            if (i % 1000 == 0) {
                a1.remover(valor);
                a2.remover(valor);
                a1.remover(a1.getRaiz());
                a2.remover(a2.getRaiz());
            }

            if (!ArvoreBinaria.igual(a1, a2)) {
                System.out.println("Árvores diferentes...");
                break;
            }
        }

        // PARTE 3: TreeSort demonstrativo
        System.out.println("\n=== PARTE 3: TREESORT ===");
        TreeSort ts = new TreeSort();
        Random r = new Random(0);
        System.out.print("Array original: ");
        for (int i = 0; i < 20; i++) {
            int valor = Math.abs(r.nextInt() % 100);
            ts.inserir(valor);
            System.out.print(valor + " ");
        }

        System.out.print("\nArray ordenado: ");
        int[] array = ts.sort();
        for (int v : array) System.out.print(v + " ");
        System.out.println("\n=== FIM ===");
    }
}

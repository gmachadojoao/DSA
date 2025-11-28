// Classe que representa um nó da árvore binária
class NoArvore {
    int elemento;
    NoArvore esq, dir;

    NoArvore(int elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
    }
}

// Classe que representa uma célula da lista encadeada
class Celula {
    NoArvore raiz;
    Celula prox;

    Celula(NoArvore raiz) {
        this.raiz = raiz;
        this.prox = null;
    }
}

// Classe que representa a lista de árvores
class ListaDeArvores {
    Celula primeiro;

    // Inserir uma nova árvore na lista
    void inserir(NoArvore raiz) {
        Celula nova = new Celula(raiz);
        if (primeiro == null) {
            primeiro = nova;
        } else {
            Celula atual = primeiro;
            while (atual.prox != null) {
                atual = atual.prox;
            }
            atual.prox = nova;
        }
    }

    // ------------------------------
    // Funções de Árvore Binária
    // ------------------------------

    // Inserir um novo valor na árvore
    NoArvore inserirNaArvore(NoArvore i, int x) {
        if (i == null) {
            return new NoArvore(x);
        } else if (x < i.elemento) {
            i.esq = inserirNaArvore(i.esq, x);
        } else if (x > i.elemento) {
            i.dir = inserirNaArvore(i.dir, x);
        }
        return i;
    }

    // Função pública de remoção (chama a recursiva)
    NoArvore remover(int x, NoArvore i) throws Exception {
        if (i == null) {
            throw new Exception("Erro: elemento não encontrado!");
        } else if (x < i.elemento) {
            i.esq = remover(x, i.esq);   // busca na subárvore esquerda
        } else if (x > i.elemento) {
            i.dir = remover(x, i.dir);   // busca na subárvore direita
        } else if (i.dir == null) {
            // caso 1: nó só tem filho à esquerda
            i = i.esq;
        } else if (i.esq == null) {
            // caso 2: nó só tem filho à direita
            i = i.dir;
        } else {
            // caso 3: nó com dois filhos
            i.esq = maiorEsq(i, i.esq);
        }
        return i;
    }

    // Função auxiliar para encontrar o maior elemento da esquerda
    NoArvore maiorEsq(NoArvore i, NoArvore j) {
        if (j.dir == null) {
            // Substitui o valor do nó removido
            i.elemento = j.elemento;
            // Retorna a subárvore esquerda (removendo o nó máximo)
            j = j.esq;
        } else {
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    // ------------------------------
    // Impressão de folhas
    // ------------------------------
    void imprimirFolhasArvore(NoArvore raiz) {
        if (raiz == null) return;

        if (raiz.esq == null && raiz.dir == null) {
            System.out.print(raiz.elemento + " ");
        }
        imprimirFolhasArvore(raiz.esq);
        imprimirFolhasArvore(raiz.dir);
    }

    void imprimirFolhasDeCadaArvore() {
        Celula atual = primeiro;
        int indice = 1;

        while (atual != null) {
            System.out.print("Folhas da árvore " + indice + ": ");
            imprimirFolhasArvore(atual.raiz);
            System.out.println();
            indice++;
            atual = atual.prox;
        }
    }
}

// Classe principal
public class ListaArvore {
    public static void main(String[] args) {
        try {
            // Criando a primeira árvore (igual à imagem da função)
            NoArvore a1 = new NoArvore(3);
            a1.esq = new NoArvore(1);
            a1.esq.dir = new NoArvore(2);
            a1.dir = new NoArvore(5);
            a1.dir.esq = new NoArvore(4);
            a1.dir.dir = new NoArvore(8);
            a1.dir.dir.esq = new NoArvore(7);
            a1.dir.dir.esq.esq = new NoArvore(6);

            // Criando lista de árvores
            ListaDeArvores lista = new ListaDeArvores();
            lista.inserir(a1);

            System.out.println("Antes da remoção:");
            lista.imprimirFolhasDeCadaArvore();

            // Remover o valor 2 da árvore
            a1 = lista.remover(2, a1);

            System.out.println("\nDepois da remoção do elemento 2:");
            lista.imprimirFolhasDeCadaArvore();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

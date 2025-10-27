import java.util.Scanner;

class Celula {
    public int elemento;
    public Celula inf, sup, esq, dir;

    public Celula() { this(0); }
    public Celula(int elemento) { this(elemento, null, null, null, null); }
    public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir) {
        this.elemento = elemento;
        this.inf = inf;
        this.sup = sup;
        this.esq = esq;
        this.dir = dir;
    }
}

class Matriz {
    private Celula inicio;
    private int linha, coluna;

    public Matriz() { this(3, 3); }

    public Matriz(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        construirMatriz();
    }

    private void construirMatriz() {
        inicio = new Celula(0);
        Celula atual = inicio;

        for (int c = 1; c < coluna; c++) {
            atual.dir = new Celula(0);
            atual.dir.esq = atual;
            atual = atual.dir;
        }

        Celula linhaAcima = inicio;
        for (int l = 1; l < linha; l++) {
            Celula novaLinha = new Celula(0);
            linhaAcima.inf = novaLinha;
            novaLinha.sup = linhaAcima;

            Celula aux = novaLinha;
            Celula acima = linhaAcima.dir;
            for (int c = 1; c < coluna; c++) {
                aux.dir = new Celula(0);
                aux.dir.esq = aux;
                aux.dir.sup = acima;
                acima.inf = aux.dir;
                aux = aux.dir;
                acima = acima.dir;
            }

            linhaAcima = linhaAcima.inf;
        }
    }

    public void ler(Scanner sc, String nome) {
        System.out.println("Preencha " + nome + ":");
        Celula l = inicio;
        for (int i = 0; i < linha; i++, l = l.inf) {
            Celula c = l;
            for (int j = 0; j < coluna; j++, c = c.dir) {
                System.out.print("Elemento [" + i + "][" + j + "]: ");
                if (sc.hasNextInt()) {
                    c.elemento = sc.nextInt();
                } else {
                    c.elemento = 0;
                }
            }
        }
    }

    public void print() {
        Celula l = inicio;
        for (int i = 0; i < linha; i++, l = l.inf) {
            Celula c = l;
            for (int j = 0; j < coluna; j++, c = c.dir) {
                System.out.print(c.elemento + " ");
            }
            System.out.println();
        }
    }

    public void mostrarMatriz() {
        Celula linhaAtual = inicio;
        
        for (int i = 0; i < linha; i++) {
            Celula colAtual = linhaAtual;
            
            for (int j = 0; j < coluna; j++) {
                System.out.print("[" + colAtual.elemento + "]");
                
                if (colAtual.dir != null) {
                    System.out.print("-->");
                }
                
                colAtual = colAtual.dir;
            }
            
            System.out.println();
            
            if (linhaAtual.inf != null) {
                Celula temp = linhaAtual;
                for (int j = 0; j < coluna; j++) {
                    System.out.print(" |  ");
                    if (j < coluna - 1) {
                        System.out.print("   ");
                    }
                    temp = temp.dir;
                }
                System.out.println();
            }
            
            linhaAtual = linhaAtual.inf;
        }
    }

    public Matriz soma(Matriz m) {
        Matriz resp = null;

        if (this.linha == m.linha && this.coluna == m.coluna) {
            resp = new Matriz(this.linha, this.coluna);

            Celula aLinha = this.inicio;
            Celula bLinha = m.inicio;
            Celula cLinha = resp.inicio;

            for (int i = 0; i < linha; i++) {
                Celula a = aLinha;
                Celula b = bLinha;
                Celula c = cLinha;

                for (int j = 0; j < coluna; j++) {
                    c.elemento = a.elemento + b.elemento;
                    a = a.dir;
                    b = b.dir;
                    c = c.dir;
                }

                aLinha = aLinha.inf;
                bLinha = bLinha.inf;
                cLinha = cLinha.inf;
            }
        }

        return resp;
    }

    public Matriz multiplicacao(Matriz m) {
        Matriz resp = null;

        if (this.coluna == m.linha) {
            resp = new Matriz(this.linha, m.coluna);

            Celula linhaA = this.inicio;
            Celula linhaResp = resp.inicio;

            for (int i = 0; i < this.linha; i++) {
                Celula colResp = linhaResp;
                for (int j = 0; j < m.coluna; j++) {
                    int soma = 0;
                    Celula a = linhaA;
                    for (int k = 0; k < this.coluna; k++) {
                        soma += a.elemento * getCelula(m.inicio, k, j).elemento;
                        a = a.dir;
                    }
                    colResp.elemento = soma;
                    colResp = colResp.dir;
                }
                linhaA = linhaA.inf;
                linhaResp = linhaResp.inf;
            }
        }

        return resp;
    }

    private Celula getCelula(Celula inicio, int linha, int coluna) {
        Celula atual = inicio;
        for (int i = 0; i < linha; i++) atual = atual.inf;
        for (int j = 0; j < coluna; j++) atual = atual.dir;
        return atual;
    }
}

public class MatrizFlexivel {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Linhas M1: ");
        int l1 = sc.nextInt();
        System.out.print("Colunas M1: ");
        int c1 = sc.nextInt();

        System.out.print("Linhas M2: ");
        int l2 = sc.nextInt();
        System.out.print("Colunas M2: ");
        int c2 = sc.nextInt();

        System.out.print("Linhas M3: ");
        int l3 = sc.nextInt();
        System.out.print("Colunas M3: ");
        int c3 = sc.nextInt();

        System.out.print("Linhas M4: ");
        int l4 = sc.nextInt();
        System.out.print("Colunas M4: ");
        int c4 = sc.nextInt();

        Matriz m1 = new Matriz(l1, c1);
        Matriz m2 = new Matriz(l2, c2);
        Matriz m3 = new Matriz(l3, c3);
        Matriz m4 = new Matriz(l4, c4);

        m1.ler(sc, "M1");
        m2.ler(sc, "M2");
        m3.ler(sc, "M3");
        m4.ler(sc, "M4");

        System.out.println("\n=== Representação da Matriz M1 ===");
        m1.mostrarMatriz();

        System.out.println("\nSoma de M1 + M2:");
        Matriz soma = m1.soma(m2);
        if (soma != null) soma.print();
        else System.out.println("Não é possível somar as matrizes.");

        System.out.println("\nMultiplicação de M3 x M4:");
        Matriz mult = m3.multiplicacao(m4);
        if (mult != null) mult.print();
        else System.out.println("Não é possível multiplicar as matrizes.");

        sc.close();
    }
}
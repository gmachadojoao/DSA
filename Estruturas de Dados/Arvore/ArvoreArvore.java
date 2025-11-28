public class ArvoreArvore {
    private No raiz;

    public ArvoreArvore() {
        raiz = null;
        inserir('M');
        inserir('T');
        inserir('F');
        inserir('A');
        inserir('B');
        inserir('C');
        inserir('D');
        inserir('E');
        inserir('G');
        inserir('H');
        inserir('I');
        inserir('J');
        inserir('K');
        inserir('L');
        inserir('N');
        inserir('O');
        inserir('P');
        inserir('Q');
        inserir('R');
        inserir('S');
        inserir('U');
        inserir('V');
        inserir('W');
        inserir('X');
        inserir('Y');
        inserir('Z');
    }

    public void inserir(char letra) {
        raiz = inserir(letra, raiz);
    }

    private No inserir(char letra, No i) {
        if (i == null) {
            i = new No(letra);
        } else if (letra < i.elemento) {
            i.esq = inserir(letra, i.esq);
        } else if (letra > i.elemento) {
            i.dir = inserir(letra, i.dir);
        }
        return i;
    }

    public void inserir(String s) throws Exception {
        inserir(s, raiz);
    }

    private void inserir(String s, No i) throws Exception {
        if (i == null) {
            throw new Exception("Erro ao inserir: caractere invalido!");
        } else if (s.charAt(0) < i.elemento) {
            inserir(s, i.esq);
        } else if (s.charAt(0) > i.elemento) {
            inserir(s, i.dir);
        } else {
            i.outro = inserirNaSegundaArvore(s, i.outro);
        }
    }

    private No2 inserirNaSegundaArvore(String s, No2 i) throws Exception {
        if (i == null) {
            i = new No2(s);
        } else if (s.compareTo(i.elemento) < 0) {
            i.esq = inserirNaSegundaArvore(s, i.esq);
        } else if (s.compareTo(i.elemento) > 0) {
            i.dir = inserirNaSegundaArvore(s, i.dir);
        } else {
            throw new Exception("Erro ao inserir: elemento existente!");
        }
        return i;
    }

    public void mostrar() {
        mostrar(raiz);
    }

    private void mostrar(No i) {
        if (i != null) {
            mostrar(i.esq);
            System.out.println("Letra: " + i.elemento);
            mostrar(i.outro);
            mostrar(i.dir);
        }
    }

    private void mostrar(No2 i) {
        if (i != null) {
            mostrar(i.esq);
            System.out.println("  Palavra: " + i.elemento);
            mostrar(i.dir);
        }
    }

    public void visualizarArvore() {
        System.out.println("\n========== ÁRVORE DE CARACTERES ==========\n");
        visualizarArvore(raiz, "", true);
    }

    private void visualizarArvore(No no, String prefixo, boolean isUltimo) {
        if (no != null) {
            System.out.print(prefixo);
            System.out.print(isUltimo ? "└── " : "├── ");
            System.out.println("[" + no.elemento + "]");

            String novoPrefixo = prefixo + (isUltimo ? "    " : "│   ");

            if (no.outro != null) {
                visualizarSubArvore(no.outro, novoPrefixo + "    ", true);
            }

            if (no.esq != null || no.dir != null) {
                if (no.dir != null) {
                    visualizarArvore(no.esq, novoPrefixo, false);
                    visualizarArvore(no.dir, novoPrefixo, true);
                } else {
                    visualizarArvore(no.esq, novoPrefixo, true);
                }
            }
        }
    }

    private void visualizarSubArvore(No2 no, String prefixo, boolean isUltimo) {
        if (no != null) {
            System.out.print(prefixo);
            System.out.print(isUltimo ? "└── " : "├── ");
            System.out.println("\"" + no.elemento + "\"");

            String novoPrefixo = prefixo + (isUltimo ? "    " : "│   ");

            if (no.esq != null || no.dir != null) {
                if (no.dir != null) {
                    visualizarSubArvore(no.esq, novoPrefixo, false);
                    visualizarSubArvore(no.dir, novoPrefixo, true);
                } else {
                    visualizarSubArvore(no.esq, novoPrefixo, true);
                }
            }
        }
    }

    public boolean hasStringTam10() {
        return hasStringTam10(raiz);
    }

    private boolean hasStringTam10(No i) {
        boolean resp = false;
        if (i != null) {
            resp = hasStringTam10(i.outro) || hasStringTam10(i.esq) || hasStringTam10(i.dir);
        }
        return resp;
    }

    private boolean hasStringTam10(No2 i) {
        boolean resp = false;
        if (i != null) {
            resp = i.elemento.length() == 10 || hasStringTam10(i.esq) || hasStringTam10(i.dir);
        }
        return resp;
    }

    public boolean hasStringTam10(char c) {
        return hasStringTam10(raiz, c);
    }

    private boolean hasStringTam10(No i, char c) {
        boolean resp;
        if (i == null) {
            resp = false;
        } else if (c < i.elemento) {
            resp = hasStringTam10(i.esq, c);
        } else if (c > i.elemento) {
            resp = hasStringTam10(i.dir, c);
        } else {
            resp = hasStringTam10(i.outro);
        }
        return resp;
    }

    public boolean pesquisar(String elemento) {
        return pesquisar(raiz, elemento);
    }

    private boolean pesquisar(No no, String x) {
        boolean resp;
        if (no == null) {
            resp = false;
        } else if (x.charAt(0) < no.elemento) {
            resp = pesquisar(no.esq, x);
        } else if (x.charAt(0) > no.elemento) {
            resp = pesquisar(no.dir, x);
        } else {
            resp = pesquisarSegundaArvore(no.outro, x);
        }
        return resp;
    }

    private boolean pesquisarSegundaArvore(No2 no, String x) {
        boolean resp;
        if (no == null) {
            resp = false;
        } else if (x.compareTo(no.elemento) < 0) {
            resp = pesquisarSegundaArvore(no.esq, x);
        } else if (x.compareTo(no.elemento) > 0) {
            resp = pesquisarSegundaArvore(no.dir, x);
        } else {
            resp = true;
        }
        return resp;
    }

    public int contPalavra(char letra) throws Exception {
        return contPalavra(letra, raiz);
    }

    private int contPalavra(char letra, No i) throws Exception {
        int resp = 0;
        if (i == null) {
            throw new Exception("Erro ao pesquisar: caractere invalido!");
        } else if (letra < i.elemento) {
            resp = contPalavra(letra, i.esq);
        } else if (letra > i.elemento) {
            resp = contPalavra(letra, i.dir);
        } else {
            resp = contPalavra(i.outro);
        }
        return resp;
    }

    private int contPalavra(No2 i) {
        int resp = 0;
        if (i != null) {
            resp = 1 + contPalavra(i.esq) + contPalavra(i.dir);
        }
        return resp;
    }
}

class No {
    public char elemento;
    public No esq, dir;
    public No2 outro;

    No(char elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
        this.outro = null;
    }

    No(char elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.outro = null;
    }
}

class No2 {
    public String elemento;
    public No2 esq, dir;

    No2(String elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
    }

    No2(String elemento, No2 esq, No2 dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class Main {
    public static void main(String[] args) {
        try {
            ArvoreArvore arvore = new ArvoreArvore();

            arvore.inserir("Macaco");
            arvore.inserir("Mesa");
            arvore.inserir("Tartaruga");
            arvore.inserir("Tatu");
            arvore.inserir("Tigre");
            arvore.inserir("Flor");
            arvore.inserir("Fogo");
            arvore.inserir("Ferramenta");
            arvore.inserir("Montanha");
            arvore.inserir("Matematica");
            arvore.inserir("Transbordante");
            arvore.inserir("Mochila");

            arvore.visualizarArvore();

            System.out.println("\n==== Testes de pesquisa ====");
            System.out.println("Pesquisar 'Mesa'.........: " + arvore.pesquisar("Mesa"));
            System.out.println("Pesquisar 'Fogo'.........: " + arvore.pesquisar("Fogo"));
            System.out.println("Pesquisar 'Computador'...: " + arvore.pesquisar("Computador"));

            System.out.println("\n==== Teste hasStringTam10 ====");
            System.out.println("Existe alguma palavra com 10 letras? " + arvore.hasStringTam10());
            System.out.println("Existe palavra com 10 letras na letra 'M'? " + arvore.hasStringTam10('M'));

            System.out.println("\n==== Contagem de palavras por letra ====");
            System.out.println("Palavras que começam com 'M': " + arvore.contPalavra('M'));
            System.out.println("Palavras que começam com 'T': " + arvore.contPalavra('T'));
            System.out.println("Palavras que começam com 'F': " + arvore.contPalavra('F'));

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
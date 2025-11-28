class No {
    int dado;
    No proximo;
    
    public No(int dado) {
        this.dado = dado;
        this.proximo = null;
    }
}

class ListaEncadeada {
    No cabeca;
    
    public void adicionar(int dado) {
        No novoNo = new No(dado);
        if (cabeca == null) {
            cabeca = novoNo;
            return;
        }
        
        No atual = cabeca;
        while (atual.proximo != null) {
            atual = atual.proximo;
        }
        atual.proximo = novoNo;
    }
    
    public void imprimir() {
        No atual = cabeca;
        while (atual != null) {
            System.out.print(atual.dado + " -> ");
            atual = atual.proximo;
        }
        System.out.println("null");
    }
    
    public static ListaEncadeada metodo1_ConcatenarCriandoNova(ListaEncadeada lista1, ListaEncadeada lista2) {
        ListaEncadeada resultado = new ListaEncadeada();
        
        No atual = lista1.cabeca;
        while (atual != null) {
            resultado.adicionar(atual.dado);
            atual = atual.proximo;
        }
        
        atual = lista2.cabeca;
        while (atual != null) {
            resultado.adicionar(atual.dado);
            atual = atual.proximo;
        }
        
        return resultado;
    }
    
    public static ListaEncadeada metodo2_ConcatenarModificandoPrimeira(ListaEncadeada lista1, ListaEncadeada lista2) {
        if (lista1.cabeca == null) {
            return lista2;
        }
        
        No atual = lista1.cabeca;
        while (atual.proximo != null) {
            atual = atual.proximo;
        }
        
        atual.proximo = lista2.cabeca.proximo;
        
        return lista1;
    }
    
    public static ListaEncadeada metodo3_ConcatenarComCauda(ListaEncadeadaComCauda lista1, ListaEncadeadaComCauda lista2) {
        if (lista1.cabeca == null) {
            return lista2;
        }
        
        if (lista2.cabeca == null) {
            return lista1;
        }
        
        lista1.cauda.proximo = lista2.cabeca;
        lista1.cauda = lista2.cauda;
        
        return lista1;
    }
}

class ListaEncadeadaComCauda extends ListaEncadeada {
    No cauda;
    
    @Override
    public void adicionar(int dado) {
        No novoNo = new No(dado);
        if (cabeca == null) {
            cabeca = novoNo;
            cauda = novoNo;
            return;
        }
        
        cauda.proximo = novoNo;
        cauda = novoNo;
    }
}

public class ConcatenarListasEncadeadas {
    public static void main(String[] args) {
        ListaEncadeada lista1 = new ListaEncadeada();
        lista1.adicionar(1);
        lista1.adicionar(2);
        lista1.adicionar(3);
        
        ListaEncadeada lista2 = new ListaEncadeada();
        lista2.adicionar(4);
        lista2.adicionar(5);
        lista2.adicionar(6);
        
        System.out.println("Lista 1:");
        lista1.imprimir();
        System.out.println("Lista 2:");
        lista2.imprimir();
        
        System.out.println("\nMétodo 1 - Criar nova lista:");
        ListaEncadeada resultado1 = ListaEncadeada.metodo1_ConcatenarCriandoNova(lista1, lista2);
        resultado1.imprimir();
        
        ListaEncadeada lista3 = new ListaEncadeada();
        lista3.adicionar(1);
        lista3.adicionar(2);
        lista3.adicionar(3);
        
        ListaEncadeada lista4 = new ListaEncadeada();
        lista4.adicionar(4);
        lista4.adicionar(5);
        lista4.adicionar(6);
        
        System.out.println("\nMétodo 2 - Modificar primeira lista:");
        ListaEncadeada resultado2 = ListaEncadeada.metodo2_ConcatenarModificandoPrimeira(lista3, lista4);
        resultado2.imprimir();
        
        ListaEncadeadaComCauda lista5 = new ListaEncadeadaComCauda();
        lista5.adicionar(1);
        lista5.adicionar(2);
        lista5.adicionar(3);
        
        ListaEncadeadaComCauda lista6 = new ListaEncadeadaComCauda();
        lista6.adicionar(4);
        lista6.adicionar(5);
        lista6.adicionar(6);
        
        System.out.println("\nMétodo 3 - Lista com ponteiro de cauda (O(1)):");
        ListaEncadeada resultado3 = ListaEncadeada.metodo3_ConcatenarComCauda(lista5, lista6);
        resultado3.imprimir();
    }
}
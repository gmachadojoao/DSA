#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Elemento {
    char tipo[20];
    char cor;
    struct Elemento *prox;
} Elemento;

typedef struct Celula {
    int linha, coluna;
    struct Celula *sup, *inf, *esq, *dir;
    Elemento *lista;
} Celula;

typedef struct {
    Celula *inicio;
    int linhas, colunas;
} Tabuleiro;

Tabuleiro* criarTabuleiro(int linhas, int colunas) {
    Tabuleiro *t = (Tabuleiro*)malloc(sizeof(Tabuleiro));
    t->linhas = linhas;
    t->colunas = colunas;
    
    Celula ***matriz = (Celula***)malloc(linhas * sizeof(Celula**));
    
    for (int i = 0; i < linhas; i++) {
        matriz[i] = (Celula**)malloc(colunas * sizeof(Celula*));
        for (int j = 0; j < colunas; j++) {
            matriz[i][j] = (Celula*)malloc(sizeof(Celula));
            matriz[i][j]->linha = i;
            matriz[i][j]->coluna = j;
            matriz[i][j]->sup = NULL;
            matriz[i][j]->inf = NULL;
            matriz[i][j]->esq = NULL;
            matriz[i][j]->dir = NULL;
            matriz[i][j]->lista = NULL;
        }
    }
    
    for (int i = 0; i < linhas; i++) {
        for (int j = 0; j < colunas; j++) {
            if (i > 0) matriz[i][j]->sup = matriz[i-1][j];
            if (i < linhas-1) matriz[i][j]->inf = matriz[i+1][j];
            if (j > 0) matriz[i][j]->esq = matriz[i][j-1];
            if (j < colunas-1) matriz[i][j]->dir = matriz[i][j+1];
        }
    }
    
    t->inicio = matriz[0][0];
    
    for (int i = 0; i < linhas; i++) {
        free(matriz[i]);
    }
    free(matriz);
    
    return t;
}

void inserirElemento(Celula *c, const char *tipo, char cor) {
    Elemento *novo = (Elemento*)malloc(sizeof(Elemento));
    strcpy(novo->tipo, tipo);
    novo->cor = cor;
    novo->prox = c->lista;
    c->lista = novo;
}

void imprimirTabuleiro(Tabuleiro *t) {
    Celula *linhaAtual = t->inicio;
    
    while (linhaAtual != NULL) {
        Celula *celulaAtual = linhaAtual;
        
        while (celulaAtual != NULL) {
            if (celulaAtual->lista != NULL) {
                printf("[%d,%d]: ", celulaAtual->linha, celulaAtual->coluna);
                Elemento *elem = celulaAtual->lista;
                while (elem != NULL) {
                    printf("%s(%c)", elem->tipo, elem->cor);
                    if (elem->prox != NULL) printf(", ");
                    elem = elem->prox;
                }
                printf("\n");
            }
            celulaAtual = celulaAtual->dir;
        }
        linhaAtual = linhaAtual->inf;
    }
}

Celula* buscarCelula(Tabuleiro *t, int linha, int coluna) {
    Celula *atual = t->inicio;
    
    for (int i = 0; i < linha; i++) {
        if (atual == NULL) return NULL;
        atual = atual->inf;
    }
    
    for (int j = 0; j < coluna; j++) {
        if (atual == NULL) return NULL;
        atual = atual->dir;
    }
    
    return atual;
}

void moverElemento(Tabuleiro *t, int lOrig, int cOrig, int lDest, int cDest, const char *tipo) {
    Celula *origem = buscarCelula(t, lOrig, cOrig);
    Celula *destino = buscarCelula(t, lDest, cDest);
    
    if (origem == NULL || destino == NULL) {
        printf("Erro: célula inválida\n");
        return;
    }
    
    Elemento *ant = NULL;
    Elemento *atual = origem->lista;
    
    while (atual != NULL && strcmp(atual->tipo, tipo) != 0) {
        ant = atual;
        atual = atual->prox;
    }
    
    if (atual == NULL) {
        printf("Erro: elemento '%s' não encontrado em [%d,%d]\n", tipo, lOrig, cOrig);
        return;
    }
    
    if (ant == NULL) {
        origem->lista = atual->prox;
    } else {
        ant->prox = atual->prox;
    }
    
    atual->prox = destino->lista;
    destino->lista = atual;
    
    printf("Elemento '%s' movido de [%d,%d] para [%d,%d]\n", tipo, lOrig, cOrig, lDest, cDest);
}

void mostrarCaminhos(Celula *origem, int passos) {
    if (origem == NULL || passos < 0) return;
    
    printf("[%d,%d] ", origem->linha, origem->coluna);
    
    if (passos == 0) return;
    
    printf("\n  Cima: ");
    if (origem->sup) mostrarCaminhos(origem->sup, passos - 1);
    
    printf("\n  Baixo: ");
    if (origem->inf) mostrarCaminhos(origem->inf, passos - 1);
    
    printf("\n  Esquerda: ");
    if (origem->esq) mostrarCaminhos(origem->esq, passos - 1);
    
    printf("\n  Direita: ");
    if (origem->dir) mostrarCaminhos(origem->dir, passos - 1);
}

int main() {
    printf("=== Criando tabuleiro 8x8 ===\n");
    Tabuleiro *tab = criarTabuleiro(8, 8);
    
    printf("\n=== Inserindo peças ===\n");
    Celula *c00 = buscarCelula(tab, 0, 0);
    inserirElemento(c00, "torre", 'b');
    
    Celula *c01 = buscarCelula(tab, 0, 1);
    inserirElemento(c01, "cavalo", 'b');
    
    Celula *c34 = buscarCelula(tab, 3, 4);
    inserirElemento(c34, "peao", 'p');
    inserirElemento(c34, "disco", 'p');
    
    Celula *c77 = buscarCelula(tab, 7, 7);
    inserirElemento(c77, "rei", 'b');
    
    printf("\n=== Tabuleiro atual ===\n");
    imprimirTabuleiro(tab);
    
    printf("\n=== Movendo peça ===\n");
    moverElemento(tab, 0, 0, 2, 0, "torre");
    
    printf("\n=== Tabuleiro após movimento ===\n");
    imprimirTabuleiro(tab);
    
    printf("\n=== Caminhos possíveis de [3,4] em 2 passos ===\n");
    mostrarCaminhos(c34, 2);
    printf("\n");
    
    return 0;
}
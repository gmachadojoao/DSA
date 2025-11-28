#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct Movimento {
    char jogada[10];           
    struct Movimento *prox;
} Movimento;

typedef struct NoPeca {
    char id[10];               
    char tipo;                 
    Movimento *historico;      
    struct NoPeca *esq, *dir;
} NoPeca;

// Cria um nó de peça
NoPeca* criarPeca(char *id, char tipo) {
    NoPeca *novo = malloc(sizeof(NoPeca));
    strcpy(novo->id, id);
    novo->tipo = tipo;
    novo->historico = NULL;
    novo->esq = novo->dir = NULL;
    return novo;
}

// Insere peça na árvore
NoPeca* inserirPeca(NoPeca *raiz, char *id, char tipo) {
    if (!raiz) return criarPeca(id, tipo);
    int cmp = strcmp(id, raiz->id);
    if (cmp < 0) raiz->esq = inserirPeca(raiz->esq, id, tipo);
    else if (cmp > 0) raiz->dir = inserirPeca(raiz->dir, id, tipo);
    return raiz;
}

// Busca peça na árvore
NoPeca* buscarPeca(NoPeca *raiz, char *id) {
    if (!raiz) return NULL;
    int cmp = strcmp(id, raiz->id);
    if (cmp == 0) return raiz;
    else if (cmp < 0) return buscarPeca(raiz->esq, id);
    else return buscarPeca(raiz->dir, id);
}

// Adiciona movimento à peça
void adicionarMovimento(NoPeca *peca, const char *jogada) {
    Movimento *m = malloc(sizeof(Movimento));
    strcpy(m->jogada, jogada);
    m->prox = NULL;
    if (!peca->historico) peca->historico = m;
    else {
        Movimento *aux = peca->historico;
        while (aux->prox) aux = aux->prox;
        aux->prox = m;
    }
}

// Imprime o histórico completo
void imprimirHistorico(NoPeca *raiz) {
    if (!raiz) return;
    imprimirHistorico(raiz->esq);
    printf("Peça: %s (%c)\n", raiz->id, raiz->tipo);
    for (Movimento *m = raiz->historico; m; m = m->prox)
        printf("  -> %s\n", m->jogada);
    imprimirHistorico(raiz->dir);
}

// Determina o tipo de peça pela notação PGN
char identificarTipo(char *jogada) {
    if (jogada[0] >= 'A' && jogada[0] <= 'Z') return jogada[0]; // N, B, R, Q, K
    return 'P'; // Peão se não houver letra maiúscula inicial
}

// Gera um ID de peça simples baseado no tipo e na coluna inicial (simplificado)
void gerarID(char *jogada, char *id) {
    char tipo = identificarTipo(jogada);
    if (tipo == 'P') {
        snprintf(id, 10, "Peao%c%c", jogada[0], jogada[1]); // e2 -> PeaoE2
    } else {
        snprintf(id, 10, "%c%c%c", tipo, jogada[1], jogada[2]); // Nf3 -> Nf3
    }
}

// Processa um arquivo PGN simplificado
void processarPGN(const char *arquivo, NoPeca **raiz) {
    FILE *fp = fopen(arquivo, "r");
    if (!fp) {
        printf("Não foi possível abrir o arquivo.\n");
        return;
    }

    char linha[256];
    while (fgets(linha, sizeof(linha), fp)) {
        if (linha[0] == '[' || strlen(linha) < 3) continue; // ignora metadados ou linhas pequenas
        char *token = strtok(linha, " \n");
        while (token) {
            if (isdigit(token[0])) { // ignora números de lance
                token = strtok(NULL, " \n");
                continue;
            }

            char id[10];
            gerarID(token, id);
            char tipo = identificarTipo(token);
            NoPeca *peca = buscarPeca(*raiz, id);
            if (!peca) *raiz = inserirPeca(*raiz, id, tipo), peca = buscarPeca(*raiz, id);
            adicionarMovimento(peca, token);

            token = strtok(NULL, " \n");
        }
    }

    fclose(fp);
}

int main() {
    NoPeca *raiz = NULL;

    // Arquivo PGN simplificado
    processarPGN("partida1.pgn", &raiz);

    // Imprime histórico por peça
    imprimirHistorico(raiz);

    return 0;
}

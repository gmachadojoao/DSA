#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>

typedef struct Movimento {
    char jogada[20];
    int linha_origem, col_origem;
    int linha_destino, col_destino;
    struct Movimento *prox;
} Movimento;

typedef struct NoPeca {
    char id[30];
    char tipo;
    char cor;
    int linha_atual, col_atual;
    Movimento *historico;
    struct NoPeca *esq, *dir;
} NoPeca;

typedef struct {
    NoPeca* tabuleiro[8][8];
    NoPeca* raiz_arvore;
    int num_pecas_brancas;
    int num_pecas_pretas;
} Jogo;

NoPeca* criarPeca(char tipo, char cor, int linha, int col, const char *id_base) {
    NoPeca *novo = malloc(sizeof(NoPeca));
    if (!novo) {
        fprintf(stderr, "Erro de alocação de memória\n");
        exit(1);
    }
    snprintf(novo->id, sizeof(novo->id), "%s_%c_%d%d", id_base, cor, linha, col);
    novo->tipo = tipo;
    novo->cor = cor;
    novo->linha_atual = linha;
    novo->col_atual = col;
    novo->historico = NULL;
    novo->esq = novo->dir = NULL;
    return novo;
}

NoPeca* inserirPeca(NoPeca *raiz, NoPeca *peca) {
    if (!raiz) return peca;
    int cmp = strcmp(peca->id, raiz->id);
    if (cmp < 0) raiz->esq = inserirPeca(raiz->esq, peca);
    else if (cmp > 0) raiz->dir = inserirPeca(raiz->dir, peca);
    return raiz;
}

NoPeca* buscarPeca(NoPeca *raiz, const char *id) {
    if (!raiz) return NULL;
    int cmp = strcmp(id, raiz->id);
    if (cmp == 0) return raiz;
    else if (cmp < 0) return buscarPeca(raiz->esq, id);
    else return buscarPeca(raiz->dir, id);
}

void adicionarMovimento(NoPeca *peca, const char *jogada, int l_orig, int c_orig, int l_dest, int c_dest) {
    Movimento *m = malloc(sizeof(Movimento));
    if (!m) {
        fprintf(stderr, "Erro de alocação de memória\n");
        exit(1);
    }
    strncpy(m->jogada, jogada, sizeof(m->jogada) - 1);
    m->jogada[sizeof(m->jogada) - 1] = '\0';
    m->linha_origem = l_orig;
    m->col_origem = c_orig;
    m->linha_destino = l_dest;
    m->col_destino = c_dest;
    m->prox = NULL;
    
    if (!peca->historico) {
        peca->historico = m;
    } else {
        Movimento *aux = peca->historico;
        while (aux->prox) aux = aux->prox;
        aux->prox = m;
    }
}

void liberarMovimentos(Movimento *mov) {
    while (mov) {
        Movimento *temp = mov;
        mov = mov->prox;
        free(temp);
    }
}

void liberarArvore(NoPeca *raiz) {
    if (!raiz) return;
    liberarArvore(raiz->esq);
    liberarArvore(raiz->dir);
    liberarMovimentos(raiz->historico);
    free(raiz);
}

void liberarJogo(Jogo *jogo) {
    liberarArvore(jogo->raiz_arvore);
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            jogo->tabuleiro[i][j] = NULL;
        }
    }
}

void inicializarTabuleiro(Jogo *jogo) {
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            jogo->tabuleiro[i][j] = NULL;
        }
    }
    jogo->raiz_arvore = NULL;
    jogo->num_pecas_brancas = 0;
    jogo->num_pecas_pretas = 0;
    
    char tipos[] = {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'};
    const char *nomes[] = {"Torre", "Cavalo", "Bispo", "Dama", "Rei", "Bispo", "Cavalo", "Torre"};
    
    for (int i = 0; i < 8; i++) {
        NoPeca *peao_branco = criarPeca('P', 'B', 6, i, "Peao");
        jogo->tabuleiro[6][i] = peao_branco;
        jogo->raiz_arvore = inserirPeca(jogo->raiz_arvore, peao_branco);
        jogo->num_pecas_brancas++;
        
        NoPeca *peao_preto = criarPeca('P', 'P', 1, i, "Peao");
        jogo->tabuleiro[1][i] = peao_preto;
        jogo->raiz_arvore = inserirPeca(jogo->raiz_arvore, peao_preto);
        jogo->num_pecas_pretas++;
    }
    
    for (int i = 0; i < 8; i++) {
        NoPeca *peca_branca = criarPeca(tipos[i], 'B', 7, i, nomes[i]);
        jogo->tabuleiro[7][i] = peca_branca;
        jogo->raiz_arvore = inserirPeca(jogo->raiz_arvore, peca_branca);
        jogo->num_pecas_brancas++;
        
        NoPeca *peca_preta = criarPeca(tipos[i], 'P', 0, i, nomes[i]);
        jogo->tabuleiro[0][i] = peca_preta;
        jogo->raiz_arvore = inserirPeca(jogo->raiz_arvore, peca_preta);
        jogo->num_pecas_pretas++;
    }
}

void limparJogada(char *jogada) {
    int len = strlen(jogada);
    while (len > 0 && (jogada[len-1] == '+' || jogada[len-1] == '#' || 
           jogada[len-1] == '!' || jogada[len-1] == '?')) {
        jogada[len-1] = '\0';
        len--;
    }
}

bool parseDestino(const char *jogada, int *linha, int *col) {
    int len = strlen(jogada);
    if (len < 2) return false;
    
    char col_char = jogada[len-2];
    char lin_char = jogada[len-1];
    
    if (col_char < 'a' || col_char > 'h') return false;
    if (lin_char < '1' || lin_char > '8') return false;
    
    *col = col_char - 'a';
    *linha = 8 - (lin_char - '0');
    return true;
}

NoPeca* encontrarPeca(Jogo *jogo, char tipo, int linha_dest, int col_dest, char cor, char col_origem_hint, int linha_origem_hint) {
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            NoPeca *p = jogo->tabuleiro[i][j];
            if (p && p->tipo == tipo && p->cor == cor) {
                if (col_origem_hint >= 0 && j != col_origem_hint) continue;
                if (linha_origem_hint >= 0 && i != linha_origem_hint) continue;
                
                if (tipo == 'P') {
                    int direcao = (cor == 'B') ? -1 : 1;
                    if (col_dest == j) {
                        if (i + direcao == linha_dest) return p;
                        if ((i == 6 && cor == 'B') || (i == 1 && cor == 'P')) {
                            if (i + 2*direcao == linha_dest) return p;
                        }
                    } else if (abs(col_dest - j) == 1 && i + direcao == linha_dest) {
                        return p;
                    }
                } else if (tipo == 'N') {
                    int dl = abs(linha_dest - i);
                    int dc = abs(col_dest - j);
                    if ((dl == 2 && dc == 1) || (dl == 1 && dc == 2)) return p;
                } else if (tipo == 'K') {
                    if (abs(linha_dest - i) <= 1 && abs(col_dest - j) <= 1) return p;
                } else {
                    return p;
                }
            }
        }
    }
    return NULL;
}

void processarRoque(Jogo *jogo, const char *jogada, char cor) {
    int linha = (cor == 'B') ? 7 : 0;
    
    if (strcmp(jogada, "O-O") == 0) {
        NoPeca *rei = jogo->tabuleiro[linha][4];
        NoPeca *torre = jogo->tabuleiro[linha][7];
        
        if (rei && torre) {
            jogo->tabuleiro[linha][6] = rei;
            jogo->tabuleiro[linha][5] = torre;
            jogo->tabuleiro[linha][4] = NULL;
            jogo->tabuleiro[linha][7] = NULL;
            
            adicionarMovimento(rei, jogada, linha, 4, linha, 6);
            adicionarMovimento(torre, "O-O(Torre)", linha, 7, linha, 5);
            
            rei->linha_atual = linha;
            rei->col_atual = 6;
            torre->linha_atual = linha;
            torre->col_atual = 5;
        }
    } else if (strcmp(jogada, "O-O-O") == 0) {
        NoPeca *rei = jogo->tabuleiro[linha][4];
        NoPeca *torre = jogo->tabuleiro[linha][0];
        
        if (rei && torre) {
            jogo->tabuleiro[linha][2] = rei;
            jogo->tabuleiro[linha][3] = torre;
            jogo->tabuleiro[linha][4] = NULL;
            jogo->tabuleiro[linha][0] = NULL;
            
            adicionarMovimento(rei, jogada, linha, 4, linha, 2);
            adicionarMovimento(torre, "O-O-O(Torre)", linha, 0, linha, 3);
            
            rei->linha_atual = linha;
            rei->col_atual = 2;
            torre->linha_atual = linha;
            torre->col_atual = 3;
        }
    }
}

void processarMovimento(Jogo *jogo, char *jogada, char cor) {
    limparJogada(jogada);
    
    if (strcmp(jogada, "O-O") == 0 || strcmp(jogada, "O-O-O") == 0) {
        processarRoque(jogo, jogada, cor);
        return;
    }
    
    int linha_dest, col_dest;
    if (!parseDestino(jogada, &linha_dest, &col_dest)) return;
    
    char tipo = 'P';
    int idx = 0;
    if (isupper(jogada[0])) {
        tipo = jogada[0];
        idx = 1;
    }
    
    char col_origem_hint = -1;
    int linha_origem_hint = -1;
    
    if (jogada[idx] >= 'a' && jogada[idx] <= 'h' && jogada[idx+1] != '=') {
        col_origem_hint = jogada[idx] - 'a';
        idx++;
    }
    if (jogada[idx] >= '1' && jogada[idx] <= '8') {
        linha_origem_hint = 8 - (jogada[idx] - '0');
        idx++;
    }
    
    if (jogada[idx] == 'x') idx++;
    
    NoPeca *peca = encontrarPeca(jogo, tipo, linha_dest, col_dest, cor, col_origem_hint, linha_origem_hint);
    
    if (peca) {
        int linha_orig = peca->linha_atual;
        int col_orig = peca->col_atual;
        
        NoPeca *capturada = jogo->tabuleiro[linha_dest][col_dest];
        if (capturada) {
            if (capturada->cor == 'B') jogo->num_pecas_brancas--;
            else jogo->num_pecas_pretas--;
        }
        
        jogo->tabuleiro[linha_orig][col_orig] = NULL;
        jogo->tabuleiro[linha_dest][col_dest] = peca;
        
        adicionarMovimento(peca, jogada, linha_orig, col_orig, linha_dest, col_dest);
        
        peca->linha_atual = linha_dest;
        peca->col_atual = col_dest;
        
        if (tipo == 'P' && (linha_dest == 0 || linha_dest == 7)) {
            char *promocao = strchr(jogada, '=');
            if (promocao && promocao[1]) {
                peca->tipo = promocao[1];
            } else {
                peca->tipo = 'Q';
            }
        }
    }
}

void processarPGN(const char *arquivo, Jogo *jogo) {
    FILE *fp = fopen(arquivo, "r");
    if (!fp) {
        printf("Erro: não foi possível abrir o arquivo '%s'\n", arquivo);
        return;
    }
    
    char linha[512];
    bool vez_brancas = true;
    
    while (fgets(linha, sizeof(linha), fp)) {
        if (linha[0] == '[' || strlen(linha) < 3) continue;
        
        char *token = strtok(linha, " \t\n");
        while (token) {
            if (isdigit(token[0]) && strchr(token, '.')) {
                token = strtok(NULL, " \t\n");
                continue;
            }
            
            if (strcmp(token, "1-0") == 0 || strcmp(token, "0-1") == 0 || 
                strcmp(token, "1/2-1/2") == 0 || strcmp(token, "*") == 0) {
                break;
            }
            
            char cor = vez_brancas ? 'B' : 'P';
            processarMovimento(jogo, token, cor);
            vez_brancas = !vez_brancas;
            
            token = strtok(NULL, " \t\n");
        }
    }
    
    fclose(fp);
}

void imprimirHistorico(NoPeca *raiz) {
    if (!raiz) return;
    imprimirHistorico(raiz->esq);
    
    printf("\n=== Peça: %s (Tipo: %c, Cor: %c) ===\n", raiz->id, raiz->tipo, raiz->cor);
    printf("Posição atual: %c%d\n", 'a' + raiz->col_atual, 8 - raiz->linha_atual);
    
    if (raiz->historico) {
        printf("Histórico de movimentos:\n");
        int num = 1;
        for (Movimento *m = raiz->historico; m; m = m->prox) {
            printf("  %d. %s: %c%d -> %c%d\n", 
                   num++, m->jogada,
                   'a' + m->col_origem, 8 - m->linha_origem,
                   'a' + m->col_destino, 8 - m->linha_destino);
        }
    } else {
        printf("Nenhum movimento registrado.\n");
    }
    
    imprimirHistorico(raiz->dir);
}

void exibirEstatisticas(Jogo *jogo) {
    printf("\n========== ESTATÍSTICAS DA PARTIDA ==========\n");
    printf("Peças brancas restantes: %d\n", jogo->num_pecas_brancas);
    printf("Peças pretas restantes: %d\n", jogo->num_pecas_pretas);
    printf("=============================================\n");
}

int main() {
    Jogo jogo;
    inicializarTabuleiro(&jogo);
    
    printf("Processando arquivo PGN...\n");
    processarPGN("partida1.pgn", &jogo);
    
    printf("\n========== HISTÓRICO COMPLETO ==========\n");
    imprimirHistorico(jogo.raiz_arvore);
    
    exibirEstatisticas(&jogo);
    
    liberarJogo(&jogo);
    
    return 0;
}
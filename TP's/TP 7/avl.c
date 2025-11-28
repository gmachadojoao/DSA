#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define TAMANHO_MAX_LINHA 4096
#define TAMANHO_MAX_ID 128
#define TAMANHO_MAX_CAMPO 1024
#define NUM_CAMPOS 14

typedef struct {
    char *appID;
    char *name;
    char *releaseDate;
    char *compradores;
    char *preco;
    char *score;
    char *userScore;
    char *nConquistas;
    char *supportedLanguages;
    char *publishers;
    char *developers;
    char *categorias;
    char *generos;
    char *tags;
} Tp4;

typedef struct NoAVL {
    Tp4 *jogo;
    struct NoAVL *esq;
    struct NoAVL *dir;
    int altura;
} NoAVL;

int comparacoes = 0;

void limparNome(char* nome) {
    char *src = nome, *dst = nome;
    while (*src) {
        if (*src != '"') *dst++ = *src;
        src++;
    }
    *dst = '\0';
}

char* alocar_string(const char* s) {
    if (!s) return NULL;
    char *p = malloc(strlen(s) + 1);
    if (!p) return NULL;
    strcpy(p, s);
    return p;
}

void limpar(char* campo) {
    if (!campo) return;
    char *src = campo, *dst = campo;
    while (*src) {
        if (*src != '"' && *src != '[' && *src != ']') *dst++ = *src;
        src++;
    }
    *dst = '\0';
}

void formatarLista(char* campo) {
    if (!campo || !*campo) return;
    
    char temp[TAMANHO_MAX_CAMPO], resultado[TAMANHO_MAX_CAMPO] = "";
    strncpy(temp, campo, TAMANHO_MAX_CAMPO - 1);
    temp[TAMANHO_MAX_CAMPO - 1] = '\0';
    
    limpar(temp);

    char *token = strtok(temp, ",");
    bool primeiro = true;
    
    while (token) {
        while (*token == ' ') token++;
        
        if (!primeiro) strcat(resultado, ", ");
        strncat(resultado, token, TAMANHO_MAX_CAMPO - strlen(resultado) - 1);
        primeiro = false;
        token = strtok(NULL, ",");
    }
    
    strncpy(campo, resultado, TAMANHO_MAX_CAMPO - 1);
    campo[TAMANHO_MAX_CAMPO - 1] = '\0';
}

void formatarData(const char* entrada, char* saida) {
    const char* meses[] = {
        "Jan","Feb","Mar","Apr","May","Jun",
        "Jul","Aug","Sep","Oct","Nov","Dec"
    };
    
    int dia, ano;
    char mesStr[4];
    
    if (sscanf(entrada, "\"%3s %d, %d\"", mesStr, &dia, &ano) == 3) {
        int mes = 0;
        for (; mes < 12; mes++) {
            if (strcmp(mesStr, meses[mes]) == 0) break;
        }
        snprintf(saida, 20, "%02d/%02d/%04d", dia, mes + 1, ano);
    } else {
        strncpy(saida, entrada, 19);
        saida[19] = '\0';
    }
}

void imprimirTp4(Tp4 *jogo) {
    char dataFormatada[20];
    char langs[TAMANHO_MAX_CAMPO], pubs[TAMANHO_MAX_CAMPO], devs[TAMANHO_MAX_CAMPO];
    char cats[TAMANHO_MAX_CAMPO], gens[TAMANHO_MAX_CAMPO], tgs[TAMANHO_MAX_CAMPO];
    
    strcpy(langs, jogo->supportedLanguages);
    strcpy(pubs, jogo->publishers);
    strcpy(devs, jogo->developers);
    strcpy(cats, jogo->categorias);
    strcpy(gens, jogo->generos);
    strcpy(tgs, jogo->tags);
    
    formatarData(jogo->releaseDate, dataFormatada);
    formatarLista(langs);
    formatarLista(pubs);
    formatarLista(devs);
    formatarLista(cats);
    formatarLista(gens);
    formatarLista(tgs);

    char nome[TAMANHO_MAX_CAMPO], comp[TAMANHO_MAX_CAMPO], preco[TAMANHO_MAX_CAMPO];
    char score[TAMANHO_MAX_CAMPO], uscore[TAMANHO_MAX_CAMPO], nconq[TAMANHO_MAX_CAMPO];
    
    strcpy(nome, jogo->name);
    strcpy(comp, jogo->compradores);
    strcpy(preco, jogo->preco);
    strcpy(score, jogo->score);
    strcpy(uscore, jogo->userScore);
    strcpy(nconq, jogo->nConquistas);
    
    limpar(nome);
    limpar(comp);
    limpar(preco);
    limpar(score);
    limpar(uscore);
    limpar(nconq);

    printf("=> %s ## %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## [%s] ## [%s] ## [%s] ## [%s] ## [%s] ##\n",
           jogo->appID, nome, dataFormatada, comp, preco, langs,
           score, uscore, nconq, pubs, devs, cats, gens, tgs);
}

void liberarTp4(Tp4 *jogo) {
    if (!jogo) return;
    free(jogo->appID);
    free(jogo->name);
    free(jogo->releaseDate);
    free(jogo->compradores);
    free(jogo->preco);
    free(jogo->score);
    free(jogo->userScore);
    free(jogo->nConquistas);
    free(jogo->supportedLanguages);
    free(jogo->publishers);
    free(jogo->developers);
    free(jogo->categorias);
    free(jogo->generos);
    free(jogo->tags);
    free(jogo);
}

Tp4* analisarLinha(char* linha) {
    Tp4 *jogo = calloc(1, sizeof(Tp4));
    if (!jogo) return NULL;

    char *campos[NUM_CAMPOS];
    int i = 0, dentroAspas = 0, inicio = 0;
    int len = strlen(linha);
    
    for (int j = 0; j < len && i < NUM_CAMPOS; j++) {
        if (linha[j] == '"') dentroAspas = !dentroAspas;
        if (linha[j] == ',' && !dentroAspas) {
            linha[j] = '\0';
            campos[i++] = &linha[inicio];
            inicio = j + 1;
        }
    }
    if (i < NUM_CAMPOS) campos[i++] = &linha[inicio];

    if (i < NUM_CAMPOS) {
        free(jogo);
        return NULL;
    }

    jogo->appID = alocar_string(campos[0]);
    jogo->name = alocar_string(campos[1]);
    jogo->releaseDate = alocar_string(campos[2]);
    jogo->compradores = alocar_string(campos[3]);
    jogo->preco = alocar_string(campos[4]);
    jogo->supportedLanguages = alocar_string(campos[5]);
    jogo->score = alocar_string(campos[6]);
    jogo->userScore = alocar_string(campos[7]);
    jogo->nConquistas = alocar_string(campos[8]);
    jogo->publishers = alocar_string(campos[9]);
    jogo->developers = alocar_string(campos[10]);
    jogo->categorias = alocar_string(campos[11]);
    jogo->generos = alocar_string(campos[12]);
    jogo->tags = alocar_string(campos[13]);

    return jogo;
}

int max(int a, int b) {
    return (a > b) ? a : b;
}

int altura(NoAVL *n) {
    return n ? n->altura : 0;
}

int fatorBalanceamento(NoAVL *n) {
    return n ? altura(n->esq) - altura(n->dir) : 0;
}

NoAVL* rotacaoDireita(NoAVL *y) {
    NoAVL *x = y->esq;
    NoAVL *T2 = x->dir;
    
    x->dir = y;
    y->esq = T2;
    
    y->altura = max(altura(y->esq), altura(y->dir)) + 1;
    x->altura = max(altura(x->esq), altura(x->dir)) + 1;
    
    return x;
}

NoAVL* rotacaoEsquerda(NoAVL *x) {
    NoAVL *y = x->dir;
    NoAVL *T2 = y->esq;
    
    y->esq = x;
    x->dir = T2;
    
    x->altura = max(altura(x->esq), altura(x->dir)) + 1;
    y->altura = max(altura(y->esq), altura(y->dir)) + 1;
    
    return y;
}

NoAVL* inserir(NoAVL *no, Tp4 *jogo) {
    if (!no) {
        NoAVL *novo = malloc(sizeof(NoAVL));
        novo->jogo = jogo;
        novo->esq = NULL;
        novo->dir = NULL;
        novo->altura = 1;
        return novo;
    }
    
    char nome1[TAMANHO_MAX_CAMPO], nome2[TAMANHO_MAX_CAMPO];
    strcpy(nome1, jogo->name);
    strcpy(nome2, no->jogo->name);
    limparNome(nome1);
    limparNome(nome2);
    
    int cmp = strcmp(nome1, nome2);
    
    if (cmp < 0) {
        no->esq = inserir(no->esq, jogo);
    } else if (cmp > 0) {
        no->dir = inserir(no->dir, jogo);
    } else {
        return no;
    }
    
    no->altura = 1 + max(altura(no->esq), altura(no->dir));
    
    int balance = fatorBalanceamento(no);
    
    strcpy(nome2, no->esq ? no->esq->jogo->name : "");
    limparNome(nome2);
    
    if (balance > 1 && strcmp(nome1, nome2) < 0) {
        return rotacaoDireita(no);
    }
    
    strcpy(nome2, no->dir ? no->dir->jogo->name : "");
    limparNome(nome2);
    
    if (balance < -1 && strcmp(nome1, nome2) > 0) {
        return rotacaoEsquerda(no);
    }
    
    strcpy(nome2, no->esq ? no->esq->jogo->name : "");
    limparNome(nome2);
    
    if (balance > 1 && strcmp(nome1, nome2) > 0) {
        no->esq = rotacaoEsquerda(no->esq);
        return rotacaoDireita(no);
    }
    
    strcpy(nome2, no->dir ? no->dir->jogo->name : "");
    limparNome(nome2);
    
    if (balance < -1 && strcmp(nome1, nome2) < 0) {
        no->dir = rotacaoDireita(no->dir);
        return rotacaoEsquerda(no);
    }
    
    return no;
}

bool pesquisar(NoAVL *no, const char *nome) {
    if (!no) {
        return false;
    }
    
    char nome1[TAMANHO_MAX_CAMPO], nome2[TAMANHO_MAX_CAMPO];
    strcpy(nome1, nome);
    strcpy(nome2, no->jogo->name);
    limparNome(nome1);
    limparNome(nome2);
    
    int cmp = strcmp(nome1, nome2);
    comparacoes++;
    
    if (cmp == 0) {
        return true;
    } else if (cmp < 0) {
        printf(" esq");
        return pesquisar(no->esq, nome);
    } else {
        printf(" dir");
        return pesquisar(no->dir, nome);
    }
}

void liberarArvore(NoAVL *no) {
    if (no) {
        liberarArvore(no->esq);
        liberarArvore(no->dir);
        liberarTp4(no->jogo);
        free(no);
    }
}

int main() {
    const char *caminho = "/tmp/games.csv";
    FILE *arquivo = fopen(caminho, "r");
    
    if (!arquivo) {
        fprintf(stderr, "Arquivo não encontrado.\n");
        return 1;
    }

    char linha[TAMANHO_MAX_LINHA];
    if (!fgets(linha, sizeof(linha), arquivo)) {
        fclose(arquivo);
        return 1;
    }

    Tp4 **jogos = NULL;
    int numJogos = 0, capacidade = 100;
    jogos = malloc(capacidade * sizeof(Tp4*));
    
    while (fgets(linha, sizeof(linha), arquivo)) {
        Tp4 *jogo = analisarLinha(linha);
        if (jogo) {
            if (numJogos >= capacidade) {
                capacidade *= 2;
                jogos = realloc(jogos, capacidade * sizeof(Tp4*));
            }
            jogos[numJogos++] = jogo;
        }
    }
    fclose(arquivo);

    NoAVL *raiz = NULL;
    char idBusca[TAMANHO_MAX_ID];
    
    while (fgets(idBusca, sizeof(idBusca), stdin)) {
        idBusca[strcspn(idBusca, "\n")] = 0;
        if (strcmp(idBusca, "FIM") == 0) break;
        
        for (int i = 0; i < numJogos; i++) {
            if (strcmp(jogos[i]->appID, idBusca) == 0) {
                raiz = inserir(raiz, jogos[i]);
                break;
            }
        }
    }

    clock_t inicio = clock();
    
    while (fgets(idBusca, sizeof(idBusca), stdin)) {
        idBusca[strcspn(idBusca, "\n\r")] = 0;
        
        if (strlen(idBusca) == 0) continue;
        if (strcmp(idBusca, "FIM") == 0) break;
        
        comparacoes = 0;
        printf("%s: =>raiz", idBusca);
        bool encontrado = pesquisar(raiz, idBusca);
        printf(encontrado ? " SIM\n" : " NAO\n");
    }
    
    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000;

    FILE *log = fopen("775080_avl.txt", "w");
    if (log) {
        fprintf(log, "775080\t%.0fms\n", tempo);
        fclose(log);
    }

    liberarArvore(raiz);
    free(jogos);
    
    return 0;
}
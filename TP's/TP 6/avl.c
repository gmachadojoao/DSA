#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_LINE 2048
#define MAX_FIELD 512

typedef struct {
    char appID[50];
    char name[300];
    char releaseDate[50];
    char compradores[100];
    char preco[50];
    char score[50];
    char userScore[50];
    char nConquistas[50];
    char supportedLanguages[500];
    char publishers[300];
    char developers[300];
    char categorias[300];
    char generos[300];
    char tags[500];
} Jogo;

typedef struct No {
    Jogo jogo;
    struct No *esq;
    struct No *dir;
    int altura;
} No;

void limpar(char *campo) {
    char *src = campo, *dst = campo;
    while (*src) {
        if (*src != '"' && *src != '[' && *src != ']') {
            *dst++ = *src;
        }
        src++;
    }
    *dst = '\0';
    
    int len = strlen(campo);
    while (len > 0 && (campo[len-1] == ' ' || campo[len-1] == '\n' || campo[len-1] == '\r')) {
        campo[--len] = '\0';
    }
    
    src = campo;
    while (*src == ' ') src++;
    if (src != campo) {
        memmove(campo, src, strlen(src) + 1);
    }
}

void limparLista(char *campo) {
    limpar(campo);
    char temp[512];
    strcpy(temp, campo);
    campo[0] = '\0';
    
    char *token = strtok(temp, ",");
    int first = 1;
    while (token != NULL) {
        while (*token == ' ') token++;
        char *end = token + strlen(token) - 1;
        while (end > token && *end == ' ') end--;
        *(end + 1) = '\0';
        
        if (strlen(token) > 0) {
            if (!first) strcat(campo, ", ");
            strcat(campo, token);
            first = 0;
        }
        token = strtok(NULL, ",");
    }
}

void parseCSVLine(char *line, char fields[][MAX_FIELD], int *fieldCount) {
    int i = 0, j = 0, inQuotes = 0;
    *fieldCount = 0;
    
    for (int k = 0; line[k] != '\0' && line[k] != '\n'; k++) {
        if (line[k] == '"') {
            inQuotes = !inQuotes;
        } else if (line[k] == ',' && !inQuotes) {
            fields[i][j] = '\0';
            i++;
            j = 0;
        } else {
            fields[i][j++] = line[k];
        }
    }
    fields[i][j] = '\0';
    *fieldCount = i + 1;
}

void formatarData(char *releaseDate, char *dataFormatada) {
    char meses[12][4] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int dia, ano, mes = -1;
    char mesStr[10];
    
    if (sscanf(releaseDate, "%s %d, %d", mesStr, &dia, &ano) == 3) {
        for (int i = 0; i < 12; i++) {
            if (strcmp(mesStr, meses[i]) == 0) {
                mes = i + 1;
                break;
            }
        }
        if (mes != -1) {
            sprintf(dataFormatada, "%02d/%02d/%d", dia, mes, ano);
            return;
        }
    }
    strcpy(dataFormatada, releaseDate);
}

void imprimirJogo(Jogo *j) {
    char dataFormatada[50];
    formatarData(j->releaseDate, dataFormatada);
    
    printf("=> %s ## %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## [%s] ## [%s] ## [%s] ## [%s] ## [%s] ##\n",
           j->appID, j->name, dataFormatada, j->compradores, j->preco,
           j->supportedLanguages, j->score, j->userScore, j->nConquistas,
           j->publishers, j->developers, j->categorias, j->generos, j->tags);
}

int altura(No *n) {
    return n ? n->altura : 0;
}

int max(int a, int b) {
    return (a > b) ? a : b;
}

int fatorBalanceamento(No *n) {
    return n ? altura(n->esq) - altura(n->dir) : 0;
}

No* rotacaoDireita(No *y) {
    No *x = y->esq;
    No *T2 = x->dir;
    
    x->dir = y;
    y->esq = T2;
    
    y->altura = max(altura(y->esq), altura(y->dir)) + 1;
    x->altura = max(altura(x->esq), altura(x->dir)) + 1;
    
    return x;
}

No* rotacaoEsquerda(No *x) {
    No *y = x->dir;
    No *T2 = y->esq;
    
    y->esq = x;
    x->dir = T2;
    
    x->altura = max(altura(x->esq), altura(x->dir)) + 1;
    y->altura = max(altura(y->esq), altura(y->dir)) + 1;
    
    return y;
}

No* novoNo(Jogo jogo) {
    No *no = (No*)malloc(sizeof(No));
    no->jogo = jogo;
    no->esq = NULL;
    no->dir = NULL;
    no->altura = 1;
    return no;
}

No* inserir(No *no, Jogo jogo, int *inserido) {
    if (no == NULL) {
        *inserido = 1;
        return novoNo(jogo);
    }
    
    int cmp = strcmp(jogo.name, no->jogo.name);
    
    if (cmp < 0) {
        no->esq = inserir(no->esq, jogo, inserido);
    } else if (cmp > 0) {
        no->dir = inserir(no->dir, jogo, inserido);
    } else {
        *inserido = 0;
        return no;
    }
    
    no->altura = 1 + max(altura(no->esq), altura(no->dir));
    
    int balance = fatorBalanceamento(no);
    
    if (balance > 1 && strcmp(jogo.name, no->esq->jogo.name) < 0) {
        return rotacaoDireita(no);
    }
    
    if (balance < -1 && strcmp(jogo.name, no->dir->jogo.name) > 0) {
        return rotacaoEsquerda(no);
    }
    
    if (balance > 1 && strcmp(jogo.name, no->esq->jogo.name) > 0) {
        no->esq = rotacaoEsquerda(no->esq);
        return rotacaoDireita(no);
    }
    
    if (balance < -1 && strcmp(jogo.name, no->dir->jogo.name) < 0) {
        no->dir = rotacaoDireita(no->dir);
        return rotacaoEsquerda(no);
    }
    
    return no;
}

int pesquisar(No *no, char *nome) {
    printf("%s: =>raiz", nome);
    int encontrado = pesquisarRec(no, nome);
    printf(encontrado ? " SIM\n" : " NAO\n");
    return encontrado;
}

int pesquisarRec(No *no, char *nome) {
    if (no == NULL) {
        return 0;
    }
    
    int cmp = strcmp(nome, no->jogo.name);
    
    if (cmp == 0) {
        return 1;
    } else if (cmp < 0) {
        printf(" esq");
        return pesquisarRec(no->esq, nome);
    } else {
        printf(" dir");
        return pesquisarRec(no->dir, nome);
    }
}

void liberarArvore(No *no) {
    if (no != NULL) {
        liberarArvore(no->esq);
        liberarArvore(no->dir);
        free(no);
    }
}

int main() {
    char path[] = "/tmp/games.csv";
    FILE *file = fopen(path, "r");
    
    if (!file) {
        printf("Arquivo não encontrado\n");
        return 1;
    }
    
    Jogo jogos[5000];
    int totalJogos = 0;
    char line[MAX_LINE];
    
    fgets(line, MAX_LINE, file);
    
    while (fgets(line, MAX_LINE, file) && totalJogos < 5000) {
        char fields[20][MAX_FIELD];
        int fieldCount;
        parseCSVLine(line, fields, &fieldCount);
        
        if (fieldCount < 14) continue;
        
        strcpy(jogos[totalJogos].appID, fields[0]);
        strcpy(jogos[totalJogos].name, fields[1]);
        strcpy(jogos[totalJogos].releaseDate, fields[2]);
        strcpy(jogos[totalJogos].compradores, fields[3]);
        strcpy(jogos[totalJogos].preco, fields[4]);
        strcpy(jogos[totalJogos].supportedLanguages, fields[5]);
        strcpy(jogos[totalJogos].score, fields[6]);
        strcpy(jogos[totalJogos].userScore, fields[7]);
        strcpy(jogos[totalJogos].nConquistas, fields[8]);
        strcpy(jogos[totalJogos].publishers, fields[9]);
        strcpy(jogos[totalJogos].developers, fields[10]);
        strcpy(jogos[totalJogos].categorias, fields[11]);
        strcpy(jogos[totalJogos].generos, fields[12]);
        strcpy(jogos[totalJogos].tags, fields[13]);
        
        limpar(jogos[totalJogos].appID);
        limpar(jogos[totalJogos].name);
        limpar(jogos[totalJogos].releaseDate);
        limpar(jogos[totalJogos].compradores);
        limpar(jogos[totalJogos].preco);
        limpar(jogos[totalJogos].score);
        limpar(jogos[totalJogos].userScore);
        limpar(jogos[totalJogos].nConquistas);
        limparLista(jogos[totalJogos].supportedLanguages);
        limparLista(jogos[totalJogos].publishers);
        limparLista(jogos[totalJogos].developers);
        limparLista(jogos[totalJogos].categorias);
        limparLista(jogos[totalJogos].generos);
        limparLista(jogos[totalJogos].tags);
        
        totalJogos++;
    }
    
    fclose(file);
    
    No *raiz = NULL;
    char idBusca[50];
    
    while (scanf(" %[^\n]", idBusca) == 1) {
        if (strcmp(idBusca, "FIM") == 0) break;
        
        for (int i = 0; i < totalJogos; i++) {
            if (strcmp(jogos[i].appID, idBusca) == 0) {
                int inserido = 0;
                raiz = inserir(raiz, jogos[i], &inserido);
                break;
            }
        }
    }
    
    clock_t inicio = clock();
    int numPesquisas = 0;
    
    while (scanf(" %[^\n]", idBusca) == 1) {
        if (strcmp(idBusca, "FIM") == 0) break;
        pesquisar(raiz, idBusca);
        numPesquisas++;
    }
    
    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000;
    
    FILE *log = fopen("775080_avl.txt", "w");
    fprintf(log, "775080\t%.0fms\t%d pesquisas\n", tempo, numPesquisas);
    fclose(log);
    
    liberarArvore(raiz);
    
    return 0;
}
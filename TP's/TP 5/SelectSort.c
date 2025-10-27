#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_LINE 8192
#define MAX_FIELD 2048
#define MAX_GAMES 50000

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
    int dia, mes, ano;
} Game;

typedef struct {
    long comparacoes;
    long movimentacoes;
    double tempo;
} LogMetrics;

LogMetrics metrics = {0, 0, 0.0};

char* str_safe_alloc(const char* s) {
    if (!s) return NULL;
    size_t len = strlen(s);
    char *p = malloc(len + 1);
    if (!p) {
        fprintf(stderr, "Erro de alocação de memória\n");
        exit(1);
    }
    strcpy(p, s);
    return p;
}

void limpar(char* campo) {
    if (!campo) return;
    char *src = campo, *dst = campo;
    while (*src) {
        if (*src != '"' && *src != '[' && *src != ']') {
            *dst++ = *src;
        }
        src++;
    }
    *dst = '\0';
}

void formatList(char* campo) {
    if (!campo || strlen(campo) == 0) return;
    char temp[MAX_FIELD], resultado[MAX_FIELD] = "";
    strncpy(temp, campo, MAX_FIELD - 1);
    temp[MAX_FIELD - 1] = '\0';
    limpar(temp);
    char *token = strtok(temp, ",");
    int primeiro = 1;
    while (token) {
        while (*token == ' ') token++;
        if (!primeiro && strlen(resultado) < MAX_FIELD - 3) {
            strcat(resultado, ", ");
        }
        strncat(resultado, token, MAX_FIELD - strlen(resultado) - 1);
        primeiro = 0;
        token = strtok(NULL, ",");
    }
    strcpy(campo, resultado);
}

void parseDate(const char* input, char* output, int* dia, int* mes, int* ano) {
    const char* meses[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    char mesStr[4] = "";
    *dia = 0; *mes = 0; *ano = 0;
    if (sscanf(input, "\"%3s %d, %d\"", mesStr, dia, ano) == 3) {
        for (int i = 0; i < 12; i++) {
            if (strcmp(mesStr, meses[i]) == 0) {
                *mes = i + 1;
                break;
            }
        }
        sprintf(output, "%02d/%02d/%04d", *dia, *mes, *ano);
    } else {
        strcpy(output, input);
    }
}

int parseCSVLine(char* linha, char** campos, int maxCampos) {
    int campo = 0;
    int dentroAspas = 0;
    char* inicio = linha;
    char* p = linha;
    while (*p && campo < maxCampos) {
        if (*p == '"') {
            dentroAspas = !dentroAspas;
        } else if (*p == ',' && !dentroAspas) {
            *p = '\0';
            campos[campo++] = inicio;
            inicio = p + 1;
        }
        p++;
    }
    if (campo < maxCampos) {
        char* nl = strchr(inicio, '\n');
        if (nl) *nl = '\0';
        campos[campo++] = inicio;
    }
    return campo;
}

Game* criarGame(char** campos) {
    Game* g = malloc(sizeof(Game));
    if (!g) {
        fprintf(stderr, "Erro de alocação\n");
        exit(1);
    }
    g->appID = str_safe_alloc(campos[0]);
    g->name = str_safe_alloc(campos[1]);
    g->releaseDate = str_safe_alloc(campos[2]);
    g->compradores = str_safe_alloc(campos[3]);
    g->preco = str_safe_alloc(campos[4]);
    g->supportedLanguages = str_safe_alloc(campos[5]);
    g->score = str_safe_alloc(campos[6]);
    g->userScore = str_safe_alloc(campos[7]);
    g->nConquistas = str_safe_alloc(campos[8]);
    g->publishers = str_safe_alloc(campos[9]);
    g->developers = str_safe_alloc(campos[10]);
    g->categorias = str_safe_alloc(campos[11]);
    g->generos = str_safe_alloc(campos[12]);
    g->tags = str_safe_alloc(campos[13]);
    char dataFormatada[20];
    parseDate(g->releaseDate, dataFormatada, &g->dia, &g->mes, &g->ano);
    return g;
}

void printGame(Game *jogo) {
    char dataFormatada[20];
    int d, m, a;
    parseDate(jogo->releaseDate, dataFormatada, &d, &m, &a);
    char langs[MAX_FIELD], pubs[MAX_FIELD], devs[MAX_FIELD];
    char cats[MAX_FIELD], gens[MAX_FIELD], tags[MAX_FIELD];
    strcpy(langs, jogo->supportedLanguages);
    strcpy(pubs, jogo->publishers);
    strcpy(devs, jogo->developers);
    strcpy(cats, jogo->categorias);
    strcpy(gens, jogo->generos);
    strcpy(tags, jogo->tags);
    formatList(langs);
    formatList(pubs);
    formatList(devs);
    formatList(cats);
    formatList(gens);
    formatList(tags);
    char name[MAX_FIELD], comp[MAX_FIELD], preco[MAX_FIELD];
    char score[MAX_FIELD], uscore[MAX_FIELD], nconq[MAX_FIELD];
    strcpy(name, jogo->name);
    strcpy(comp, jogo->compradores);
    strcpy(preco, jogo->preco);
    strcpy(score, jogo->score);
    strcpy(uscore, jogo->userScore);
    strcpy(nconq, jogo->nConquistas);
    limpar(name);
    limpar(comp);
    limpar(preco);
    limpar(score);
    limpar(uscore);
    limpar(nconq);
    char userScoreFormatado[50];
    if (strchr(uscore, '.') == NULL && strlen(uscore) > 0) {
        sprintf(userScoreFormatado, "%s.0", uscore);
    } else {
        strcpy(userScoreFormatado, uscore);
    }
    printf("=> %s ## %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## [%s] ## [%s] ## [%s] ## [%s] ## [%s] ## ",
           jogo->appID, name, dataFormatada, comp, preco, langs,
           score, userScoreFormatado, nconq, pubs, devs, cats, gens, tags);
}

void freeGame(Game *jogo) {
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

int compararPorNome(Game* a, Game* b) {
    metrics.comparacoes++;
    char nomeA[MAX_FIELD], nomeB[MAX_FIELD];
    strcpy(nomeA, a->name);
    strcpy(nomeB, b->name);
    limpar(nomeA);
    limpar(nomeB);
    return strcmp(nomeA, nomeB);
}

void selectionSort(Game** array, int n) {
    for (int i = 0; i < n - 1; i++) {
        int menor = i;
        for (int j = i + 1; j < n; j++) {
            if (compararPorNome(array[j], array[menor]) < 0) {
                menor = j;
            }
        }
        if (menor != i) {
            metrics.movimentacoes += 3;
            Game* temp = array[i];
            array[i] = array[menor];
            array[menor] = temp;
        }
    }
}

void salvarLog(const char* matricula) {
    FILE* log = fopen("778050_selecao.txt", "w");
    if (!log) {
        fprintf(stderr, "Erro ao criar arquivo de log\n");
        return;
    }
    fprintf(log, "%s\t%ld\t%ld\t%.6f\n", 
            matricula, metrics.comparacoes, metrics.movimentacoes, metrics.tempo);
    fclose(log);
}

int main() {
    const char* path = "/tmp/games.csv";
    FILE* fp = fopen(path, "r");
    if (!fp) {
        fprintf(stderr, "Arquivo não encontrado: %s\n", path);
        return 1;
    }
    char linha[MAX_LINE];
    if (!fgets(linha, sizeof(linha), fp)) {
        fclose(fp);
        return 1;
    }
    char** idsBusca = malloc(MAX_GAMES * sizeof(char*));
    int nBuscas = 0;
    char idBusca[128];
    while (fgets(idBusca, sizeof(idBusca), stdin)) {
        idBusca[strcspn(idBusca, "\n")] = 0;
        if (strcmp(idBusca, "FIM") == 0) break;
        idsBusca[nBuscas++] = str_safe_alloc(idBusca);
    }
    Game** games = malloc(MAX_GAMES * sizeof(Game*));
    int nGames = 0;
    for (int i = 0; i < nBuscas; i++) {
        rewind(fp);
        fgets(linha, sizeof(linha), fp);
        while (fgets(linha, sizeof(linha), fp)) {
            char* campos[14];
            if (parseCSVLine(linha, campos, 14) >= 14) {
                if (strcmp(campos[0], idsBusca[i]) == 0) {
                    games[nGames++] = criarGame(campos);
                    break;
                }
            }
        }
    }
    clock_t inicio = clock();
    selectionSort(games, nGames);
    clock_t fim = clock();
    metrics.tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    for (int i = 0; i < nGames; i++) {
        printGame(games[i]);
    }
    salvarLog("778050");
    for (int i = 0; i < nGames; i++) {
        freeGame(games[i]);
    }
    for (int i = 0; i < nBuscas; i++) {
        free(idsBusca[i]);
    }
    free(games);
    free(idsBusca);
    fclose(fp);
    return 0;
}
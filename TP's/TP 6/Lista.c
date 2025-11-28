#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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
} Game;

typedef struct Celula {
    Game *jogo;
    struct Celula *prox;
} Celula;

typedef struct {
    Celula *primeiro;
    Celula *ultimo;
} Lista;

char* str_alloc(const char* s) {
    if (!s) return NULL;
    char *p = malloc(strlen(s) + 1);
    strcpy(p, s);
    return p;
}

void limparAspasLista(char* campo) {
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

void limparAspasSimples(char* campo) {
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
    if (!campo) return;
    char temp[4096], resultado[4096] = "";
    int i = 0, j = 0;
    while (campo[i]) {
        if (campo[i] != '"' && campo[i] != '[' && campo[i] != ']')
            temp[j++] = campo[i];
        i++;
    }
    temp[j] = '\0';
    char *token = strtok(temp, ",");
    int primeiro = 1;
    while (token) {
        while (*token == ' ') token++;
        if (!primeiro) strcat(resultado, ", ");
        strcat(resultado, token);
        primeiro = 0;
        token = strtok(NULL, ",");
    }
    strcpy(campo, resultado);
}


void formatDate(const char* input, char* output) {
    const char* meses[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    int dia = 0, ano = 0;
    char mesStr[4];
    
    if (sscanf(input, "\"%3s %d, %d\"", mesStr, &dia, &ano) == 3) {
        int mes = 0;
        for (mes = 0; mes < 12; mes++) {
            if (strcmp(mesStr, meses[mes]) == 0) break;
        }
        sprintf(output, "%02d/%02d/%04d", dia, mes + 1, ano);
    } else {
        strcpy(output, input);
    }
}

void formatarValor(char* campo) {
    if (!campo) return;
    limparAspasSimples(campo);
    
    char *ponto = strchr(campo, '.');
    if (!ponto) {
        strcat(campo, ".0");
    } else {
        char *p = ponto + 1;
        int temDecimal = 0;
        while (*p) {
            if (*p != '0') {
                temDecimal = 1;
                break;
            }
            p++;
        }
        if (!temDecimal && *(ponto + 1) == '0' && *(ponto + 2) == '\0') {
            *ponto = '\0';
            strcat(campo, ".0");
        }
    }
}

void printGame(Game *jogo, int comIndice, int indice) {
    char dataFormatada[20];
    formatDate(jogo->releaseDate, dataFormatada);

    char langs[4096], pubs[4096], devs[4096], cats[4096], gens[4096], tgs[4096];
    strcpy(langs, jogo->supportedLanguages);
    strcpy(pubs, jogo->publishers);
    strcpy(devs, jogo->developers);
    strcpy(cats, jogo->categorias);
    strcpy(gens, jogo->generos);
    strcpy(tgs, jogo->tags);

    formatList(langs);
    formatList(pubs);
    formatList(devs);
    formatList(cats);
    formatList(gens);
    formatList(tgs);

    char nome[512], comp[128], preco[64], score[64], uscore[64], nconq[64];
    strcpy(nome, jogo->name);
    strcpy(comp, jogo->compradores);
    strcpy(preco, jogo->preco);
    strcpy(score, jogo->score);
    strcpy(uscore, jogo->userScore);
    strcpy(nconq, jogo->nConquistas);

    limparAspasSimples(nome);
    limparAspasSimples(comp);
    limparAspasSimples(score);
    limparAspasSimples(uscore);
    limparAspasSimples(nconq);
    formatarValor(preco);

    if (strchr(score, '.')) {
        if (strcmp(score + strlen(score) - 2, ".0") == 0)
            score[strlen(score) - 2] = '\0';
    }
    if (strchr(nconq, '.')) {
        if (strcmp(nconq + strlen(nconq) - 2, ".0") == 0)
            nconq[strlen(nconq) - 2] = '\0';
    }
    formatarValor(uscore);

    if (comIndice) {
        printf("[%d] => %s ## %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## [%s] ## [%s] ## [%s] ## [%s] ## [%s] ##",
               indice, jogo->appID, nome, dataFormatada, comp, preco, langs, score, uscore, nconq,
               pubs, devs, cats, gens, tgs);
    } else {
        printf("(R) %s\n", nome);
    }
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

void iniciarLista(Lista *lista) {
    lista->primeiro = malloc(sizeof(Celula));
    lista->ultimo = lista->primeiro;
    lista->primeiro->prox = NULL;
    lista->primeiro->jogo = NULL;
}

void inserirFim(Lista *lista, Game *jogo) {
    lista->ultimo->prox = malloc(sizeof(Celula));
    lista->ultimo = lista->ultimo->prox;
    lista->ultimo->jogo = jogo;
    lista->ultimo->prox = NULL;
}

void inserirInicio(Lista *lista, Game *jogo) {
    Celula *nova = malloc(sizeof(Celula));
    nova->jogo = jogo;
    nova->prox = lista->primeiro->prox;
    lista->primeiro->prox = nova;
    if (nova->prox == NULL) lista->ultimo = nova;
}

void inserirPosicao(Lista *lista, Game *jogo, int pos) {
    if (pos <= 0) {
        inserirInicio(lista, jogo);
        return;
    }
    Celula *atual = lista->primeiro;
    for (int i = 0; i < pos && atual->prox != NULL; i++) atual = atual->prox;
    Celula *nova = malloc(sizeof(Celula));
    nova->jogo = jogo;
    nova->prox = atual->prox;
    atual->prox = nova;
    if (nova->prox == NULL) lista->ultimo = nova;
}

Game* removerInicio(Lista *lista) {
    if (lista->primeiro->prox == NULL) return NULL;
    Celula *temp = lista->primeiro->prox;
    Game *jogo = temp->jogo;
    lista->primeiro->prox = temp->prox;
    if (temp == lista->ultimo) lista->ultimo = lista->primeiro;
    free(temp);
    return jogo;
}

Game* removerFim(Lista *lista) {
    if (lista->primeiro->prox == NULL) return NULL;
    Celula *penultima = lista->primeiro;
    while (penultima->prox != lista->ultimo) penultima = penultima->prox;
    Game *jogo = lista->ultimo->jogo;
    free(lista->ultimo);
    lista->ultimo = penultima;
    lista->ultimo->prox = NULL;
    return jogo;
}

Game* removerPosicao(Lista *lista, int pos) {
    if (lista->primeiro->prox == NULL) return NULL;
    Celula *atual = lista->primeiro;
    for (int i = 0; i < pos && atual->prox != NULL; i++) atual = atual->prox;
    if (atual->prox == NULL) return NULL;
    Celula *temp = atual->prox;
    Game *jogo = temp->jogo;
    atual->prox = temp->prox;
    if (temp == lista->ultimo) lista->ultimo = atual;
    free(temp);
    return jogo;
}

void mostrarLista(Lista *lista) {
    int idx = 0;
    for (Celula *i = lista->primeiro->prox; i != NULL; i = i->prox) {
        printGame(i->jogo, 1, idx++);
    }
}

void liberarLista(Lista *lista) {
    Celula *i = lista->primeiro->prox;
    while (i != NULL) {
        Celula *temp = i;
        i = i->prox;
        freeGame(temp->jogo);
        free(temp);
    }
    free(lista->primeiro);
}

int splitCSV(char *linha, char *campos[], int maxCampos) {
    int i = 0, dentroAspas = 0, dentroColchetes = 0;
    char *inicio = linha;
    
    for (char *p = linha; *p && i < maxCampos - 1; p++) {
        if (*p == '"') {
            dentroAspas = !dentroAspas;
        } else if (*p == '[' && !dentroAspas) {
            dentroColchetes++;
        } else if (*p == ']' && !dentroAspas) {
            dentroColchetes--;
        } else if (*p == ',' && !dentroAspas && dentroColchetes == 0) {
            *p = '\0';
            campos[i++] = inicio;
            inicio = p + 1;
        }
    }
    campos[i++] = inicio;
    return i;
}

Game* buscarNoCSV(FILE *fp, const char *id) {
    rewind(fp);
    char linha[16384];
    fgets(linha, sizeof(linha), fp);
    
    while (fgets(linha, sizeof(linha), fp)) {
        linha[strcspn(linha, "\n")] = 0;
        
        char linhaCopia[16384];
        strcpy(linhaCopia, linha);
        
        char *campos[14];
        int qtd = splitCSV(linhaCopia, campos, 14);
        
        if (qtd >= 14 && strcmp(campos[0], id) == 0) {
            Game *jogo = malloc(sizeof(Game));
            jogo->appID = str_alloc(campos[0]);
            jogo->name = str_alloc(campos[1]);
            jogo->releaseDate = str_alloc(campos[2]);
            jogo->compradores = str_alloc(campos[3]);
            jogo->preco = str_alloc(campos[4]);
            jogo->supportedLanguages = str_alloc(campos[5]);
            jogo->score = str_alloc(campos[6]);
            jogo->userScore = str_alloc(campos[7]);
            jogo->nConquistas = str_alloc(campos[8]);
            jogo->publishers = str_alloc(campos[9]);
            jogo->developers = str_alloc(campos[10]);
            jogo->categorias = str_alloc(campos[11]);
            jogo->generos = str_alloc(campos[12]);
            jogo->tags = str_alloc(campos[13]);
            return jogo;
        }
    }
    return NULL;
}

int main() {
    const char *path = "/tmp/games.csv";
    FILE *fp = fopen(path, "r");
    if (!fp) return 1;
    
    Lista lista;
    iniciarLista(&lista);
    
    char comando[128];
    while (1) {
        if (!fgets(comando, sizeof(comando), stdin)) break;
        comando[strcspn(comando, "\n")] = 0;
        if (strcmp(comando, "FIM") == 0) break;
        Game *jogo = buscarNoCSV(fp, comando);
        if (jogo) inserirFim(&lista, jogo);
    }
    
    while (fgets(comando, sizeof(comando), stdin)) {
        comando[strcspn(comando, "\n")] = 0;
        if (strlen(comando) == 0) continue;
        
        if (strncmp(comando, "II ", 3) == 0) {
            char id[128];
            sscanf(comando, "II %s", id);
            Game *jogo = buscarNoCSV(fp, id);
            if (jogo) inserirInicio(&lista, jogo);
        } else if (strncmp(comando, "IF ", 3) == 0) {
            char id[128];
            sscanf(comando, "IF %s", id);
            Game *jogo = buscarNoCSV(fp, id);
            if (jogo) inserirFim(&lista, jogo);
        } else if (strncmp(comando, "I* ", 3) == 0) {
            int pos;
            char id[128];
            sscanf(comando, "I* %d %s", &pos, id);
            Game *jogo = buscarNoCSV(fp, id);
            if (jogo) inserirPosicao(&lista, jogo, pos);
        } else if (strcmp(comando, "RI") == 0) {
            Game *removido = removerInicio(&lista);
            if (removido) {
                printGame(removido, 0, 0);
                freeGame(removido);
            }
        } else if (strcmp(comando, "RF") == 0) {
            Game *removido = removerFim(&lista);
            if (removido) {
                printGame(removido, 0, 0);
                freeGame(removido);
            }
        } else if (strncmp(comando, "R* ", 3) == 0) {
            int pos;
            sscanf(comando, "R* %d", &pos);
            Game *removido = removerPosicao(&lista, pos);
            if (removido) {
                printGame(removido, 0, 0);
                freeGame(removido);
            }
        }
    }
    
    mostrarLista(&lista);
    liberarLista(&lista);
    fclose(fp);
    return 0;
}
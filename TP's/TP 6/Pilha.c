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
    Game *game;
    struct Celula *prox;
} Celula;

typedef struct {
    Celula *topo;
    int tamanho;
} Pilha;

char* str_alloc(const char* s) {
    if (!s) return NULL;
    char *p = malloc(strlen(s) + 1);
    strcpy(p, s);
    return p;
}

void trim(char *str) {
    if (!str) return;
    char *start = str;
    while (*start && (*start == ' ' || *start == '\t' || *start == '\n' || *start == '\r')) start++;
    if (start != str) memmove(str, start, strlen(start) + 1);
    char *end = str + strlen(str) - 1;
    while (end > str && (*end == ' ' || *end == '\t' || *end == '\n' || *end == '\r')) end--;
    *(end + 1) = '\0';
}

void limpar(char* campo) {
    if (!campo) return;
    char *src = campo, *dst = campo;
    while (*src) {
        if (*src != '"' && *src != '\'' && *src != '[' && *src != ']') *dst++ = *src;
        src++;
    }
    *dst = '\0';
}

void formatList(char* campo) {
    if (!campo) return;
    char temp[4096], resultado[4096] = "";
    strncpy(temp, campo, sizeof(temp) - 1);
    temp[sizeof(temp) - 1] = '\0';
    limpar(temp);

    char *token = strtok(temp, ",");
    int primeiro = 1;
    while (token) {
        trim(token);
        if (strlen(token) > 0) {
            if (!primeiro) strcat(resultado, ", ");
            strncat(resultado, token, sizeof(resultado) - strlen(resultado) - 1);
            primeiro = 0;
        }
        token = strtok(NULL, ",");
    }
    strncpy(campo, resultado, 4095);
    campo[4095] = '\0';
}

void formatDate(const char* input, char* output) {
    const char* meses[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    int mes = 0, dia = 0, ano = 0;
    char mesStr[4];
    if (sscanf(input, "\"%3s %d, %d\"", mesStr, &dia, &ano) == 3) {
        for (mes = 0; mes < 12; mes++) {
            if (strcmp(mesStr, meses[mes]) == 0) {
                sprintf(output, "%02d/%02d/%04d", dia, mes + 1, ano);
                return;
            }
        }
    }
    strncpy(output, input, 19);
    output[19] = '\0';
}

int parseCsvLine(char *linha, char **campos, int maxCampos) {
    int campo = 0;
    int dentroAspas = 0;
    char *inicio = linha;
    
    for (char *p = linha; *p && campo < maxCampos; p++) {
        if (*p == '"') {
            dentroAspas = !dentroAspas;
        }
        if (*p == ',' && !dentroAspas) {
            *p = '\0';
            campos[campo++] = inicio;
            inicio = p + 1;
        }
    }
    if (campo < maxCampos) {
        campos[campo++] = inicio;
    }
    return campo;
}

Game* criarGame(char **campos) {
    Game *g = malloc(sizeof(Game));
    g->appID = str_alloc(campos[0]);
    g->name = str_alloc(campos[1]);
    g->releaseDate = str_alloc(campos[2]);
    g->compradores = str_alloc(campos[3]);
    g->preco = str_alloc(campos[4]);
    g->supportedLanguages = str_alloc(campos[5]);
    g->score = str_alloc(campos[6]);
    g->userScore = str_alloc(campos[7]);
    g->nConquistas = str_alloc(campos[8]);
    g->publishers = str_alloc(campos[9]);
    g->developers = str_alloc(campos[10]);
    g->categorias = str_alloc(campos[11]);
    g->generos = str_alloc(campos[12]);
    g->tags = str_alloc(campos[13]);
    return g;
}

void freeGame(Game *g) {
    if (!g) return;
    free(g->appID);
    free(g->name);
    free(g->releaseDate);
    free(g->compradores);
    free(g->preco);
    free(g->score);
    free(g->userScore);
    free(g->nConquistas);
    free(g->supportedLanguages);
    free(g->publishers);
    free(g->developers);
    free(g->categorias);
    free(g->generos);
    free(g->tags);
    free(g);
}

void printGame(Game *g) {
    char dataFormatada[20];
    formatDate(g->releaseDate, dataFormatada);
    
    char auxLanguages[4096], auxPublishers[4096], auxDevelopers[4096];
    char auxCategorias[4096], auxGeneros[4096], auxTags[4096];
    
    strncpy(auxLanguages, g->supportedLanguages, sizeof(auxLanguages) - 1);
    strncpy(auxPublishers, g->publishers, sizeof(auxPublishers) - 1);
    strncpy(auxDevelopers, g->developers, sizeof(auxDevelopers) - 1);
    strncpy(auxCategorias, g->categorias, sizeof(auxCategorias) - 1);
    strncpy(auxGeneros, g->generos, sizeof(auxGeneros) - 1);
    strncpy(auxTags, g->tags, sizeof(auxTags) - 1);
    
    auxLanguages[sizeof(auxLanguages) - 1] = '\0';
    auxPublishers[sizeof(auxPublishers) - 1] = '\0';
    auxDevelopers[sizeof(auxDevelopers) - 1] = '\0';
    auxCategorias[sizeof(auxCategorias) - 1] = '\0';
    auxGeneros[sizeof(auxGeneros) - 1] = '\0';
    auxTags[sizeof(auxTags) - 1] = '\0';
    
    formatList(auxLanguages);
    formatList(auxPublishers);
    formatList(auxDevelopers);
    formatList(auxCategorias);
    formatList(auxGeneros);
    formatList(auxTags);
    
    char auxName[2048], auxCompradores[512], auxPreco[512];
    char auxScore[512], auxUserScore[512], auxNConquistas[512];
    strncpy(auxName, g->name, sizeof(auxName) - 1);
    strncpy(auxCompradores, g->compradores, sizeof(auxCompradores) - 1);
    strncpy(auxPreco, g->preco, sizeof(auxPreco) - 1);
    strncpy(auxScore, g->score, sizeof(auxScore) - 1);
    strncpy(auxUserScore, g->userScore, sizeof(auxUserScore) - 1);
    strncpy(auxNConquistas, g->nConquistas, sizeof(auxNConquistas) - 1);
    
    auxName[sizeof(auxName) - 1] = '\0';
    auxCompradores[sizeof(auxCompradores) - 1] = '\0';
    auxPreco[sizeof(auxPreco) - 1] = '\0';
    auxScore[sizeof(auxScore) - 1] = '\0';
    auxUserScore[sizeof(auxUserScore) - 1] = '\0';
    auxNConquistas[sizeof(auxNConquistas) - 1] = '\0';
    
    limpar(auxName);
    limpar(auxCompradores);
    limpar(auxPreco);
    limpar(auxScore);
    limpar(auxUserScore);
    limpar(auxNConquistas);

    printf("%s ## %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## [%s] ## [%s] ## [%s] ## [%s] ## [%s] ##\n",
           g->appID, auxName, dataFormatada, auxCompradores, auxPreco,
           auxLanguages, auxScore, auxUserScore, auxNConquistas,
           auxPublishers, auxDevelopers, auxCategorias, auxGeneros, auxTags);
}

void iniciarPilha(Pilha *p) {
    p->topo = NULL;
    p->tamanho = 0;
}

void empilhar(Pilha *p, Game *g) {
    Celula *nova = malloc(sizeof(Celula));
    nova->game = g;
    nova->prox = p->topo;
    p->topo = nova;
    p->tamanho++;
}

Game* desempilhar(Pilha *p) {
    if (p->topo == NULL) return NULL;
    
    Celula *temp = p->topo;
    Game *g = temp->game;
    p->topo = p->topo->prox;
    p->tamanho--;
    free(temp);
    return g;
}

void mostrarPilhaRecursivo(Celula *cel, int *pos) {
    if (cel->prox == NULL) {
        printf("[%d] => ", (*pos)++);
        printGame(cel->game);
        return;
    }
    mostrarPilhaRecursivo(cel->prox, pos);
    printf("[%d] => ", (*pos)++);
    printGame(cel->game);
}

void mostrarPilha(Pilha *p) {
    int pos = 0;
    mostrarPilhaRecursivo(p->topo, &pos);
}

void liberarPilha(Pilha *p) {
    while (p->topo != NULL) {
        Game *g = desempilhar(p);
        freeGame(g);
    }
}

Game* buscarGame(FILE *fp, const char *idBusca) {
    char linha[4096];
    rewind(fp);
    fgets(linha, sizeof(linha), fp);
    
    while (fgets(linha, sizeof(linha), fp)) {
        char linhaCopia[4096];
        strcpy(linhaCopia, linha);
        
        char *campos[14];
        int numCampos = parseCsvLine(linhaCopia, campos, 14);
        
        for (int i = 0; i < numCampos; i++) trim(campos[i]);
        
        if (numCampos >= 1 && strcmp(campos[0], idBusca) == 0) {
            return criarGame(campos);
        }
    }
    return NULL;
}

int main() {
    const char *path = "/tmp/games.csv";
    FILE *fp = fopen(path, "r");
    if (!fp) {
        printf("Arquivo não encontrado.\n");
        return 1;
    }

    Pilha pilha;
    iniciarPilha(&pilha);

    char linha[128];
    while (1) {
        if (!fgets(linha, sizeof(linha), stdin)) break;
        linha[strcspn(linha, "\n")] = 0;
        linha[strcspn(linha, "\r")] = 0;
        if (strcmp(linha, "FIM") == 0) break;
        
        trim(linha);
        Game *g = buscarGame(fp, linha);
        if (g) {
            empilhar(&pilha, g);
        }
    }

    int numComandos;
    if (scanf("%d\n", &numComandos) != 1) {
        fclose(fp);
        return 1;
    }

    char comando[128];
    for (int i = 0; i < numComandos; i++) {
        if (!fgets(comando, sizeof(comando), stdin)) break;
        comando[strcspn(comando, "\n")] = 0;
        comando[strcspn(comando, "\r")] = 0;
        
        trim(comando);
        
        if (strlen(comando) == 0) {
            i--;
            continue;
        }
        
        if (comando[0] == 'I' && comando[1] == ' ') {
            char *id = comando + 2;
            trim(id);
            Game *g = buscarGame(fp, id);
            if (g) {
                empilhar(&pilha, g);
            }
        } else if (comando[0] == 'R') {
            Game *g = desempilhar(&pilha);
            if (g) {
                printf("(R) %s\n", g->name);
                freeGame(g);
            }
        }
    }

    mostrarPilha(&pilha);
    liberarPilha(&pilha);
    fclose(fp);
    return 0;
}
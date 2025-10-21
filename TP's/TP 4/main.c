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
} Tp4;

char* str_alloc(const char* s) {
    if (!s) return NULL;
    char *p = malloc(strlen(s) + 1);
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

void formatList(char* campo) {
    if (!campo) return;
    char temp[1024], resultado[1024] = "";
    strcpy(temp, campo);
    limpar(temp);

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
    int mes = 0, dia = 0, ano = 0;
    char mesStr[4];
    if (sscanf(input, "\"%3s %d, %d\"", mesStr, &dia, &ano) == 3) {
        for (mes = 0; mes < 12; mes++) {
            if (strcmp(mesStr, meses[mes]) == 0) break;
        }
        sprintf(output, "%02d/%02d/%04d", dia, mes+1, ano);
    } else {
        strcpy(output, input);
    }
}

void printTp4(Tp4 *jogo) {
    char dataFormatada[20];
    formatDate(jogo->releaseDate, dataFormatada);

    formatList(jogo->supportedLanguages);
    formatList(jogo->publishers);
    formatList(jogo->developers);
    formatList(jogo->categorias);
    formatList(jogo->generos);
    formatList(jogo->tags);

    limpar(jogo->name);
    limpar(jogo->compradores);
    limpar(jogo->preco);
    limpar(jogo->score);
    limpar(jogo->userScore);
    limpar(jogo->nConquistas);

    printf("=> %s ## %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## [%s] ## [%s] ## [%s] ## [%s] ## [%s] ## [%s] ##\n",
           jogo->appID,
           jogo->name,
           dataFormatada,
           jogo->compradores,
           jogo->preco,
           jogo->supportedLanguages,
           jogo->score,
           jogo->userScore,
           jogo->nConquistas,
           jogo->publishers,
           jogo->developers,
           jogo->categorias,
           jogo->generos,
           jogo->tags);
}

void freeTp4(Tp4 *jogo) {
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

int main() {
    const char *path = "/tmp/games.csv";
    FILE *fp = fopen(path, "r");
    if (!fp) {
        printf("Arquivo não encontrado.\n");
        return 1;
    }

    char linha[4096];
    if (!fgets(linha, sizeof(linha), fp)) {
        fclose(fp);
        return 1;
    }

    char idBusca[128];
    while (1) {
        if (!fgets(idBusca, sizeof(idBusca), stdin)) break;
        idBusca[strcspn(idBusca, "\n")] = 0;
        if (strcmp(idBusca, "Fim") == 0) break;

        rewind(fp);
        fgets(linha, sizeof(linha), fp);
        int encontrado = 0;
        while (fgets(linha, sizeof(linha), fp)) {
            Tp4 *jogo = malloc(sizeof(Tp4));
            memset(jogo, 0, sizeof(Tp4));

            char *campos[14];
            char *token = strtok(linha, ",");
            int i = 0;
            while (token && i < 14) {
                campos[i++] = token;
                token = strtok(NULL, ",");
            }

            jogo->appID = str_alloc(campos[0]);
            if (strcmp(jogo->appID, idBusca) == 0) {
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

                printTp4(jogo);
                encontrado = 1;
                freeTp4(jogo);
                break;
            }
            free(jogo->appID);
            free(jogo);
        }
    }

    fclose(fp);
    return 0;
}

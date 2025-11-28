#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

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
    formatarData(jogo->releaseDate, dataFormatada);

    formatarLista(jogo->supportedLanguages);
    formatarLista(jogo->publishers);
    formatarLista(jogo->developers);
    formatarLista(jogo->categorias);
    formatarLista(jogo->generos);
    formatarLista(jogo->tags);

    limpar(jogo->name);
    limpar(jogo->compradores);
    limpar(jogo->preco);
    limpar(jogo->score);
    limpar(jogo->userScore);
    limpar(jogo->nConquistas);

    printf("=> %s ## %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## [%s] ## [%s] ## [%s] ## [%s] ## [%s] ##\n",
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
    char *token = strtok(linha, ",");
    int i = 0;
    
    while (token && i < NUM_CAMPOS) {
        campos[i++] = token;
        token = strtok(NULL, ",");
    }

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

bool buscarEImprimir(FILE *arquivo, const char *idBusca) {
    char linha[TAMANHO_MAX_LINHA];
    
    rewind(arquivo);
    fgets(linha, sizeof(linha), arquivo);
    
    while (fgets(linha, sizeof(linha), arquivo)) {
        Tp4 *jogo = analisarLinha(linha);
        if (!jogo) continue;
        
        if (strcmp(jogo->appID, idBusca) == 0) {
            imprimirTp4(jogo);
            liberarTp4(jogo);
            return true;
        }
        
        liberarTp4(jogo);
    }
    
    return false;
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

    char idBusca[TAMANHO_MAX_ID];
    while (fgets(idBusca, sizeof(idBusca), stdin)) {
        idBusca[strcspn(idBusca, "\n")] = 0;
        
        if (strcmp(idBusca, "Fim") == 0) break;
        
        buscarEImprimir(arquivo, idBusca);
    }

    fclose(arquivo);
    return 0;
}
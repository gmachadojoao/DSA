#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Definindo tamanhos máximos seguros
#define MAX_LIST_LENGTH 8192
#define MAX_LINE_LENGTH 16384

typedef struct {
    char *appID;
    char *name;
    char *releaseDate;
    char *compradores;
    char *preco;
    char *supportedLanguages;
    char *score;
    char *userScore;
    char *nConquistas;
    char *publishers;
    char *developers;
    char *categorias;
    char *generos;
    char *tags;
} Tp4;

// Declaração antecipada da função freeTp4
void freeTp4(Tp4 *jogo);

// Aloca e copia uma string com verificação de NULL
char* str_alloc(const char* s) {
    if (!s) return NULL;
    char *p = malloc(strlen(s) + 1);
    if (p) {
        strcpy(p, s);
    } else {
        fprintf(stderr, "Erro: Falha na alocação de memória para string\n");
    }
    return p;
}

// Remove aspas duplas ("), colchetes ([ e ]), e caracteres de nova linha/retorno de carro
void limpar(char* campo) {
    if (!campo) return;
    char *src = campo, *dst = campo;
    while (*src) {
        if (*src != '"' && *src != '[' && *src != ']' && *src != '\r' && *src != '\n') {
            *dst++ = *src;
        }
        src++;
    }
    *dst = '\0';
}

// Formata listas com alocação dinâmica segura (recebe ponteiro para ponteiro)
void formatList(char** campo_ptr) {
    if (!campo_ptr || !(*campo_ptr) || strlen(*campo_ptr) == 0) return;

    char *campo = *campo_ptr;

    // Cria uma cópia temporária para o strtok e limpeza
    char *temp = str_alloc(campo);
    if (!temp) return;
    limpar(temp);

    // Aloca dinamicamente a string de resultado com calloc (inicializa com zeros)
    char *resultado = (char*)calloc(MAX_LIST_LENGTH, 1);
    if (!resultado) {
        fprintf(stderr, "Erro: Falha na alocação de memória para formatList\n");
        free(temp);
        return;
    }

    char *token = strtok(temp, ",");
    int primeiro = 1;

    // Reconstrói a string
    while (token) {
        // Remove espaços em branco do início do token
        while (*token && *token == ' ') token++;

        // Verifica se há espaço suficiente no buffer resultado
        size_t espaco_necessario = strlen(resultado) + strlen(token) + (primeiro ? 1 : 3);
        if (espaco_necessario >= MAX_LIST_LENGTH) {
            fprintf(stderr, "Aviso: Lista muito longa, truncando...\n");
            break;
        }

        if (!primeiro) strcat(resultado, ", ");
        strcat(resultado, token);
        primeiro = 0;

        token = strtok(NULL, ",");
    }

    // Libera o campo antigo e substitui pelo novo
    free(*campo_ptr);
    *campo_ptr = resultado;

    free(temp);
}

// Formata a data de "Mmm dd, yyyy" para "dd/mm/yyyy"
void formatDate(const char* input, char* output, size_t output_size) {
    const char* meses[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    int mes = 0, dia = 0, ano = 0;
    char mesStr[4];

    // Tenta ler o formato "Mmm dd, yyyy"
    if (sscanf(input, "%3s %d, %d", mesStr, &dia, &ano) == 3) {
        for (mes = 0; mes < 12; mes++) {
            if (strcmp(mesStr, meses[mes]) == 0) break;
        }
        if (mes < 12) {
            snprintf(output, output_size, "%02d/%02d/%04d", dia, mes+1, ano);
        } else {
            strncpy(output, input, output_size - 1);
            output[output_size - 1] = '\0';
        }
    } else {
        strncpy(output, input, output_size - 1);
        output[output_size - 1] = '\0';
    }
    limpar(output);
}

// Função de parsing CSV robusta que lida com vírgulas dentro de aspas
int parseCSV(char *linha, char *campos[], int max_campos) {
    int i = 0;
    char *ptr = linha;
    char *inicio = ptr;
    int aspas = 0;

    // Remove nova linha/retorno de carro da linha
    linha[strcspn(linha, "\n\r")] = '\0';

    while (*ptr && i < max_campos) {
        if (*ptr == '"') {
            aspas = !aspas;
        } else if (*ptr == ',' && !aspas) {
            *ptr = '\0';
            campos[i++] = inicio;
            inicio = ptr + 1;
        }
        ptr++;
    }
    // O último campo
    if (i < max_campos) {
        campos[i++] = inicio;
    }
    return i;
}

// Libera memória de uma estrutura Tp4
void freeTp4(Tp4 *jogo) {
    if (!jogo) return;

    free(jogo->appID);
    free(jogo->name);
    free(jogo->releaseDate);
    free(jogo->compradores);
    free(jogo->preco);
    free(jogo->supportedLanguages);
    free(jogo->score);
    free(jogo->userScore);
    free(jogo->nConquistas);
    free(jogo->publishers);
    free(jogo->developers);
    free(jogo->categorias);
    free(jogo->generos);
    free(jogo->tags);
    free(jogo);
}

// Aloca e inicializa uma estrutura Tp4 com validação
Tp4* createTp4(char *campos[]) {
    Tp4 *jogo = (Tp4*)malloc(sizeof(Tp4));
    if (!jogo) {
        fprintf(stderr, "Erro: Falha na alocação de memória para Tp4\n");
        return NULL;
    }

    // Aloca e copia todos os campos com verificação
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

    // Verifica se alguma alocação falhou
    if (!jogo->appID || !jogo->name || !jogo->releaseDate || !jogo->compradores ||
        !jogo->preco || !jogo->supportedLanguages || !jogo->score || !jogo->userScore ||
        !jogo->nConquistas || !jogo->publishers || !jogo->developers || !jogo->categorias ||
        !jogo->generos || !jogo->tags) {
        freeTp4(jogo);
        return NULL;
    }

    return jogo;
}

void printTp4(Tp4 *jogo) {
    if (!jogo) return;

    char dataFormatada[20] = "";
    formatDate(jogo->releaseDate, dataFormatada, sizeof(dataFormatada));

    // Limpeza de campos simples
    limpar(jogo->name);
    limpar(jogo->compradores);
    limpar(jogo->preco);
    limpar(jogo->score);
    limpar(jogo->userScore);
    limpar(jogo->nConquistas);

    // Formatação de campos de lista (passando ponteiro para ponteiro)
    formatList(&jogo->supportedLanguages);
    formatList(&jogo->publishers);
    formatList(&jogo->developers);
    formatList(&jogo->categorias);
    formatList(&jogo->generos);
    formatList(&jogo->tags);

    printf("=> %s ## %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## [%s] ## [%s] ## [%s] ## [%s] ## [%s]\n",
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

// Função auxiliar para buscar um jogo por ID no arquivo
Tp4* buscarJogoPorID(FILE *fp, const char *idBusca) {
    if (!fp || !idBusca) return NULL;

    char linha[MAX_LINE_LENGTH];

    // Reseta o arquivo para o início
    rewind(fp);

    // Pula o cabeçalho
    if (!fgets(linha, sizeof(linha), fp)) {
        return NULL;
    }

    // Busca linha por linha
    while (fgets(linha, sizeof(linha), fp)) {
        char linhaCopia[MAX_LINE_LENGTH];
        strncpy(linhaCopia, linha, sizeof(linhaCopia) - 1);
        linhaCopia[sizeof(linhaCopia) - 1] = '\0';

        char *campos[14];
        int num_campos = parseCSV(linhaCopia, campos, 14);

        // Verifica se a linha tem todos os campos necessários
        if (num_campos < 14) continue;

        // Verifica o appID (primeiro campo)
        if (strcmp(campos[0], idBusca) == 0) {
            return createTp4(campos);
        }
    }

    return NULL;
}

int main() {
    const char *path = "/tmp/games.csv";
    FILE *fp = fopen(path, "r");

    if (!fp) {
        fprintf(stderr, "Erro: Arquivo '%s' não encontrado.\n", path);
        return 1;
    }

    char idBusca[128];

    // Loop de leitura de IDs do stdin
    while (fgets(idBusca, sizeof(idBusca), stdin)) {
        // Remove newline/carriage return
        idBusca[strcspn(idBusca, "\n\r")] = '\0';

        // Verifica condição de parada
        if (strcmp(idBusca, "Fim") == 0) break;

        // Busca o jogo no arquivo
        Tp4 *jogo = buscarJogoPorID(fp, idBusca);

        if (jogo) {
            printTp4(jogo);
            freeTp4(jogo);
        }
    }

    fclose(fp);
    return 0;
}
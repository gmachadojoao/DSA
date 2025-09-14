#include <stdio.h>

typedef struct {
    char idioma[50];
    char saudacao[100];
} Traducao;

typedef struct {
    char nome[100];
    char idioma[50];
} Crianca;

int main() {
    int N, M;
    scanf("%d", &N);
    getchar(); // consumir o '\n' após o número

    Traducao traducoes[N];
    for (int i = 0; i < N; i++) {
        fgets(traducoes[i].idioma, 50, stdin);
        traducoes[i].idioma[strcspn(traducoes[i].idioma, "\n")] = 0; // remover '\n'
        fgets(traducoes[i].saudacao, 100, stdin);
        traducoes[i].saudacao[strcspn(traducoes[i].saudacao, "\n")] = 0; // remover '\n'
    }

    scanf("%d", &M);
    getchar(); // consumir o '\n'

    Crianca criancas[M];
    for (int i = 0; i < M; i++) {
        fgets(criancas[i].nome, 100, stdin);
        criancas[i].nome[strcspn(criancas[i].nome, "\n")] = 0; // remover '\n'
        fgets(criancas[i].idioma, 50, stdin);
        criancas[i].idioma[strcspn(criancas[i].idioma, "\n")] = 0; // remover '\n'
    }

    // Imprimir etiquetas
    for (int i = 0; i < M; i++) {
        char *saudacao = NULL;

        // Procurar a tradução correta
        for (int j = 0; j < N; j++) {
            if (strcmp(criancas[i].idioma, traducoes[j].idioma) == 0) {
                saudacao = traducoes[j].saudacao;
                break;
            }
        }

        // Imprimir etiqueta
        printf("%s\n", criancas[i].nome);
        printf("%s\n\n", saudacao); // linha em branco após a etiqueta
    }

    return 0;
}

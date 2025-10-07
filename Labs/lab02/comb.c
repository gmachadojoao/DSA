#include <stdio.h>
#include <string.h>

void combinar(char s1[], char s2[], char resultado[]) {
    int i = 0, j = 0, k = 0;
    int tamanho1 = strlen(s1);
    int tamanho2 = strlen(s2);
    int tamanhoMax = tamanho1 > tamanho2 ? tamanho1 : tamanho2;

    for (i = 0; i < tamanhoMax; i++) {
        if (i < tamanho1) {
            resultado[k++] = s1[i];
        }
        if (i < tamanho2) {
            resultado[k++] = s2[i];
        }
    }
    resultado[k] = '\0'; // Finaliza a string
}

int main() {
    char s1[100], s2[100], resultado[200];

    fgets(s1, sizeof(s1), stdin);
    s1[strcspn(s1, "\n")] = 0; // Remove o '\n' do fgets

    fgets(s2, sizeof(s2), stdin);
    s2[strcspn(s2, "\n")] = 0; // Remove o '\n' do fgets

    combinar(s1, s2, resultado);

    printf(" %s\n", resultado);

    return 0;
}

#include <stdio.h>
#include <string.h>

int ehFim(const char *str) {
    // Comparação direta de caracteres
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0');
}

int contaMaiuscula(const char *str) {
    int conta = 0;
    for (int i = 0; str[i] != '\0'; i++) {
        if (str[i] >= 'A' && str[i] <= 'Z') {
            conta++;
        }
    }
    return conta;
}

int main(void) {
    char str[100];

    while (scanf(" %[^\n]", str) == 1) {
        if (ehFim(str)) {
            printf("FIM\n");
            break;
        }
        printf("%d\n", contaMaiuscula(str));
    }

    return 0;
}
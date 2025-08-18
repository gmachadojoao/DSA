#include <stdio.h>
#include <string.h>

int ehFim(const char *str) {
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0');
}

int contaMaiuscula(char *str) {
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

    while (scanf(" %[^\n]", str)) {
        if (ehFim(str)) {
            printf("FIM\n");
        }
        else
            printf("%d\n", contaMaiuscula(str));
    }

    return 0;
}

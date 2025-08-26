#include <stdio.h>

int theEnd(char str[], int len) {
    return (len == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M');
}

int ehpalin(char str[], int len) {
    int esq = 0;
    int dir = len - 1;

    while (esq < dir) {
        if (str[esq] != str[dir]) {
            return 0; 
        }
        esq++;
        dir--;
    }
    return 1;
}

int main() {
    char palavra[100];
    int len;

    fgets(palavra, sizeof(palavra), stdin);

    len = 0;
    while (palavra[len] != '\0') {
        len++;
    }

    if (len > 0 && palavra[len - 1] == '\n') {
        palavra[len - 1] = '\0';
        len--;
    }

    while (!theEnd(palavra, len)) {

        if (ehpalin(palavra, len)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }

        fgets(palavra, sizeof(palavra), stdin);

        len = 0;
        while (palavra[len] != '\0') {
            len++;
        }

        if (len > 0 && palavra[len - 1] == '\n') {
            palavra[len - 1] = '\0';
            len--;
        }
    }

    return 0;
}

#include <stdio.h>

int theEnd(char str[], int len) {
    return (len == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M');
}

int ehpalinRec(char str[], int esq, int dir) {
    if (esq >= dir) {
        return 1;
    }
    if (str[esq] != str[dir]) {
        return 0;
    }
    return ehpalinRec(str, esq + 1, dir - 1);
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

        if (ehpalinRec(palavra, 0, len - 1)) {
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

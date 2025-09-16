#include <stdio.h>
#include <string.h>

// Função recursiva que soma os dígitos de uma string representando um número
int somaDigitos(char str[], int index) {
    if (str[index] == '\0')
        return 0;
    return (str[index] - '0') + somaDigitos(str, index + 1);
}

// Função para verificar se a entrada é "FIM"
int theEnd(char str[]) {
    return (strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M');
}

int main() {
    char input[100];

    if (fgets(input, sizeof(input), stdin) == NULL)
        return 0;

    size_t len = strlen(input);
    if (len > 0 && input[len - 1] == '\n')
        input[len - 1] = '\0';

    while (!theEnd(input)) {
        int somaDigitosTotal = 0;

        for (int i = 0; i < strlen(input); i++) {
            if (input[i] >= '0' && input[i] <= '9') {
                somaDigitosTotal += input[i] - '0';
            }
        }

        printf("%d\n", somaDigitosTotal);

        if (fgets(input, sizeof(input), stdin) == NULL)
            break;

        len = strlen(input);
        if (len > 0 && input[len - 1] == '\n')
            input[len - 1] = '\0';
    }

    return 0;
}

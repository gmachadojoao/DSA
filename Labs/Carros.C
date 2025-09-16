#include <stdio.h>
#include <string.h>

// Verifica se é padrão Brasileiro
int ehBrasileira(char *placa) {
    if (strlen(placa) != 8) {
        return 0;
    }

    // três primeiras letras maiúsculas
    for (int i = 0; i < 3; i++) {
        if (!(placa[i] >= 'A' && placa[i] <= 'Z')) {
            return 0;
        }
    }

    // quarto caractere deve ser '-'
    if (placa[3] != '-') {
        return 0;
    }

    // últimos quatro dígitos
    for (int i = 4; i < 8; i++) {
        if (!(placa[i] >= '0' && placa[i] <= '9')) {
            return 0;
        }
    }

    return 1;
}

// Verifica se é padrão Mercosul
int ehMercosul(char *placa) {
    if (strlen(placa) != 7) {
        return 0;
    }

    // três primeiras letras maiúsculas
    for (int i = 0; i < 3; i++) {
        if (!(placa[i] >= 'A' && placa[i] <= 'Z')) {
            return 0;
        }
    }

    // quarto caractere deve ser dígito
    if (!(placa[3] >= '0' && placa[3] <= '9')) {
        return 0;
    }

    // quinto caractere deve ser letra maiúscula
    if (!(placa[4] >= 'A' && placa[4] <= 'Z')) {
        return 0;
    }

    // últimos dois devem ser dígitos
    if (!(placa[5] >= '0' && placa[5] <= '9')) {
        return 0;
    }
    if (!(placa[6] >= '0' && placa[6] <= '9')) {
        return 0;
    }

    return 1;
}

int main() {
    char carro[20];
    scanf("%s", carro);

    if (ehBrasileira(carro)) {
        printf("1\n");
    } else {
        if (ehMercosul(carro)) {
            printf("2\n");
        } else {
            printf("0\n");
        }
    }

    return 0;
}

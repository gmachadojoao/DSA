#include <stdio.h>

int ehVogal(char c) {
    return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
            c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U');
}

int ehLetra(char c) {
    return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
}

int somenteVogais(char str[], int i) {
    if (str[i] == '\0') return 1;
    if (!ehVogal(str[i])) return 0;
    return somenteVogais(str, i + 1);
}

int somenteConsoantes(char str[], int i) {
    if (str[i] == '\0') return 1;
    if (!(ehLetra(str[i]) && !ehVogal(str[i]))) return 0;
    return somenteConsoantes(str, i + 1);
}

int numeroInteiro(char str[], int i) {
    if (str[0] == '\0') return 0;
    if (i == 0 && (str[i] == '-' || str[i] == '+'))
        return numeroInteiro(str, i + 1);
    if (str[i] == '\0') return (i > ((str[0] == '-' || str[0] == '+') ? 1 : 0));
    if (!(str[i] >= '0' && str[i] <= '9')) return 0;
    return numeroInteiro(str, i + 1);
}

int numeroReal(char str[], int i, int ponto, int digito) {
    if (str[0] == '\0') return 0;
    if (i == 0 && (str[i] == '-' || str[i] == '+'))
        return numeroReal(str, i + 1, ponto, digito);

    if (str[i] == '\0') return digito;

    if (str[i] == '.' || str[i] == ',') {
        if (ponto) return 0;
        return numeroReal(str, i + 1, 1, digito);
    } else if (str[i] >= '0' && str[i] <= '9') {
        return numeroReal(str, i + 1, ponto, 1);
    } else {
        return 0;
    }
}

int tamanho(char str[], int i) {
    if (str[i] == '\0') return i;
    return tamanho(str, i + 1);
}

int main() {
    char str[100];

    while (fgets(str, sizeof(str), stdin) != NULL) {
        int len = tamanho(str, 0);
        if (len > 0 && str[len - 1] == '\n') str[len - 1] = '\0';

        printf("%s %s %s %s\n",
               somenteVogais(str, 0) ? "SIM" : "NAO",
               somenteConsoantes(str, 0) ? "SIM" : "NAO",
               numeroInteiro(str, 0) ? "SIM" : "NAO",
               numeroReal(str, 0, 0, 0) ? "SIM" : "NAO");
    }

    return 0;
}
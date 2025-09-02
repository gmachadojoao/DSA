#include <stdio.h>

// Verifica se um caractere é uma vogal
int ehVogal(char c) {
    return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
            c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U');
}

// Verifica se um caractere é uma letra
int ehLetra(char c) {
    return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
}

// Verifica se todos os caracteres da string são vogais
int somenteVogais(char str[]) {
    for (int i = 0; str[i] != '\0'; i++) {
        if (!ehVogal(str[i]))
            return 0; // NÃO
    }
    return 1; // SIM
}

// Verifica se todos os caracteres da string são consoantes
int somenteConsoantes(char str[]) {
    for (int i = 0; str[i] != '\0'; i++) {
        if (ehLetra(str[i]) || !ehVogal(str[i]))
            return 0; // NÃO
    }
    return 1; // SIM
}

// Verifica se a string representa um número inteiro
int numeroInteiro(char str[]) {
    if (str[0] == '\0') return 0;
    int i = (str[0] == '-' || str[0] == '+') ? 1 : 0;
    for (; str[i] != '\0'; i++)
        if (!(str[i] >= '0' && str[i] <= '9'))
            return 0; // NÃO
    return (i > ((str[0] == '-' || str[0] == '+') ? 1 : 0)) ? 1 : 0;
}

// Verifica se a string representa um número real
int numeroReal(char str[]) {
    if (str[0] == '\0') return 0;
    int i = (str[0] == '-' || str[0] == '+') ? 1 : 0;
    int ponto = 0;
    int digito = 0;

    for (; str[i] != '\0'; i++) {
        if (str[i] == '.' || str[i] == ',') {
            if (ponto) return 0; // MAIS DE UM PONTO
            ponto = 1;
        } else if (str[i] >= '0' && str[i] <= '9') {
            digito = 1;
        } else {
            return 0; // NÃO
        }
    }
    return digito; // Deve ter pelo menos um dígito
}

// Função para obter o tamanho da string
int tamanho(char str[]) {
    int i = 0;
    while (str[i] != '\0'){
		i++;
	}
    return i;
}

int main() {
    char str[100];

    while (fgets(str, sizeof(str), stdin) != NULL) {
        // Remove o '\n' do final da string
        int len = tamanho(str);
        if (len > 0 && str[len - 1] == '\n') str[len - 1] = '\0';

        printf("%s %s %s %s\n",
               somenteVogais(str) ? "SIM" : "NÃO",
               somenteConsoantes(str) ? "SIM" : "NÃO",
               numeroInteiro(str) ? "SIM" : "NÃO",
               numeroReal(str) ? "SIM" : "NÃO");
    }

    return 0;
}

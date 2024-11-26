/*
Elabore um programa que preencha uma matriz M de ordem 4  6
e uma segunda matriz N de ordem 6  4,
calcule e imprima a soma das linhas de M com as colunas de N.
*/

#include <stdio.h>

int main()
{
    int M[4][6];        // Matriz M de ordem 4x6
    int N[6][4];        // Matriz N de ordem 6x4
    int soma[4][4] = 0; // Matriz para armazenar a soma das linhas de M com as colunas de N

    // Entrada da matriz M
    printf("Digite os elementos da matriz M (4x6):\n");
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 6; j++)
        {

            scanf("%d", &M[i][j]);
        }
    }

    // Entrada da matriz N
    printf("\nDigite os elementos da matriz N (6x4):\n");
    for (int i = 0; i < 6; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            scanf("%d", &N[i][j]);
        }
    }

    // Calculando a soma das linhas de M com as colunas de N
    for (int i = 0; i < 4; i++)
    { // Percorre as linhas de M
        for (int j = 0; j < 4; j++)
        { // Percorre as colunas de N
            for (int k = 0; k < 6; k++)
            { // Percorre os elementos da linha de M e coluna de N
                soma[i][j] += M[i][k] + N[k][j];
            }
        }
    }

    // Exibindo o resultado
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            printf("%d ", soma[i][j]);
        }
    }

    return 0;
}

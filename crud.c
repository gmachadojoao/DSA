#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void LeExibeArquivo ()
{
    FILE  *arquivo = fopen("dadosSalvos.txt","r");
    int num;

    while(fscanf(arquivo,"%d",&num) != EOF)
    {
        printf("%d ",num);
    }

    fclose(arquivo);
}

int contaNumeros()
{
    FILE *ponteiro = fopen ("dadosSalvos.txt","r");
    int N = 0, num;

    while (fscanf(ponteiro,"%d",&num) != EOF)
    {
        N++;
    }

    fclose(ponteiro);

    return N;
}

void CriaArquivo ()
{
    FILE *arq = fopen("dadosSalvos.txt","w");
    int total, num;

    srand((unsigned)time(NULL));
    total = rand()%101; // quantidade de números

    for (int i=1; i <= total; i++)
    {
        num = rand()%1000; // 0 a 999
        fprintf(arq,"%d ",num);
    }

    fclose(arq);
}

void InsereFinal ()
{
    FILE *arq = fopen("dadosSalvos.txt","a");
    int total, num;

    srand((unsigned)time(NULL));
    total = rand()%101; // quantidade de números
                        // 0 a 100 número

    for (int i=1; i <= total; i++)
    {
        num = rand()%1000; // 0 a 999
        fprintf(arq,"%d ",num);
    }

    fclose(arq);
}

void EditaArquivo (int pos, int novoValor)
{
    FILE *temp = fopen ("temp.txt","w");
    FILE *arq = fopen("dadosSalvos.txt","r");
    int num;

    for (int i=1; i < pos; i++)
    {
        fscanf(arq,"%d",&num);
        fprintf(temp,"%d ",num);
    }

    fscanf(arq,"%d",&num);
    fprintf(temp,"%d ",novoValor);

    while (fscanf(arq,"%d",&num) != EOF)
    {
        fprintf(temp,"%d ",num);
    }

    fclose(temp);
    fclose(arq);

    temp = fopen("temp.txt","r");
    arq = fopen("dadosSalvos.txt","w");

    while (fscanf(temp,"%d",&num) != EOF)
    {
        fprintf(arq,"%d ",num);
    }

    fclose(temp);
    fclose(arq);

    system("del temp.txt");
}

void InsereMeio (int pos, int val)
{
    FILE *temp = fopen ("temp.txt","w");
    FILE *arq = fopen("dadosSalvos.txt","r");
    int num;

    for (int i=1; i < pos; i++)
    {
        fscanf(arq,"%d",&num);
        fprintf(temp,"%d ",num);
    }

    fprintf(temp,"%d ",val);

    while (fscanf(arq,"%d",&num) != EOF)
    {
        fprintf(temp,"%d ",num);
    }

    fclose(temp);
    fclose(arq);

    temp = fopen("temp.txt","r");
    arq = fopen("dadosSalvos.txt","w");

    while (fscanf(temp,"%d",&num) != EOF)
    {
        fprintf(arq,"%d ",num);
    }

    fclose(temp);
    fclose(arq);

    system("del temp.txt");
}

int menu()
{
    int op;

    printf("Escolha uma das opcoes\n");
    printf("1 - Criar novo arquivo (w)\n");
    printf("2 - Inserir valores no final (a)\n");
    printf("3 - Substituir valor em determinada posicao\n");
    printf("4 - Excluir valor do arquivos (todas as ocorrencias)\n");
    printf("5 - Inserir valor em determinada posicao\n");
    printf("6 - Exibir todo o arquivo\n");
    printf("7 - Sair\n");
    printf("Opcao: ");
    scanf("%d",&op);

    return op;
}

void ExcluirArquivo (int val)
{
    FILE *arq = fopen ("dadosSalvos.txt","r");
    FILE *temp = fopen ("temp.txt","w");
    int num;

    while (fscanf(arq,"%d",&num) != EOF)
    {
        if (num != val)
            fprintf(temp,"%d ",num);
    }

    fclose(arq);
    fclose(temp);

    arq = fopen("dadosSalvos.txt","w");
    temp = fopen("temp.txt","r");

    while (fscanf(temp,"%d",&num) != EOF)
    {
        fprintf(arq,"%d ",num);
    }

    fclose(arq);
    fclose(temp);

    system("del temp.txt");
}

int main()
{
    int opcao = menu(), pos, novo, total, valor;

    while (opcao != 7)
    {
        switch(opcao)
        {
            case 1: CriaArquivo();
                break;
            case 2: InsereFinal();
                break;
            case 3:
                total = contaNumeros();
                printf("Qual posicao sera substituida (1 a %d)? ",total);
                scanf("%d",&pos);
                printf("Qual eh o novo valor? ");
                scanf("%d",&novo);
                EditaArquivo(pos,novo);
                break;
            case 4:
                printf("Qual valor quer excluir? ");
                scanf("%d",&valor);
                ExcluirArquivo(valor);
                break;
            case 5:
                total = contaNumeros();
                printf("Qual posicao deseja inserir (1 a %d)? ",total);
                scanf("%d",&pos);
                printf("Qual eh o valor? ");
                scanf("%d",&valor);
                InsereMeio(pos,valor);
                break;
            case 6:
                LeExibeArquivo();
                break;
            case 7:
                printf("Fim do programa\n");
                break;
            default: printf("Opcao invalida\n");
                break;
        }
        printf("\n");
        opcao = menu();
    }

    return 0;
}

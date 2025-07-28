/*
Codigo para estudo de Struct na linguagem C
Projeto interativo para criação de Personagem de um RPG com Classes pré-determinadas
*/

#include <stdio.h>
#include <string.h>

#define TOTAL_CLASSES 3
#define TOTAL_RACAS 3

// Struct para Raça
typedef struct {
    char nome[30];
    int forcaBonus;
    int inteligenciaBonus;
} Raca;

// Struct para Classe
typedef struct {
    char nome[30];
    int vidaBase;
    int manaBase;
    int danoBase;
} Classe;

// Struct do Personagem
typedef struct {
    char nome[50];
    Raca raca;
    Classe classe;
} Personagem;

// Lista de raças disponíveis
Raca racasDisponiveis[TOTAL_RACAS] = {
    {"Elfo", -1, 3},
    {"Anão", 3, -1},
    {"Humano", 1, 1}
};

// Lista de classes disponíveis
Classe classesDisponiveis[TOTAL_CLASSES] = {
    {"Guerreiro", 100, 30, 15},
    {"Mago", 50, 100, 25},
    {"Ladino", 80, 60, 20}
};

// Função para listar raças
void listarRacas() {
    printf("Escolha uma raça:\n");
    for (int i = 0; i < TOTAL_RACAS; i++) {
        printf("%d. %s (Força %+d, Inteligência %+d)\n",
               i + 1,
               racasDisponiveis[i].nome,
               racasDisponiveis[i].forcaBonus,
               racasDisponiveis[i].inteligenciaBonus);
    }
}

// Função para listar classes
void listarClasses() {
    printf("Escolha uma classe:\n");
    for (int i = 0; i < TOTAL_CLASSES; i++) {
        printf("%d. %s (Vida: %d, Mana: %d, Dano: %d)\n",
               i + 1,
               classesDisponiveis[i].nome,
               classesDisponiveis[i].vidaBase,
               classesDisponiveis[i].manaBase,
               classesDisponiveis[i].danoBase);
    }
}

int main() {
    Personagem p1;
    int escolha;

    // Nome do personagem
    printf("Digite o nome do seu personagem: ");
    fgets(p1.nome, sizeof(p1.nome), stdin);
    p1.nome[strcspn(p1.nome, "\n")] = 0;

    // Escolha de raça
    listarRacas();
    printf("Digite o número da raça: ");
    scanf("%d", &escolha);
    if (escolha < 1 || escolha > TOTAL_RACAS) {
        printf("Raça inválida!\n");
        return 1;
    }
    p1.raca = racasDisponiveis[escolha - 1];

    // Escolha de classe
    listarClasses();
    printf("Digite o número da classe: ");
    scanf("%d", &escolha);
    if (escolha < 1 || escolha > TOTAL_CLASSES) {
        printf("Classe inválida!\n");
        return 1;
    }
    p1.classe = classesDisponiveis[escolha - 1];

    // Exibir personagem criado
    printf("\n--- Personagem Criado ---\n");
    printf("Nome: %s\n", p1.nome);
    printf("Raça: %s (Força %+d, Inteligência %+d)\n",
           p1.raca.nome, p1.raca.forcaBonus, p1.raca.inteligenciaBonus);
    printf("Classe: %s (Vida: %d, Mana: %d, Dano: %d)\n",
           p1.classe.nome,
           p1.classe.vidaBase,
           p1.classe.manaBase,
           p1.classe.danoBase);

    return 0;
}

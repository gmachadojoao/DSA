import java.util.Scanner;

class OrdenaNota {
    private static class Aluno {
        String nome;
        double nota;

        public Aluno(String nome, double nota) {
            this.nome = nome;
            this.nota = nota;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o nome do aluno: ");
        String nome = sc.nextLine();  // lê o nome

        System.out.print("Digite a nota do aluno: ");
        double nota = sc.nextDouble();  // lê a nota

        Aluno aluno1 = new Aluno(nome, nota);

        System.out.println("Aluno: " + aluno1.nome + " - Nota: " + aluno1.nota);

        sc.close();
    }
}

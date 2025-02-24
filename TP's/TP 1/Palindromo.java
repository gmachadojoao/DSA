import java.util.Scanner;

class Q1 {
	public static boolean theEnd(String str) {

		if (str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M') {
			return true;
		} else {
			return false;
		}
	}

	public static boolean ehpalin(String str) {
		int esq = 0;
		int dir = str.length() - 1;
		while (esq < dir) {
			if (str.charAt(esq) != str.charAt(dir)) {
				return false;
			}
			esq++;
			dir--;
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String palavra;

		palavra = "";

		while (!theEnd(palavra)) {

			palavra = sc.nextLine();
			if (ehpalin(palavra)) {
				System.out.println("SIM");
			} else {
				System.out.println("NAO");
			}

		}
		sc.close();
	}
}

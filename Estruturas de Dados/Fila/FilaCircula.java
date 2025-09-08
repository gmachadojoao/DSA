class FilaCircula {
   private int[] array;
   private int primeiro;
   private int ultimo;

   public FilaCircula () {
      this(6);
   }

   public FilaCircula (int tamanho){
      array = new int[tamanho+1];
      primeiro = ultimo = 0;
   }

   public void inserir(int x) throws Exception {
      if (((ultimo + 1) % array.length) == primeiro) { // false: 0 + 1 % 6 == 0
         throw new Exception("Erro ao inserir!");
      }
      array[ultimo] = x;
      ultimo = (ultimo + 1) % array.length; // +1 na posição do Ultimo
   }

   public int remover() throws Exception {
      if (primeiro == ultimo) {
         throw new Exception("Erro ao remover!");
      }
      int resp = array[primeiro];
      primeiro = (primeiro + 1) % array.length;
      return resp;
   }

   public void mostrar (){
      System.out.print("[ ");
      int i = primeiro;
      while(i!=ultimo){
         System.out.print(array[i] + " ");
         i = (i +1 ) % array.length;
      }
      System.out.println("]");
   }

   public void mostrarRec(){
      System.out.print("[ ");
      mostrar();
      System.out.println("]");
   }

   // public void mostrarRec(int i){
   //    if(i != ultimo){
   //       System.out.print(array[i] + " ");
   //       mostrarRec((i + 1) % array.length);
   //    }
   // }

   public boolean isVazia() {
      if(primeiro == ultimo){
      System.out.println("Fila vazia"); 
         return true;
   }
   return false;
   }

   public void ordenaFila(){
         for (int i = 0; i < array.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[menor] > array[j]) {
                    menor = j;
                }
            }
            // troca
            int temp = array[i];
            array[i] = array[menor];
            array[menor] = temp;
        }

        // Imprime vetor ordenado
        System.out.println("Fila ordenada:");
        System.out.print("[ ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print("]");
   }
}

class PrincipalFila {
   public static void main(String[] args) throws Exception {
      System.out.println("==== FILA ESTATICA ====");
      FilaCircula fila = new FilaCircula();
      int x1, x2, x3;

      fila.isVazia();

      fila.inserir(5);
      fila.inserir(7);
      fila.inserir(8);
      fila.inserir(9);

      System.out.println("Apos insercoes(5, 7, 8, 9): ");
      fila.mostrar();

      x1 = fila.remover();
      x2 = fila.remover();

      System.out.println("Apos remocoes (" + x1 + ", " + x2 + "):");
      fila.mostrar();

      fila.inserir(3);
      fila.inserir(4);

      System.out.println("Apos insercoes(3, 4): ");
      fila.mostrar();

      x1 = fila.remover();
      x2 = fila.remover();
      x3 = fila.remover();

      System.out.println("Apos remocoes (" + x1 + ", " + x2 + ", " + x3 + "):");
      fila.mostrar();

      fila.inserir(6);
      fila.inserir(5);

      System.out.println("Apos insercoes(6, 5): ");
      fila.mostrar();

      fila.ordenaFila();

   }
}

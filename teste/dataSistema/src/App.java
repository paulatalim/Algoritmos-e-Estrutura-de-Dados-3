import java.util.Date;
import java.text.SimpleDateFormat;

public class App {

        /*
         * Descricao: essa funcao troca dois elementos de um vetor de inteiros
         * Parametros: um vetor de inteiros (vetor que tera os
         * elementos trocados) e dois inteiros (indice dos
         * dois elementos a serem trocados)
         */
        public static void swap (int[] vet, int i, int j) {
            int aux = vet[i];
            vet[i] = vet[j];
            vet[j] = aux;
        }

        /*
         * Descricao: essa funcao calcula o indice o elemento pai no heap
         * Parametro: um inteiro (indice do elemento filho)
         * Retorno: um inteiro (indice do elemento pai)
         */
        public static int indice_pai (int filho) {
            int flag = filho;

            filho /= 2;

            if (flag %2 == 0) {
                filho --;
            }
            return filho;
        }

        /*
         * Descricao: essa funcao constroi o heap de um vetor de inteiros
         *
         * Parametros: um vetor de inteiros (vetor a ser construido
         * a arvore heap) e um inteiro (tamanho valido do vetor)
         */
        public static void construir (int[] vet, int[]chave, int tam) {
            int indice = indice_pai(tam);
            int i = tam;
            boolean condicao1 = chave[i] < chave[indice];
            boolean condicao2 = chave[i] == chave[indice] && vet[i] < vet[indice];
            boolean condicao3 = vet[i] < vet[indice];

            System.out.println (condicao1 + ", " + condicao2);

            while (i > 0 && (chave[i] < chave[indice] || chave[i] == chave[indice] && vet[i] < vet[indice])) {
                swap(vet, i, indice);
                swap(chave, i, indice);

                i = indice_pai(i);

                if (indice % 2 == 0) {
                    indice = indice_pai(indice);
                } else {
                    indice /= 2;
                }
            }
        }

        /*
         * Descricao: essa funcao ordena um vetor com numeros
         * do tipo inteiro em ordem crescente com o metodo HeapSort
         *
         * Parametro: um vetor de inteiros (vetor a ser ordenado)
         */
        public static void heapsort (int[] vet, int[]chave) {
            int tam;

            //Construcao do heap
            for (tam = 1; tam < vet.length; tam++) {
                construir(vet, chave, tam);
            }
        }


        /*
         * Descricao: essa funcao exibe os numeros de um vetor de inteiros
         * Parametro: um vetor de inteiros (vetor a ser exibido)
         */
        public static void exibirVetor (int[] vet) {
            //Inicio da sequencia
            System.out.print ("{");

            //Exibe numeros no vetor
            for (int i = 0; i < vet.length-1; i ++) {
                System.out.print (vet[i] + ", ");
            }

            //Final da sequencia
            System.out.println (vet[vet.length-1] + "}");
        }


    public static void main(String[] args) throws Exception {

        int op = 18%4;
        System.out.println(op);
        // int[] vet = new int[] { 5,   2};
        // int[] ve1 = new int[] { 2,   2};

        // heapsort(vet, ve1);

        // exibirVetor(vet);
        /*
        //marca a data de hj
        Date hoje = new Date();

        long horario = System.currentTimeMillis();

        //transforma a data de millisgundos para a data atual
        Date d2 = new Date(horario);

        //System.out.println(hoje);
        System.out.println(d2);


        //Formata a data
        SimpleDateFormat formatar_data = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        String data_formatada = formatar_data.format(d2);
        System.out.println(data_formatada);

         */
    }
}

import java.io.*;

public class lista4 {
	
	/*
	 * Descricao: essa funcao reinicializa o vetor, misturando-o
	 * Parametro: vetor a ser misturado
	 * */
	public static void reiniciar_vetor (int[] vet) {
		vet [0] = 2;
		vet [1] = 10;
		vet [2] = 25;
		vet [3] = 12;
		vet [4] = 1;
		vet [5] = 10;
		vet [6] = 8;
		vet [7] = 95;
		vet [8] = 42;
		vet [9] = 13;
	}
	
	/*
	 * Descricao: essa funcao exibe os termos de um vetor de inteiros
	 * Parametros: um vetor de inteiros (vetor a ser exibido)
	 * */
	public static void exibir_vetor (int[]vet) {
		System.out.print("{" + vet[0]);
		
		for (int i = 1; i < vet.length; i ++) {
			System.out.print(", " + vet[i]);
		}
		
		System.out.println("}");
	}

	public static void main(String[] args) throws IOException {
		/* arq é um ponteiro que aponta para o endereço do arquivo */
        File arq = new File("C:\\MeusDocumentos\\paula\\PUC\\AED2\\java\\lista\\lista4\\lista4\\src\\bovespaagosto2022.dat");
		
		/* arq2 é um ponteiro que aponta para um objeto 
		que lê o arquivo caracter a caracter */		
        FileReader arq2 = new FileReader(arq);
		
		/* leitor é um ponteiro que aponta para um objeto 
		que lê o arquivo via armazenamento de caracteres 
		em um buffer. Iremos usar o método readLine, 
		implementado na classe BufferedReader para ler 
		um stream de caracteres */
        BufferedReader leitor = new BufferedReader(arq2);

		/* O arquivo contém um float por linha portanto
		deve se usar a linha abaixo para ler cada linha. 
		Use o método Float.parseFloat(linha) para converter
		a String lida em float.		
		*/
		String linha = leitor.readLine();
        
		/*** EXERCICIO 1 ***/
		float num, maior, menor, media;
		int total;
		
		//Inicializando variaveis
		total = 0;
		media = 0;
		maior = 0;
		menor = 0;
		
		while (linha != null) {
			//Conversao da linha lida para float
			num = Float.parseFloat(linha);
			linha = leitor.readLine();
			
			//Quando ser a primeira linha lida
			if (total == 0) {
				maior = num;
				menor = num;
			//Verifica o maior indice
			} else if (num > maior) {
				maior = num;
				
			//Verifica o menor indice
			} else if (num < menor) {
				menor = num;
			}
			
			//Soma dos indices
			media += num;
			
			//Contador dos indices
			total ++;
			
		}
		//Calculo da media dos indices
		media /= total;
		
		//Exibe o resultado das operacoes
		System.out.printf ( "Maior indice: %.02f\n", maior);
		System.out.printf("Menor indice: %.02f\n", menor);
		System.out.printf("Media dos indices: %.02f\n", media);

		arq2.close();
        leitor.close();
        
		//Reinicia objetos de leitura
        arq2 = new FileReader(arq);
        leitor = new BufferedReader(arq2);

        /*** EXERCICIO 2 ***/
        float[] vet_num_arq = new float [total];
        float aux;
        int i = 0, j;
        
        linha = leitor.readLine();
        
        //Le o arquivo
        while (linha != null) {
        	//Preenche o vetor
        	vet_num_arq[i] = Float.parseFloat(linha);
        	linha = leitor.readLine();
        	i++;
        }
        
		/* Feche os objetos de leitura. */
        arq2.close();
        leitor.close();
        
        //Ordenacao de vetor
        for (i = total-1; i > 0; i--) {
        	for (j = 0; j < i; j++) {
        		if (vet_num_arq[j] > vet_num_arq[j+1]) {
        			aux = vet_num_arq[j];
        			vet_num_arq[j] = vet_num_arq[j+1];
        			vet_num_arq[j+1] = aux;
        		}
        	}
        }
        
        //Exibe o vetor
        System.out.print("Numeros no vetor: " + vet_num_arq[0]);
        for (i = 1; i < vet_num_arq.length; i++) {
        	System.out.print(", " + vet_num_arq[i]);
        }
        System.out.println();

        
        /*** EXERCICIO 3 ***/
        int [] vet = new int[] {2, 10, 25, 12, 1, 10, 8, 95, 42, 13};
        int menor_num, maior_num, aux2;
        
        //Ordenando vetor em ordem crescente com select sort
        for (i = 0; i < vet.length - 1; i++) {
        	menor_num = i;
        	for (j = i+1; j < vet.length; j++) {
        		if (vet[menor_num] > vet[j]) {
        			menor_num = j;
        		}
        	}
        	
        	if (i != menor_num) {
        		aux2 = vet[menor_num];
        		vet[menor_num] = vet[i];
        		vet[i] = aux2;
        	}
        }
        
        System.out.print("Vetor em ordem crescente com select sort: ");
        exibir_vetor(vet);
        
        /*** EXERCICIO 4 ***/
        
        //Misturando vetor
        reiniciar_vetor (vet);
        
        //Ordenando vetor em ordem decrecente com select sort
        for (i = 0; i < vet.length - 1; i++) {
        	maior_num = i;
        	for (j = i+1; j < vet.length; j++) {
        		if (vet[maior_num] < vet[j]) {
        			maior_num = j;
        		}
        	}
        	
        	if (i != maior_num) {
        		aux2 = vet[maior_num];
        		vet[maior_num] = vet[i];
        		vet[i] = aux2;
        	}
        }
        System.out.print("Vetor em ordem decrecente com select sort: ");
        exibir_vetor(vet);
        
        /*** EXERCICIO 5 ***/
        reiniciar_vetor (vet);
        //Ordenando vetor em ordem crescente com insertion sort
        for (i = 1; i < vet.length; i++) {
        	int temp = vet[i];
        	j = i-1;
        	
        	while (j>=0 && vet[j]>temp) {
        		vet[j+1] = vet[j];
        		j--;
        	}
        	
        	vet[j+1] = temp;
        }
        
        //Exibe o resultado
        System.out.print("Vetor em ordem crescente com insertion sort: ");
        exibir_vetor(vet);
        
        //Ordenando vetor em ordem decrecente com insertion sort
        reiniciar_vetor (vet);
	    for (i = 1; i < vet.length; i++) {
	      	int temp = vet[i];
	      	j = i-1;
	      	
	      	while (j>=0 && vet[j]<temp) {
	      		vet[j+1] = vet[j];
	      		j--;
	      	}
	      	
	      	vet[j+1] = temp;
	    }
	      
		//Exibe o resultado
		System.out.print("Vetor em ordem decrecente com insertion sort: ");
		exibir_vetor(vet);
	}
}

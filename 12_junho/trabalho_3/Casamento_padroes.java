package trabalho_3;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.HBox;
import manipulacao_arquivo.Pokemon;
import trabalho_1.CRUD;

/**
 * Classe com algoritmo de casamento de 
 * padroes para a busca no documento
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class Casamento_padroes {
    static int operacao;

    /**
     * Cria uma tabela de prefixos
     * 
     * @param padrao a ser prefixado
     * @return vetor de inteiros como uma tabela de prefixos
     */
    public static int[] criarTabelaPrefixo(String padrao) {
        int[] tabelaprefixo = new int[padrao.length()];
        int i = 0;

        // Criacao de vetor, inicializacao de variavel e atribuicao do for
        operacao += 3;

        for (int j = 1; j < padrao.length(); j++) {
            
            if (padrao.charAt(i) == padrao.charAt(j)) {
                i++;
                tabelaprefixo[j] = i;

            } else if (i != 0) {
                i = tabelaprefixo[i - 1];
                j--;
                operacao ++;
            } else {
                tabelaprefixo[j] = 0;
            }

            operacao += 5;
        }

        //Retorno
        operacao++;

        return tabelaprefixo;
    }

    /**
     *  Algoritmo KMP para o casamento de padroes
     * 
     * @param texto onde sera realizado a busca
     * @param padrao a ser pesquiado
     * @return Lista de inteiros com as posicoes onde foi encontrado o padrao
     */
    private static List<Integer> kmpBusca(String texto, String padrao) {
        List<Integer> matches = new ArrayList<>();
        int[] tabelaprfixo = criarTabelaPrefixo (padrao);
        int i = 0, j = 0;

        // Criacao de lista, inicializacao de variaveis
        operacao += 3;

        while (i < texto.length()) {
            if (texto.charAt(i) == padrao.charAt(j)) {
                i++;
                j++;

                if (j == padrao.length()) {
                    matches.add(i - j);
                    j = tabelaprfixo[j - 1];
                    operacao += 2;
                }

                // Comparacao e soma nos contadores
                operacao ++;
            
            } else if (j != 0) {
                j = tabelaprfixo[j - 1];
                
            } else {
                i++;
            }

            //Comparacao while e if
            operacao += 4; 
        }

        operacao += 2; //comparacao while e retorno

        return matches;
    }
   
    /**
     * Busca um padrao em um arquivo data base ,
     * identificando a posicao em que foi achado o padrao,
     * exibe sua execusao de tempo e quantidade de operacoes
     * 
     * @param padrao a ser pesquisado
     * @param arq data base onde o padrao sera pesquisado
     * @param crud objeto de leitura do arquivo data  base
     * @throws Exception
     */
    public static void busca(String padrao, RandomAccessFile arq, CRUD crud) throws Exception {
        operacao = 0;
        Pokemon poke;
        int cont=0;

        long startTime; //Tempo de inicio
        long endTime; //Tempo final
        long tempo; //Duração em nano segundos
        
        //Leitura do ultimo id
        arq.seek(0);
        int tamanho = arq.readInt();

        operacao += 3;
        startTime = System.nanoTime(); //Demarcação do tempo de inicio

        // Percorre do primeiro ao ultimo id
        for (int id = 1; id < tamanho; id++) {
            operacao += 2; // Comparacao e acrescimo do for

            poke = new Pokemon();
            poke = crud.ler(id);
            operacao += 2; // Criacao do objeto e leitura no arquivo

            String partes[];
            partes = poke.palavras_por_partes();
            operacao += 19; // Criacao e preenchimento do vetor string

            for (int i = 0; i < partes.length; i++){
                List<Integer> matches = kmpBusca(partes[i], padrao);
                if (!matches.isEmpty()) {
                    System.out.println("ID do poke-registro: " + id);
                    System.out.println("Padrão encontrado nas posições: " + matches);
                    cont++;
                    System.out.println("\n");
                    
                    operacao += 2;
                }

                // Acrescimo da operacao for, criacao de lista e comparacao
                operacao += 4;
            }

        }
        endTime = System.nanoTime(); //Demarcação do tempo final
        tempo = endTime - startTime; //Conta duração em nano segundos

        // Exibe resultados
        float tempo_segundos = (float) tempo / 1_000_000_000; //Passando a duração para segundos
        System.out.println("\n\t" + "**************************************************" + "\n\n\n" );
        System.out.println("Em: " + tempo_segundos + " segundos");
        System.out.println("Foram encontradas: " + cont + " ocorrências desse padrão");

        

    }
}
    


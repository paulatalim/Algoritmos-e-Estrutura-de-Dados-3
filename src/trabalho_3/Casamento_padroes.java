package trabalho_3;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

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
   
    
    public static void busca(String palavra, RandomAccessFile arq, CRUD crud) throws Exception {
        operacao = 0;
        Pokemon poke;
        
        //Leitura do ultimo id
        arq.seek(0);
        int tamanho = arq.readInt();

        operacao += 3;

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
                List<Integer> matches = kmpBusca(partes[i], palavra);
                if (!matches.isEmpty()) {
                    System.out.println("Padrão encontrado nas posições: " + matches);
                    System.out.println("ID do objeto: " + id);
                    operacao += 2;
                }

                // Acrescimo da operacao for, criacao de lista e comparacao
                operacao += 4;
            }
        }

        // Exibe resultados

    }
}
    


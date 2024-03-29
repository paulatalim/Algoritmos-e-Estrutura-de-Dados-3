package trabalho_3;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import aplicacao.Tela;
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

            // Operacoes do for
            operacao += 5;
        }

        // Operacoes na funcao
        operacao += 5;

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

        // Operacoes da funcao
        operacao += 6;

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
        Boolean padrao_existe = false;

        long startTime;
        long endTime;
        
        //Leitura do ultimo id
        arq.seek(0);
        int tamanho = arq.readInt();

        Tela.limpar_console();
        Tela.print ( "\n\t\t\t\t\t\t" + "*** CASAMENTO DOS POKE PADROES ***" + "\n\n\n" 
                    + "\t" + "REGISTROS ENCONTRADOS" + "\n\n");

        //Demarcação do tempo de inicio
        startTime = System.nanoTime();
        operacao += 6;
        
        // Percorre do primeiro ao ultimo id
        for (int id = 1; id < tamanho; id++) {
            poke = new Pokemon();
            poke = crud.ler(id);

            String partes[];
            partes = poke.palavras_por_partes();

            for (int i = 0; i < partes.length; i++){
                List<Integer> matches = kmpBusca(partes[i], padrao);
                if (!matches.isEmpty()) {
                    Tela.printlt("ID do poke-registro: " + id + "\n");
                    Tela.printlt("Padrão encontrado nas posições: " + matches + "\n\n");
                    
                    padrao_existe = true;
                    operacao += 3;
                }

                // Acrescimo da operacao for, criacao de lista e comparacao
                operacao += 4;
            }

            //Operacoes realizadas dentro do for
            operacao += 24; 
        }

        //Demarcacao do tempo final
        endTime = System.nanoTime();
        operacao += 2; 

        // Caso nao achar nenhum padrao no arquivo
        if (!padrao_existe) {
            Tela.printlt("Padrao nao encontrado\n\n");
        }

        //Exibe os resultados
        Tela.println("\n\t" + "RESULTADOS:");
        Tela.printlt("Operacoes executadas: " + operacao + "\n");
        Tela.printlt("Tempo de execusao: " + (float) (endTime - startTime) / 1000000000 + " s" + "\n");
    }
}
    


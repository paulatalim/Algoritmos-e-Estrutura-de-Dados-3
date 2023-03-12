import java.io.File;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.RandomAccessFile;

public class App {

    /************************
     * MANIPULACAO DE TELAS *
     ************************/

    /*
     * Descricao: essa funcao limpa a tela do console de windows, linux e MacOS
     */
    public static void limpar_console () throws Exception {
        //Limpa a tela no windows, no linux e no MacOS
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
		    Runtime.getRuntime().exec("clear");
    }

    public static void exibir_fim_tela () throws Exception {
        System.out.print("\n\n\t" + "Pressione 'enter' para continuar");
        System.in.read();
        limpar_console();
    }

    public static void exibir_tela_inicial_e_info () throws Exception {
        //Capa do trab
        System.out.println (  "\n\t"       + "Mariana, Paula e Yago apresenta:"  + "\n\n\n" 
                            + "\t\t\t\t\t" + "      *** POKE-TRABALHO ***"       + "\n" 
                            + "\t\t\t\t\t" + "Algoritmos e Estrutura de Dados 3" + "\n\n");

        System.out.println (  "\t\t\t\t\t" + "         \\:.             .:/" + "\n"
                            + "\t\t\t\t\t" + "          \\``._________.''/"  + "\n"
                            + "\t\t\t\t\t" + "           \\             /"   + "\n"
                            + "\t\t\t\t\t" + "   .--.--, / .':.   .':. \\"   + "\n"
                            + "\t\t\t\t\t" + "  /__:  /  | '::' . '::' |"    + "\n"
                            + "\t\t\t\t\t" + "     / /   |`.   ._.   .'|"    + "\n"
                            + "\t\t\t\t\t" + "    / /    |.'         '.|"    + "\n"
                            + "\t\t\t\t\t" + "   /___-_-,|.\\  \\   /  /.|"  + "\n"
                            + "\t\t\t\t\t" + "        // |''\\.;   ;,/ '|"   + "\n"
                            + "\t\t\t\t\t" + "        `==|:=         =:|"    + "\n"
                            + "\t\t\t\t\t" + "           `.          .'"     + "\n"
                            + "\t\t\t\t\t" + "             :-._____.-:"      + "\n"
                            + "\t\t\t\t\t" + "            `''       `''"     + "\n\n\n");

        exibir_fim_tela();
    }

    public static void exibir_tela_agradecimentos () {
        System.out.println (  "\n\t"       + "Deligando poke-sistema ..."       + "\n\n\n"
                            + "\t\t\t\t\t" + "*** Poke-obrigado e ate logo ***" + "\n\n");
            
        System.out.println (  "\t\t\t\t\t" + "       \\:.             .:/ " + "\n"
                            + "\t\t\t\t\t" + "        \\``._________.''/ "  + "\n"
                            + "\t\t\t\t\t" + "         \\             / "   + "\n"
                            + "\t\t\t\t\t" + " .--.--, / .':.   .':. \\"    + "\n"
                            + "\t\t\t\t\t" + "/__:  /  | '::' . '::' |"     + "\n"
                            + "\t\t\t\t\t" + "   / /   |`.   ._.   ;'\"/"   + "\n"
                            + "\t\t\t\t\t" + "  / /    |.'        /  /"     + "\n"
                            + "\t\t\t\t\t" + " /___-_-,|.\\  \\       .|"   + "\n"
                            + "\t\t\t\t\t" + "      // |''\\.;       '|"    + "\n"
                            + "\t\t\t\t\t" + "      `==|:=         =:|"     + "\n"
                            + "\t\t\t\t\t" + "         `.          .'"      + "\n"
                            + "\t\t\t\t\t" + "           :-._____.-:"       + "\n"
                            + "\t\t\t\t\t" + "          `''       `''"      + "\n\n\n");
    }

    /*********************
     * IMPORTACAO DE CSV *
     *********************/

    /*
     * Descricao: essa funcao trata a string lida no arquivo csv, retirando as aspas
     * Parametro: uma string (linha lida do arquivo)
     * Retorno: uma string (linha do arquivo tratada)
     */
    public static String tratar_string (String linha) throws Exception {
        StringBuilder sb = new StringBuilder(linha);
                
        //Deleta as aspas lidas da planilha
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '"') {

                if (i+1 != sb.length() && sb.charAt(i+1) == '"') {
                    sb.insert(i+1, "null");
                }

                sb.deleteCharAt(i);
                
                i--;
            }
        }

        return sb.toString();
    }

    public static void passar_arq_csv_para_db (RandomAccessFile arq_atual)  {
        Pokemon pokemon;
        String[] atributos_csv;
        byte[] poke_info_byte;
        String linha;
        int id_metadados = 0;
        
        FileOutputStream arq_db;
	    DataOutputStream dos;

        File arq_csv = new File("src/pokedex.csv");
        Scanner scanner = null;

        try {
            //Abre os objetos do arquivos db
            arq_db = new FileOutputStream("src/pokedex.db");
            dos = new DataOutputStream(arq_db);

            //Le arquivos csv
            scanner = new Scanner (arq_csv);

            //Le o cabecalho da planilha
            linha = new String(scanner.nextLine());

            //Escreve os metadados no arquivo
            dos.writeInt(id_metadados);

            //Le o arquivo csv e passa as informacoes para o arquivo db
            while (scanner.hasNextLine()) {
                linha = new String(scanner.nextLine());
                
                //Trata a string lida e a coloca em um vetor de string
                atributos_csv = tratar_string(linha).split(",");
                
                //Cria o regirtro
                pokemon = new Pokemon (id_metadados, Integer.parseInt(atributos_csv[0]), 
                                    atributos_csv[1], Integer.parseInt(atributos_csv[2]),
                                    atributos_csv[5], Float.parseFloat(atributos_csv[8]), 
                                    Float.parseFloat(atributos_csv[9]), atributos_csv[6], 
                                    atributos_csv[7], Integer.parseInt(atributos_csv[10]), 
                                    Integer.parseInt(atributos_csv[11]), Integer.parseInt(atributos_csv[12]), 
                                    Integer.parseInt(atributos_csv[13]), Integer.parseInt(atributos_csv[14]),
                                    Integer.parseInt(atributos_csv[15]), Boolean.parseBoolean(atributos_csv[3]), Boolean.parseBoolean(atributos_csv[4]));

                //Escreve o registro
                poke_info_byte = pokemon.toByteArray();
                dos.writeByte('*');
                dos.writeInt(poke_info_byte.length);
                dos.write(poke_info_byte);

                //Atualiza o id
                id_metadados++;
            }

            arq_db.close();
            dos.close();
            
            arq_atual.writeInt(id_metadados-1);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            scanner.close();            
        }
    }

    /*********************
     * ORDENACAO EXTERNA *
     *********************/

    /*
    * Descricao: essa funcao troca dois elementos de um vetor de pokemons
    * Parametros: um vetor de pokemons (vetor que tera os 
    * elementos trocados) e dois pokemons (indice dos 
    * dois elementos a serem trocados)
    */
    public static void swap (Pokemon[] vet, int i, int j) {
        Pokemon aux = vet[i];
        vet[i] = vet[j];
        vet[j] = aux;
    }

    /* 
    * Descricao: essa funcao calcula o indice o elemento pai no heap
    * Parametro: um inteiro (indice do elemento filho)
    * Retorno: um inteiro (indice do elemento pai)
    */
    public static int calcular_indice_pai (int filho) {
        int flag = filho;

        filho /= 2;

        if (flag %2 == 0) {
            filho --;
        }
        return filho;
    }

    /* 
    * Descricao: essa funcao constroi o heap de um vetor de pokemons
    * 
    * Parametros: um vetor de pokemons (vetor a ser construido 
    * a arvore heap) e um pokemon (tamanho valido do vetor)
    */
    public static void construir_heap (Pokemon[] bloco, int tam) {
        int indice = calcular_indice_pai(tam);
        int i = tam;

        while (i > 0 && bloco[i].getIdSecundario() < bloco[indice].getIdSecundario()) {
            swap (bloco, i, indice);

            i = calcular_indice_pai(i);

            if (indice % 2 == 0) {
                indice = calcular_indice_pai(indice);
            } else {
                indice /= 2;
            }
        }
    }

    /* 
    * Descricao: ordena um vetor de pokemons com o heap min
    * 
    * Parametro: um vetor de pokemons (vetor a ser ordenado)
    */
    public static void fazer_heapmin (Pokemon[] bloco) {
        int tam;

        //Construcao do heap
        for (tam = 1; tam < bloco.length; tam++) {
            construir_heap(bloco, tam);
        }
    }
    
    public static void ordenacao (RandomAccessFile arq) throws Exception {
        int i;

        /* DISTRIBUICAO */
        //Mensagem para o usuario
        System.out.println("Iniciando etapa de distribuicao...");
        limpar_console();

        //Criacao de arquivos temporarios
        FileOutputStream arq_temp_1 = new FileOutputStream ("src/arqTemp1.db");
        FileOutputStream arq_temp_2 = new FileOutputStream ("src/arqTemp2.db");

        //Abre para escrita
        DataOutputStream out_1 = new DataOutputStream (arq_temp_1);
        DataOutputStream out_2 = new DataOutputStream (arq_temp_2);
        
        
    
        //Reinicia o ponteiro
        arq.seek(0);

        Pokemon[] bloco = new Pokemon [10];
        int qnt_segmentos = arq.readInt();
        int tam;
        byte[] poke_vet_byte;
        byte lapide;
        
        try {
            //Preenche o vetor com os registros
            for (i = 0; i < bloco.length && arq.getFilePointer() < arq.length(); i++) {
                
                //Verifica se o arquivo foi excluido
                if (arq.readByte() == ' ') {
                    //Le o arquivo
                    poke_vet_byte = new byte [arq.readInt()];
                    arq.read(poke_vet_byte);

                    //Cria e registra o pokemon
                    bloco[i] = new Pokemon();
                    bloco[i].fromByteArray(poke_vet_byte);
                } else {
                    long ta = arq.readInt();
                    long ponteiro =  arq.getFilePointer();
                    
                    //tam = arq.readInt();
                    arq.seek(ponteiro + ta);
                    i--;
                }
            }
            
            //Verifica se ha mais pokemons para se ler
            if (arq.getFilePointer() < arq.length()) {
                //Atualizacao na quantidade de segmentos
                qnt_segmentos -= 6;

                //Ordena o vetor com o heap minimo
                fazer_heapmin(bloco);

                //Leitura do proximo registro
                Pokemon novo_pokemon = new Pokemon();
                Pokemon antigo_pokemon = new Pokemon();
                boolean escrever_arq1 = true;

                //Mensagem para o usuario
                System.out.println("Distribuindo pokemons...");
                limpar_console();
            
                while (arq.getFilePointer() < arq.length()) {
                    //Le o arquivo
                    lapide = arq.readByte();
                    tam = arq.readInt();
                    poke_vet_byte = new byte [tam];
                    arq.read(poke_vet_byte);

                    //Registra o novo pokemon
                    if (lapide == ' ') {    
                        novo_pokemon.fromByteArray(poke_vet_byte);

                        //Vetifica se o novo registro pode entrar no antigo segmento
                        if (novo_pokemon.getIdSecundario() < bloco[0].getIdSecundario()) {
                            novo_pokemon.setIdSecundario(novo_pokemon.getIdSecundario() + 1);
                        } else {
                            //Atualiza a quantidade de segmentos
                            qnt_segmentos --;
                        }

                        //Verificando se ha a troca de arquivo
                        if ((int)bloco[0].getIdSecundario() != (int)antigo_pokemon.getIdSecundario()) {
                            escrever_arq1 = !escrever_arq1;
                        }

                        //Escreve o pokemon no arquivo
                        antigo_pokemon = bloco[0];
                        poke_vet_byte = bloco[0].toByteArray();

                        if (escrever_arq1) {
                            out_1.writeInt(poke_vet_byte.length);
                            out_1.write(poke_vet_byte);
                        } else {
                            out_2.writeInt(poke_vet_byte.length);
                            out_2.write(poke_vet_byte);
                        }
                        
                        //Inclui novo pokemon do vetor
                        bloco[0] = novo_pokemon;

                        //Ordena o vetor com heap
                        fazer_heapmin(bloco);
                    }
                }
            }
            
            /* INTERCALACAO */
            System.out.println("Iniciando etapa de intercalacao ...");
            limpar_console();

            //Verificar se ha registros para intercalar

            //Fecha arquivo de escrita para leitura
            arq_temp_1.close();
            arq_temp_2.close();

            out_1.close();
            out_2.close();

            FileInputStream arq_in_1 = new FileInputStream("src/arqTemp1.db");
            FileInputStream arq_in_2 = new FileInputStream("src/arqTemp2.db");

            FileOutputStream arq_out_1 = new FileOutputStream ("src/arqTemp3.db");
            FileOutputStream arq_out_2 = new FileOutputStream ("src/arqTemp4.db");

            //Abre objetos de leitura e escrita
            out_1 = new DataOutputStream(arq_out_1);
            out_2 = new DataOutputStream(arq_out_2);

            DataInputStream in_1 = new DataInputStream(arq_in_1);
            DataInputStream in_2 = new DataInputStream(arq_in_2);

            //Le o primeiro arquivo
            //Le o segundo arquivo
            //Intercarla
            //Verifica se ha troca de arquivo
            //Verificar se um dos arquivos está vazio

            //Limpar antigos arquivos de leitura
            //Trocar arquivos

            System.out.println("Intercalando pokemons ...");
            limpar_console();

            System.out.println("Finalizando Intercalacao ...");
            limpar_console();
        
            in_1.close();
            in_2.close();
            
        } finally {
            //Fecha os arquivos temporarios
            arq_temp_1.close();
            arq_temp_2.close();

            out_1.close();
            out_2.close();

            //Deleta os arquivos temporarios
            File arq_temp;
            for (i = 1; i <= 2; i++) {
                arq_temp = new File ("src/arqTemp" + i + ".db");
                arq_temp.delete();
            }
        }
        System.out.println("Ordenacao concluida");

    }

    public static void main(String[] args) {
        RandomAccessFile arq;

        try {
            arq = new RandomAccessFile("src/pokedex.db", "rw");
            passar_arq_csv_para_db(arq);
            ordenacao(arq);
            arq.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

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
        System.in.read(new byte[System.in.available()]);
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

        System.out.println (  "\n\t\t\t\t\t" +                        "*** POKE-INSTRUCOES ***"                         + "\n\n\n"
                            + "\t"           + "Esse sistema eh um trabalho de Algortmos e Estrutura de Dados 3"        + "\n"
                            + "\t"           + "com o objetivo de manipulacao de arquivos de base dados. Posto isso"    + "\n"
                            + "\t"           + "nesse sistema sera utilizado uma base de dados sobre pokemon."          + "\n\n"

                            + "\t"           + "Se a base de dados .db nao for encontrada ou estiver vazia, "           + "\n"
                            + "\t"           + "as informacoes serao importadas do arquivo .csv para preenche-la "      + "\n"
                            + "\t"           + "automaticamente. Apos o processo de importacao, ou caso o arquivo .db " + "\n"
                            + "\t"           + "exista, sera direcionado para o menu de opcoes de manipulacao da base " + "\n"
                            + "\t"           + "de dados, onde podera executar as acoes desejadas."                     + "\n\n"
                            + "\t\t\t\t\t"   +                            "Seja bem vindo!"                             + "\n");
        
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

        //Exibe mensagem para o usuario
        System.out.println (  "\n\t\t\t\t\t" + "*** IMPORTANDO ARQUIVO .CSV PARA .DB ***" + "\n\n\n"
                            + "\t" + "Iniciando importacao ..." + "\n");
        
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

            //Exibe mensagem para o usuario
            limpar_console();
            System.out.println (  "\n\t\t\t\t\t" + "*** IMPORTANDO ARQUIVO .CSV PARA .DB ***" + "\n\n\n"
                            + "\t" + "Importando arquivo ..." + "\n");

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
                dos.writeByte(' ');
                dos.writeInt(poke_info_byte.length);
                dos.write(poke_info_byte);

                //Atualiza o id
                id_metadados++;
            }

            arq_db.close();
            dos.close();
            
            arq_atual.writeInt(id_metadados-1);

            //Exibe mensagem para o usuario
            limpar_console();
            System.out.println (  "\n\t\t\t\t\t" + "*** IMPORTANDO ARQUIVO .CSV PARA .DB ***" + "\n\n\n"
                            + "\t" + "Importacao concluida com sucesso !!!" + "\n");

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
    
    public static Pokemon escrever_pokemon (Pokemon pokemon, 
                                            DataInputStream in, 
                                            DataOutputStream[] out,
                                            boolean escolha_out) throws Exception 
    {
        byte[] vet = pokemon.toByteArray();
        int indice = escolha_out ? 1 : 0;
        
        out[indice].writeInt(vet.length);
        out[indice].write(vet);
        
        vet = new byte [in.readInt()];
        in.read(vet);
        pokemon.fromByteArray(vet);

        return pokemon;
    }

    public static void ordenacao (RandomAccessFile arq) throws Exception {
        Pokemon novo_pokemon = new Pokemon();
        Pokemon antigo_pokemon = new Pokemon();
        boolean escrever_arq1 = true;
        int indice = 0;
        int i;

        //Declaracao de constante para o tamanho dos vetores
        final int vet_tam = 2;

        /* DISTRIBUICAO */
        //Mensagem para o usuario
        System.out.println("\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Iniciando etapa de distribuicao ...");

        //Criacao de arquivos temporarios
        FileOutputStream[] arq_out = new FileOutputStream [vet_tam];
        DataOutputStream[] out = new DataOutputStream [vet_tam];
        
        //Reinicia o ponteiro
        arq.seek(0);

        Pokemon[] bloco = new Pokemon [10];
        int metadados = arq.readInt();
        byte[] poke_vet_byte;
        
        try {
            //Abre os objetos
            for (i = 0; i < vet_tam; i++) {
                arq_out[i] = new FileOutputStream("src/arqTemp" + (i+1) + ".db");
                out[i] = new DataOutputStream (arq_out[i]);
            }

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
                    //Pula o registro
                    arq.seek(arq.readInt() + arq.getFilePointer());
                    i--;
                }
            }
            
            //Verifica se ha mais pokemons para se ler
            if (arq.getFilePointer() < arq.length()) {
                //Ordena o vetor com o heap minimo
                fazer_heapmin(bloco);

                //Mensagem para o usuario
                limpar_console();
                System.out.println("\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Distribuindo pokemons ...");

                int ajuste_id = 0;
                
                //Leitura do proximo registro
                while (arq.getFilePointer() < arq.length()) {
                    //Verifica se o registro existe
                    if (arq.readByte() == ' ') {
                        //Le o arquivo
                        poke_vet_byte = new byte [arq.readInt()];
                        arq.read(poke_vet_byte);
                        novo_pokemon.fromByteArray(poke_vet_byte);

                        //Verifica se o novo registro pode entrar no antigo segmento
                        if (novo_pokemon.getIdSecundario() < bloco[0].getIdSecundario()) {
                            ajuste_id ++;
                        }

                        novo_pokemon.setIdSecundario(novo_pokemon.getIdSecundario() + ajuste_id);

                        
                        //Verificando se ha a troca de arquivo
                        if ((int)bloco[0].getIdSecundario() != (int)antigo_pokemon.getIdSecundario()) {
                            indice = (indice == 0) ? 1 : 0;
                        }

                        //Escreve o pokemon no arquivo
                        antigo_pokemon = bloco[0];
                        poke_vet_byte = bloco[0].toByteArray();

                        out[indice].writeInt(poke_vet_byte.length);
                        out[indice].write(poke_vet_byte);
                        
                        //Inclui novo pokemon do vetor
                        bloco[0] = novo_pokemon;

                        //Ordena o vetor com heap
                        fazer_heapmin(bloco);
                    } else {
                        //Pula o arquivo
                        arq.seek(arq.readInt() + arq.getFilePointer());
                    }
                }
            }
        } finally {
            //Fecha os arquivos temporarios
            for (i = 0; i < vet_tam; i ++) {
                arq_out[i].close();
                out[i].close();
            }         
        }
            
        /* INTERCALACAO */
        limpar_console();
        System.out.println("\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Iniciando etapa de intercalacao ...");
        
        
        //Abre os arquivos
        FileInputStream[] arq_in = new FileInputStream [vet_tam];
        DataInputStream[] in = new DataInputStream [vet_tam];
        Pokemon[] poke = new Pokemon [vet_tam];

        boolean terminou_segmento = false;
        boolean modo1 = false;

        //Reinicia variaveis
        escrever_arq1 = true;
        
        try {
            //Abre objetos de leitura e escrita
            for (i = 0; i < vet_tam; i++) {
                arq_in[i] = new FileInputStream("src/arqTemp" + (i+1) + ".db");
                arq_out[i] = new FileOutputStream("src/arqTemp" + (i+3) + ".db");

                in[i] = new DataInputStream(arq_in[i]);
                out[i] = new DataOutputStream(arq_out[i]);

                poke[i] = new Pokemon();
            }

            limpar_console();
            System.out.println("\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Intercalando pokemons ...");

            //Verifica se ha registros para intercalar
            while (in[0].available() > 0 && in[1].available() > 0) {

                //Le o primeiro registro dos arquivos
                for (i = 0; i < vet_tam; i ++) {
                    poke_vet_byte = new byte [in[i].readInt()];
                    in[i].read(poke_vet_byte);
                    poke[i].fromByteArray(poke_vet_byte);
                }
            
                //Intercala de arquivos
                while (in[0].available() > 0 && in[1].available() > 0) {

                    //Verifica o proximo registro a ser registrado
                    indice = (poke[0].getId() < poke[1].getId()) ? 0 : 1;
                    
                    //Registra o novo pokemon
                    antigo_pokemon = poke[indice];
                    poke[indice] = escrever_pokemon(poke[indice], in[indice], out, escrever_arq1);

                    //Verifica se o segmento acabou
                    if (antigo_pokemon.getId() > poke[indice].getId() || in[indice].available() <= 0) {
                        terminou_segmento = true;

                        //If ternario para inverter o indice
                        indice = indice == 0 ? 1 : 0;

                        //Escreve o resto do segmento do outro arquivo
                        while (antigo_pokemon.getId() < poke[indice].getId() || in[indice].available() > 0) {
                            antigo_pokemon = poke[indice];
                            poke[indice] = escrever_pokemon(poke[indice], in[indice], out, escrever_arq1);
                        }
                    }

                    //Verifica se ha troca de arquivo
                    if (terminou_segmento) {
                        escrever_arq1 = !escrever_arq1;
                        
                        //Reinicia variavel
                        terminou_segmento = false;
                    }
                }

                //Trocar arquivos
                for (i = 0; i < vet_tam; i++) {
                    arq_in[i].close();
                    arq_out[i].close();
                    in[i].close();
                    out[i].close();
                }

                for (i = 0; i < vet_tam; i++) {
                    if (modo1) {
                        arq_in[i] = new FileInputStream("src/arqTemp" + (i+1) + ".db");
                        arq_out[i] = new FileOutputStream("src/arqTemp" + (i+3) + ".db");
                    } else {
                        arq_in[i] = new FileInputStream("src/arqTemp" + (i+3) + ".db");
                        arq_out[i] = new FileOutputStream("src/arqTemp" + (i+1) + ".db");        
                    }

                    in[i] = new DataInputStream(arq_in[i]);
                    out[i] = new DataOutputStream(arq_out[i]);
                }

                modo1 = !modo1;        
            }

            //Limpar antigos arquivos de leitura
            arq.setLength(0);
            arq.writeInt(metadados);

            indice = in[0].available() > 0 ? 0 : 1;

            while (in[indice].available() > 0) {
                //escrever arq
                poke_vet_byte = new byte [in[indice].readInt()];
                in[indice].read(poke_vet_byte);

                arq.writeByte(' ');
                arq.writeInt(poke_vet_byte.length);
                arq.write(poke_vet_byte);
            }
            
            limpar_console();
            System.out.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                                + "\t" + "Finalizando Intercalacao ...");

        } finally {
            for (i = 0; i < vet_tam; i++) {
                arq_in[i].close();
                arq_out[i].close();
                in[i].close();
                out[i].close();
            }

            //Deleta os arquivos temporarios
            File arq_temp;
            for (i = 1; i <= 4; i++) {
                arq_temp = new File ("src/arqTemp" + i + ".db");
                arq_temp.delete();
            }
        }

        limpar_console();
        System.out.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Ordenacao concluida com sucesso !!!" + "\n");
    }

    public static void main(String[] args) {
        RandomAccessFile arq;

        try {
            arq = new RandomAccessFile("src/pokedex.db", "rw");
            
            //Verifica se o arquivo esta vazio, acabou de ser criado
            if (arq.length() == 0) {
                //Importa arquivo .csv automatico
                passar_arq_csv_para_db(arq);
                exibir_fim_tela();
            }
            ordenacao(arq);
            exibir_fim_tela();
            arq.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

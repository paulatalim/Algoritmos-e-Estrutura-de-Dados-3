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

    public static void limpar_buffer (Scanner scanner) {
        if(scanner.hasNextLine()){
            scanner.nextLine();
        }
    }

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

    /*
     * Descricao: essa funcao exibe no fim da tela
     */
    public static void exibir_fim_tela () throws Exception {
        System.out.print("\n\n\t" + "Pressione 'enter' para continuar");
        System.in.read();
        System.in.read(new byte[System.in.available()]);
        limpar_console();
    }

    /*
     * Descricao: essa funcao exibe a tela inicial, as informacoes 
     * do grupo e instrucoes do sistema
     */
    public static void exibir_tela_inicial_e_info () throws Exception {
        //Capa do trab
        System.out.println (  "\n\t"       + "Mariana, Paula e Yago apresentam:"  + "\n\n\n" 
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
                            + "\t"           + "Esse sistema eh um trabalho de Algoritmos e Estrutura de Dados 3"       + "\n"
                            + "\t"           + "com o objetivo de manipulacao de arquivos de base dados. Como exemplo," + "\n"
                            + "\t"           + "nesse sistema sera utilizado uma base de dados sobre pokemons."         + "\n\n"

                            + "\t"           + "A seguir, as informacoes do arquivo .csv serao importadas para preencher"    + "\n"
                            + "\t"           + "a base de dados .db. Apos o processo de importacao, sera redirecionado "     + "\n"
                            + "\t"           + "para o menu de opcoes de manipulacao da base de dados, onde podera executar" + "\n"
                            + "\t"           + "as acoes desejadas."                                                         + "\n\n\n"
                            + "\t\t\t\t\t"   +                            "Seja bem vindo!"                                  + "\n");
        
        exibir_fim_tela();
    }

    /*
     * Descricao: essa funcao exibe a tela fnal, tela de agradecimentos
     */
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

    /*
     * Descricao: essa funcao importa um arquivo csv e passa sua informacoes para a database
     * Parametro: um arquivo RandomAccessFile (arquivo database a ser preenchido)
     */
    public static void passar_arq_csv_para_db (RandomAccessFile arq_atual)  {
        Pokemon pokemon;
        String[] atributos_csv;
        byte[] poke_info_byte;
        String linha;
        int id_metadados = 1;
        
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
    public static void swap_pokemon (Pokemon[] vet, int i, int j) {
        Pokemon aux = vet[i];
        vet[i] = vet[j];
        vet[j] = aux;
    }

    public static void swap_int (int[] vet, int i, int j) {
        int aux = vet[i];
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
    * Parametros: um vetor de pokemons (vetor a ser construido 
    * a arvore heap) e um pokemon (tamanho valido do vetor)
    */
    public static void construir_heap (Pokemon[] bloco, int[]chave, int tam) {
        int indice = calcular_indice_pai(tam);
        int i = tam;

        while (i > 0 && (chave[i] < chave[indice] || bloco[i].getId() < bloco[indice].getId())) {
            swap_pokemon(bloco, i, indice);
            swap_int(chave, indice, i);
            
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
    * Parametro: um vetor de pokemons (vetor a ser ordenado)
    */
    public static void fazer_heapmin (Pokemon[] bloco, int[] chaves) {
        //Construcao do heap
        for (int tam = 1; tam < bloco.length; tam++) {
            construir_heap(bloco, chaves, tam);
        }
    }
    
    public static Pokemon escrever_pokemon_e_ler_prox ( Pokemon pokemon, 
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

    /* 
    * Descricao: ordena os registros do arquivo com a ordenacao externa
    * Parametro: arquivo RandomAccessFile (arquivo a ser ordenado)
    */
    public static void ordenacao (RandomAccessFile arq) throws Exception {
        Pokemon[] bloco = new Pokemon [10];
        byte[] poke_vet_byte;
        int i, metadados;
        boolean escrever_arq1 = true;
        int indice = 0;
        final int vet_tam = 2; // Constante inteira

        Pokemon novo_pokemon = new Pokemon();
        Pokemon antigo_pokemon = new Pokemon();

        //Criacao de arquivos temporarios
        FileOutputStream[] arq_out = new FileOutputStream [vet_tam];
        DataOutputStream[] out = new DataOutputStream [vet_tam];

        /* DISTRIBUICAO */
        //Mensagem para o usuario
        System.out.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Iniciando etapa de distribuicao ...");

        //Abre os objetos
        for (i = 0; i < vet_tam; i++) {
            arq_out[i] = new FileOutputStream("src/arqTemp" + (i+1) + ".db");
            out[i] = new DataOutputStream (arq_out[i]);
        }

        //Reinicia o ponteiro
        arq.seek(0);

        //Leitura de metadados
        metadados = arq.readInt();

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

        int[] chaves = new int [10];
        
        //Inicializa o vetor 
        for (i = 0; i < chaves.length; i++) {
            chaves[i] = 0;
        }

        int nova_chave = 0;
        int chave_antiga = 0;

        //Ordena o vetor com o heap minimo
        fazer_heapmin(bloco, chaves);            

        //Mensagem para o usuario
        limpar_console();
        System.out.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
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
                //nova_chave = chave_antiga;

                //Verifica se o novo registro pode entrar no antigo segmento
                if (novo_pokemon.getId() < bloco[0].getId()) {
                    ajuste_id ++;
                }

                nova_chave = ajuste_id;

                // novo_pokemon.setIdSecundario(novo_pokemon.getIdSecundario() + ajuste_id);

                //Verificando se ha a troca de arquivo
                if (chaves[0] != chave_antiga) {
                    indice = (indice == 0) ? 1 : 0;
                }

                //Escreve o pokemon no arquivo
                antigo_pokemon = bloco[0];
                chave_antiga = chaves[0];
                poke_vet_byte = bloco[0].toByteArray();

                out[indice].writeInt(poke_vet_byte.length);
                out[indice].write(poke_vet_byte);
                
                //Inclui novo pokemon do vetor
                bloco[0] = novo_pokemon;
                chaves[0] = nova_chave;

                //Ordena o vetor com heap
                fazer_heapmin(bloco, chaves);
            } else {
                //Pula o arquivo
                arq.seek(arq.readInt() + arq.getFilePointer());
            }
        }
        
        //Registra o resto do vetor
        // for (i = 0; i < bloco.length; i++) {
            
        //     //Verificando se ha a troca de arquivo
        //     if (chaves[0] != chave_antiga) {
        //         indice = (indice == 0) ? 1 : 0;
        //     }

        //     //Registra novo pokemon
        //     poke_vet_byte = bloco[0].toByteArray();

        //     out[indice].writeInt(poke_vet_byte.length);
        //     out[indice].write(poke_vet_byte);

        //     //Inserir sentinela
        //     bloco[0] = new Pokemon();
        //     chaves[0] = 0x7FFFFFFF;

        //     //heap
        //     fazer_heapmin(bloco, chaves);
        // }

        //Fecha os arquivos temporarios

        for (i = 0; i < vet_tam; i ++) {
            arq_out[i].close();
            out[i].close();
        }
            
        /* INTERCALACAO */

        //Mensagem para o usuario
        limpar_console();
        System.out.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Iniciando etapa de intercalacao ...");
        
        FileInputStream[] arq_in = new FileInputStream [vet_tam];
        DataInputStream[] in = new DataInputStream [vet_tam];
        Pokemon[] poke = new Pokemon [vet_tam];

        boolean terminou_segmento = false;
        boolean inverter_arq = false;

        //Reinicia variaveis
        escrever_arq1 = true;
        
        //Abre objetos de leitura e escrita
        for (i = 0; i < vet_tam; i++) {
            arq_in[i] = new FileInputStream("src/arqTemp" + (i+1) + ".db");
            arq_out[i] = new FileOutputStream("src/arqTemp" + (i+3) + ".db");
            in[i] = new DataInputStream(arq_in[i]);
            out[i] = new DataOutputStream(arq_out[i]);
            poke[i] = new Pokemon();
        }

        //Mensagem para o usuario
        limpar_console();
        System.out.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Intercalando pokemons ...");

        //Verifica se ha registros para intercalar
        while (in[0].available() > 0 && in[1].available() > 0) {

            //Reajuste na variavel
            inverter_arq = !inverter_arq;

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
                poke[indice] = escrever_pokemon_e_ler_prox(poke[indice], in[indice], out, escrever_arq1);

                //Verifica se o segmento acabou
                if (antigo_pokemon.getId() > poke[indice].getId() || in[indice].available() <= 0) {
                    terminou_segmento = true;

                    //If ternario para inverter o indice
                    indice = indice == 0 ? 1 : 0;

                    //Escreve o resto do segmento do outro arquivo
                    while (antigo_pokemon.getId() < poke[indice].getId() || in[indice].available() > 0) {
                        antigo_pokemon = poke[indice];
                        poke[indice] = escrever_pokemon_e_ler_prox(poke[indice], in[indice], out, escrever_arq1);
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

            //Troca de arquivos
            for (i = 0; i < vet_tam; i++) {
                if (inverter_arq) {
                    arq_in[i] = new FileInputStream("src/arqTemp" + (i+1) + ".db");
                    arq_out[i] = new FileOutputStream("src/arqTemp" + (i+3) + ".db");
                } else {
                    arq_in[i] = new FileInputStream("src/arqTemp" + (i+3) + ".db");
                    arq_out[i] = new FileOutputStream("src/arqTemp" + (i+1) + ".db");        
                }

                in[i] = new DataInputStream(arq_in[i]);
                out[i] = new DataOutputStream(arq_out[i]);
            }
        }

        //Limpar antigos arquivos de leitura
        arq.setLength(0);
        arq.writeInt(metadados);

        //Reajuste de variavel
        indice = 0;

        //Fecha os arquivos e objetos
        for (i = 0 ; i < vet_tam; i++) {
            arq_in[i].close();
            arq_out[i].close();
            in[i].close();
            out[i].close();
        }

        for (i = 1; i < 4; i ++) {
            //Verifica em qual arquivo q possui dados
            arq_in[indice] = new FileInputStream("src/arqTemp" + i + ".db");
            in[indice] = new DataInputStream(arq_in[indice]);

            //Transferencia de dados do arquivo temporario para o arquivo .db
            while (in[indice].available() > 0) {
                //escrever arq
                poke_vet_byte = new byte [in[indice].readInt()];
                in[indice].read(poke_vet_byte);

                arq.writeByte(' ');
                arq.writeInt(poke_vet_byte.length);
                arq.write(poke_vet_byte);
            }
        
            arq_in[indice].close();
            in[indice].close();
        }

        //Mensagem para o usuario
        limpar_console();
        System.out.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Finalizando Intercalacao ...");

        //Deleta os arquivos temporarios
        File arq_temp;
        for (i = 1; i <= 4; i++) {
            arq_temp = new File ("src/arqTemp" + i + ".db");
            arq_temp.delete();
        }

        //Mensagem para o usuario
        limpar_console();
        System.out.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Ordenacao concluida com sucesso !!!" + "\n");
    }

    public static Pokemon info_poke_atualizadas (Pokemon pokemon) throws Exception {
        int entrada_int;
        float entrada_float;
        String entrada_string;
        Scanner scanner = new Scanner (System.in);

        //Exibe o pokemon
        pokemon.exibir_pokemon();

        int opcao;
        System.out.println ("\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n"
                            +"\n\t" + "Digite para atualizar:" + "\n\n"
                            + "\t" + "1- Nome" + "\t\t\t"          + "5- Altura" + "\t" + "9- Hp" + "\t\t\t"           + "13 - Defesa Especial" + "\n"
                            + "\t" + "2- Numero da pokedex" + "\t" + "6- Peso" + "\t\t" + "10- Ataque" + " \t\t"       + "14- Velocidade" + "\n"
                            + "\t" + "3- Geracao" + "\t\t"         + "7- Tipo1" + "\t"  + "11- Defesa" + "\t\t"        + "15- Eh mistico" + "\n"
                            + "\t" + "4- Especie" + "\t\t"         + "8- Tipo2" + "\t"  + "12- Ataque especial" + "\t" + "16- Eh lendario");

        System.out.print("\n\t" + "Sua opcao: ");
        opcao = scanner.nextInt();
        limpar_buffer(scanner);
        limpar_console();

        //Exibe o titulo da pagina
        System.out.println("\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n");

        switch(opcao){
            case 1:
                //Entrada nome
                System.out.print("\t" + "Novo nome: ");
                entrada_string = new String (scanner.nextLine());
                pokemon.setNome(entrada_string); 
                break;
             
            case 2:
                //Entrada numero pokedex
                System.out.print("\t" + "Numero na Pokedex: ");
                entrada_int = scanner.nextInt();
                pokemon.setNumPokedex(entrada_int);
                break;

            case 3:
                //Entrada geracao
                System.out.print("\t" + "Geracao: ");
                entrada_int = scanner.nextInt();
                pokemon.setGeracao(entrada_int);
                break;

            case 4:
                //Entrada especie
                System.out.print("\t" + "Especie: ");
                entrada_string = new String (scanner.nextLine());
                pokemon.setEspecie(entrada_string);
                break;

            case 5:
                //Entrada altura
                System.out.print("\t" + "Altura: ");
                entrada_float = scanner.nextFloat();
                pokemon.setAltura(entrada_float);
                break;

            case 6:
                //Entrada peso
                System.out.print("\t" + "Peso: ");
                entrada_float = scanner.nextFloat();
                pokemon.setPeso(entrada_float);
                break;

            case 7:
                //Entrada tipo 1
                System.out.print("\t" + "Tipo 1: ");
                entrada_string= new String (scanner.nextLine());
                pokemon.setTipo1(entrada_string);
                break;

            case 8:
                //Entrada tipo 2
                System.out.print("\t" + "Tipo 2: ");
                entrada_string = new String (scanner.nextLine());
                pokemon.setTipo2(entrada_string);
                break;

            case 9:
                //Entrada hp
                System.out.print("\t" + "Hp: ");
                entrada_int = scanner.nextInt();
                pokemon.setHp(entrada_int);
                break;  

            case 10:
                //Entrada ataque
                System.out.print("\t" + "Ataque: ");
                entrada_int = scanner.nextInt();
                pokemon.setAtaque(entrada_int);
                break;

            case 11:
                //Entrada defesa
                System.out.print("\t" + "Defesa: ");
                entrada_int = scanner.nextInt();
                pokemon.setDefesa(entrada_int);
                break;

            case 12:
                //Entrada ataque especial
                System.out.print("\t" + "Ataque Especial: ");
                entrada_int = scanner.nextInt();
                pokemon.setAtaqueEspecial(entrada_int);
                break;

            case 13:
                //Entrada defesa especial
                System.out.print("\t" + "Defesa Especial: ");
                entrada_int = scanner.nextInt();
                pokemon.setDefesaEspecial(entrada_int);
                break;

            case 14:
                //Entrada velocidade
                System.out.print("\t" + "Velocidade: ");
                entrada_int = scanner.nextInt();
                pokemon.setVelocidade(entrada_int);
                break;

            case 15:
                //Entrada eh mistico
                System.out.print("\t" + "(responda sim ou nao)" + "\n"
                                + "\t" + "Eh mistico: ");
                entrada_string = new String (scanner.nextLine());
                
                if (entrada_string.toLowerCase().compareTo("sim") == 0) {
                    pokemon.setEhMistico(true);
                } else {
                    pokemon.setEhMistico(false);
                }
                break;

            case 16:
                //Entrada eh lendario
                System.out.print("\t" + "(responda sim ou nao)" + "\n"
                                + "\t" + "Eh lendario: ");
                entrada_string = new String (scanner.nextLine());
                
                if (entrada_string.toLowerCase().compareTo("sim") == 0) {
                    pokemon.setEhLendario(true);
                } else {
                    pokemon.setEhLendario(false);
                }
                break;
            
            default:
                System.out.println("\t" + "Opcao invalida");
                return null;
        }

        return pokemon;
    }

    public static Pokemon info_poke_novo () {
        int entrada_int;
        float entrada_float;
        String entrada_string;

        Scanner scanner = new Scanner(System.in);
        Pokemon pokemon = new Pokemon();

        //Entrada nome
        System.out.println();

        System.out.print( "\t" + "INFORMACOES BASICAS" + "\n"
                        + "\t" + "Nome: ");
        entrada_string = new String (scanner.nextLine());
        //limpar_buffer(scanner);
        pokemon.setNome(entrada_string); 

        //Entrada num_pokedex;
        System.out.print("\t" + "Numero na Pokedex: ");
        entrada_int = scanner.nextInt();
        pokemon.setNumPokedex(entrada_int); 

        //Entrada geracao
        System.out.print("\t" + "Geracao: ");
        entrada_int = scanner.nextInt();
        pokemon.setGeracao(entrada_int);

        //Entrada especie
        System.out.print("\t" + "Especie: ");
        limpar_buffer(scanner);
        entrada_string = new String (scanner.nextLine());
        pokemon.setEspecie(entrada_string);

        //Entrada altura
        System.out.print("\t" +"Altura: ");
        entrada_float = scanner.nextFloat();
        pokemon.setAltura(entrada_float);

        //Entradapeso
        System.out.print("\t" + "Peso: ");
        entrada_float = scanner.nextFloat();
        pokemon.setPeso(entrada_float);

        //Entrada tipo 1
        System.out.print("\t" + "Tipo 1: ");
        limpar_buffer(scanner);
        entrada_string = new String (scanner.nextLine());
        pokemon.setTipo1(entrada_string);

        //Entrada tipo 2
        System.out.print("\t" + "Tipo 2: ");
        entrada_string = new String (scanner.nextLine());
        pokemon.setTipo2(entrada_string);

        //Entrada hp
        System.out.print("\n\t" + "ESTATISTICAS BASICAS" + "\n"
                        + "\t" + "Hp: ");
        entrada_int = scanner.nextInt();
        pokemon.setHp(entrada_int);

        //Entrada ataque
        System.out.print("\t" + "Ataque: ");
        entrada_int = scanner.nextInt();
        pokemon.setAtaque(entrada_int);

        //Entrada defesa
        System.out.print("\t" + "Defesa: ");
        entrada_int = scanner.nextInt();
        pokemon.setDefesa(entrada_int);

        //Entrada ataque especial
        System.out.print("\t" + "Ataque Especial: ");
        entrada_int= scanner.nextInt();
        pokemon.setAtaqueEspecial(entrada_int);

        //Entrada defesa especial
        System.out.print("\t" + "Defesa Especial: ");
        entrada_int= scanner.nextInt();
        pokemon.setDefesaEspecial(entrada_int);

        //Entrada velocidade
        System.out.print("\t" + "Velocidade: ");
        entrada_int = scanner.nextInt();
        pokemon.setVelocidade(entrada_int);
        
        //Entrada se eh mistico
        System.out.print("\n\t" + "POKE-ESPECIAL" + "\n"
                        + "\t" + "(responda sim ou nao)" + "\n\n"
                        + "\t" + "Eh mistico: ");
                        limpar_buffer(scanner);
        entrada_string = new String (scanner.nextLine());

        //Verifica a entrada
        if (entrada_string.toLowerCase().compareTo("sim") == 0) {
            pokemon.setEhMistico(true);
        } else {
            pokemon.setEhMistico(false);

            //Entrada se eh lendario
            System.out.print("\t" + "Eh lendario: ");
            entrada_string = new String (scanner.nextLine());
            
            //Verifica a entrada
            if (entrada_string.toLowerCase().compareTo("sim") == 0) {
                pokemon.setEhLendario(true);
            } else {
                pokemon.setEhLendario(false);
            }
        }
        
        return pokemon;
    }

    public static void main(String[] args) {
        RandomAccessFile arq;
        Scanner scanner = new Scanner (System.in);

        int opcao, id;
        Pokemon pokemon = new Pokemon();
        CRUD crud;

        try {
            arq = new RandomAccessFile("src/pokedex.db", "rw");
            crud = new CRUD ("src/pokedex.db");

            //Exibe o inicio do programa
            exibir_tela_inicial_e_info();
            
            //Importa arquivo .csv automatico
            passar_arq_csv_para_db(arq);
            exibir_fim_tela();
            
            //Repete o programa
            do {

                //Validacao da entrada do usuario
                do {
                    //Exibe o menu das opcoes
                    System.out.println ("\n\t\t\t\t\t" + "*** POKE-MENU ***" + "\n\n\n"
                                        + "\t" + "Opcoes da Pokedex:" + "\n\n"
                                        + "\t" + "1 - CRIAR pokemon" + "\n"
                                        + "\t" + "2 - LER pokemon" + "\n "
                                        + "\t" + "3 - ATUALIZAR informacao de pokemon" + "\n"
                                        + "\t" + "4 - DELETAR pokemon da pokedex" + "\n"
                                        + "\t" + "5 - ORDENAR pokemons na pokedex" + "\n"
                                        + "\t" + "0 - SAIR" + "\n");

                    System.out.print("\t" + "Insira o numero da opcao: ");
                    opcao = scanner.nextInt();
                    limpar_console();
                } while (opcao < 0 || opcao > 5);
    
                switch (opcao) {
                    case 1:
                        System.out.print("\n\t\t\t\t\t" + "    *** POKE-CRIACAO ***" + "\n"
                                        + "\t\t\t\t\t"  + "Insira as novas informacoes" + "\n");
                        //Cria novo pokemon
                        pokemon = info_poke_novo();
                        crud.criar(pokemon);
                        limpar_console();

                        //Exibe o pokemon criado
                        pokemon.exibir_pokemon();
                        System.out.println("\n\t\t\t\t\t" + "Pokemon registrado com sucesso!");
                        break;
    
                    case 2:
                        //crud.listar_registros();

                        System.out.print("\n\t\t\t\t\t" + "*** POKE-WIKI ***" + "\n\n\n"
                                        + "\t" + "Insira o id do pokemon procurado: ");

                        id = scanner.nextInt();
                        limpar_console();
    
                        pokemon = crud.ler(id);

                        //Exibe o novo registro
                        if (pokemon != null) {
                            pokemon.exibir_pokemon();
                        } else {
                            System.out.println("\n\t\t\t\t\t" + "*** POKE-WIKI ***" + "\n\n\n" 
                                                + "\t" + "Pokemon nao encontrado");
                        }

                        break;
    
                    case 3:
                        //Exibe o titulo da pagina e entrada de id
                        System.out.print ( "\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n\n\n"
                                            + "\t" + "Insira o id do pokemon a ter um update: ");
                        id = scanner.nextInt();
                        limpar_console();

                        //Le o pokemon a ser atualizado
                        pokemon = crud.ler(id); 
                        
                        //Caso o id existir
                        if (pokemon != null) {

                            //Funções para atualizar o pokemon
                            pokemon = info_poke_atualizadas(pokemon); //Escolhe o atributo e atualiza
                            
                            //Caso a atualizacao nao ser invalida
                            if (pokemon != null) {
                                //Atualiza o pokemon no arquivo e retorna um boolean
                                if (crud.atualizar(pokemon)){
                                    System.out.println("\n\t" + "Pokemon atualizado com sucesso!");
                                } else {
                                    System.out.println ("\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n\n\n" 
                                                        + "\t" + "Pokemon nao encontrado");
                                }
                            }
                        } else {
                            System.out.println("\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n\n\n" 
                                                + "\t" + "Pokemon nao encontrado");
                        }
                        break;
    
                    case 4:
                        System.out.print("\n\t\t\t\t\t\t" + "*** POKE-DELETE ***" + "\n\n\n"
                                        + "\t"+ "Insira o id do pokemon a ser deletado: "); 
                        id = scanner.nextInt();

                        if (crud.excluir(id)) {
                            System.out.println("\n\t" + "Pokemon deletado da pokedex com sucesso!");
                        } else {
                            System.out.println("\n\t" + "Pokemon nao encontrado");
                        }
                        break;

                    case 5:
                        ordenacao(arq);
                        break;
                }

                //Preparo para reiniciar programa
                if (opcao != 0) {
                    exibir_fim_tela();
                }
                
            } while(opcao != 0);

            scanner.close();
            arq.close();

            exibir_tela_agradecimentos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

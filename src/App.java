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
                            + "\t"           + "Esse sistema eh um trabalho de Algoritmos e Estrutura de Dados 3"        + "\n"
                            + "\t"           + "com o objetivo de manipulacao de arquivos de base dados. Posto isso"    + "\n"
                            + "\t"           + "nesse sistema sera utilizado uma base de dados sobre pokemon."          + "\n\n"

                            + "\t"           + "Se a base de dados .db nao for encontrada ou estiver vazia, "           + "\n"
                            + "\t"           + "as informacoes serao importadas do arquivo .csv para preenche-la "      + "\n"
                            + "\t"           + "automaticamente. Apos o processo de importacao, ou caso o arquivo .db " + "\n"
                            + "\t"           + "exista, sera direcionado para o menu de opcoes de manipulacao da base " + "\n"
                            + "\t"           + "de dados, onde podera executar as acoes desejadas."                     + "\n\n\n"
                            + "\t\t\t\t\t"   +                            "Seja bem vindo!"                             + "\n");
        
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
    * Parametro: um vetor de pokemons (vetor a ser ordenado)
    */
    public static void fazer_heapmin (Pokemon[] bloco) {
        int tam;

        //Construcao do heap
        for (tam = 1; tam < bloco.length; tam++) {
            construir_heap(bloco, tam);
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
        
        //Verifica se ha mais pokemons para se ler
        if (arq.getFilePointer() < arq.length()) {
            //Ordena o vetor com o heap minimo
            fazer_heapmin(bloco);

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

    public static void escolher_atualizar (Pokemon pokemon) throws Exception{

        Scanner scanner = new Scanner (System.in);

        int op;
        System.out.println("\nDigite para atualizar: \n1- Nome \n2- Numero da pokedex \n3- Geracao \n4- Especie \n5- Altura \n6- Peso \n7- Tipo1 \n8- Tipo2 \n9- Hp \n10- Ataque \n11- Defesa \n12- Ataque especial \n13 - Defesa Especial \n14- Velocidade \n15- É mistico \n16- É lendario");
        op = scanner.nextInt();

        switch(op){

            case 1:
                //nome
                System.out.println("\nDigite o novo nome: ");
                if(scanner.hasNextLine()){
                    scanner.nextLine();
                }
                String atualNome = new String (scanner.nextLine());
    
                pokemon.setNome(atualNome); 
                break;
             
            case 2:
                // num_pokedex;
                System.out.println("\nNumero na Pokedex: ");
                int atualNum = (scanner.nextInt());

                pokemon.setNumPokedex(atualNum);
                break;

            case 3:
                // geracao
                System.out.println("\nGeração: ");
                int atualGeracao = (scanner.nextInt());

                pokemon.setGeracao(atualGeracao);
                break;

            case 4:
                // especie
                System.out.println("\nEspecie: ");
                if(scanner.hasNextLine()){
                    scanner.nextLine();
                }
                String atualEspecie = new String (scanner.nextLine());

                pokemon.setEspecie(atualEspecie);
                break;

            case 5:
                // altura
                System.out.println("\nAltura: ");
                Float atualAltura = (float) (scanner.nextInt());

                pokemon.setAltura(atualAltura);
                break;

            case 6:
                //peso
                System.out.println("\nPeso: ");
                Float atualPeso = (float) (scanner.nextInt());

                pokemon.setPeso(atualPeso);
                break;

            case 7:
              //tipo 1
              System.out.println("\nTipo 1: ");
              if(scanner.hasNextLine()){
                scanner.nextLine();
            }
              String atualTipo1= new String (scanner.nextLine());

              pokemon.setTipo1(atualTipo1);
              break;

            case 8:
                //tipo 2
                System.out.println("\nTipo 2: ");
                if(scanner.hasNextLine()){
                    scanner.nextLine();
                }
                String atualTipo2 = new String (scanner.nextLine());

                pokemon.setTipo2(atualTipo2);
                break;

            case 9:
                //hp
                System.out.println("\nHp: ");
                int atualHp = (scanner.nextInt());

                pokemon.setHp(atualHp);
                break;  

            case 10:
                //ataque
                System.out.println("\nAtaque: ");
                int atualAtaque = (scanner.nextInt());

                pokemon.setAtaque(atualAtaque);
                break;

            case 11:
                //defesa
                System.out.println("\nDefesa: ");
                int atualDefesa = (scanner.nextInt());

                pokemon.setDefesa(atualDefesa);
                break;

            case 12:
                //ataque_especial
                System.out.println("\nAtaque Especial: ");
                int atualAtaque_especial= (scanner.nextInt());

                pokemon.setAtaqueEspecial(atualAtaque_especial);
                break;

            case 13:
                //defesa_especial
                System.out.println("\nDefesa Especial: ");
                int atualDefesa_especial = (scanner.nextInt());

                pokemon.setDefesaEspecial(atualDefesa_especial);
                break;

            case 14:
                //velocidade
                System.out.println("\nVelocidade: ");
                int atualVelocidade = (scanner.nextInt());

                pokemon.setVelocidade(atualVelocidade);
                break;

            case 15:
                //eh_mistico
                System.out.println("\nÉ mistico: ");
                Boolean atualEh_mistico = new Boolean (scanner.nextBoolean());

                pokemon.setEhMistico(atualEh_mistico);
                break;

            case 16:
                //eh_lendario
                System.out.println("\nÉ lendario: ");
                Boolean atualEh_lendario = new Boolean (scanner.nextBoolean());

                
                pokemon.setEhLendario(atualEh_lendario);
                break;
        }

    }

    public static void main(String[] args) {
        RandomAccessFile arq;
        Scanner scanner = new Scanner (System.in);

        int num;
        Pokemon pokemon = new Pokemon();
        crud crud = new crud();

        try {
            arq = new RandomAccessFile("src/pokedex.db", "rw");

            exibir_tela_inicial_e_info();
            
            //Verifica se o arquivo esta vazio, acabou de ser criado
            if (arq.length() == 0) {
                //Importa arquivo .csv automatico
                passar_arq_csv_para_db(arq);
                exibir_fim_tela();
            }

            do {

                System.out.println("Digite: \n\n 1 - Para CRIAR\n 2 - Para LER\n 3 - Para ATUALIZAR\n 4 - Para DELETAR\n 5 - para ORDENAR\n 0 - Para FECHAR");
                num =scanner.nextInt();
    
                switch (num) {
                    case 1:
                        limpar_console ();
    
                        //nome
                        System.out.println("Digite o novo: ");
                        System.out.println("\nNome: ");
                        if(scanner.hasNextLine()){
                            scanner.nextLine();
                        }
                        String novoNome = new String (scanner.nextLine());
    
                        // num_pokedex;
                        System.out.println("\nNumero na Pokedex: ");
                        int novoNum = (scanner.nextInt());
    
                         // geracao
                         System.out.println("\nGeração: ");
                         int novoGeracao = (scanner.nextInt());
    
                         // especie
                        System.out.println("\nEspecie: ");
                        if(scanner.hasNextLine()){
                            scanner.nextLine();
                        }
                        String novoEspecie = new String (scanner.nextLine());
    
                        // altura
                        System.out.println("\nAltura: ");
                        Float novoAltura = (float) (scanner.nextInt());
    
                         //peso
                         System.out.println("\nPeso: ");
                         Float novoPeso = (float) (scanner.nextInt());
    
                         //tipo 1
                         System.out.println("\nTipo 1: ");
                         if(scanner.hasNextLine()){
                            scanner.nextLine();
                        }
                         String novoTipo1= new String (scanner.nextLine());
    
                         //tipo 2
                         System.out.println("\nTipo 2: ");
                         if(scanner.hasNextLine()){
                            scanner.nextLine();
                        }
                         String novoTipo2 = new String (scanner.nextLine());
    
                         //hp
                         System.out.println("\nHp: ");
                         int novoHp = (scanner.nextInt());
    
                         //ataque
                         System.out.println("\nAtaque: ");
                         int novoAtaque = (scanner.nextInt());
    
                         //defesa
                         System.out.println("\nDefesa: ");
                         int novoDefesa = (scanner.nextInt());
                        
                         //ataque_especial
                         System.out.println("\nAtaque Especial: ");
                         int novoAtaque_especial= (scanner.nextInt());
    
                         //defesa_especial
                         System.out.println("\nDefesa Especial: ");
                         int novoDefesa_especial = (scanner.nextInt());
    
                         //velocidade
                         System.out.println("\nVelocidade: ");
                         int novoVelocidade = (scanner.nextInt());
    
                         //eh_mistico
                         System.out.println("\nÉ mistico: ");
                         Boolean novoEh_mistico = new Boolean (scanner.nextBoolean());
    
                         //eh_lendario
                         System.out.println("\nÉ lendario: ");
                         Boolean novoEh_lendario = new Boolean (scanner.nextBoolean());
        
                        pokemon.setnome(novoNome); 
                        pokemon.setnum_pokedex(novoNum); 
                        pokemon.setgeracao(novoGeracao);
                        pokemon.setespecie(novoEspecie);
                        pokemon.setaltura(novoAltura);
                        pokemon.setpeso(novoPeso);
                        pokemon.settipo1(novoTipo1);
                        pokemon.settipo2(novoTipo2);
                        pokemon.sethp(novoHp);
                        pokemon.setataque(novoAtaque);
                        pokemon.setdefesa(novoDefesa);
                        pokemon.setataque_especial(novoAtaque_especial);
                        pokemon.setdefesa_especial(novoDefesa_especial);
                        pokemon.setvelocidade(novoVelocidade);
                        pokemon.seteh_mistico(novoEh_mistico);
                        pokemon.seteh_lendario(novoEh_lendario);
    
                        crud.criar(pokemon);
                        System.out.print("\n"); 
                        pokemon.exibir_pokemon();
                        System.out.print("\n"); 
    
                        break;
    
                    case 2:
                        limpar_console ();
    
                        int lerId;
                        System.out.println("Digite O id do pokemon procurado: "); 
                        lerId = scanner.nextInt();
    
                        pokemon = crud.ler(lerId); 
                        pokemon.exibir_pokemon();
    
                        System.out.print("\n"); 
    
                        break;
    
                    case 3:
                        limpar_console ();
    
                        int  atualizarId;
                        Boolean foi_atualizado;
    
                        System.out.println("Digite O id do pokemon a ser atualizado: ");
                        atualizarId = scanner.nextInt();
                        
                        pokemon = crud.ler(atualizarId); 
                        pokemon.exibir_pokemon();
    
                        //Funções para atualizar o pokemon
                        foi_atualizado = crud.atualizar(pokemon); //Verifica o tamnho do novo registro
                        escolher_atualizar(pokemon); //Escolhe o atributo e atualiza
                        
                       if (foi_atualizado == true){
                            System.out.println("\nO pokemon foi atualizado com sucesso!");
                        }
                        else{
                            System.out.println("\nPokemon não encontrado");
                       }
                        break;
    
                    case 4:
                        limpar_console ();
    
                        int deletarId;
                        System.out.println("Digite O id do pokemon procurado: "); 
                        deletarId = scanner.nextInt();
                        crud.excluir(deletarId);
    
                        break; 
    
                    case 5:
                        limpar_console ();
                        
                        break;
                }
                
            }
            while(num!=0);
            arq.close();

            exibir_tela_agradecimentos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

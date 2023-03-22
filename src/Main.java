import java.io.File;
import java.util.Scanner;
import java.io.RandomAccessFile;

public class Main {
    /**
    * Essa funcao trata a string lida no arquivo csv, retirando as aspas
    * @param linha linha lida do arquivo
    * @return linha do arquivo tratada
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

    /**
     * Funcao para importar um arquivo csv e passa sua informacoes para o arquivo database
     * @param arq_db arquivo database a ser preenchido
     * @param caminho_arq_csv url do arquivo csv a ser importado
     */
    public static void passar_arq_csv_para_db (RandomAccessFile arq_db, String caminho_arq_csv)  {
        Pokemon pokemon;
        String[] atributos_csv;
        byte[] poke_info_byte;
        String linha;
        int id_metadados = 1;

        File arq_csv = new File(caminho_arq_csv);
        Scanner scanner = null;

        //Exibe mensagem para o usuario
        Tela.println (  "\n\t\t\t\t\t" + "*** IMPORTANDO ARQUIVO .CSV PARA .DB ***" + "\n\n\n"
                            + "\t" + "Iniciando importacao ..." + "\n");
        
        try {
            //Le arquivos csv
            scanner = new Scanner (arq_csv);

            //Le o cabecalho da planilha
            linha = new String(scanner.nextLine());

            //Escreve os metadados no arquivo
            arq_db.seek(0);
            arq_db.writeInt(id_metadados);

            //Exibe mensagem para o usuario
            Tela.limpar_console();
            Tela.println (  "\n\t\t\t\t\t" + "*** IMPORTANDO ARQUIVO .CSV PARA .DB ***" + "\n\n\n"
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
                arq_db.writeByte(' ');
                arq_db.writeInt(poke_info_byte.length);
                arq_db.write(poke_info_byte);

                //Atualiza o id
                id_metadados++;
            }
            
            //Atualiza metadados de arquivo
            arq_db.seek(0);
            arq_db.writeInt(id_metadados-1);

            //Exibe mensagem para o usuario
            Tela.limpar_console();
            Tela.println (  "\n\t\t\t\t\t" + "*** IMPORTANDO ARQUIVO .CSV PARA .DB ***" + "\n\n\n"
                                + "\t" + "Importacao concluida com sucesso !!!" + "\n");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            scanner.close();            
        }
    }
    

    /******************************
     * ENTRADA DE DADOS PARA CRUD *
     ******************************/

    public static Pokemon info_poke_atualizadas (Pokemon pokemon) throws Exception {
        int entrada_int;
        float entrada_float;
        String entrada_string;
        Scanner scanner = new Scanner (System.in);

        //Exibe o pokemon
        Tela.print(pokemon.toString());

        int opcao;
        Tela.println ("\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n"
                            +"\n\t" + "Digite para atualizar:" + "\n\n"
                            + "\t" + "1- Nome" + "\t\t\t"          + "5- Altura" + "\t" + "9- HP" + "\t\t\t"           + "13 - Defesa Especial" + "\n"
                            + "\t" + "2- Numero da pokedex" + "\t" + "6- Peso" + "\t\t" + "10- Ataque" + " \t\t"       + "14- Velocidade" + "\n"
                            + "\t" + "3- Geracao" + "\t\t"         + "7- Tipo1" + "\t"  + "11- Defesa" + "\t\t"        + "15- Eh mistico" + "\n"
                            + "\t" + "4- Especie" + "\t\t"         + "8- Tipo2" + "\t"  + "12- Ataque especial" + "\t" + "16- Eh lendario");

        Tela.print("\n\t" + "Sua opcao: ");
        opcao = scanner.nextInt();
        Tela.limpar_buffer(scanner);
        Tela.limpar_console();

        //Exibe o titulo da pagina
        Tela.println("\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n\t");

        switch(opcao){
            case 1:
                //Entrada nome
                Tela.printlt("Novo nome: ");
                entrada_string = new String (scanner.nextLine());
                pokemon.setNome(entrada_string); 
                break;
             
            case 2:
                //Entrada numero pokedex
                Tela.printlt("Numero na Pokedex: ");
                entrada_int = scanner.nextInt();
                pokemon.setNumPokedex(entrada_int);
                break;

            case 3:
                //Entrada geracao
                Tela.printlt("Geracao: ");
                entrada_int = scanner.nextInt();
                pokemon.setGeracao(entrada_int);
                break;

            case 4:
                //Entrada especie
                Tela.printlt("Especie: ");
                entrada_string = new String (scanner.nextLine());
                pokemon.setEspecie(entrada_string);
                break;

            case 5:
                //Entrada altura
                Tela.printlt("Altura: ");
                entrada_float = scanner.nextFloat();
                pokemon.setAltura(entrada_float);
                break;

            case 6:
                //Entrada peso
                Tela.printlt("Peso: ");
                entrada_float = scanner.nextFloat();
                pokemon.setPeso(entrada_float);
                break;

            case 7:
                //Entrada tipo 1
                Tela.printlt("Tipo 1: ");
                entrada_string= new String (scanner.nextLine());
                pokemon.setTipo1(entrada_string);
                break;

            case 8:
                //Entrada tipo 2
                Tela.printlt("Tipo 2: ");
                entrada_string = new String (scanner.nextLine());
                pokemon.setTipo2(entrada_string);
                break;

            case 9:
                //Entrada hp
                Tela.printlt("HP: ");
                entrada_int = scanner.nextInt();
                pokemon.setHp(entrada_int);
                break;  

            case 10:
                //Entrada ataque
                Tela.printlt("Ataque: ");
                entrada_int = scanner.nextInt();
                pokemon.setAtaque(entrada_int);
                break;

            case 11:
                //Entrada defesa
                Tela.printlt("Defesa: ");
                entrada_int = scanner.nextInt();
                pokemon.setDefesa(entrada_int);
                break;

            case 12:
                //Entrada ataque especial
                Tela.printlt("Ataque Especial: ");
                entrada_int = scanner.nextInt();
                pokemon.setAtaqueEspecial(entrada_int);
                break;

            case 13:
                //Entrada defesa especial
                Tela.printlt("Defesa Especial: ");
                entrada_int = scanner.nextInt();
                pokemon.setDefesaEspecial(entrada_int);
                break;

            case 14:
                //Entrada velocidade
                Tela.printlt("Velocidade: ");
                entrada_int = scanner.nextInt();
                pokemon.setVelocidade(entrada_int);
                break;

            case 15:
                //Entrada eh mistico
                Tela.printlt("(responda sim ou nao)" + "\n"
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
                Tela.printlt("(responda sim ou nao)" + "\n"
                                + "\t" + "Eh lendario: ");
                entrada_string = new String (scanner.nextLine());
                
                if (entrada_string.toLowerCase().compareTo("sim") == 0) {
                    pokemon.setEhLendario(true);
                } else {
                    pokemon.setEhLendario(false);
                }
                break;
            
            default:
                Tela.println( "\t" + "Opcao invalida");
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


        // toda essa parte pode ser uma unica função con tentradas diferentes
        //Entrada nome
        Tela.printlt("INFORMACOES BASICAS" + "\n"
                        + "\t" + "Nome: ");
        entrada_string = new String (scanner.nextLine());
        //Tela.limpar_buffer(scanner);
        pokemon.setNome(entrada_string); 

        //Entrada num_pokedex;
        Tela.printlt("Numero na Pokedex: ");
        entrada_int = scanner.nextInt();
        pokemon.setNumPokedex(entrada_int); 

        //Entrada geracao
        Tela.printlt("Geracao: ");
        entrada_int = scanner.nextInt();
        pokemon.setGeracao(entrada_int);

        //Entrada especie
        Tela.printlt("Especie: ");
        Tela.limpar_buffer(scanner);
        entrada_string = new String (scanner.nextLine());
        pokemon.setEspecie(entrada_string);

        //Entrada altura
        Tela.printlt("Altura: ");
        entrada_float = scanner.nextFloat();
        pokemon.setAltura(entrada_float);

        //Entradapeso
        Tela.printlt("Peso: ");
        entrada_float = scanner.nextFloat();
        pokemon.setPeso(entrada_float);

        //Entrada tipo 1
        Tela.printlt("Tipo 1: ");
        Tela.limpar_buffer(scanner);
        entrada_string = new String (scanner.nextLine());
        pokemon.setTipo1(entrada_string);

        //Entrada tipo 2
        Tela.printlt("Tipo 2: ");
        entrada_string = new String (scanner.nextLine());
        pokemon.setTipo2(entrada_string);

        //Entrada hp
        Tela.printlt("\n\t" + "ESTATISTICAS BASICAS" + "\n"
                        + "\t" + "HP: ");
        entrada_int = scanner.nextInt();
        pokemon.setHp(entrada_int);

        //Entrada ataque
        Tela.printlt("Ataque: ");
        entrada_int = scanner.nextInt();
        pokemon.setAtaque(entrada_int);

        //Entrada defesa
        Tela.printlt("Defesa: ");
        entrada_int = scanner.nextInt();
        pokemon.setDefesa(entrada_int);

        //Entrada ataque especial
        Tela.printlt("Ataque Especial: ");
        entrada_int= scanner.nextInt();
        pokemon.setAtaqueEspecial(entrada_int);

        //Entrada defesa especial
        Tela.printlt("Defesa Especial: ");
        entrada_int= scanner.nextInt();
        pokemon.setDefesaEspecial(entrada_int);

        //Entrada velocidade
        Tela.printlt("Velocidade: ");
        entrada_int = scanner.nextInt();
        pokemon.setVelocidade(entrada_int);
        
        //Entrada se eh mistico
        Tela.print("\n\t" + "POKE-ESPECIAL" + "\n"
                        + "\t" + "(responda sim ou nao)" + "\n\n"
                        + "\t" + "Eh mistico: ");
                        Tela.limpar_buffer(scanner);
        entrada_string = new String (scanner.nextLine());

        //Verifica a entrada
        if (entrada_string.toLowerCase().compareTo("sim") == 0) {
            pokemon.setEhMistico(true);
        } else {
            pokemon.setEhMistico(false);

            //Entrada se eh lendario
            Tela.printlt("Eh lendario: ");
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
        String caminho_arq_csv = "src/pokedex.csv";
        String caminho_arq_db = "src/pokedex.db";

        RandomAccessFile arq;
        Scanner scanner = new Scanner (System.in);

        int opcao, id;
        Pokemon pokemon = new Pokemon();
        CRUD crud;
        boolean opcao_invalida;
        
        try {
            arq = new RandomAccessFile(caminho_arq_db, "rw");
            crud = new CRUD (caminho_arq_db);

            //Exibe o inicio do programa
            Tela.exibir_tela_inicial_e_info ();
            
            //Importa arquivo .csv automatico
            passar_arq_csv_para_db(arq, caminho_arq_csv);
            Tela.exibir_fim_tela();
            
            //Repete o programa
            do {

                //Reinicia a variavel
                opcao_invalida = false;

                //Validacao da entrada do usuario
                do {
                    //Exibe o menu das opcoes
                    Tela.println ("\n\t\t\t\t\t" + "*** POKE-MENU ***" + "\n\n\n"
                                        + "\t" + "O que deseja fazer em sua Pokedex:" + "\n\n"
                                        + "\t" + "1 - CRIAR pokemon" + "\n"
                                        + "\t" + "2 - LER pokemon" + "\n "
                                        + "\t" + "3 - ATUALIZAR informacao de pokemon" + "\n"
                                        + "\t" + "4 - DELETAR pokemon da pokedex" + "\n"
                                        + "\t" + "5 - ORDENAR pokemons na pokedex" + "\n"
                                        + "\t" + "6 - Ver" + "\n"
                                        + "\t" + "0 - SAIR" + "\n");
                    
                    if (opcao_invalida) {
                        Tela.println("\t" + "Opcao invalida. Tente novavemente." + "\n");
                    }

                    Tela.print("\t" + "Insira o numero da operacao: ");
                    opcao = scanner.nextInt();
                    Tela.limpar_console();

                    opcao_invalida = true;
                } while (opcao < 0 || opcao > 6);
    
                switch (opcao) {
                    case 1:
                        Tela.print("\n\t\t\t\t\t" + "    *** POKE-CRIACAO ***" + "\n"
                                        + "\t\t\t\t\t"  + "Insira as novas informacoes" + "\n");
                        //Cria novo pokemon
                        pokemon = info_poke_novo();
                        crud.criar(pokemon);
                        Tela.limpar_console();

                        //Exibe o pokemon criado
                        Tela.print(pokemon.toString());
                        Tela.println("\n\t\t\t\t\t" + "Pokemon registrado com sucesso!");
                        break;
    
                    case 2:
                        Tela.print("\n\t\t\t\t\t" + "*** POKE-WIKI ***" + "\n\n\n"
                                        + "\t" + "Insira o id do pokemon procurado: ");

                        id = scanner.nextInt();
                        Tela.limpar_console();
    
                        pokemon = crud.ler(id);

                        //Exibe o novo registro
                        if (pokemon != null) {
                            Tela.print(pokemon.toString());
                        } else {
                            Tela.println("\n\t\t\t\t\t" + "*** POKE-WIKI ***" + "\n\n\n" 
                                                + "\t" + "Pokemon nao encontrado");
                        }
                        break;
    
                    case 3:
                        //Exibe o titulo da pagina e entrada de id
                        Tela.print ( "\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n\n\n"
                                            + "\t" + "Insira o id do pokemon a ter um update: ");
                        id = scanner.nextInt();
                        Tela.limpar_console();

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
                                    Tela.println("\n\t" + "Pokemon atualizado com sucesso!");
                                } else {
                                    Tela.println ("\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n\n\n" 
                                                        + "\t" + "Pokemon nao encontrado");
                                }
                            }
                        } else {
                            Tela.println("\n\t\t\t\t\t\t" + "*** POKE-UP ***" + "\n\n\n" 
                                                + "\t" + "Pokemon nao encontrado");
                        }
                        break;
    
                    case 4:
                        Tela.print("\n\t\t\t\t\t\t" + "*** POKE-DELETE ***" + "\n\n\n"
                                        + "\t"+ "Insira o id do pokemon a ser deletado: "); 
                        id = scanner.nextInt();

                        if (crud.excluir(id)) {
                            Tela.println("\n\t" + "Pokemon deletado da pokedex com sucesso!");
                        } else {
                            Tela.println("\n\t" + "Pokemon nao encontrado");
                        }
                        break;

                    case 5:
                        Ordenacao_externa.ordenar_registros(arq);
                        break;
                    
                    case 6:
                        crud.listar_registros();
                        break;
                    
                    case 0:
                        Tela.exibir_tela_agradecimentos();
                        break;
                }

                //Preparo para reiniciar programa
                if (opcao != 0) {
                    Tela.exibir_fim_tela();
                }
                
            } while(opcao != 0);

            //Fecha os objetos
            scanner.close();
            arq.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

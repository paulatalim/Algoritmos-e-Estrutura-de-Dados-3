import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {

    /**
     * Entrada de dados de string
     * 
     * @param campo nome do dado a ser digitado
     * @return o dado string lido
     */
    public static String entrada_string (String campo) {
        Scanner scanner = new Scanner(System.in);
        Tela.printlt(campo + ": ");
        return scanner.nextLine();
    }

    /**
     * Entrada de dados int
     * 
     * @param campo nome do dado a ser digitado
     * @return o dado int lido
     */
    public static int entrada_int (String campo) {
        Scanner scanner = new Scanner(System.in);
        Tela.printlt(campo + ": ");
        return scanner.nextInt();
    }

    /**
     * Entrada de dados float
     * 
     * @param campo nome do dado a ser digitado
     * @return o dado float lido
     */
    public static float entrada_float (String campo) {
        Scanner scanner = new Scanner(System.in);
        Tela.printlt(campo + ": ");
        return scanner.nextFloat();
    }

    /**
     *  Entrada de dados para atualizar pokemon
     * 
     * @param pokemon a ser atualizado
     * @return pokemon com informacoes atualizadas
     * @throws Exception
     */
    public static Pokemon info_poke_atualizadas (Pokemon pokemon) throws Exception {
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
                pokemon.setNome(entrada_string("Nome")); 
                break;
             
            case 2:
                //Entrada numero pokedex
                pokemon.setNumPokedex(entrada_int("Numero na Pokedex"));
                break;

            case 3:
                //Entrada geracao
                pokemon.setGeracao(entrada_int("Geracao"));
                break;

            case 4:
                //Entrada especie
                pokemon.setEspecie(entrada_string("Especie"));
                break;

            case 5:
                //Entrada altura
                pokemon.setAltura(entrada_float("Altura"));
                break;

            case 6:
                //Entrada peso
                pokemon.setPeso(entrada_float("Peso"));
                break;

            case 7:
                //Entrada tipo 1
                pokemon.setTipo1(entrada_string("Tipo 1"));
                break;

            case 8:
                //Entrada tipo 2
                pokemon.setTipo2(entrada_string("Tipo 2"));
                break;

            case 9:
                //Entrada hp
                pokemon.setHp(entrada_int("HP"));
                break;  

            case 10:
                //Entrada ataque
                pokemon.setAtaque(entrada_int("Ataque"));
                break;

            case 11:
                //Entrada defesa
                pokemon.setDefesa(entrada_int("Defesa"));
                break;

            case 12:
                //Entrada ataque especial
                pokemon.setAtaqueEspecial(entrada_int("Ataque Especial"));
                break;

            case 13:
                //Entrada defesa especial
                pokemon.setDefesaEspecial(entrada_int("Defesa Especial"));
                break;

            case 14:
                //Entrada velocidade
                pokemon.setVelocidade(entrada_int("Velocidade"));
                break;

            case 15:
                //Entrada eh mistico
                Tela.printlt("(responda sim ou nao)" + "\n");
                
                if (entrada_string("Eh mistico").toLowerCase().compareTo("sim") == 0) {
                    pokemon.setEhMistico(true);
                } else {
                    pokemon.setEhMistico(false);
                }
                break;

            case 16:
                //Entrada eh lendario
                Tela.printlt("(responda sim ou nao)" + "\n");
                
                if (entrada_string("Eh lendario").toLowerCase().compareTo("sim") == 0) {
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

    /**
     * Entrada de dados para criar novo Pokemon
     * 
     * @return pokemon com dados novos
     */
    public static Pokemon info_poke_novo () {
        Pokemon pokemon = new Pokemon();

        //Entrada informacoes basicas
        Tela.printlt("INFORMACOES BASICAS" + "\n");
        pokemon.setNome(entrada_string("Nome")); 
        pokemon.setNumPokedex(entrada_int("Numero na Pokedex")); 
        pokemon.setGeracao(entrada_int("Geracao"));
        pokemon.setEspecie(entrada_string("Especie"));
        pokemon.setAltura(entrada_float("Altura"));
        pokemon.setPeso(entrada_float("Peso"));
        pokemon.setTipo1(entrada_string("Tipo 1"));
        pokemon.setTipo2(entrada_string("Tipo 2"));

        //Entrada das estatisticas basicas
        Tela.printlt("\n\t" + "ESTATISTICAS BASICAS" + "\n");
        pokemon.setHp(entrada_int("HP"));
        pokemon.setAtaque(entrada_int("Ataque"));
        pokemon.setDefesa(entrada_int("Defesa"));
        pokemon.setAtaqueEspecial(entrada_int("Defesa Especial"));
        pokemon.setDefesaEspecial(entrada_int("Defesa Especial"));
        pokemon.setVelocidade(entrada_int("Velocidade"));

        //Entrada de pokemon especial
        Tela.print("\n\t" + "POKE-ESPECIAL" + "\n"
                    + "\t" + "(responda sim ou nao)" + "\n\n");

        //Verifica a entrada
        if (entrada_string("Eh mistico").toLowerCase().compareTo("sim") == 0) {
            pokemon.setEhMistico(true);
        } else {
            pokemon.setEhMistico(false);
            
            //Verifica a entrada
            if (entrada_string("Eh lendario").toLowerCase().compareTo("sim") == 0) {
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
            Importacao_csv.passar_arq_csv_para_db(arq, caminho_arq_csv);
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

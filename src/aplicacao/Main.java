package aplicacao;

import java.io.RandomAccessFile;
import java.util.Scanner;

import manipulacao_arquivo.Importa_csv;
import manipulacao_arquivo.Pokemon;
import trabalho_1.CRUD;
import trabalho_1.Ordenacao_externa;
import trabalho_1.entrada_dados;
import trabalho_2.indexacao;

/**
 * Classe da aplicacao do programa
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class Main {
    public static void main(String[] args) {
        String caminho_arq_csv = "src/pokedex.csv";
        String caminho_arq_db = "src/pokedex.db";

        int opcao, id;
        boolean opcao_invalida;
        CRUD crud;
        RandomAccessFile arq;
        Pokemon pokemon = new Pokemon();
        Scanner scanner = new Scanner (System.in);
        
        try {
            arq = new RandomAccessFile(caminho_arq_db, "rw");
            crud = new CRUD (caminho_arq_db);

            
            
//indexacao.teste();
            //Exibe o inicio do programa
            Tela.exibir_tela_inicial_e_info ();
            
            //Importa arquivo .csv automatico
            Importa_csv.passar_arq_csv_para_db(arq, caminho_arq_csv);
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
                                        + "\t" + "0 - SAIR" + "\n");
                                        System.out.println(indexacao.funcao_hash(9, 2));
                    
                    if (opcao_invalida) {
                        Tela.println("\t" + "Opcao invalida. Tente novavemente." + "\n");
                    }

                    Tela.print("\t" + "Insira o numero da operacao: ");
                    opcao = scanner.nextInt();
                    Tela.limpar_console();

                    opcao_invalida = true;
                } while (opcao < 0 || opcao > 5);
    
                switch (opcao) {
                    case 1:
                        Tela.print("\n\t\t\t\t\t" + "    *** POKE-CRIACAO ***" + "\n"
                                        + "\t\t\t\t\t"  + "Insira as novas informacoes" + "\n");
                        //Cria novo pokemon
                        pokemon = entrada_dados.info_poke_novo();
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
                            pokemon = entrada_dados.info_poke_atualizadas(pokemon); //Escolhe o atributo e atualiza
                            
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

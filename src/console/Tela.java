package console;
import java.util.Scanner;

/**
 * Classe funcional que organiza a saida no console
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class Tela {
    /**
     * Limpa o buffer do teclado
     * 
     * @param scanner a ter o buffer limpado
     */
    public static void limpar_buffer (Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    /**
     * Limpa a tela do console de windows, linux e MacOS
     */
    public static void limpar_console () throws Exception {
        //Limpa a tela no windows, no linux e no MacOS
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    }

    /**
     * Exibe mensagem no fim da tela
     */
    public static void exibir_fim_tela () throws Exception {
        System.out.print("\n\n\t" + "Pressione 'enter' para continuar . . .");
        System.in.read();
        System.in.read(new byte[System.in.available()]);
        limpar_console();
    }

    /**
     * Exibe a tela inicial, as informacoes 
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

    /**
     * Exibe a tela fnal, a tela de agradecimentos
     */
    public static void exibir_tela_agradecimentos () {
        System.out.println (  "\n\t"       + "Deligando poke-sistema ..."       + "\n\n\n"
                            + "\t\t\t\t\t" + "*** Poke-obrigado e ate logo ***" + "\n\n");
            
        System.out.print (  "\t\t\t\t\t" + "       \\:.             .:/ " + "\n"
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
                            + "\t\t\t\t\t" + "          `''       `''"      + "\n\n\n\t");
    }

    /**
     * Imprime uma mensagem no console
     * 
     * @param str mensagem a ser imprimida
     */
    public static void print(String str){
        System.out.print(str);
    }

    /**
     * Imprime uma mensagem no console com tabulacao no inicio
     * 
     * @param str mensagem a ser imprimida
     */
    public static void printlt(String str){
        System.out.print("\t" + str);
    }

    /**
     * Imprime uma mensagem no console com quebra de linha
     * 
     * @param str mensagem a ser imprimida
     */
    public static void println(String str){
        System.out.println(str);
    }

}

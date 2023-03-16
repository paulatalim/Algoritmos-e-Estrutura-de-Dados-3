import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.RandomAccessFile;


public class Main {

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

        File arq_csv = new File("C:src\\pokedex.csv");
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
                dos.writeByte(' ');
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

    //Escolher o dado para atualizar o pokemon
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
    
                pokemon.setnome(atualNome); 
                break;
             
            case 2:
                // num_pokedex;
                System.out.println("\nNumero na Pokedex: ");
                int atualNum = (scanner.nextInt());

                pokemon.setnum_pokedex(atualNum);
                break;

            case 3:
                // geracao
                System.out.println("\nGeração: ");
                int atualGeracao = (scanner.nextInt());

                pokemon.setgeracao(atualGeracao);
                break;

            case 4:
                // especie
                System.out.println("\nEspecie: ");
                if(scanner.hasNextLine()){
                    scanner.nextLine();
                }
                String atualEspecie = new String (scanner.nextLine());

                pokemon.setespecie(atualEspecie);
                break;

            case 5:
                // altura
                System.out.println("\nAltura: ");
                Float atualAltura = (float) (scanner.nextInt());

                pokemon.setaltura(atualAltura);
                break;

            case 6:
                //peso
                System.out.println("\nPeso: ");
                Float atualPeso = (float) (scanner.nextInt());

                pokemon.setpeso(atualPeso);
                break;

            case 7:
              //tipo 1
              System.out.println("\nTipo 1: ");
              if(scanner.hasNextLine()){
                scanner.nextLine();
            }
              String atualTipo1= new String (scanner.nextLine());

              pokemon.settipo1(atualTipo1);
              break;

            case 8:
                //tipo 2
                System.out.println("\nTipo 2: ");
                if(scanner.hasNextLine()){
                    scanner.nextLine();
                }
                String atualTipo2 = new String (scanner.nextLine());

                pokemon.settipo2(atualTipo2);
                break;

            case 9:
                //hp
                System.out.println("\nHp: ");
                int atualHp = (scanner.nextInt());

                pokemon.sethp(atualHp);
                break;  

            case 10:
                //ataque
                System.out.println("\nAtaque: ");
                int atualAtaque = (scanner.nextInt());

                pokemon.setataque(atualAtaque);
                break;

            case 11:
                //defesa
                System.out.println("\nDefesa: ");
                int atualDefesa = (scanner.nextInt());

                pokemon.setdefesa(atualDefesa);
                break;

            case 12:
                //ataque_especial
                System.out.println("\nAtaque Especial: ");
                int atualAtaque_especial= (scanner.nextInt());

                pokemon.setataque_especial(atualAtaque_especial);
                break;

            case 13:
                //defesa_especial
                System.out.println("\nDefesa Especial: ");
                int atualDefesa_especial = (scanner.nextInt());

                pokemon.setdefesa_especial(atualDefesa_especial);
                break;

            case 14:
                //velocidade
                System.out.println("\nVelocidade: ");
                int atualVelocidade = (scanner.nextInt());

                pokemon.setvelocidade(atualVelocidade);
                break;

            case 15:
                //eh_mistico
                System.out.println("\nÉ mistico: ");
                Boolean atualEh_mistico = new Boolean (scanner.nextBoolean());

                pokemon.seteh_mistico(atualEh_mistico);
                break;

            case 16:
                //eh_lendario
                System.out.println("\nÉ lendario: ");
                Boolean atualEh_lendario = new Boolean (scanner.nextBoolean());

                
                pokemon.seteh_lendario(atualEh_lendario);
                break;
        }

    }


    //MAIN
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner (System.in);

        //Acessar o arquivo
        RandomAccessFile arq;
        try {
            arq = new RandomAccessFile("src/pokedex.db", "rw");
            passar_arq_csv_para_db(arq);
            arq.close();

            //Switch case
            int num;
            Pokemon pokemon = new Pokemon();
            crud crud = new crud();

            
        
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

        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

}

import java.io.File;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.RandomAccessFile;

public class App {

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

    /*
    * Descricao: essa funcao troca dois elementos de um vetor de inteiros
    * Parametros: um vetor de inteiros (vetor que tera os 
    * elementos trocados) e dois inteiros (indice dos 
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
    public static int indice_pai (int filho) {
        int flag = filho;

        filho /= 2;

        if (flag %2 == 0) {
            filho --;
        }
        return filho;
    }

    /* 
    * Descricao: essa funcao constroi o heap de um vetor de inteiros
    * 
    * Parametros: um vetor de inteiros (vetor a ser construido 
    * a arvore heap) e um inteiro (tamanho valido do vetor)
    */
    public static void construir (Pokemon[] bloco, int tam) {
        int indice = indice_pai(tam);
        int i = tam;

        while (i > 0 && bloco[i].getIdSecundario() < bloco[indice].getIdSecundario()) {
            swap (bloco, i, indice);

            i = indice_pai(i);

            if (indice % 2 == 0) {
                indice = indice_pai(indice);
            } else {
                indice /= 2;
            }
        }
    }

    /* 
    * Descricao: essa funcao ordena um vetor com numeros 
    * do tipo inteiro em ordem crescente com o metodo HeapSort
    * 
    * Parametro: um vetor de inteiros (vetor a ser ordenado)
    */
    public static void heapsort (Pokemon[] bloco) {
        int tam;

        //Construcao do heap
        for (tam = 1; tam < bloco.length; tam++) {
            construir(bloco, tam);
        }
    }
    
    public static void ordenacao (RandomAccessFile arq) throws Exception {
        int i;

        /* DISTRIBUICAO */
        //Mensagem para o usuario
        System.out.println("Iniciando etapa de distribuicao...");
        limpar_console();

        //Criacao de arquivos temporarios
        RandomAccessFile arq_temp_1 = new RandomAccessFile("src/arqTemp1.db", "rw");
        RandomAccessFile arq_temp_2 = new RandomAccessFile ("src/arqTemp2.db", "rw");
        RandomAccessFile arq_temp_3 = new RandomAccessFile ("src/arqTemp3.db", "rw");
        // RandomAccessFile arq_temp_4 = new RandomAccessFile ("src/arqTemp4.db", "rw");
        // RandomAccessFile arq_temp_5 = new RandomAccessFile ("src/arqTemp5.db", "rw");
        // RandomAccessFile arq_temp_6 = new RandomAccessFile ("src/arqTemp6.db", "rw");

        //Reinicia o ponteiro
        arq.seek(0);

        Pokemon[] bloco = new Pokemon [7];
        int qnt_registros = arq.readInt();
        int tam;
        byte[] poke_vet_byte;
        byte lapide;

        //Preenche o vetor com os registros
        for (i = 0; i < bloco.length; i++) {
            //Le o arquivo
            lapide = arq.readByte();
            tam = arq.readInt();
            poke_vet_byte = new byte [tam];
            arq.read(poke_vet_byte);

            //Verifica se o arquivo foi excluido
            if (lapide == ' ') {
                //Cria e registra o pokemon
                bloco[i] = new Pokemon();
                bloco[i].fromByteArray(poke_vet_byte);
            
            }
        }
        
        //Ordena o vetor com o heap minimo
        heapsort(bloco);

        //Leitura do proximo registro
        Pokemon novo_pokemon = new Pokemon();
        Pokemon antigo_pokemon = new Pokemon();

        //Mensagem para o usuario
        System.out.println("Distribuindo pokemons...");
        limpar_console();

        while (arq.getFilePointer() < arq.length()) {
            //Le o arquivo
            lapide = arq.readByte();
            tam = arq.readInt();
            poke_vet_byte = new byte [tam];
            arq.read(poke_vet_byte);

            if (lapide == ' ') {    
                novo_pokemon.fromByteArray(poke_vet_byte);
            }

            if (novo_pokemon.getIdSecundario() < bloco[0].getIdSecundario()) {
                novo_pokemon.setIdSecundario(novo_pokemon.getIdSecundario() + 1);
                
            }

            int cont = 0;

            //Verificando se ha a troca de arquivo
            if ((int)bloco[0].getIdSecundario() != (int)antigo_pokemon.getIdSecundario()) {
                cont ++;
                
                //Verifica se reinicia o contador
                if (cont == 3) {
                    cont = 0;
                }
            }

            //Escreve o pokemon no arquivo
            antigo_pokemon = bloco[0];
            poke_vet_byte = bloco[0].toByteArray();

            switch (cont) {
                case 0:
                    arq_temp_1.writeInt(poke_vet_byte.length);
                    arq_temp_1.write(poke_vet_byte);
                    break;
                case 1:
                    arq_temp_2.writeInt(poke_vet_byte.length);
                    arq_temp_2.write(poke_vet_byte);
                    break;
                default:
                    arq_temp_3.writeInt(poke_vet_byte.length);
                    arq_temp_3.write(poke_vet_byte);
            }
            
            //Inclui novo pokemon do vetor
            bloco[0] = novo_pokemon;

            //Ordena o vetor com heap
            heapsort(bloco);
        }
        
        /* INTERCALACAO */
        System.out.println("Iniciando etapa de intercalacao ...");
        limpar_console();

        System.out.println("Intercalando pokemons ...");
        limpar_console();

        System.out.println("Finalizando Intercalacao ...");
        limpar_console();
        
        //Fecha os arquivos temporarios
        arq_temp_1.close();
        arq_temp_2.close();
        arq_temp_3.close();
        

        //Deleta os arquivos temporarios
        File arq_temp;
        for (i = 1; i <=3 ; i++) {
            arq_temp = new File ("src/arqTemp" + i + ".db");
            arq_temp.delete();
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

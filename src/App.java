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
        /* DISTRIBUICAO */
        //Criacao de arquivos temporarios
        File arq_temp_1 = new File ("src/arqTemp1.db");
        File arq_temp_2 = new File ("src/arqTemp2.db");
        File arq_temp_3 = new File ("src/arqTemp3.db");
        File arq_temp_4 = new File ("src/arqTemp4.db");
        File arq_temp_5 = new File ("src/arqTemp5.db");
        File arq_temp_6 = new File ("src/arqTemp6.db");

        arq_temp_1.createNewFile();
        arq_temp_2.createNewFile();
        arq_temp_3.createNewFile();
        arq_temp_4.createNewFile();
        arq_temp_5.createNewFile();
        arq_temp_6.createNewFile();

        //Reinicia o ponteiro
        arq.seek(0);

        Pokemon[] bloco = new Pokemon [7];

        heapsort(bloco);
        
    }

    public static void main(String[] args) {
        RandomAccessFile arq;

        try {

            arq = new RandomAccessFile("src/pokedex.db", "rw");
            arq.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

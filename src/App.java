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
            
            arq_atual.writeInt(id_metadados);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            scanner.close();            
        }
    }

    public static void main(String[] args) {
        RandomAccessFile arq;

        try {

            arq = new RandomAccessFile("src/pokedex.db", "rw");
            passar_arq_csv_para_db(arq);
            arq.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

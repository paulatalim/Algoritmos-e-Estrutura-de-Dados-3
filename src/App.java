import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.RandomAccessFile;

import java.io.File;
import java.util.Scanner;

public class App {

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
                sb.deleteCharAt(i);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        registro r;
        int id_metadados = 0;
        String[] vet;
        byte[] registro_vet_byte;

        //Declara o arquivo db
		FileOutputStream arq;
	    DataOutputStream dos;
        RandomAccessFile arq2;

        //Declara o arquivo csv
        File arq_csv = new File("src\\planilha_teste.csv");        
        Scanner scanner = null;
        String linha;

        try {
            //Abre os objetos do arquivos db
			arq = new FileOutputStream("src/base.db");
	        dos = new DataOutputStream(arq);

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
                vet = tratar_string(linha).split(",");                
    
                //Cria o regirtro
                r = new registro (id_metadados, vet[0], Integer.parseInt(vet[1]), vet[2].charAt(0));
    
                //Escreve o registro
                registro_vet_byte = r.toByteArray();
                dos.writeByte(' ');
                dos.writeInt(registro_vet_byte.length);
                dos.write(registro_vet_byte);

                //Atualiza o id
                id_metadados++;
            }

            //Atualiza os metadados
            arq.close();
            dos.close();
            arq2 = new RandomAccessFile("src/base.db", "rw");
            
           
            arq2.writeInt(id_metadados);

            arq2.close();

            

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
    
        } finally {
            scanner.close();            
        }
    }
}

import java.io.FileOutputStream;
import java.io.DataOutputStream;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class App {

    public static String tratar_string (String linha) {
        StringBuilder sb = new StringBuilder(linha);
                
        //Deleta as aspas lidas da planilha
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '"') {
                sb.deleteCharAt(i);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        registro r;
        String vet[];
        byte[] registro_vet_byte;

        //Declara o arquivo db
		FileOutputStream arq;
	    DataOutputStream dos;

        //Declara o arquivo csv
        File arq_csv = new File("src\\planilha_teste.csv");
        FileReader arq_reader_csv;
        BufferedReader leitor;
        String linha;

        try {
            //Abre os objetos do arquivos db
			arq = new FileOutputStream("src/base.db");
	        dos = new DataOutputStream(arq);

            //Abre os objetos do arquivos csv
            arq_reader_csv = new FileReader(arq_csv);
            leitor = new BufferedReader(arq_reader_csv);
            
            //Le o cabecalho da planilha
            linha = leitor.readLine();

            //Le o arquivo csv e passa as informacoes para o arquivo db
            while (linha != null) {
                linha = leitor.readLine();

                //Trata a string lida e a coloca em um vetor de string
                vet = tratar_string(linha).split(",");                
    
                //Cria o regirtro
                r = new registro (vet[0], Integer.parseInt(vet[1]), vet[2].charAt(0));
    
                //traduzur o registro
                registro_vet_byte = r.toByteArray();
                
                //Escreve o registro
                dos.writeInt(registro_vet_byte.length);
                dos.write(registro_vet_byte);
            }

            arq_reader_csv.close();
            leitor.close();

            arq.close();
            dos.close();
        
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

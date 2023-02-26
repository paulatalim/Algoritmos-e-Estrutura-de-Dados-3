import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class App {
    public static void main(String[] args) throws Exception {
        //Declara o arquivo db
		FileOutputStream arq;
	    DataOutputStream dos;

        //Declara o arquivo csv
        File arq_csv = new File("src\\planilha_teste.csv");
        FileReader arq2 = new FileReader(arq_csv);
        BufferedReader leitor = new BufferedReader(arq2);
        String linha = leitor.readLine();

        String vet[] = new String [3];
        byte[] escrito;

        while (linha != null) {
            vet = linha.split(",");
            for (int i = 0; i < 3; i ++) {
                System.out.println(vet[i]);
            }
            
            linha = leitor.readLine();
        }

        arq2.close();
        leitor.close();

        try {
            //Abre os objetos do arquivos bd
			arq = new FileOutputStream("src/base.db");
	        dos = new DataOutputStream(arq);

            dos.writeInt(19);
            dos.writeUTF("Paula");

            arq.close();
            dos.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

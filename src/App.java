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

        registro r;
        String vet[] = new String [3];
        byte[] vet_traduzido;

        try {
            //Abre os objetos do arquivos bd
			arq = new FileOutputStream("src/base.db");
	        dos = new DataOutputStream(arq);

            while (linha != null) {
                vet = linha.split(",");

                System.out.println( Integer.parseInt(vet[1]));
    
                //Cria o regirtro
                r = new registro (vet[0], Integer.parseInt(vet[1]), vet[2].charAt(0));
    
                //traduzur o registro
                vet_traduzido = r.toByteArray();
                
                //Escreve o registro
                dos.writeInt(vet_traduzido.length);
                dos.write(vet_traduzido);
                
                linha = leitor.readLine();
            }
    
            arq2.close();
            leitor.close();

            arq.close();
            dos.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

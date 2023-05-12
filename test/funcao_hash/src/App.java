import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.DataOutputStream;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        // int chave = 31;
        // int profundidade = 2;
        // int result1 = (int) Math.pow(2, profundidade);
        // int result = chave % (int) Math.pow(2, profundidade);

        // int qnt_buckets_cria;

        // int profundidade_diretorio = 4;

        // if (profundidade_diretorio != 1) {
        //     qnt_buckets_cria = (int) Math.pow(2, profundidade_diretorio) / 2;
        // } else {
        //     qnt_buckets_cria = 2;
        // }

        // RandomAccessFile test = new RandomAccessFile("src/teste.db", "rw");
        // test.seek(test.length());

        // long teste = test.getFilePointer();

        FileInputStream arq = new FileInputStream("test.db");
        //DataOutputStream dos = new DataOutputStream(arq);
        //dos.writeLong(3);


        long sla = arq.getChannel().size();
        System.out.println(arq.getChannel().size());
    }
}

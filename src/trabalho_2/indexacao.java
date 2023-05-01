package trabalho_2;

import java.io.RandomAccessFile;
import java.io.File;

public class indexacao {
    public static void teste () throws Exception {
        String diretorio_indices = "src/arquivos_de_indices";
        File arq = new File(diretorio_indices);
        arq.mkdir();

        RandomAccessFile diretorio = new RandomAccessFile(diretorio_indices + "/diretorio.db", "rw");
        RandomAccessFile buckets = new RandomAccessFile(diretorio_indices + "/buckets.db", "rw");
    }
}

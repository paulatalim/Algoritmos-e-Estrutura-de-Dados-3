package trabalho_2;

import java.io.RandomAccessFile;

import manipulacao_arquivo.Pokemon;

import java.io.File;

public class indexacao {

    public static int funcao_hash (int chave, int profundidade) {
        return chave % (int) Math.pow(2, profundidade);
    }

    public static void teste () throws Exception {
        String diretorio_indices = "src/arquivos_de_indices";

        //Criacao de pasta para os arquivos de indices
        File arq = new File(diretorio_indices);
        arq.mkdir();

        //Criacao de arquivos de indices
        RandomAccessFile diretorio = new RandomAccessFile(diretorio_indices + "/diretorio.db", "rw");
        RandomAccessFile buckets = new RandomAccessFile(diretorio_indices + "/buckets.db", "rw");
        RandomAccessFile data_base = new RandomAccessFile("src/pokedex.db", "rw");

        //Profundidade
        diretorio.writeShort(0);

        diretorio.writeInt(1);

        data_base.readInt();

        Pokemon pokemon;
        byte[] vet_byte_pokemon;

        while (data_base.getFilePointer() < data_base.length()) {
            //Verifica se o arquivo foi excluido
            if (data_base.readByte() == ' ') {
                //Le o arquivo
                vet_byte_pokemon = new byte [data_base.readInt()];
                data_base.read(vet_byte_pokemon);

                pokemon = new Pokemon();
                pokemon.fromByteArray(vet_byte_pokemon);

            } else {
                //Pula o registro
                data_base.seek(data_base.readInt() + data_base.getFilePointer());
            }
        }
    }
}

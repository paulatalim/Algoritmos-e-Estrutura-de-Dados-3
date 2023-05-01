package trabalho_2;

import java.io.RandomAccessFile;

import manipulacao_arquivo.Pokemon;

import java.io.File;

public class indexacao {

    public static int funcao_hash (int chave, int profundidade) {
        return chave % (int) Math.pow(2, profundidade);
    }

    public static long pesquisa_buckets (int id_bucket) {
        return id_bucket * 124;
    }

    public static long pesquisa_diretorio (int id_chave) {
        return 2 + (id_chave * 8);
    }

    public static void criar_novo_bucket(RandomAccessFile arq_buckets, RandomAccessFile arq_diretorio, int id_novo_bucket) throws Exception {
        arq_diretorio.seek(0);

        short profundidade = arq_diretorio.readShort();
        arq_buckets.seek(arq_buckets.length());

        //Atualizacao de diretorio
        arq_diretorio.seek(pesquisa_diretorio(id_novo_bucket));
        arq_diretorio.writeLong(arq_buckets.getFilePointer());
        
        //Inicializando bucket
        arq_buckets.writeShort(profundidade);
        arq_buckets.writeShort(0);
        for (int i = 0; i < 10; i++) {
            arq_buckets.writeInt(0);
            arq_buckets.writeLong(0);
        }
    }

    public static void aumentar_profundidade_diretorio (RandomAccessFile arq_buckets, RandomAccessFile arq_diretorio, short profundidade_diretorio) throws Exception {
        arq_diretorio.seek(arq_diretorio.length());

        int qnt_buckets_cria;

        //Calcula a quantidade de buckets a ser criado
        if (profundidade_diretorio != 1) {
            qnt_buckets_cria = (int) Math.pow(2, profundidade_diretorio) / 2;

            for (int i = 0; i < qnt_buckets_cria; i++) {
                arq_diretorio.writeLong(pesquisa_buckets(i));
            }
        }
    }

    public static void utilizar_novo_bucket (RandomAccessFile bucket, RandomAccessFile diretorio, int id_bucket_dividir) throws Exception{
        bucket.seek(pesquisa_diretorio(id_bucket_dividir));

        diretorio.seek(0);

        short profundidade_diretorio = diretorio.readShort();
        short profundidade_bucket = bucket.readShort();
        profundidade_bucket ++;

        if (profundidade_diretorio == profundidade_bucket) {
            profundidade_diretorio ++;

            diretorio.seek(0);
            diretorio.writeShort(profundidade_diretorio);
            aumentar_profundidade_diretorio(bucket, diretorio, profundidade_diretorio);
        }

        //Atualiza o diretorio
        //diretorio.seek(pesquisa_diretorio(profundidade_bucket));

        //Atualiza profundidade e tamanho do bucket
        bucket.writeShort(profundidade_bucket);
        bucket.writeInt(0);

        int[] id_registro = new int[10];
        long[] endereco_registro = new long[10];

        //Le os reistros no bucket
        for (int i = 0; i < 10; i++) {
            id_registro[i] = bucket.readInt();
            endereco_registro[i] = bucket.readLong();
        }

        //redividindo registros
        for (int i = 0; i < 10; i++) {
            int hash = funcao_hash(id_registro[i], profundidade_bucket);
            
            bucket.seek(pesquisa_buckets(hash) + 2);

            int tamanho = bucket.readInt();

            //Colocando registro
            bucket.seek(tamanho*12);
            bucket.writeInt(id_registro[i]);
            bucket.writeLong(endereco_registro[i]);
            
            //Atualiza o tamanho
            tamanho++;
            bucket.seek(tamanho - 4);
            bucket.writeInt(tamanho);
        }
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

        short profundidade_diretorio = 1;

        //Profundidade
        diretorio.writeShort(profundidade_diretorio);

        diretorio.writeInt(0);

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

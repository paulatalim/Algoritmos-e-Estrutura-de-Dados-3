package trabalho_2;

import java.io.RandomAccessFile;

import manipulacao_arquivo.Pokemon;

import java.io.File;

public class Indexacao {
    private RandomAccessFile diretorio;
    private RandomAccessFile buckets;
    private RandomAccessFile data_base;

    public Indexacao (String url_data_base) throws Exception {
        //Criacao de pasta para os arquivos de indices
        String folder = "src/arquivos_de_indices";
        File arq = new File(folder);
        arq.mkdir();

        diretorio = new RandomAccessFile(folder + "/diretorio.db", "rw");
        buckets = new RandomAccessFile(folder + "/buckets.db", "rw");
        data_base = new RandomAccessFile(url_data_base, "rw");
    }

    public static int funcao_hash (int chave, int profundidade) {
        return chave % (int) Math.pow(2, profundidade);
    }

    public static long pesquisa_buckets (int id_bucket) {
        return id_bucket * 124;
    }

    public static long pesquisa_diretorio (int id_chave) {
        return 2 + (id_chave * 8);
    }

    public void criar_novo_bucket(int id_novo_bucket) throws Exception {
        diretorio.seek(0);
        short profundidade = diretorio.readShort();

        //Atualizacao de diretorio
        buckets.seek(buckets.length());
        diretorio.seek(pesquisa_diretorio(id_novo_bucket));
        diretorio.writeLong(buckets.getFilePointer());
        
        //Inicializando bucket
        buckets.writeShort(profundidade);
        buckets.writeShort(0);
        for (int i = 0; i < 10; i++) {
            buckets.writeInt(0);
            buckets.writeLong(0);
        }
    }

    public void aumentar_profundidade_diretorio (short profundidade_diretorio) throws Exception {
        diretorio.seek(diretorio.length());

        int qnt_buckets_cria;

        //Calcula a quantidade de buckets a ser criado
        if (profundidade_diretorio != 1) {
            qnt_buckets_cria = (int) Math.pow(2, profundidade_diretorio) / 2;

            for (int i = 0; i < qnt_buckets_cria; i++) {
                diretorio.writeLong(pesquisa_buckets(i));
            }
        }
    }

    public void dividir_bucket (int id_bucket_dividir) throws Exception{
        buckets.seek(pesquisa_diretorio(id_bucket_dividir));

        diretorio.seek(0);

        //Leitura de profundidades
        short profundidade_diretorio = diretorio.readShort();
        short profundidade_bucket = buckets.readShort();
        profundidade_bucket ++;

        //Verificacao de alteracao da profundidade do diretorio
        if (profundidade_diretorio == profundidade_bucket) {
            profundidade_diretorio ++;

            diretorio.seek(0);
            diretorio.writeShort(profundidade_diretorio);
            aumentar_profundidade_diretorio(profundidade_diretorio);
        }

        //Atualiza profundidade e tamanho do bucket
        buckets.writeShort(profundidade_bucket);
        buckets.writeInt(0);

        int[] id_registro = new int[10];
        long[] endereco_registro = new long[10];

        //Le os reistros no bucket
        for (int i = 0; i < 10; i++) {
            id_registro[i] = buckets.readInt();
            endereco_registro[i] = buckets.readLong();

            //Inicializa bucket
            buckets.seek(buckets.getFilePointer() - 12);
            buckets.writeInt(0);
            buckets.writeLong(0);
        }

        //redividindo registros
        for (int i = 0; i < 10; i++) {
            int hash_anterior = funcao_hash(id_registro[i], profundidade_bucket-1);
            int hash = funcao_hash(id_registro[i], profundidade_bucket);

            //verifica se o novo bucket foi criado
            if (pesquisa_diretorio(hash) == pesquisa_diretorio(hash_anterior) && hash != hash_anterior) {
                criar_novo_bucket(hash);
            }
            
            buckets.seek(pesquisa_diretorio(hash) + 2);

            short tamanho = buckets.readShort();
            long point = buckets.getFilePointer();

            //Colocando registro
            buckets.seek(point + tamanho*12);
            buckets.writeInt(id_registro[i]);
            buckets.writeLong(endereco_registro[i]);
            
            //Atualiza o tamanho
            tamanho++;
            buckets.seek(point - 4);
            buckets.writeInt(tamanho);
        }
    }

    public void incluir_novo_registro (int id, long endereco) throws Exception {
        diretorio.seek(0);
        int profundidade = diretorio.readShort();
        int hash = funcao_hash(id, profundidade);
        buckets.seek(pesquisa_diretorio(hash));

        buckets.readShort();

        long point = buckets.getFilePointer();
        int tamanho = buckets.readShort();

        if (tamanho == 10) {
            dividir_bucket(tamanho);
        }
        
        //Inclui o novo registro
        buckets.seek(buckets.getFilePointer() + tamanho*12);
        buckets.writeInt(id);
        buckets.writeLong(endereco);

        //Atualizacao de tamanho
        tamanho++;
        buckets.seek(point);
        buckets.writeShort(tamanho);
    }

    public long ler_registro (int id) throws Exception {
        diretorio.seek(0);
        int hash = funcao_hash(id, diretorio.readShort());

        buckets.seek(pesquisa_diretorio(hash));
        buckets.readShort();

        short tamanho = buckets.readShort();

        for (int i = 0; i < tamanho; i++) {
            if (id == buckets.readInt()) {
                return buckets.readLong();
            }
            buckets.readLong();
        }

        return -1;
    }


    //Atualizar endereco
    public void atualizar_endereco (int id, long novo_endereco) throws Exception {
        diretorio.seek(0);
        int hash = funcao_hash(id, diretorio.readShort());

        buckets.seek(pesquisa_diretorio(hash));

        short tamanho = buckets.readShort();

        for (int i = 0; i < tamanho; i++) {
            if (id == buckets.readInt()) {
               buckets.writeLong(novo_endereco);
            }
            buckets.readLong();
        }
    }

    public void inicializar_indexacao () throws Exception {
        short profundidade_diretorio = 1;
        Pokemon pokemon;
        byte[] vet_byte_pokemon;

        //Inicializando arquivos
        diretorio.setLength(0);
        buckets.setLength(0);

        //Registra a profundidade
        diretorio.writeShort(profundidade_diretorio);

        //Cria dois buckets iniciais
        for (int i = 0; i < 2; i ++) {
            criar_novo_bucket(i);
        }

        data_base.readInt();

        while (data_base.getFilePointer() < data_base.length()) {
            long endereco = data_base.getFilePointer();

            //Verifica se o arquivo foi excluido
            if (data_base.readByte() == ' ') {
                //Le o arquivo
                vet_byte_pokemon = new byte [data_base.readInt()];
                data_base.read(vet_byte_pokemon);

                pokemon = new Pokemon();
                pokemon.fromByteArray(vet_byte_pokemon);

                //Inclui nos arquivos indexados
                incluir_novo_registro(pokemon.getId(), endereco);

            } else {
                //Pula o registro
                data_base.seek(data_base.readInt() + data_base.getFilePointer());
            }
        }
    }
}

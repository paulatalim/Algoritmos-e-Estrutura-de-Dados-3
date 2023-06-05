package trabalho_2;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Classe que opera os arquivos indexados
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class Indexacao {
    private RandomAccessFile diretorio;
    private RandomAccessFile buckets;

    /**
     * Construtor da classe
     * 
     * @param url_data_base , arquivo a ser indexado
     * @throws Exception
     */
    public Indexacao () throws Exception {
        //Criacao de pasta para os arquivos de indices
        String name_folder = "src/arquivos_de_indices";
        File folder = new File(name_folder);
        folder.mkdir();

        diretorio = new RandomAccessFile(name_folder + "/diretorio.db", "rw");
        buckets = new RandomAccessFile(name_folder + "/buckets.db", "rw");
    }

    public void inicializar () throws Exception {
        //Inicializando arquivos
        diretorio.setLength(0);
        buckets.setLength(0);

        //Registra a profundidade
        diretorio.writeShort(1);

        //Cria dois buckets iniciais
        for (int i = 0; i < 2; i ++) {
            criar_novo_bucket(i);
        }
    }

    /**
     * Calcula o hash do numero
     * 
     * @param chave numero a ser calculado o hash
     * @param profundidade do diretorio
     * @return resultado da funcao hash
     */
    private static int calcular_hash (int chave, int profundidade) {
        return chave % (int) Math.pow(2, profundidade);
    }

    /**
     * Encontra no diretorio a localizacao do endereco da chave
     * 
     * @param chave a ser localizada
     * @return endereco da localizacao no diretorio
     */
    private static long endereco_chave_no_diretorio (int chave) {
        return 2 + (chave * 8);
    }

    /**
     * Cria um novo bucket no arquivo com todos os elementos zerados 
     * e profundidade igual a profundidade do diretorio e atualiza o diretorio
     * 
     * @param id_novo_bucket
     * @throws Exception
     */
    private void criar_novo_bucket(int id_novo_bucket) throws Exception {
        diretorio.seek(0);
        short profundidade = diretorio.readShort();

        //Atualizacao de diretorio
        buckets.seek(buckets.length());
        diretorio.seek(endereco_chave_no_diretorio(id_novo_bucket));
        diretorio.writeLong(buckets.getFilePointer());
        
        //Inicializando bucket
        buckets.writeShort(profundidade);
        buckets.writeShort(0);
        for (int i = 0; i < 10; i++) {
            buckets.writeInt(0);
            buckets.writeLong(0);
        }
    }

    /**
     * Aumenta a profundidade do diretorio, colocando os 
     * enderecos do buckets antigos nos novos id criado no diretorio
     * 
     * @throws Exception
     */
    private void aumentar_profundidade_diretorio () throws Exception {
        int hash_antigo;
        long endereco;

        //Leitura da profundidade do diretorio
        diretorio.seek(0);
        short profundidade = diretorio.readShort();
        profundidade++;

        //Atualizacao da profundidade no diretorio
        diretorio.seek(0);
        diretorio.writeShort(profundidade);

        //Calcula a quantidade de buckets a ser criado
        int qnt_buckets = (int) Math.pow(2, profundidade) / 2;

        //Reposicionando o ponteiro
        diretorio.seek(diretorio.length());

        //Adiciona os novos enderecos
        for (int i = 0; i < qnt_buckets; i++) {
            hash_antigo = calcular_hash(i, profundidade - 1);
            
            diretorio.seek(endereco_chave_no_diretorio(hash_antigo));
            endereco = diretorio.readLong();

            diretorio.seek(diretorio.length());
            diretorio.writeLong(endereco);
        }
    }

    /**
     * Divide o bucket quanto sua capacidade estoura
     * 
     * @param id_bucket a ser dividido
     * @throws Exception
     */
    private void dividir_bucket (int id_bucket) throws Exception {
        diretorio.seek(endereco_chave_no_diretorio(id_bucket));
        long endereco_bucket = diretorio.readLong();
        buckets.seek(endereco_bucket);
       
        diretorio.seek(0);

        //Leitura de profundidades
        short profundidade_diretorio = diretorio.readShort();
        short profundidade_bucket = buckets.readShort();
        profundidade_bucket ++;

        //Verificacao de alteracao da profundidade do diretorio
        if (profundidade_diretorio < profundidade_bucket) {
            aumentar_profundidade_diretorio();
        }

        //Atualiza profundidade e tamanho do bucket
        buckets.seek(endereco_bucket);
        buckets.writeShort(profundidade_bucket);
        buckets.writeShort(0);

        int[] id_registro = new int[10];
        long[] endereco_registro = new long[10];

        //Le os registros no bucket
        for (int i = 0; i < 10; i++) {
            id_registro[i] = buckets.readInt();
            endereco_registro[i] = buckets.readLong();

            //Inicializa bucket
            buckets.seek(buckets.getFilePointer() - 12);
            buckets.writeInt(0);
            buckets.writeLong(0);
        }

        //Redividindo os registros
        for (int i = 0; i < 10; i++) {
            int hash_anterior = calcular_hash(id_registro[i], profundidade_bucket-1);
            int hash = calcular_hash(id_registro[i], profundidade_bucket);

            diretorio.seek(endereco_chave_no_diretorio(hash_anterior));
            long endereco_anterior = diretorio.readLong();

            diretorio.seek(endereco_chave_no_diretorio(hash));
            long endereco_atual = diretorio.readLong();

            //verifica se o novo bucket foi criado
            if (endereco_anterior == endereco_atual && hash != hash_anterior) {
                criar_novo_bucket(hash);
            }
            
            diretorio.seek(endereco_chave_no_diretorio(hash));
            buckets.seek(diretorio.readLong() + 2);

            short tamanho = buckets.readShort();
            long point = buckets.getFilePointer();

            //Colocando registro
            buckets.seek(point + tamanho*12);
            buckets.writeInt(id_registro[i]);
            buckets.writeLong(endereco_registro[i]);
            
            //Atualiza o tamanho
            tamanho++;
            buckets.seek(point - 2);
            buckets.writeShort(tamanho);
        }
    }

    /**
     * Atraves da chave, encontra o possivel endereco do 
     * bucket onde ele pode estar armazenado
     * 
     * @param chave a ser localizada
     * @return endereco do bucket da chave
     * @throws Exception
     */
    private long endereco_bucket (int chave) throws Exception {
        diretorio.seek(0);
        int hash = calcular_hash(chave, diretorio.readShort());

        diretorio.seek(endereco_chave_no_diretorio(hash));

        return diretorio.readLong();
    }

    /**
     * Inclui um novo registro nos arquivos indexados
     * 
     * @param id do registro
     * @param endereco da localizacao do registro na base de dados 
     * @throws Exception
     */
    public void incluir_registro (int id, long endereco) throws Exception {
        //Localiza o bucket a ser inserido o registro
        diretorio.seek(0);
        int hash = calcular_hash(id, diretorio.readShort());
        diretorio.seek(endereco_chave_no_diretorio(hash));
        buckets.seek(diretorio.readLong());
        buckets.readShort();

        long point = buckets.getFilePointer();
        short tamanho = buckets.readShort();

        //Caso a capacidade do bucket estoura
        while (tamanho >= 10) {
            dividir_bucket(hash);

            //Recalculando hash
            diretorio.seek(0);
            hash = calcular_hash(id, diretorio.readShort());
            diretorio.seek(endereco_chave_no_diretorio(hash));

            buckets.seek(diretorio.readLong());
            buckets.readShort();
            point = buckets.getFilePointer();
            tamanho = buckets.readShort();
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

    /**
     * Pesquisa nos arquivos indexado o registro
     * 
     * @param id do registro a ser pesquisado
     * @return Endereco do registro na base de dados ou -1, 
     * caso nao encontrar
     * 
     * @throws Exception
     */
    public long buscar_registro (int id) throws Exception {
        buckets.seek(endereco_bucket(id));
        buckets.readShort();

        short tamanho = buckets.readShort();

        //Localiza registro no bucket
        for (int i = 0; i < tamanho; i++) {
            if (id == buckets.readInt()) {
                return buckets.readLong();
            }
            buckets.readLong();
        }

        return -1;
    }

    /**
     * Atualiza o endereco de um registro 
     * nos arquivos indexados
     * 
     * @param id do registro a ser atualizado
     * @param novo_endereco a ser substituido
     * @throws Exception
     */
    public void atualizar_registro (int id, long novo_endereco) throws Exception {
        buckets.seek(endereco_bucket(id));

        buckets.readShort();
        short tamanho = buckets.readShort();

        //Localiza o registro no bucket
        for (int i = 0; i < tamanho; i++) {
            if (id == buckets.readInt()) {
               buckets.writeLong(novo_endereco);
            }
            buckets.readLong();
        }
    }
}

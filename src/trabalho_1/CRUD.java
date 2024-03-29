package trabalho_1;

import manipulacao_arquivo.Pokemon;
import trabalho_2.Indexacao;
import trabalho_3.Criptografia;

import java.io.RandomAccessFile;

/**
 * Classe que possui metodos CRUD para manipular arquivo .db
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class CRUD {
    private RandomAccessFile arq;
    private Indexacao index;
    private Criptografia criptografia;

    /**
     * Construtor da classe
     * 
     * @param caminho_arq data base
     * @param criptografia : objeto de criptografia
     * @throws Exception
     */
    public CRUD (String caminho_arq, Criptografia criptografia) throws Exception {
        arq = new RandomAccessFile(caminho_arq, "rw");
        index = new Indexacao();
        this.criptografia = criptografia;
    }

    /*** OUTROS METODOS ***/

    /**
     * Leitura da chave de criptografia na data base
     * 
     * @return chave de criptografia lida
     * @throws Exception
     */
    private String ler_chave () throws Exception {
        arq.seek(0);
        arq.readInt();
        return arq.readUTF();
    }

    /**
     * Cria registro no arquivo
     * 
     * @param pokemon novo registro a ser inserido
     * @throws Exception
     */
    public void criar (Pokemon pokemon) throws Exception {
        arq.seek(0);
        int id = arq.readInt();
        arq.readUTF();
        pokemon.setId(++id);

        arq.seek(0);//Acessou o inicio do arquivo
        arq.writeInt(id);//Reescreveu o inicio
        arq.seek(arq.length());//Apontando pro ultimo espaço do arquivo

        //Inclui arquivo na indexacao
        index.incluir_registro(id, arq.getFilePointer());

        //Escreve o registro
        arq.writeByte(' ');
        byte[] poke_vet = pokemon.toByteArray(criptografia);//Criou poke_vet_antigor de byte com as informacoes do pokemon
        arq.writeInt(poke_vet.length);//Escreveu o tamanho no arquivo
        arq.write(poke_vet);//Escreve as informaçoes pro arquivo
    }

    /**
     * Le registro do arquivo
     * 
     * @param id do registro requerido
     * @return o registro se encontrar ou null caso nao encontrar o registro
     * @throws Exception
     */
    public Pokemon ler (int id) throws Exception {
        Pokemon pokemon = new Pokemon();
        byte[] poke_vet_antigo;

        long endereco = index.buscar_registro(id);

        if (endereco != -1) {
            arq.seek(endereco);

            if (arq.readByte() == ' ') {
                poke_vet_antigo = new byte[arq.readInt()];
                arq.read(poke_vet_antigo);
                pokemon.fromByteArray(poke_vet_antigo, ler_chave(), criptografia);
                return pokemon;
            }
        }

        return null;
    }

    /**
     * Atualiza registro do arquivo
     * 
     * @param poke registro com informacoes atualizadas
     * @return se foi possivel atualizar ou nao
     * @throws Exception
     */
    public boolean atualizar(Pokemon poke) throws Exception {
        Pokemon pokemon = new Pokemon();
        byte[] poke_vet_antigo;
        byte[] poke_vet_atualizado;

        long endereco = index.buscar_registro(poke.getId());

        if (endereco != -1) {
            //Lendo registro antigo
            arq.seek(endereco);

            if (arq.readByte() == ' ') {
            
                poke_vet_antigo = new byte[arq.readInt()];
                arq.read(poke_vet_antigo);
                pokemon.fromByteArray(poke_vet_antigo, ler_chave(), criptografia);

                poke_vet_atualizado = poke.toByteArray(criptografia);

                arq.seek(endereco);

                //Verifica o tamanho do novo registro
                if(poke_vet_atualizado.length <= poke_vet_antigo.length){
                    arq.writeByte(' ');
                    arq.writeInt(poke_vet_antigo.length);
                } else {
                    arq.writeByte('*');

                    //Posiciona o ponteiro no final do arquivo
                    arq.seek(arq.length());

                    //Atualiza arquivos index
                    index.atualizar_registro(poke.getId(), arq.getFilePointer());

                    arq.writeByte(' ');
                    arq.writeInt(poke_vet_atualizado.length);
                }

                //Escreve o registro
                arq.write(poke_vet_atualizado);
                return true;
            }
        }

        return false;
    }
    
    /**
     * Excluir registro do arquivo
     * 
     * @param id do registro
     * @return se excluiu ou nao o registro
     * @throws Exception
     */
    public boolean excluir(int id) throws Exception{        
        long endereco = index.buscar_registro(id);

        if (endereco != -1) {
            arq.seek(endereco);
            arq.writeByte('*');
            return true;
        }

        return false;
    }
}

package trabalho_1;

import manipulacao_arquivo.Pokemon;
import trabalho_2.indexacao;

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

    public CRUD (String caminho_arq) throws Exception {
        arq = new RandomAccessFile(caminho_arq, "rw");
    }

    /*** OUTROS METODOS ***/
    /**
     * Cria registro no arquivo
     * 
     * @param pokemon novo registro a ser inserido
     * @throws Exception
     */
    public void criar (Pokemon pokemon) throws Exception {
        arq.seek(0);
        int id = arq.readInt();
        pokemon.setId(++id);//Gerar um id aleatorio

        arq.seek(0);//Acessou o inicio do arquivo
        arq.writeInt(id);//Reescreveu o inicio
        arq.seek(arq.length());//Apontando pro ultimo espaço do arquivo
        arq.writeByte(' ');//Lapde

        byte[]poke_vet = pokemon.toByteArray();//Criou poke_vet_antigor de byte com as informaçoes do pokemon
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

        long endereco = indexacao.ler_registro(id);

        if (endereco != -1) {
            poke_vet_antigo = new byte[arq.readInt()];
            arq.read(poke_vet_antigo);
            pokemon.fromByteArray(poke_vet_antigo);
            return pokemon;
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

        arq.seek(0);//Inicializa o ponteiro
        arq.readInt();

        while(arq.getFilePointer()<arq.length()){
            long lapide = arq.getFilePointer();

            if (arq.readByte() == ' ') { //Se não possuir uma lapide
                poke_vet_antigo = new byte[arq.readInt()];
                arq.read(poke_vet_antigo);
                pokemon.fromByteArray(poke_vet_antigo);

                if(poke.getId() == pokemon.getId()){
                    poke_vet_atualizado = poke.toByteArray();

                    arq.seek(lapide);

                    //Verifica o tamanho do novo registro
                    if(poke_vet_atualizado.length <= poke_vet_antigo.length){
                        arq.writeByte(' ');
                        arq.writeInt(poke_vet_antigo.length);
                    } else {
                        arq.writeByte('*');

                        //Posiciona o ponteiro no final do arquivo
                        arq.seek(arq.length());
                        arq.writeByte(' ');
                        arq.writeInt(poke_vet_atualizado.length);
                    }

                    //Escreve o registro
                    arq.write(poke_vet_atualizado);
                    return true;
                }
            } else{
                //Pula o registro
                arq.seek(arq.readInt() + arq.getFilePointer());
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
        Pokemon pokemon = new Pokemon();
        byte[]poke_vet_antigo;
        
        arq.seek(0);//Inicializa o ponteiro
        arq.readInt();

        while(arq.getFilePointer()<arq.length()){

            long ponteiro = arq.getFilePointer();
            if(arq.readByte() == ' '){
                poke_vet_antigo = new byte[arq.readInt()];
                arq.read(poke_vet_antigo);
                pokemon.fromByteArray(poke_vet_antigo);

                if(id == pokemon.getId()){
                    arq.seek(ponteiro);
                    arq.writeByte('*');
                    return true;
                }
            } else{
                //Pula o registro
                arq.seek(arq.readInt() + arq.getFilePointer());
            }
        }

        return false;
    }
}

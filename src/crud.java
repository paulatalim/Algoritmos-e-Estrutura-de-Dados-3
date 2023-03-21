import java.io.RandomAccessFile;


public class CRUD {
    private RandomAccessFile arq;

    /*** CONSTRUTOR ***/
    CRUD (String caminho_arq) throws Exception {
        arq = new RandomAccessFile(caminho_arq, "rw");
    }

    /*** OUTROS METODOS ***/
    //CREATE
    public void criar (Pokemon pokemon) throws Exception {
        arq.seek(0);
        int id = arq.readInt();
        pokemon.setId(++id);//Gerar um id aleatorio

        arq.seek(0);//Acessou o inicio do arquivo
        arq.writeInt(id);//Reescreveu o inicio
        arq.seek(arq.length());//Apontando pro ultimo espaço do arquivo
        arq.writeByte(' ');//Lapde

        byte[]poke_poke_vet_antigo_info = pokemon.toByteArray();//Criou poke_vet_antigoor de byte com as informaçoes do pokemon
        arq.writeInt(poke_poke_vet_antigo_info.length);//Escreveu o tamanho no arquivo
        arq.write(poke_poke_vet_antigo_info);//Escreve as informaçoes pro arquivo
    }

    //READ
    public Pokemon ler (int id) throws Exception {
        Pokemon pokemon = new Pokemon();
        byte[] poke_vet_antigo;

        arq.seek(0);//Inicializa o ponteiro
        arq.readInt();

        while(arq.getFilePointer() < arq.length()){
            
            if(arq.readByte() == ' '){
                poke_vet_antigo = new byte[arq.readInt()];
                arq.read(poke_vet_antigo);
                pokemon.fromByteArray(poke_vet_antigo);
                
                if(id == pokemon.getId()){
                    return pokemon;
                }
            } else {
                //Pula o registro
                arq.seek(arq.readInt() + arq.getFilePointer());
            }
        }
        return null;
    }

    public void listar_registros () throws Exception {
        Pokemon pokemon = new Pokemon();
        byte[] poke_vet_antigo;
        int cont = 0;

        arq.seek(0);
        arq.readInt();

        while (arq.getFilePointer() < arq.length()) {
            if (arq.readByte() == ' ') {
                poke_vet_antigo = new byte[arq.readInt()];
                arq.read(poke_vet_antigo);
                pokemon.fromByteArray(poke_vet_antigo);

                System.out.print(pokemon.getId() + " - " + pokemon.getNome());
                
                if (cont < 2) {
                    System.out.print("\t\t\t\t");
                    cont++;
                } else {
                    System.out.println();
                    cont = 0;
                }
            } else {
                //Pula o registro
                arq.seek(arq.readInt() + arq.getFilePointer());
            }
        }
    }
    //UPDATE
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
    
    //DELETE
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

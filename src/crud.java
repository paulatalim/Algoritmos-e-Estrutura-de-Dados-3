import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;
import java.io.IOException;
import java.io.RandomAccessFile;


public class crud{

//Acessar o arquivo
private static RandomAccessFile arq; 
crud()throws Exception {
    arq = new RandomAccessFile("src/pokedex.db", "rw");
}

    //CREATE
    public void criar(Pokemon pokemon) throws Exception {
        arq.seek(0);
        int id = arq.readInt();
        pokemon.setId(id);//Gerar um id aleatorio

        arq.seek(0);//Acessou o inicio do arquivo
        arq.writeInt(++id);//Reescreveu o inicio
        arq.seek(arq.length());//Apontando pro ultimo espaço do arquivo
        arq.writeByte(' ');//Lapde

        byte[]pokvet=pokemon.toByteArray();//Criou vetor de byte com as informaçoes do pokemon
        arq.writeInt(pokvet.length);//Escreveu o tamanho no arquivo
        arq.write(pokvet);//Escreve as informaçoes pro arquivo
    }

    //READ
    public static Pokemon ler (int id) throws Exception {

        Pokemon pokemon = new Pokemon();
        byte[]vet;

        arq.seek(0);//Inicializa o ponteiro
        arq.readInt();
        while(arq.getFilePointer()<arq.length()){
            if(arq.readByte()==' '){
                vet=new byte[arq.readInt()];
                arq.read(vet);
                pokemon.fromByteArray(vet);
                if(id == pokemon.getId()){
                    return pokemon;
                }
            }
            else{
                //Pula o registro
                arq.seek(arq.readInt()+arq.getFilePointer());
            }
        }
        return pokemon;
    }

 
    //UPDATE
    public boolean atualizar(Pokemon poke) throws Exception {

        Pokemon pokemon = new Pokemon();
        byte[]vet;
        byte[]vet2;

        arq.seek(0);//Inicializa o ponteiro
        arq.readInt();

        while(arq.getFilePointer()<arq.length()){
            long lapide = arq.getFilePointer();
            if(arq.readByte()==' '){ //Se não possuir uma lapide
                vet=new byte[arq.readInt()];
                arq.read(vet);
                pokemon.fromByteArray(vet);

                if(poke.getId() == pokemon.getId()){
                    vet2 = poke.toByteArray();
                    if(vet2.length<=vet.length){
                        arq.seek(lapide);
                        arq.writeByte(' ');
                        arq.writeInt(vet2.length);
                        arq.write(vet2);
                    } else {
                        arq.seek(lapide);
                        arq.writeByte('*');
                        arq.seek(arq.length());
                        arq.writeByte(' ');
                        arq.writeInt(vet2.length);
                        arq.write(vet2);
                    }
                    return true;
                }
            }

        }
       return false;
    }
    

    //DELETE
    public void excluir(int id) throws Exception{
        Pokemon pokemon = new Pokemon();
        byte[]vet;
        
        arq.seek(0);//Inicializa o ponteiro
        arq.readInt();

        while(arq.getFilePointer()<arq.length()){

            long ponteiro = arq.getFilePointer();
            if(arq.readByte()==' '){
                vet=new byte[arq.readInt()];
                arq.read(vet);
                pokemon.fromByteArray(vet);
                if(id == pokemon.getId()){
                    arq.seek(ponteiro);
                    arq.writeByte('*');
                }
            }
            else{
                //Pula o registro
                arq.seek(arq.readInt()+arq.getFilePointer());
            }
        }

        System.out.println("Pokemon excluido com sucesso");
}

   
}

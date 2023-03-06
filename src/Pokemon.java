import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;
//import java.text.SimpleDateFormat;

public class Pokemon {
    private int id;
    private String nome;
    private Date data_de_registro;


    private int idade;
    private char genre;

    // CONSTRUTOR
    Pokemon (int id, String nome, int idade, char genre) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.genre = genre;

        data_de_registro = new Date(System.currentTimeMillis());
    }

    /*
     * Descricao: essa funcao elabora um vetor de bytes com 
     * seus atributos para a insercao no arquivo
     * Retorno: vetor de bytes
     */
    public byte[] toByteArray () throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeInt(idade);
        dos.writeChar(genre);

        dos.writeLong(data_de_registro.getTime());

		//Retorno do vetor de bytes para escrever no arquivo
        return baos.toByteArray();
    }

    /*
     * Descricao: essa funcao, a partir de um vetor de 
     * bytes, preenche os atributos da classe
     * Parametro: vetor de bytes
     */
    public void fromByteArray (byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        id = dis.readInt();
        nome = dis.readUTF();

        data_de_registro = new Date(dis.readLong());

        // idLivro = dis.readInt();
        // titulo = dis.readUTF();
        // autor = dis.readUTF();
        // preco = dis.readFloat();
    }

}

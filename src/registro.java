import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class registro {
    private String nome;
    private int idade;
    private char genre;

    // CONSTRUTOR
    registro (String nome, int idade, char genre) {
        this.nome = nome;
        this.idade = idade;
        this.genre = genre;
    }

    /*
     * Descricao: essa funcao elabora um vetor de bytes com 
     * seus atributos para a insercao no arquivo
     * Retorno: vetor de bytes
     */
    public byte[] toByteArray () throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        dos.writeUTF(nome);
        dos.writeInt(idade);
        dos.writeChar(genre);

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

        nome = dis.readUTF();
        // idLivro = dis.readInt();
        // titulo = dis.readUTF();
        // autor = dis.readUTF();
        // preco = dis.readFloat();
    }

}

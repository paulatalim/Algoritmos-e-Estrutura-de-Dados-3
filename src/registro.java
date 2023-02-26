//import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class registro {
    private String nome;
    private int idade;
    private char genre;

    registro (String nome, int idade, char genre) {
      this.nome = nome;
      this.idade = idade;
      this. genre = genre;
    }

    public byte[] toByteArray () throws IOException {
		//classe que fazem operaçõoes para memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        dos.writeUTF(nome);
        dos.writeInt(idade);
        dos.writeChar(genre);

		//Retorno do vetor de bytes para escrever no arquivo
        return baos.toByteArray();
  }

}

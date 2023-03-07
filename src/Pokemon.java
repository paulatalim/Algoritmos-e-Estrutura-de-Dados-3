import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Pokemon {
    private int id;
    private int num_pokedex;
    private String nome;
    private int geracao;
    private String especie;
    private float altura;
    private float peso;
    private String tipo1;
    private String tipo2;
    private int hp;
    private int ataque;
    private int defesa;
    private int ataque_especial;
    private int defesa_especial;
    private int velocidade;
    private boolean eh_mistico;
    private boolean eh_lendario;
    private Date data_de_registro;

    private int idade;
    private char genre;

    // CONSTRUTOR
    Pokemon () {
        id = -1;
        num_pokedex = -1;
        nome = " ";
        tipo1 = " ";
        tipo2 = " ";
        eh_mistico = false;
        eh_lendario = false;
        data_de_registro = new Date();
    }

    Pokemon (int id, String nome, int idade, char genre) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.genre = genre;

        data_de_registro = new Date();
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

    /*
     * Descricao: esse metodo escreve na tela do 
     * console os atributos do pokemon
     */
    public void exibir_pokemon () {


        //Escreve a data
        SimpleDateFormat formatar_data = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatar_horario = new SimpleDateFormat("hh:mm:ss");
        System.out.println("Criacao do registro do pokemon");
        System.out.println("Data: " + formatar_data.format(data_de_registro));
        System.out.println("Horario: " + formatar_horario.format(data_de_registro));
    }

}

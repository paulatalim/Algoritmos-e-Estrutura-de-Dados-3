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

    // CONSTRUTOR
    Pokemon () {
        id = -1;
        num_pokedex = -1;
        nome = "null";
        geracao = -1;
        especie = "null";
        altura = -1;
        peso = -1;
        tipo1 = "null";
        tipo2 = "null";
        hp = -1;
        ataque = -1;
        defesa = -1;
        ataque_especial = -1;
        defesa_especial = -1;
        velocidade = -1;
        eh_mistico = false;
        eh_lendario = false;
        data_de_registro = new Date();
    }

    Pokemon (int id, int num_pokedex, String nome, int geracao,
            String especie, float altura, float peso, String tipo1,
            String tipo2, int hp, int ataque, int defesa, int ataque_especial,
            int defesa_especial, int velocidade, boolean eh_mistico, boolean eh_lendario) 
    {
        this.id = id;
        this.nome = nome;
        this.geracao = geracao;
        this.especie = especie;
        this.altura = altura;
        this.peso = peso;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.hp = hp;
        this.ataque = ataque;
        this.defesa = defesa;
        this.ataque_especial = ataque_especial;
        this.defesa_especial = defesa_especial;
        this.velocidade = velocidade;
        this.eh_mistico = eh_mistico;
        this.eh_lendario = eh_lendario;

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
        dos.writeInt(num_pokedex);
        dos.writeUTF(nome);
        dos.writeInt(geracao);
        dos.writeUTF(especie);
        dos.writeFloat(altura);
        dos.writeFloat(peso);
        dos.writeUTF(tipo1 + "-" + tipo2);
        dos.writeInt(hp);
        dos.writeInt(ataque);
        dos.writeInt(defesa);
        dos.writeInt(ataque_especial);
        dos.writeInt(defesa_especial);
        dos.writeInt(velocidade);
        dos.writeBoolean(eh_mistico);
        dos.writeBoolean(eh_lendario);
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
        String[] types;

        id = dis.readInt();
        num_pokedex = dis.readInt();
        nome = dis.readUTF();
        
        geracao= dis.readInt();
        especie = dis.readUTF();
        altura = dis.readFloat();
        peso = dis.readFloat();
        types = dis.readUTF().split("-");
        
        hp = dis.readInt();
        ataque = dis.readInt();
        defesa = dis.readInt();
        ataque_especial = dis.readInt();
        defesa_especial = dis.readInt();
        velocidade = dis.readInt();
        eh_mistico = dis.readBoolean();
        eh_lendario = dis.readBoolean();

        data_de_registro = new Date(dis.readLong());

        tipo1 = types[0];
        tipo2 = types[1];
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

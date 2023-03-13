import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Pokemon {
    private int id;
    private float id_secundario;
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
        id_secundario = 0;
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
        id_secundario = id * 0.00001f;
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

    /*** METODOS GET E SET***/
    //id
    public int getid() {
        return id;
    }

    public void setNome(int id) {
        this.id = id;
    }

    //id_secundario
    public float getIdSecundario () {
        return id_secundario;
    }

    public void setIdSecundario (float id_secundario) {
        this.id_secundario = id_secundario;
    }

    //num_pokedex
    public int getnum_pokedex() {
        return num_pokedex;
    }

    public void setnum_pokedex(int num_pokedex) {
        this.num_pokedex = num_pokedex;
    }

    //nome
    public String getnome() {
        return nome;
    }

    public void setnome(String nome) {
        this.nome = nome;
    }

    //geracao
    public int getgeracao() {
        return geracao;
    }

    public void setgeracao(int geracao) {
        this.geracao = geracao;
    }

    //especie
    public String getespecie() {
        return especie;
    }
    
    public void setespecie (String especie) {
        this.especie = especie;
    }

    //altura
    public float getaltura() {
        return altura;
    }

    public void setaltura(float altura) {
        this.altura = altura;
    }

    //peso
    public float getpeso() {
        return peso;
    }

    public void setpeso(float peso) {
        this.peso= peso;
    }

    //tipo1
    public String gettipo1() {
        return tipo1;
    }

    public void settipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    //tipo2
    public String gettipo2() {
        return tipo2;
    }

    public void settipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    //hp
    public int gethp() {
        return hp;
    }

    public void sethp(int  hp) {
        this. hp = hp;
    }

    //ataque
    public int getataque() {
        return ataque;
    }

    public void setataque(int ataque) {
        this.ataque = ataque;
    }

    //defesa
    public int getdefesa() {
        return defesa;
    }

    public void setdefesa(int defesa) {
        this.defesa = defesa;
    }

    //ataque_especial
    public int getataque_especial() {
        return ataque_especial;
    }

    public void setataque_especial(int ataque_especial) {
        this.ataque_especial = ataque_especial;
    }

    //defesa_especial
    public int getdefesa_especial() {
        return defesa_especial;
    }

    public void setdefesa_especial(int defesa_especial) {
        this.defesa_especial = defesa_especial;
    }

    //velocidade
    public int getvelocidade() {
        return velocidade;
    }

    public void setvelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    //eh_mistico
    public boolean geteh_mistico() {
        return eh_mistico;
    }

    public void seteh_mistico(boolean eh_mistico) {
        this.eh_mistico = eh_mistico;
    }

     //eh_lendario
    public boolean geteh_lendario() {
        return eh_lendario;
    }

    public void seteh_lendario(boolean eh_lendario) {
        this.eh_lendario = eh_lendario;
    }

    //data_de_registro
    public Date getdata_de_registro() {
        return data_de_registro;
    }

    public void setdata_de_registro(Date data_de_registro) {
        this.data_de_registro = data_de_registro;
    }
    

    /*** OUTROS METODOS ***/
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
        String type;

        //Le o arquivo
        id = dis.readInt();
        id_secundario = id * 0.00001f;
        num_pokedex = dis.readInt();
        nome = dis.readUTF();
        
        geracao= dis.readInt();
        especie = dis.readUTF();
        altura = dis.readFloat();
        peso = dis.readFloat();
        type = dis.readUTF();
        
        hp = dis.readInt();
        ataque = dis.readInt();
        defesa = dis.readInt();
        ataque_especial = dis.readInt();
        defesa_especial = dis.readInt();
        velocidade = dis.readInt();
        eh_mistico = dis.readBoolean();
        eh_lendario = dis.readBoolean();

        data_de_registro = new Date(dis.readLong());

        //Atribui os valores do tipo
        types = type.split("-");
        tipo1 = types[0];

        //Verifica se o pokemon possui tipo 2
        if (types.length > 1)
            tipo2 = types[1];
    }

    /*
     * Descricao: esse metodo escreve na tela do 
     * console os atributos do pokemon
     */
    public void exibir_pokemon () {

        System.out.println("Nome: " + this.nome);
        System.out.println("Numero na Pokédex: " + this.num_pokedex);
        System.out.println("Geração: " + this.geracao);
        System.out.println("Espécie: " + this.especie);
        System.out.println("Altura: " + this.altura);
        System.out.println("Peso: " + this.peso);
        System.out.println("Tipo 1: " + this.tipo1);
        System.out.println("Tipo 2: " + this.tipo2);
        System.out.println("HP: " + this.hp);
        System.out.println("Ataque: " + this.ataque);
        System.out.println("Desefa: " + this.defesa);
        System.out.println("Ataque Especial: " + this.ataque_especial);
        System.out.println("Desefa Especial: " + this.defesa_especial);
        System.out.println("Velocidade: " + this.velocidade);
        if(eh_mistico == true){
            System.out.println("Místico");
        }
        if(eh_lendario == true){
            System.out.println("Lendário");
        }

        //Escreve a data
        SimpleDateFormat formatar_data = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatar_horario = new SimpleDateFormat("hh:mm:ss");
        System.out.println("Criacao do registro do pokemon");
        System.out.println("Data: " + formatar_data.format(data_de_registro));
        System.out.println("Horario: " + formatar_horario.format(data_de_registro));
    }

}

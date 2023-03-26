import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;

public class Pokemon extends Object implements Cloneable {
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

    /*** CONSTRUTORES ***/
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
        this.num_pokedex = num_pokedex;
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

    public String toString () {
        SimpleDateFormat formatar_data = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatar_horario = new SimpleDateFormat("hh:mm:ss");

        String str = "\n\t\t\t\t\t\t" + "*** " + nome.toUpperCase()   +   " ***" + "\n"
                    + "\n\t" + "Informacoes basicas".toUpperCase() + "\n"
                    + "\t" + "Numero na Pokedex: " + num_pokedex + "\n"
                    + "\t" + "Geracao: " + geracao + "\n"
                    + "\t" + "Especie: " + especie + "\n"
                    + "\t" + "Altura: " + String.format("%.2f", altura) + " m" + "\n"
                    + "\t" + "Peso: ";
        
        //Verifica se o pokemon possui peso
        if (peso != -1) {
            str += String.format("%.2f", peso) + " kg";
        } else {
            //Peso desconhecido
            str += "???";
        }

        str += "\n\t" + "Tipo 1: " + tipo1 + "\n";

        //Verifica se ha tipo 2
        if (tipo2.compareTo("null") != 0) {
            str += "\t" + "Tipo 2: " + tipo2 + "\n";
        }

        str += "\n\t" + "Estatisticas basicas".toUpperCase()+ "\n"
            + "\t" + "HP: " + hp + "\n"
            + "\t" + "Ataque: " + ataque + "\n"
            + "\t" + "Defesa: " + defesa + "\n"
            + "\t" + "Ataque Especial: " + ataque_especial + "\n"
            + "\t" + "Defesa Especial: " + defesa_especial + "\n"
            + "\t" + "Velocidade: " + velocidade + "\n";

        if(eh_mistico){
            str += "\n\t" + "Esse pokemon e mistico !!!" + "\n";
        } else if (eh_lendario){
            str += "\n\t" + "Esse pokemon e lendario !!!" + "\n";
        }
        
        str += "\n\t" + "Criacao do registro".toUpperCase() + "\n"
            + "\t" + "Data: " + formatar_data.format(data_de_registro) + "\n"
            + "\t" + "Horario: " + formatar_horario.format(data_de_registro) + "\n";

        return str;
    }

    @Override
	public Object clone () throws CloneNotSupportedException{  
	    return super.clone();  
	}

    /*** METODOS GET E SET ***/
    //id
    public int getId() {
        return id;
    }

    public void setId (int id) {
        this. id = id;
    }

    //Numero da Pokedex
    public int getNumPokedex() {
        return num_pokedex;
    }

    public void setNumPokedex(int num_pokedex) {
        this.num_pokedex = num_pokedex;
    }

    //Nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //Geracao
    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    //Especie
    public String getEspecie() {
        return especie;
    }
    
    public void setEspecie (String especie) {
        this.especie = especie;
    }

    //Altura
    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    //Peso
    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    //Tipo 1
    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    //Tipo 2
    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    //HP
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    //Ataque
    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    //Defesa
    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    //Ataque Especial
    public int getAtaqueEspecial() {
        return ataque_especial;
    }

    public void setAtaqueEspecial(int ataque_especial) {
        this.ataque_especial = ataque_especial;
    }

    //Defesa Especial
    public int getDefesaEspecial() {
        return defesa_especial;
    }

    public void setDefesaEspecial(int defesa_especial) {
        this.defesa_especial = defesa_especial;
    }

    //Velocidade
    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    //Eh Mistico
    public boolean getEhMistico() {
        return eh_mistico;
    }

    public void setEhMistico(boolean eh_mistico) {
        this.eh_mistico = eh_mistico;
    }

     //Eh lendario
    public boolean getEhLendario() {
        return eh_lendario;
    }

    public void setEhLendario(boolean eh_lendario) {
        this.eh_lendario = eh_lendario;
    }

    //Data de registro
    public Date getDataDeRegistro() {
        return data_de_registro;
    }

    public void setDataDeRegistro(Date data_de_registro) {
        this.data_de_registro = data_de_registro;
    }
}

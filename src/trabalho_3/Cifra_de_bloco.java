package trabalho_3;

public class Cifra_de_bloco {
    public static void criar_chave () {}

    public static void cifrar (String mensagem) {
        byte key = (byte) 0xFFFF;
        byte item = (byte) 0xF00F; 
        int msg;

        // if (
            
        msg = (int)key ^ (int)item;
    }
}

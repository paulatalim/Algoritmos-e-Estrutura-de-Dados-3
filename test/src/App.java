import java.math.BigDecimal;

public class App {
    public static void main(String[] args) throws Exception {
        String mensagem = "Paula";

        byte key = (byte) 0xFFFF;
        byte item = (byte) 0xF00F; 
        int msg;

        // if (
            
        msg = (int)key ^ (int)item;

        System.out.println(msg);
    }
}

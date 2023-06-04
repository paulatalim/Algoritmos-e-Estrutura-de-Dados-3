import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class App {
    public static byte[] toByteArray (int[] vet) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        for (int i = 0; i < vet.length; i++) {
            dos.writeInt(vet[i]);
        }

		//Retorno do vetor de bytes para escrever no arquivo
        return baos.toByteArray();
    }

    public static int[] fromByteArray (byte[] vet) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(vet);
        DataInputStream dis = new DataInputStream(bais);
        int[] vet1 = new int [10];

        for (int i = 0; bais.available() > 0; i++) {
            vet1[i] = dis.readInt();
            System.out.println(vet1[i]);
        }

        return vet1;
    }

    public static void main(String[] args) throws Exception {
    //     String mensagem = "Paula Talim";

    //     byte[] vet = mensagem.getBytes();
    //     int[] vetr = new int[8];

    //     byte key = (byte) 0xFF;
    //     byte item; 
    //     int msg = vet.length;
    //     String result = "";

    //     for ( int i = 0; i < 8; i ++) {
    //         //item = vet[i];

    //         vetr[i] =  (int)key ^ (int)vet[i];
    //         // result += Integer.toString(msg);

    //         // System.out.print(vetr[i] + "\t");

    //     }

    //     vet = toByteArray(vetr);
    //     byte[] vet1 = toByteArray(vetr);
    //     int tam = vet1.length;

    //     for (int i = 0; i < vet1.length; i ++) {
    //         System.out.print(vet1[i] + "\t" );
    //     }

    //     String res = new String(vet1);
    //     System.out.println("\n" + res);

    //     vet1 = res.getBytes();
    //     for (int i = 0; i < vet1.length; i++) {
    //         System.out.print(vet1[i] + "\t");
    //     }
    //     // System.out.println(res.getBytes());

    //    // int[] vet2 = fromByteArray(vet1);

        String a = "��������-����E����+";
        byte[] b = a.getBytes();

        String msg = new String(b, StandardCharsets.UTF_8);
        byte[] vet = msg.getBytes(StandardCharsets.UTF_8);
        String novo = new String(vet, StandardCharsets.UTF_8);

        System.out.println(a);
        System.out.println(msg);
        
        if (a.equals(msg)) {
            System.out.println("1");
        }

        if (msg.equals(novo)) {
            System.out.println("2");
        }
    }
    
}

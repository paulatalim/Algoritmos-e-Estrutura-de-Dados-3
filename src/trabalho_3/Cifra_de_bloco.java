package trabalho_3;

import java.util.Random;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Cifra_de_bloco {
    private int tamanho_bloco;
    private int tamanho_bloco_cifrado;
    private byte[] key;

    Cifra_de_bloco () {
        tamanho_bloco = 8;
        tamanho_bloco_cifrado = tamanho_bloco * 4;
        criar_chave();
    }

    private void criar_chave () {
        Random rand = new Random();
        key = new byte [tamanho_bloco];

        // Preenche o vetor key
        for (int i = 0; i < tamanho_bloco; i++) {
            //Gera um byte aleatorio e armazena na chave
            key[i] = (byte) (rand.nextInt(255) - 127);
        }
    }

    private String byteToString (byte[] vet) {
        String resultado = "";

        for (int i = 0; i < vet.length; i++) {
            resultado += Character.toString((char) vet[i]);
        }

        return resultado;
    }

    private byte[] stringToByteArray (String str) {
        byte[] vet = new byte[str.length()];

        for (int i = 0; i < str.length(); i++) {
            vet[i] = (byte) str.charAt(i);
        }

        return vet;
    }

    private byte[] toByteArray (int[] vet) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        for (int i = 0; i < vet.length; i++) {
            dos.writeInt(vet[i]);
        }

		//Retorno do vetor de bytes para escrever no arquivo
        return baos.toByteArray();
    }

    public int[] fromByteArray (byte[] vet) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(vet);
        DataInputStream dis = new DataInputStream(bais);
        ArrayList<Integer> vet_r = new ArrayList<Integer>();

        // Conversao do vetor de byte para vetor de int
        for (int i = 0; bais.available() > 0; i++) {
            vet_r.add(i, dis.readInt());
        }

        // Conversao do ArrayList para vetor de int
        int[] vet3 = vet_r.stream().mapToInt(i -> i).toArray();


        return vet3;
        
    }

    private byte[] cifrar_bloco (byte[] bloco) throws IOException {
        int[] bloco_cifrado = new int[bloco.length];

        // Cifragem do bloco
        for (int i = 0; i < bloco.length; i ++) {
            int result = (int)key[i] ^ (int)bloco[i];
            if ((result ^ (int)key[i]) < 48 || (result ^ (int)key[i]) > 57) {
                System.out.println("oi");
            }

            bloco_cifrado[i] = (int)key[i] ^ (int)bloco[i];
        }

        byte[] result1 = toByteArray(bloco_cifrado);
        byte[] result2 = decifrar_bloco(result1);

        return result1;
    }

    private byte[] decifrar_bloco (byte[] bloco_cifrado) throws IOException {
        int[] vet = fromByteArray(bloco_cifrado);
        byte[] result = new byte[vet.length];

        for (int i = 0; i < vet.length; i++) {
            int confere1 = (int)key[i] ^ vet[i];
            int condere2 = (int)key[i] ^ confere1;
            if (confere1 < 48 || confere1 > 57) {
                System.out.println("oi");
            }
            System.out.println(confere1 + " - " + condere2 + " - " + vet[i]);

            result[i] = (byte) ((int)key[i] ^ vet[i]);
        }

        return result;
    }

    public String cifrar (String mensagem) throws IOException {
        // byte[] vet = mensagem.getBytes(StandardCharsets.UTF_8);
        byte[] vet = stringToByteArray(mensagem);
        byte[] bloco;
        String mensagem_cifrada = "";

        // Cifra mensagem
        for (int i = 0; i < vet.length; i += tamanho_bloco) {
            // Reseta o vetor do bloco
            if (vet.length - i >= tamanho_bloco) {
                bloco = new byte[tamanho_bloco];
            } else {
                bloco = new byte[vet.length - i];
            }
            
            //Preenche o bloco
            for (int j = 0; j < bloco.length && (i + j) < vet.length; j++) {
                bloco[j] = vet[i + j];
            }

            //Cifra o bloco
            mensagem_cifrada += byteToString(cifrar_bloco(bloco));

            // new String (cifrar_bloco(bloco), StandardCharsets.UTF_8);

            // byte[] vet1 = mensagem_cifrada.getBytes(StandardCharsets.UTF_8);
            // String test = new String(vet1, StandardCharsets.UTF_8);

            // if (mensagem_cifrada.toString().compareTo(test.toString()) != 0) {
            //     System.out.println(mensagem_cifrada);
            //     System.out.println(test);
            //     System.out.println("oi");
            // }
            
            // if (!mensagem_cifrada.equals(test)) {
            //     System.out.println("oi");
            // }
            // decifrar(mensagem_cifrada);
        }

        return mensagem_cifrada;
    }

    public String decifrar (String mensagem) throws IOException {
        // byte[] vet = mensagem.getBytes(StandardCharsets.UTF_8);
        byte[] vet = stringToByteArray(mensagem);
        byte[] bloco;
        String mensagem_decifrada = "";

        for (int i = 0; i < vet.length ; i += tamanho_bloco_cifrado) {
            // Reseta o vetor do bloco
            if (vet.length - i >= tamanho_bloco_cifrado) {
                bloco = new byte[tamanho_bloco_cifrado];
            } else {
                bloco = new byte[vet.length - i];
            }

            for (int j = 0; j < bloco.length && (i + j) < vet.length; j++) {
                bloco[j] = vet[i + j];
            }

            if (i == 384) {
                System.out.println("oi");
            }

            System.out.println(i);

            mensagem_decifrada += byteToString(decifrar_bloco(bloco));
            // mensagem_decifrada += new String(decifrar_bloco(bloco), StandardCharsets.UTF_8);
        }

        return mensagem_decifrada;
    }
}

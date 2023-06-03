package trabalho_3;

import java.util.Random;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
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
            bloco_cifrado[i] = (int)key[i] ^ (int)bloco[i];
        }

        return toByteArray(bloco_cifrado);
    }

    public String cifrar (String mensagem) throws IOException {
        byte[] vet = mensagem.getBytes();

        //Separacao dos blocos
        byte[] bloco = new byte [tamanho_bloco];

        String mensagem_cifrada = "";

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
            mensagem_cifrada += new String (cifrar_bloco(bloco));
        }

        return mensagem_cifrada;
    }

    private byte[] decifrar_bloco (byte[] bloco_cifrado) throws IOException {
        int[] vet = fromByteArray(bloco_cifrado);
        byte[] result = new byte[vet.length];

        for (int i = 0; i < vet.length; i++) {
            result[i] = (byte) ((int)key[i] ^ vet[i]);
        }

        return result;
    }

    public String decifrar (String mensagem) throws IOException {
        byte[] vet = mensagem.getBytes();
        byte[] bloco = new byte [tamanho_bloco_cifrado];
        String mensagem_decifrada = "";

        for (int i = 0; i < vet.length; i += tamanho_bloco_cifrado) {
            // Reseta o vetor do bloco
            if (vet.length - i >= tamanho_bloco_cifrado) {
                bloco = new byte[tamanho_bloco_cifrado];
            } else {
                bloco = new byte[vet.length - i];
            }

            for (int j = 0; j < bloco.length; j++) {
                bloco[j] = vet[i + j];
            }

            mensagem_decifrada += new String(decifrar_bloco(bloco));
        }

        return mensagem_decifrada;
    }

    

    
}

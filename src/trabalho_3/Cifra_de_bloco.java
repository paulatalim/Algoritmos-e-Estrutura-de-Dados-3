package trabalho_3;

import java.util.Random;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Classe do algoritimo Cifra de Bloco para criptografar texto
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class Cifra_de_bloco {
    private int tamanho_bloco;
    private int tamanho_bloco_cifrado;
    private byte[] key;

    /**
     * Construtor da classe
     */
    Cifra_de_bloco () {
        tamanho_bloco = 8;
        tamanho_bloco_cifrado = tamanho_bloco * 4;
        criar_chave();
    }

    /**
     * Gera uma chave aleatoria de 64 bits
     */
    private void criar_chave () {
        Random rand = new Random();
        key = new byte [tamanho_bloco];

        // Preenche o vetor key
        for (int i = 0; i < tamanho_bloco; i++) {
            //Gera um byte aleatorio e armazena na chave
            key[i] = (byte) (rand.nextInt(255) - 127);
        }
    }

    /**
     * Converte um vetor de bytes para uma string
     * 
     * @param array a ser convertido
     * @return string com o vetor de bytes convetido
     */
    private String byteToString (byte[] array) {
        String resultado = "";

        for (int i = 0; i < array.length; i++) {
            resultado += Character.toString((char) array[i]);
        }

        return resultado;
    }

    /**
     * Converte uma string para um vetor de bytes
     * 
     * @param str string a ser convetida
     * @return vetor de bytes convertido
     */
    private byte[] stringToByteArray (String str) {
        byte[] array = new byte[str.length()];

        for (int i = 0; i < str.length(); i++) {
            array[i] = (byte) str.charAt(i);
        }

        return array;
    }

    /**
     * Transforma um vetor de int em um vetor de byte
     * 
     * @param vet de inteiros a ser convertido
     * @return vetor de bytes
     * @throws IOException
     */
    private byte[] toByteArray (int[] vet) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        for (int i = 0; i < vet.length; i++) {
            dos.writeInt(vet[i]);
        }

		//Retorno do vetor de bytes para escrever no arquivo
        return baos.toByteArray();
    }

    /**
     * Converte um vetor de byte em um vetor de inteiros
     * 
     * @param vet byte a ser convertido
     * @return vetor de inteiros convertido
     * @throws IOException
     */
    public int[] fromByteArray (byte[] vet) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(vet);
        DataInputStream dis = new DataInputStream(bais);
        int[] vet_lido = new int[vet.length/4];

        // Conversao do vetor de byte para vetor de int
        for (int i = 0; bais.available() > 0; i++) {
            vet_lido[i] = dis.readInt();
        }

        return vet_lido;
    }

    /**
     * Cifra um bloco de 64 bits
     * 
     * @param bloco a ser cifrado
     * @return bloco cifrado em um vetor de bytes
     * @throws IOException
     */
    private byte[] cifrar_bloco (byte[] bloco) throws IOException {
        int[] bloco_cifrado = new int[bloco.length];

        // Cifragem do bloco
        for (int i = 0; i < bloco.length; i ++) {
            bloco_cifrado[i] = (int)key[i] ^ (int)bloco[i];
        }

        return toByteArray(bloco_cifrado);
    }

    /**
     * Decifra um bloco de 64 bits
     * 
     * @param bloco_cifrado a ser decifrado
     * @return bloco decifrado em um vetor de bytes
     * @throws IOException
     */
    private byte[] decifrar_bloco (byte[] bloco_cifrado) throws IOException {
        int[] bloco_cifrado_convertido = fromByteArray(bloco_cifrado);
        byte[] bloco_decifrado = new byte[bloco_cifrado_convertido.length];

        // Decifra o bloco
        for (int i = 0; i < bloco_cifrado_convertido.length; i++) {
            bloco_decifrado[i] = (byte) ((int)key[i] ^ bloco_cifrado_convertido[i]);
        }

        return bloco_decifrado;
    }

    /**
     * Cifra uma string
     * 
     * @param mensagem a ser cifrada
     * @return mensagem cifrada em uma string
     * @throws IOException
     */
    public String cifrar (String mensagem) throws IOException {
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
        }

        return mensagem_cifrada;
    }

    /**
     * Decifra uma string
     * 
     * @param mensagem cifrada
     * @return mensagem decifrada
     * @throws IOException
     */
    public String decifrar (String mensagem) throws IOException {
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

            // Preenche o bloco
            for (int j = 0; j < bloco.length && (i + j) < vet.length; j++) {
                bloco[j] = vet[i + j];
            }

            // Decifra o bloco e adiciona na saida final
            mensagem_decifrada += byteToString(decifrar_bloco(bloco));
        }

        return mensagem_decifrada;
    }
}

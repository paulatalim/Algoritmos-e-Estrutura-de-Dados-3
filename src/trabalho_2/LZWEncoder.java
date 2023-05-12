package trabalho_2;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class LZWEncoder {
    private Map<String, Integer> dicionario;
    private int proximoIndice;

    public LZWEncoder(char[] dicionario) {
        this.dicionario = new HashMap<>();
        for (int i = 0; i < dicionario.length; i++) {
            this.dicionario.put(Character.toString(dicionario[i]), i);
        }
        this.proximoIndice = dicionario.length;
    }

    //Coloca o indice da compactação no arquivo de saida
    public void codificar(FileInputStream entrada, FileOutputStream saida) throws IOException {
        // Cria um buffer de leitura para o arquivo de entrada
        BufferedInputStream bufferEntrada = new BufferedInputStream(entrada);

        // Cria um buffer de escrita para o arquivo de saída
        BufferedOutputStream bufferSaida = new BufferedOutputStream(saida);

        // Inicializa a string atual com o primeiro caractere do arquivo de entrada
        String atual = Character.toString((char) bufferEntrada.read());

        // Enquanto houver dados de entrada
        int proximoCaractere;
        while ((proximoCaractere = bufferEntrada.read()) != -1) {
            // Converte o próximo caractere para uma string
            String proximo = Character.toString((char) proximoCaractere);

            // Se a string atual + o próximo caractere estiverem no dicionário, atualiza a string atual
            if (dicionario.containsKey(atual + proximo)) {
                atual = atual + proximo;
            }
            // Senão, grava o índice da string atual no arquivo de saída, adiciona a string atual + próximo
            // caractere ao dicionário e atualiza a string atual para o próximo caractere
            else {
                bufferSaida.write(criarCodigo(atual));
                dicionario.put(atual + proximo,proximoIndice++);
            }
        }
    
        // Grava o código final da string atual no arquivo de saída
        bufferSaida.write(criarCodigo(atual));
    
        // Descarrega o buffer de escrita e fecha o arquivo de saída
        bufferSaida.flush();
    }
    
    private int criarCodigo(String s) {
        return dicionario.get(s);
    }     

}

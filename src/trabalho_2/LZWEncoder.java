package trabalho_2;

import java.util.HashMap;
// import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class LZWEncoder {
    private HashMap<String, Integer> dicionario;
    private int proximoIndice;

    public LZWEncoder() {
        //Cria objeto do dicionario
        dicionario = new HashMap<>();

        //Preenche o dicionario
        preencher_dicionario_inicial();

        // Obtém o índice do último elemento do dicionário atual
        int ultimoIndice = 0;
        for (int indice : this.dicionario.values()) {
            if (indice > ultimoIndice) {
                ultimoIndice = indice;
            }
        }

        this.proximoIndice = ultimoIndice + 1; // Define o próximo índice disponível
    }

    private void preencher_dicionario_inicial () {
        int i;
    
        // Adiciona as letras maiusculas
        for (i = 0; i < 26; i++) {
            this.dicionario.put(Character.toString((char)('A' + i)), i);
        }

        // Adiciona as letras minúsculas
        for (i = 26; i < 52; i++){
            this.dicionario.put(Character.toString((char)('a' + i - 26)), i);
        }

        // Adiciona os números
        for (i = 52; i < 62; i++) {
            this.dicionario.put(Character.toString((char)('0' + i - 52)), i);
        }
        
        // Adiciona o espaço
        this.dicionario.put(" ", 62);
    }

    // private String ler_proxima_mensagem (int chave, DataInputStream bufferEntrada) throws IOException {
    //     if (chave == 1 
    //         || chave == 2 
    //         || chave == 3 
    //         || chave == 5 
    //         || chave >= 10 && chave <= 15) 
    //     {
    //         //Caso ser inteiro
    //         return Integer.toString(bufferEntrada.readInt());
        
    //     } else if (chave == 4 || chave == 6 || chave == 9) {
    //         //Caso ser string
    //         return bufferEntrada.readUTF();
    //     } else if (chave == 7 || chave == 8) {
    //         //Caso ser float
    //         return Float.toString(bufferEntrada.readFloat());
        
    //     } else if (chave == 16 || chave == 17) {
    //         //Caso ser booleano
    //         return Boolean.toString(bufferEntrada.readBoolean());
    //     } else if (chave == 0) {
    //         //Caso ser byte
    //         return Byte.toString(bufferEntrada.readByte());
    //     }

    //     //Caso ser long
    //     return Long.toString(bufferEntrada.readLong());
    // }

    //Coloca o indice da compactação no arquivo de saida
    //Coloca o indice da compactação no arquivo de saida
    public void codificar(FileInputStream entrada, FileOutputStream saida) throws IOException {
        // Cria um buffer de leitura para o arquivo de entrada
        DataInputStream bufferEntrada = new DataInputStream(entrada);

        // Cria um buffer de escrita para o arquivo de saída
        DataOutputStream bufferSaida = new DataOutputStream(saida);

        // Inicializa a string atual com o primeiro caractere do arquivo de entrada
        String atual = Character.toString((char) bufferEntrada.readByte());

        // Enquanto houver dados de entrada
        while (entrada.available() > 0) {
            // Converte o próximo caractere para uma string
            String proximo = Character.toString((char) bufferEntrada.readByte());

            // Se a string atual + o próximo caractere estiverem no dicionário, atualiza a string atual
            if (dicionario.containsKey(atual + proximo)) {
                atual += proximo;
            } else if (!dicionario.containsKey(atual)) {
                //Caso o caracter atual nao existir no docionario
                dicionario.put(atual, proximoIndice++);
                bufferSaida.write(criarCodigo(atual));
                atual = proximo;
            } else {
                //Registra no arquivo o atual e o atualiza
                bufferSaida.write(criarCodigo(atual));
                dicionario.put(atual + proximo, proximoIndice++);
                atual = proximo;
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

    public HashMap<String, Integer> getDicionario() {
        return dicionario;
    } 
}

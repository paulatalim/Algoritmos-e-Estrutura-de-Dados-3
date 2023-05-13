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
        this.dicionario.put(Character.toString(' '), 62);
    }

    //private void inserir (<T> variavel) {}
    //Coloca o indice da compactação no arquivo de saida
    public void codificar(FileInputStream entrada, FileOutputStream saida) throws IOException {
        // Cria um buffer de leitura para o arquivo de entrada
        DataInputStream bufferEntrada = new DataInputStream(entrada);

        // Cria um buffer de escrita para o arquivo de saída
        DataOutputStream bufferSaida = new DataOutputStream(saida);        

        // Inicializa a string atual com o primeiro caractere do arquivo de entrada
        String atual = Character.toString((char) bufferEntrada.readInt());

        // Enquanto houver dados de entrada
        int proximoCaractere;
        while ((proximoCaractere = bufferEntrada.readInt()) != -1) {
            // Converte o próximo caractere para uma string
            String proximo = Character.toString((char) proximoCaractere);

            // Se a string atual + o próximo caractere estiverem no dicionário, atualiza a string atual
            if (dicionario.containsKey(atual + proximo)) {
                atual = atual + proximo;
            }
            // Senão, grava o índice da string atual no arquivo de saída, adiciona a string atual + próximo
            // caractere ao dicionário e atualiza a string atual para o próximo caractere
            else {
                bufferSaida.writeInt(criarCodigo(atual));
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

    public HashMap<String, Integer> getDicionario() {
        return dicionario;
    } 
}

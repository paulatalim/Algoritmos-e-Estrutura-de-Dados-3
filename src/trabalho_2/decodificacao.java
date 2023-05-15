package trabalho_2;
import java.io.*;
import java.util.HashMap;
//import java.util.Map;

public class Decodificacao {
    private static void escrever_mensagem (String mensagem, DataOutputStream dos) throws IOException {
        for (int i = 0; i < mensagem.length(); i++){
            dos.writeByte((byte) mensagem.charAt(i));
        }
    }
    
    public static void descomprimir(String inputFile, String outputFile) throws IOException {
        // Abre o arquivo de entrada e cria o arquivo de saída
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        int cont = 0;

        //acessa o dicionario
        // HashMap<String, Integer> dicionario_hash2 = new HashMap<>();
        // LZWEncoder conferir = new LZWEncoder();
        // dicionario_hash2.putAll(conferir.getDicionario());

        HashMap<Integer, String> dicionario_invertido = new HashMap<>();

        for (int i = 0; i < 256; i++) {
            dicionario_invertido.put(i, Character.toString((char) (byte) i));
        }
       
        // Cria o objeto DataInputStream para ler o arquivo de entrada
        //FileInputStream file = new FileInputStream(inputFile); 
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        DataOutputStream dos = new DataOutputStream(outputStream);

        // Variáveis auxiliares
        int tamanhoDicionario = dicionario_invertido.size();
        int novoIndice = dicionario_invertido.size();

        //Leitura do primeiro objeto comprimido
        int lido = dataInputStream.readInt();
        String caminho = dicionario_invertido.get(lido);

        // Escreve o primeiro caractere no arquivo de saída
        escrever_mensagem(caminho, dos);

        String simbolo_antigo, proximo;

        // Loop principal de descompressão
        while (dataInputStream.available() > 0) {
            System.out.println(cont++);
            if (novoIndice == 290) {
                System.out.println("che");
            }

            lido = dataInputStream.readInt();

            // Adiciona o novo caminho ao dicionário
            if (lido == novoIndice) {
                simbolo_antigo = caminho + caminho.charAt(0);
                dicionario_invertido.put(novoIndice++, simbolo_antigo);
                proximo = dicionario_invertido.get(lido);
            } else {
                proximo = dicionario_invertido.get(lido);
                simbolo_antigo = caminho + proximo.charAt(0);
                dicionario_invertido.put(novoIndice++, simbolo_antigo);
            }
            
            caminho = proximo;
            
            escrever_mensagem(proximo, dos);
        }

        // Fecha os arquivos de entrada e saída
        dataInputStream.close();
        outputStream.close();
    }       
}



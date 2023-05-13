package trabalho_2;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class decodificacao {
    
    public static void descomprimir(String inputFile, String outputFile) throws IOException {

        // Abre o arquivo de entrada e cria o arquivo de saída
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        //acessa o dicionario
        HashMap<String, Integer> dicionario_hash2 = new HashMap<>();
        LZWEncoder conferir = new LZWEncoder(dicionario_hash2, inputStream, outputStream);
        dicionario_hash2.putAll(conferir.dicionario());
       
        // Cria o objeto DataInputStream para ler o arquivo de entrada
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));

        // Variáveis auxiliares
        int tamanhoDicionario = dicionario_hash2.size();
        //int tamanhoCaminho = 0;
        int novoIndice = tamanhoDicionario + 1;

        // Inicializa o caminho com o primeiro byte do arquivo
        char caractereAtual = (char) dataInputStream.readByte();
        String caminho = "" + caractereAtual;

        // Escreve o primeiro caractere no arquivo de saída
        outputStream.write(caractereAtual);

        // Loop principal de descompressão
        while (dataInputStream.available() > 0) {
            // Lê o próximo byte do arquivo de entrada
            byte proximoByte = dataInputStream.readByte();

            if (proximoByte < 0) {
                // Converte para o valor positivo correspondente
                proximoByte = (byte) (256 + proximoByte);
            }

            // Obtém o caractere correspondente ao novo índice
            String novoCaractere;
            if (dicionario_hash2.containsKey(caminho + (char) proximoByte)) {
                novoCaractere = caminho + (char) proximoByte;
            } else {
                novoCaractere = caminho + caminho.charAt(0);
            }

            // Escreve o caractere no arquivo de saída
            for (char c : novoCaractere.toCharArray()) {
                outputStream.write(c);
            }

            // Adiciona o novo caminho ao dicionário
            dicionario_hash2.put(caminho + (char) proximoByte, novoIndice++);
            caminho = "" + (char) proximoByte;
        }

        // Fecha os arquivos de entrada e saída
        dataInputStream.close();
        outputStream.close();
    }       
}



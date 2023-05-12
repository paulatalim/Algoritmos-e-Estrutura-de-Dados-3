package trabalho_2;
import java.io.*;

public class Decodificacao {
    
    public static void decompress(String inputFile, String outputFile) throws IOException {

         //LZWDecompression.decompress("input.lzw", "output.bd");
         
        // Abre o arquivo de entrada e cria o arquivo de saída
        DataInputStream inputStream = new DataInputStream(new FileInputStream(inputFile));
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        // Cria o dicionário inicial com todos os caracteres ASCII possíveis
        int dictionarySize = 256;
        String[] dictionary = new String[dictionarySize];
        for (int i = 0; i < dictionarySize; i++) {
            dictionary[i] = Character.toString((char) i);
        }

        // Lê o primeiro código do arquivo de entrada
        int currentCode = inputStream.readInt();

        // Cria uma sequência vazia para construir a próxima sequência a ser adicionada ao dicionário
        String currentSequence = dictionary[currentCode];

          // Escreve a sequência atual no arquivo de saída
          outputStream.write(currentSequence.getBytes());

          // Loop principal de descompressão
          while (inputStream.available() > 0) {
              // Lê o próximo código do arquivo de entrada
              int nextCode = inputStream.readInt();
  
              // Verifica se o código está no dicionário
              String nextSequence;
              if (nextCode < dictionarySize) {
                  nextSequence = dictionary[nextCode];
              } else if (nextCode == dictionarySize) {
                  // Caso contrário, a próxima sequência é a concatenação da sequência atual com o primeiro caractere da sequência atual
                  nextSequence = currentSequence + currentSequence.charAt(0);
              } else {
                  throw new IllegalStateException("Invalid LZW code");
              }
                // Escreve a próxima sequência no arquivo de saída
            outputStream.write(nextSequence.getBytes());

            // Adiciona a próxima sequência ao dicionário
            dictionary[dictionarySize++] = currentSequence + nextSequence.charAt(0);

            // Atualiza a sequência atual para a próxima sequência
            currentSequence = nextSequence;
        }

        // Fecha os arquivos de entrada e saída
        inputStream.close();
        outputStream.close();
    }
}


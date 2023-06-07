package trabalho_2;

import java.io.IOException;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class compressao {

    public static void comprimir(String inputFile, String outputFile) throws IOException {

        //Abre o arquivo de entrada e cria o arquivo de saida
        FileInputStream entrada = new FileInputStream(inputFile); //Entrada
        FileOutputStream saida = new FileOutputStream(outputFile); //Saida

        //Cria o dicionario
        char[] dicionario = new char[10000];

        // Adiciona as letras maiusculas
        for (int i = 0; i < 26; i++) {
            dicionario[i] = (char) ('A' + i);
        }

        // Adiciona as letras minúsculas
        for(int i = 0; i < 26; i++){
            dicionario[26 + i] = (char) ('a' + i);
        }

        // Adiciona os números
        for (int i = 0; i < 10; i++) {
            dicionario[52 + i] = (char) ('0' + i);
        }
        
        // Adiciona o espaço
        dicionario[62] = ' ';

        // Cria o HashMap
        HashMap<String, Integer> dicionario_hash = new HashMap<>();

        // Loop pelo array "dicionario"
        for (int i = 0; i < dicionario.length; i++) {
            // Converte o caractere em uma String e adiciona a chave-valor correspondente ao HashMap
            dicionario_hash.put(Character.toString(dicionario[i]), i);
        }
        //Preencher o resto do dicionario

        // Cria o codificador LZW
        LZWEncoder codificador = new LZWEncoder(dicionario_hash,entrada,saida);

        // Codifica os dados de entrada e grava a saída no arquivo
        codificador.codificar(entrada, saida);

        // Fecha os arquivos
        entrada.close();
        saida.close();

        
          
    }
}

   


  
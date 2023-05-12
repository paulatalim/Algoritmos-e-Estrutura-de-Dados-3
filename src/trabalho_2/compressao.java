package trabalho_2;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javafx.scene.chart.PieChart.Data;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;

public class compressao {

    public static void comprimir(String inputFile, String outputFile) throws IOException {

        //Abre o arquivo de entrada e cria o arquivo de saida
        FileInputStream entrada= new FileInputStream(inputFile); //Entrada 
        DataOutputStream saida = new DataOutputStream(new FileOutputStream(outputFile)); //Saida

        //Cria o dicionario
        char[] dicionario = new char[10000];

        for (int i = 0; i < 26; i++) {
            // Adiciona as letras maiúsculas
            dicionario[i] = (char) ('A' + i);
        }

        for(int i=0;i<26;i++){
            // Adiciona as letras minúsculas
            dicionario[26 + i] = (char) ('a' + i);
        }

        for (int i = 0; i < 10; i++) {
            // Adiciona os números
            dicionario[52 + i] = (char) ('0' + i);
        }
        
        // Adiciona o espaço
        dicionario[62] = ' ';

        //Preencher o resto do dicionario
        // Cria o codificador LZW
        LZWEncoder codificador = new LZWEncoder(dicionario);

        // Codifica os dados de entrada e grava a saída no arquivo
        codificador.codificar(entrada,saida);

        // Fecha os arquivos
        entrada.close();
        saida.close();
    }


}
//Utiliza o hashMap para terminar de preencher o dicionario com as informçoes do arquivo
class LZWEncoder {
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
    public void codificar(InputStream entrada, OutputStream saida) throws IOException {
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

   


  
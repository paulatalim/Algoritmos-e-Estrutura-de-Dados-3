package trabalho_2;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.HashMap;

/**
 * Classe para compactar arquivos data base
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class Compressao {
    private int version;
    private String arq_data_base_name;
    private String folder_url;
    private FileInputStream arq_entrada;
    private FileOutputStream arq_saida;

    /**
     * Construtor da classe
     * 
     * @param arq_data_base a ser compressada
     * @param folder_name nome da pasta para salvar os arquivos compactados
     */
    public Compressao (String arq_data_base, String folder_name) {  
        //Atribuicao a url da pasta      
        this.folder_url = "src/" + folder_name;

        //Criacao de pasta para os arquivos comprimidos
        File folder = new File(this.folder_url);
        folder.mkdir();

        //Inicializa a versao
        version = 1;
        this.arq_data_base_name = arq_data_base;
    }

    private float calcular_eficacia_compressao (FileOutputStream arq_comprimido, FileInputStream arq_original) throws IOException {
        return (1 - ((float) arq_comprimido.getChannel().size() / arq_original.getChannel().size())) * 100;
    }

    /**
     * Cria um arquivo comprimido para um arquivo data base
     * @param inputFile arquivo a ser comprimido
     * @throws IOException
     */
    public void comprimir (String inputFile) throws IOException {

        //Marca o tempo de inicio da execucao do algoritimo
        long tempo_inicial = System.currentTimeMillis();

        //Abre o arquivo de entrada e cria o arquivo de saida
        arq_entrada= new FileInputStream(inputFile); //Entrada 
        arq_saida = new FileOutputStream(folder_url + "/" + arq_data_base_name + "Compressao" + version + ".db"); //Saida

        //Atualizacao de versao
        version++;

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
        LZWEncoder codificador = new LZWEncoder(dicionario_hash, arq_entrada, arq_saida);

        // Codifica os dados de entrada e grava a saída no arquivo
        codificador.codificar(arq_entrada, arq_saida);

        //Calcula o tempo de execucao do programa
        long tempo_final = System.currentTimeMillis();
        System.out.println("Tempo de execucao: " + (tempo_final - tempo_inicial) + " ms");

        //Calculo da eficiencia da compressao
        System.out.println("Eficiencia: " + calcular_eficacia_compressao(arq_saida, arq_entrada));

        // Fecha os arquivos
        arq_entrada.close();
        arq_saida.close();
    }
}

   


  
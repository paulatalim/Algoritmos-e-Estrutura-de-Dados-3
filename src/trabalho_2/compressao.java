package trabalho_2;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

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

    /**
     * Cria um arquivo comprimido para um arquivo data base
     * @param inputFile arquivo a ser comprimido
     * @throws IOException
     */
    public void comprimir (String inputFile) throws IOException {

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

        //Preencher o resto do dicionario
        // Cria o codificador LZW
        LZWEncoder codificador = new LZWEncoder(dicionario);

        // Codifica os dados de entrada e grava a saída no arquivo
        codificador.codificar(arq_entrada, arq_saida);

        // Fecha os arquivos
        arq_entrada.close();
        arq_saida.close();
    }
}

   


  
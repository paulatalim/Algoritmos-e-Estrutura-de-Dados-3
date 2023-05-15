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
    private String data_base_name;
    private String data_base_src;
    private String folder_url;
    private FileInputStream arq_entrada;
    private FileOutputStream arq_saida;

     /**
      * Construtor da classe
      *
      * @param data_base_src caminho da base de dados a ser manipulada
      * @param data_base_name nome da base de dados a ser manipulada
      * @param folder_name nome da pasta para salvar os arquivos compactados
      */
    public Compressao (String data_base_src, String data_base_name, String folder_name) {  
        //Atribuicao a url da pasta      
        this.folder_url = "src/" + folder_name;

        //Criacao de pasta para os arquivos comprimidos
        File folder = new File(this.folder_url);
        folder.mkdir();

        //Inicializa a versao
        version = 1;
        this.data_base_name = data_base_name;
        this.data_base_src = data_base_src;
    }

    /**
     * Calcula a eficacia do algoritmo
     * 
     * @param arq_comprimido arquivo a ser comparado
     * @param arq_original arquivo a ser comparado
     * @return a eficia do algoritimo em porcentagem
     * @throws IOException
     */
    private float calcular_eficacia_compressao (FileOutputStream arq_comprimido, FileInputStream arq_original) throws IOException {
        return (1 - ((float) arq_comprimido.getChannel().size() / arq_original.getChannel().size())) * 100;
    }

    /**
     * Cria um arquivo comprimido para um arquivo data base
     * @throws IOException
     */
    public void comprimir () throws IOException {

        //Marca o tempo de inicio da execucao do algoritimo
        long tempo_inicial = System.currentTimeMillis();

        //Abre o arquivo de entrada e cria o arquivo de saida
        arq_entrada= new FileInputStream(data_base_src); //Entrada 
        arq_saida = new FileOutputStream(folder_url + "/" + data_base_name + "Compressao" + version + ".db"); //Saida

        //Atualizacao de versao
        version++;

        // Cria o codificador LZW
        LZWEncoder codificador = new LZWEncoder();

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

    /**
     * Descompacta um arquivo
     * 
     * @param num_version numero da versao do 
     * arquivo a descompactar
     * 
     * @return true (caso a descompactacao ocorrer
     * com sucesso) false (caso nao encontrar o arquivo para descompactar)
     * 
     * @throws IOException
     */
    public Boolean descomprimir (int num_version) throws IOException {
        //Caso a versao da compressao nao existir
        if (num_version >= version) {
            return false;
        }

        //Abre os arquivos
        FileInputStream fis = new FileInputStream(folder_url + "/" + data_base_name + "Compressao" + num_version + ".db");
        FileOutputStream fos = new FileOutputStream(data_base_src);

        //Descompacta
        LZWEncoder encoder = new LZWEncoder();
        encoder.decodificar(fis, fos);

        //Fecha os arquivos
        fis.close();
        fos.close();

        return true;
    }
}

   


  
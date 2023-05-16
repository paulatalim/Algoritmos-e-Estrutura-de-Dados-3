package trabalho_2;

import java.io.IOException;

import aplicacao.Tela;

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

        //Inicializa a versao
        version = 1;

        // Atribuicao de valores dos outros aributos
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
    public void comprimir () throws Exception {

        Tela.print("\n\t\t\t\t\t\t" + "*** POKE-COMPACTA ***" + "\n\n\n"
                                    + "\t"+ "Iniciando compactacao..." + "\n");
        
        //Criacao de pasta para os arquivos comprimidos
        File folder = new File(this.folder_url);
        folder.mkdir();
        
        //Marca o tempo de inicio da execucao do algoritimo
        long tempo_inicial = System.currentTimeMillis();

        //Abre o arquivo de entrada e cria o arquivo de saida
        arq_entrada= new FileInputStream(data_base_src); //Entrada 
        arq_saida = new FileOutputStream(folder_url + "/" + data_base_name + "Compressao" + version + ".db"); //Saida

        //Atualizacao de versao
        version++;

        // Cria o codificador LZW
        LZWEncoder codificador = new LZWEncoder();

        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t" + "*** POKE-COMPACTA ***" + "\n\n\n"
                            + "\t" + "Compactando ..." + "\n");

        // Codifica os dados de entrada e grava a saída no arquivo
        codificador.codificar(arq_entrada, arq_saida);

        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t" + "*** POKE-COMPACTA ***" + "\n\n\n"
                            + "\t" + "Compactacao concluida com sucesso !!!" + "\n\n"
                            + "\t" + "RESULTADOS:");

        //Calcula o tempo de execucao do programa
        long tempo_final = System.currentTimeMillis();
        Tela.printlt("Tempo de execucao: " + (tempo_final - tempo_inicial) + " ms" + "\n");

        //Calculo da eficiencia da compressao
        Tela.printlt("Eficiencia: " + calcular_eficacia_compressao(arq_saida, arq_entrada) + "%" + "\n");

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
        File arq = new File(folder_url + "/" + data_base_name + "Compressao" + num_version + ".db");

        //Caso a versao da compressao nao existir
        if (!arq.exists()) {
            return false;
        }

        //Abre os arquivos
        arq_entrada = new FileInputStream(folder_url + "/" + data_base_name + "Compressao" + num_version + ".db");
        arq_saida = new FileOutputStream(data_base_src);

        //Descompacta
        LZWEncoder encoder = new LZWEncoder();
        encoder.decodificar(arq_entrada, arq_saida);

        //Fecha os arquivos
        arq_entrada.close();
        arq_saida.close();

        return true;
    }
}

   


  
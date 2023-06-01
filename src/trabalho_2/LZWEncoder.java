package trabalho_2;

import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Classe com o algoritimo LZW para compressao dos arquivos
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class LZWEncoder {
    private HashMap<Integer, String> dicionario;
    private int proximoIndice;

    /**
     * Construtor
     */
    public LZWEncoder() {
        //Cria objeto do dicionario
        dicionario = new HashMap<>();

        //Preenche o dicionario
        preencher_dicionario_inicial();

        this.proximoIndice = dicionario.size();
    }

    /**
     * Prenche o dicionario inicial com todos os bytes possiveis
     */
    private void preencher_dicionario_inicial () {
        for (int i = 0; i < 256; i++) {
            this.dicionario.put(i, Character.toString((char) (byte) i));
        }
    }

    /**
     * Pesquisa no dicionario o indice de um elemento
     * 
     * @param value elemento a ser pesquisado
     * @return indice do elemento ou -1 se nao encontrar
     */
    private int buscar_index (String value) {
        for (int i = 0; i < dicionario.size(); i++) {
            if (dicionario.get(i).equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Escreve uma mensagem String em bytes no arquivo
     * 
     * @param mensagem a ser escrita
     * @param dos objeto de escrita do arquivo
     * @throws IOException
     */
    private static void escrever_mensagem (String mensagem, DataOutputStream dos) throws IOException {
        for (int i = 0; i < mensagem.length(); i++){
            dos.writeByte((byte) mensagem.charAt(i));
        }
    }

    /**
     * Compressao do arquivo
     * 
     * @param entrada arquivo a ser compactado
     * @param saida arquivo que tera o conteudo compactado
     * @throws IOException
     */
    public void codificar(FileInputStream entrada, FileOutputStream saida) throws IOException {
        // Cria um buffer de leitura para o arquivo de entrada
        DataInputStream dis = new DataInputStream(entrada);

        // Cria um buffer de escrita para o arquivo de saída
        DataOutputStream dos = new DataOutputStream(saida);

        // Inicializa a string simbolo_atual com o primeiro caractere do arquivo de entrada
        String simbolo_atual = Character.toString((char) dis.readByte());
        String simbolo_novo;

        // Enquanto houver dados de entrada
        while (entrada.available() > 0) {
            simbolo_novo = Character.toString((char) dis.readByte());

            // Se a string simbolo_atual + o simbolo_novo estiverem no dicionário, atualiza a string simbolo_atual
            if (buscar_index(simbolo_atual + simbolo_novo) != -1) {
                simbolo_atual += simbolo_novo;
            } else {
                //Registra no arquivo o simbolo_atual e o atualiza
                dos.writeInt(buscar_index(simbolo_atual));
                dicionario.put(proximoIndice++, simbolo_atual + simbolo_novo);
                simbolo_atual = simbolo_novo;
            }
        }
    
        // Grava o código final da string simbolo_atual no arquivo de saída
        dos.writeInt(buscar_index(simbolo_atual));
    
        // Descarrega o buffer de escrita e fecha o arquivo de saída
        dos.flush();
    }

    /**
     * Descompacta o arquivo
     * 
     * @param entrada arquivo compactado
     * @param saida arquivo que tera o conteudo descompactado
     * @throws IOException
     */
    public void decodificar (FileInputStream entrada, FileOutputStream saida) throws IOException {
        // Cria o objeto DataInputStream para ler o arquivo de entrada
        DataInputStream dis = new DataInputStream(entrada);
        DataOutputStream dos = new DataOutputStream(saida);

        //Leitura do primeiro objeto comprimido
        int indice_lido = dis.readInt();
        String simbolo = dicionario.get(indice_lido);
        String simbolo_novo;

        // Escreve o primeiro caractere no arquivo de saída
        escrever_mensagem(simbolo, dos);

        // Loop principal de descompressao
        while (dis.available() > 0) {

            //Leitura do proximo codigo de mensagem comprimido
            indice_lido = dis.readInt();

            // Adiciona o novo simbolo ao dicionário
            if (indice_lido == proximoIndice) {
                // Adiciona o simbolo primeiro e depois o busca
                dicionario.put(proximoIndice++, simbolo + simbolo.charAt(0));
                simbolo_novo = dicionario.get(indice_lido);
            } else {
                // Busca primeiro o simbolo e depois adiciona no dicionario
                simbolo_novo = dicionario.get(indice_lido);
                dicionario.put(proximoIndice++, simbolo + simbolo_novo.charAt(0));
            }
            
            //Atualizacao do simbolo
            simbolo = simbolo_novo;
            
            //Escreve o novo simbolo no arquivo
            escrever_mensagem(simbolo_novo, dos);
        }
    }
}

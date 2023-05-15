package trabalho_2;

import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class LZWEncoder {
    private HashMap<Integer, String> dicionario;
    private int proximoIndice;

    public LZWEncoder() {
        //Cria objeto do dicionario
        dicionario = new HashMap<>();

        //Preenche o dicionario
        preencher_dicionario_inicial();

        this.proximoIndice = dicionario.size();
    }

    private void preencher_dicionario_inicial () {
        for (int i = 0; i < 256; i++) {
            this.dicionario.put(i, Character.toString((char) (byte) i));
        }
    }

    private short buscar_index (String value) {
        for (int i = 0; i < dicionario.size(); i++) {
            if (dicionario.get(i).equals(value)) {
                return (short) i;
            }
        }
        return -1;
    }

    private static void escrever_mensagem (String mensagem, DataOutputStream dos) throws IOException {
        for (int i = 0; i < mensagem.length(); i++){
            dos.writeByte((byte) mensagem.charAt(i));
        }
    }

    //Coloca o indice da compactação no arquivo de saida
    public void codificar(FileInputStream entrada, FileOutputStream saida) throws IOException {
        // Cria um buffer de leitura para o arquivo de entrada
        DataInputStream bufferEntrada = new DataInputStream(entrada);

        // Cria um buffer de escrita para o arquivo de saída
        DataOutputStream bufferSaida = new DataOutputStream(saida);

        // Inicializa a string atual com o primeiro caractere do arquivo de entrada
        String atual = Character.toString((char) bufferEntrada.readByte());

        // Enquanto houver dados de entrada
        while (entrada.available() > 0) {
            String proximo = Character.toString((char) bufferEntrada.readByte());

            // Se a string atual + o próximo caractere estiverem no dicionário, atualiza a string atual
            if (buscar_index(atual + proximo) != -1) {
                atual += proximo;
            } else {
                //Registra no arquivo o atual e o atualiza
                bufferSaida.writeShort(buscar_index(atual));
                dicionario.put(proximoIndice++, atual + proximo);
                atual = proximo;
            }
        }
    
        // Grava o código final da string atual no arquivo de saída
        bufferSaida.writeShort(buscar_index(atual));
    
        // Descarrega o buffer de escrita e fecha o arquivo de saída
        bufferSaida.flush();
    }

    public void decodificar (FileInputStream entrada, FileOutputStream saida) throws IOException {
        // Cria o objeto DataInputStream para ler o arquivo de entrada
        DataInputStream dis = new DataInputStream(entrada);
        DataOutputStream dos = new DataOutputStream(saida);

        String simbolo_antigo, proximo;
       
        // Variáveis auxiliares
        int novoIndice = dicionario.size();

        //Leitura do primeiro objeto comprimido
        int lido = (int) dis.readShort();
        String caminho = dicionario.get(lido);

        // Escreve o primeiro caractere no arquivo de saída
        escrever_mensagem(caminho, dos);

        // Loop principal de descompressão
        while (dis.available() > 0) {

            //Leitura do proximo codigo de mensagem comprimido
            lido = dis.readShort();

            // Adiciona o novo caminho ao dicionário
            if (lido == novoIndice) {
                simbolo_antigo = caminho + caminho.charAt(0);
                dicionario.put(novoIndice++, simbolo_antigo);
                proximo = dicionario.get(lido);
            } else {
                proximo = dicionario.get(lido);
                simbolo_antigo = caminho + proximo.charAt(0);
                dicionario.put(novoIndice++, simbolo_antigo);
            }
            
            caminho = proximo;
            
            escrever_mensagem(proximo, dos);
        }
    }       

    public HashMap<Integer, String> getDicionario() {
        return dicionario;
    } 
}

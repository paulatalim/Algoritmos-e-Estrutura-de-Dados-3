package trabalho_1;

import console.Tela;
import manipulacao_arquivo.Pokemon;

import java.io.RandomAccessFile;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.File;

/**
 * Classe funcional para ordenar os registros no arquivo de acordo com seu id
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class Ordenacao_externa {
    /**
     * Troca os elementos de um array nos índices i e j.
     *
     * @param arr O array a ser modificado.
     * @param i O índice do primeiro elemento a ser trocado.
     * @param j O índice do segundo elemento a ser trocado.
     * @param <T> O tipo de objeto contido no array.
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Calcula o indice o elemento pai no heap
     * 
     * @param filho indice do elemento filho
     * @return indice do elemento pai
     */
    private static int calcular_indice_pai (int filho) {
        int flag = filho;

        filho /= 2;

        if (flag %2 == 0) {
            filho --;
        }
        return filho;
    }

    /** 
     * Constroi o heap de um vetor de pokemons
     * 
     * @param bloco um vetor de pokemons (vetor a ser construido a arvore heap) 
     * @param chave vetor de inteiros que representam as chaves de verificacao
     * @param tam tamanho valido do vetor
     */
    private static void construir_heap (Pokemon[] bloco, Integer[]chave, int tam) {
        int indice = calcular_indice_pai(tam);
        int i = tam;

        while (i > 0 && (chave[i] < chave[indice] || (chave[i] == chave[indice] && bloco[i].getId() < bloco[indice].getId()))) {
            swap(bloco, i, indice);
            swap(chave, indice, i);
            
            i = calcular_indice_pai(i);

            if (indice % 2 == 0) {
                indice = calcular_indice_pai(indice);
            } else {
                indice /= 2;
            }
        }
    }

    /**
     * Ordena um vetor com o heap min
     *
     * @param bloco um vetor de pokemons (vetor a ser ordenado)
     * @param chaves vetor interger com chaves de verificacao
     */
    private static void fazer_heapmin (Pokemon[] bloco, Integer[] chaves) {
        //Construcao do heap
        for (int tam = 1; tam < bloco.length; tam++) {
            construir_heap(bloco, chaves, tam);
        }
    }
    
    /**
     *  Escreve um pokemon no arquivo desejado e le o proximo registro do arquivo
     * 
     * @param pokemon registro a ser escrito
     * @param in objeto para ler do arquivo
     * @param out vetor do objeto DataOutputStream
     * @param escolha_out indicador de qual objeto out usar
     * @return proximo pokemon lido do arquivo ou null, caso o arquivo terminar
     * @throws Exception
     */
    private static Pokemon escrever_pokemon_e_ler_prox (Pokemon pokemon, 
                                                        DataInputStream in, 
                                                        DataOutputStream[] out,
                                                        boolean escolha_out) throws Exception 
    {
        byte[] vet = pokemon.toByteArray();
        int indice = escolha_out ? 1 : 0;
        
        out[indice].writeInt(vet.length);
        out[indice].write(vet);
        
        if (in.available() > 0) {
            vet = new byte [in.readInt()];
            in.read(vet);
            pokemon.fromByteArray(vet);
            return pokemon;
        } 

        //Retorno de objeto zerado
        return new Pokemon();
    }

    /**
     * Essa funcao distribui os registros em arquivos temporarios ordenados
     * @param arq .db a ser ordenado
     * @throws Exception
     */
    private static void distribuir_registros (RandomAccessFile arq) throws Exception {
        final int vet_tam = 2; // Constante inteira
        FileOutputStream[] arq_temp = new FileOutputStream [vet_tam];
        DataOutputStream[] out = new DataOutputStream [vet_tam];
        Pokemon[] bloco = new Pokemon [10];
        Integer[] chaves = new Integer [10];
        byte[] poke_vet_byte;
        Pokemon pokemon = new Pokemon();
        int i;
        int indice = 0;
        int nova_chave = 0;
        int chave_antiga = 0;

        //Reseta o ponteiro para depois dos metadados
        arq.seek(4);
        
        //Inicializa o vetor 
        for (i = 0; i < chaves.length; i++) {
            chaves[i] = 0;
        }

        //Mensagem para o usuario
        Tela.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Iniciando etapa de distribuicao ...");

        //Abre os objetos
        for (i = 0; i < vet_tam; i++) {
            arq_temp[i] = new FileOutputStream("src/arqTemp" + (i+1) + ".db");
            out[i] = new DataOutputStream (arq_temp[i]);
        }

        //Preenche o vetor com os registros
        for (i = 0; i < bloco.length && arq.getFilePointer() < arq.length(); i++) {
            
            //Verifica se o arquivo foi excluido
            if (arq.readByte() == ' ') {
                //Le o arquivo
                poke_vet_byte = new byte [arq.readInt()];
                arq.read(poke_vet_byte);

                //Cria e registra o pokemon
                bloco[i] = new Pokemon();
                bloco[i].fromByteArray(poke_vet_byte);
            } else {
                //Pula o registro
                arq.seek(arq.readInt() + arq.getFilePointer());
                i--;
            }
        }

        //Ordena o vetor com o heap minimo
        fazer_heapmin(bloco, chaves);            

        //Mensagem para o usuario
        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Distribuindo pokemons ...");
        
        //Leitura do proximo registro
        while (arq.getFilePointer() < arq.length()) {
            //Verifica se o registro existe
            if (arq.readByte() == ' ') {
                //Le o arquivo
                poke_vet_byte = new byte [arq.readInt()];
                arq.read(poke_vet_byte);
                pokemon.fromByteArray(poke_vet_byte);

                //calculo da chave do novo pokemon
                nova_chave = chaves[0];

                //Verifica se o novo registro pode entrar no antigo segmento
                if (pokemon.getId() < bloco[0].getId()) {
                    nova_chave ++;
                }

                //Verificando se ha a troca de arquivo
                if (chaves[0] != chave_antiga) {
                    indice = (indice == 0) ? 1 : 0;
                }

                //Escreve o pokemon no arquivo
                chave_antiga = chaves[0];
                poke_vet_byte = bloco[0].toByteArray();

                out[indice].writeInt(poke_vet_byte.length);
                out[indice].write(poke_vet_byte);
                
                //Inclui novo pokemon do vetor
                bloco[0] = (Pokemon)pokemon.clone();
                chaves[0] = nova_chave;

                //Ordena o vetor com heap
                fazer_heapmin(bloco, chaves);
            } else {
                //Pula o arquivo
                arq.seek(arq.readInt() + arq.getFilePointer());
            }
        }
        
        //Registra o resto do vetor
        for (i = 0; i < bloco.length; i++) {
            
            //Verificando se ha a troca de arquivo
            if (chaves[0] != chave_antiga) {
                indice = (indice == 0) ? 1 : 0;
            }

            //Registra novo pokemon
            poke_vet_byte = bloco[0].toByteArray();

            out[indice].writeInt(poke_vet_byte.length);
            out[indice].write(poke_vet_byte);

            //Inserir sentinela
            bloco[0] = new Pokemon();
            bloco[0].setId(0x7FFFFFF);
            chave_antiga = chaves[0];
            chaves[0] = 0x7FFFFFF;

            //heap
            fazer_heapmin(bloco, chaves);
        }

        //Fecha os arquivos temporarios
        for (i = 0; i < vet_tam; i ++) {
            arq_temp[i].close();
            out[i].close();
        }
    }

    /**
     * Essa funcao intercala os blocos de registros
     * @param arq .db a ser ordenado
     * @param metadados do arquivo
     * @throws Exception
     */
    private static void intercalar_registros (RandomAccessFile arq) throws Exception {
        final int vet_tam = 2;
        FileOutputStream[] arq_out = new FileOutputStream [vet_tam];
        DataOutputStream[] out = new DataOutputStream [vet_tam];
        FileInputStream[] arq_in = new FileInputStream [vet_tam];
        DataInputStream[] in = new DataInputStream [vet_tam];
        Pokemon[] pokemons = new Pokemon [vet_tam];
        byte[] poke_vet_byte;
        Pokemon pokemon_antigo = new Pokemon();
        boolean terminou_segmento = false;
        boolean inverter_arq = false;
        boolean escrever_arq1 = false;
        int i;
        int indice = 0;

        //Mensagem para o usuario
        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Iniciando etapa de intercalacao ...");
        
        //Abre objetos de leitura e escrita
        for (i = 0; i < vet_tam; i++) {
            arq_in[i] = new FileInputStream("src/arqTemp" + (i+1) + ".db");
            arq_out[i] = new FileOutputStream("src/arqTemp" + (i+3) + ".db");
            in[i] = new DataInputStream(arq_in[i]);
            out[i] = new DataOutputStream(arq_out[i]);
            pokemons[i] = new Pokemon();
        }

        //Mensagem para o usuario
        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Intercalando pokemons ...");

        //Verifica se ha registros para intercalar
        while (in[0].available() > 0 && in[1].available() > 0) {
            //Le o primeiro registro dos arquivos
            for (i = 0; i < vet_tam; i ++) {
                poke_vet_byte = new byte [in[i].readInt()];
                in[i].read(poke_vet_byte);
                pokemons[i].fromByteArray(poke_vet_byte);
            }
        
            //Intercala de arquivos
            while (in[0].available() > 0 || in[1].available() > 0) {

                //Verifica o proximo registro a ser registrado
                indice = (pokemons[0].getId() < pokemons[1].getId()) ? 0 : 1;
                
                //Registra o novo pokemon
                pokemon_antigo = (Pokemon)pokemons[indice].clone();
                pokemons[indice] = (Pokemon)escrever_pokemon_e_ler_prox(pokemons[indice], in[indice], out, escrever_arq1).clone();

                //Verifica se o segmento acabou
                if (pokemon_antigo.getId() > pokemons[indice].getId() || in[indice].available() <= 0) {
                    terminou_segmento = true;

                    //Escreve o ultimo registro do segmento
                    if (in[indice].available() > 0) {
                        poke_vet_byte = pokemons[indice].toByteArray();
                        out[indice].writeInt(poke_vet_byte.length);
                        out[indice].write(poke_vet_byte);
                    }
                    
                    //If ternario para inverter o indice
                    indice = indice == 0 ? 1 : 0;

                    //Escreve o resto do segmento do outro arquivo
                    while (pokemon_antigo.getId() < pokemons[indice].getId() || in[indice].available() > 0) {
                        pokemon_antigo = (Pokemon)pokemons[indice].clone();
                        pokemons[indice] = (Pokemon)escrever_pokemon_e_ler_prox(pokemons[indice], in[indice], out, escrever_arq1).clone();

                        //Caso o arquivo terminar
                        if (pokemons[indice].getId() == -1) {
                            indice = indice == 0 ? 1 : 0;
                            escrever_arq1 = !escrever_arq1;

                            while (in[indice].available() > 0) {
                                pokemon_antigo = (Pokemon)pokemons[indice].clone();
                                pokemons[indice] = (Pokemon)escrever_pokemon_e_ler_prox(pokemons[indice], in[indice], out, escrever_arq1).clone();
                            }

                            break;
                        }
                    }
                }

                //Verifica se ha troca de arquivo
                if (terminou_segmento) {
                    escrever_arq1 = !escrever_arq1;
                    
                    //Reinicia variavel
                    terminou_segmento = false;
                }
            }

            //Trocar arquivos
            for (i = 0; i < vet_tam; i++) {
                arq_in[i].close();
                arq_out[i].close();
                in[i].close();
                out[i].close();
            }

            //Reajuste na variavel
            inverter_arq = !inverter_arq;

            //Troca de arquivos de leitura para escrita e vice versa
            for (i = 0; i < vet_tam; i++) {
                if (inverter_arq) {
                    arq_in[i] = new FileInputStream("src/arqTemp" + (i+1) + ".db");
                    arq_out[i] = new FileOutputStream("src/arqTemp" + (i+3) + ".db");
                } else {
                    arq_in[i] = new FileInputStream("src/arqTemp" + (i+3) + ".db");
                    arq_out[i] = new FileOutputStream("src/arqTemp" + (i+1) + ".db");        
                }

                in[i] = new DataInputStream(arq_in[i]);
                out[i] = new DataOutputStream(arq_out[i]);
            }
        }

        //Fecha os arquivos e objetos
        for (i = 0 ; i < vet_tam; i++) {
            arq_in[i].close();
            arq_out[i].close();
            in[i].close();
            out[i].close();
        }
    }

    /**
     * Escreve os registros no arquivo ordenados
     * 
     * @param arq .db a ser escrito
     * @param metadados do arquivo a ser escrito
     * @throws Exception
     */
    private static void reescrever_arq_db_ordenado (RandomAccessFile arq, int metadados) throws Exception {
        FileInputStream arq_in;
        DataInputStream in;
        byte[] poke_vet_byte;
        int i;

        //Limpa arquivo db
        arq.setLength(0);
        arq.writeInt(metadados);

        //Verifica em qual arquivo q possui dados
        for (i = 1; i <= 4; i ++) {
            arq_in = new FileInputStream("src/arqTemp" + i + ".db");
            in = new DataInputStream(arq_in);

            //Transferencia de dados do arquivo temporario para o arquivo .db
            while (in.available() > 0) {
                //escrever arq
                poke_vet_byte = new byte [in.readInt()];
                in.read(poke_vet_byte);

                arq.writeByte(' ');
                arq.writeInt(poke_vet_byte.length);
                arq.write(poke_vet_byte);
            }
        
            arq_in.close();
            in.close();
        }

        //Mensagem para o usuario
        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Finalizando Intercalacao ...");

        //Deleta os arquivos temporarios
        File arq_temp;
        for (i = 1; i <= 4; i++) {
            arq_temp = new File ("src/arqTemp" + i + ".db");
            arq_temp.delete();
        }

        //Mensagem para o usuario
        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t\t" + "*** ORDENACAO EXTERNA ***" + "\n\n\n" 
                            + "\t" + "Ordenacao concluida com sucesso !!!" + "\n");
    }

    /**
     * Descricao: ordena os registros do arquivo com a ordenacao externa
     * @param arq arquivo arquivo a ser ordenado
     */
    public static void ordenar_registros (RandomAccessFile arq) throws Exception {
        //Leitura de metadados
        arq.seek(0);
        int metadados = arq.readInt();

        //Ordenacao
        distribuir_registros(arq);
        intercalar_registros(arq);
        reescrever_arq_db_ordenado(arq, metadados);
    }    
}

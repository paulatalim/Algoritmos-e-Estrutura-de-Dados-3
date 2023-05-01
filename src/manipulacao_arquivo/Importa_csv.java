package manipulacao_arquivo;

import java.io.RandomAccessFile;
import java.util.Scanner;
import aplicacao.Tela;
import java.io.File;

/**
 * Classe funcional que importa as informacoes do arquivo csv para .db
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class Importa_csv {
    /**
     * Trata a string lida no arquivo csv, retirando as 
     * aspas e colocando "null" em atributos vazios
     *
     * @param linha linha lida do arquivo
     * @return linha do arquivo tratada
     */
    private static String tratar_string (String linha) throws Exception {
        StringBuilder sb = new StringBuilder(linha);
                
        //Deleta as aspas lidas da planilha
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '"') {

                if (i+1 != sb.length() && sb.charAt(i+1) == '"') {
                    sb.insert(i+1, "null");
                }

                sb.deleteCharAt(i);
                
                i--;
            }
        }

        return sb.toString();
    }

    /**
     * Importar um arquivo csv e passa sua informacoes para o arquivo database
     * 
     * @param arq_db arquivo database a ser preenchido
     * @param caminho_arq_csv url do arquivo csv a ser importado
     */
    public static void passar_arq_csv_para_db (RandomAccessFile arq_db, String caminho_arq_csv) throws Exception {
        Pokemon pokemon;
        String[] atributos_csv;
        byte[] poke_info_byte;
        String linha;
        int id_metadados = 1;

        //Le arquivos csv
        File arq_csv = new File(caminho_arq_csv);
        Scanner scanner = new Scanner (arq_csv);

        //Exibe mensagem para o usuario
        Tela.println (  "\n\t\t\t\t\t" + "*** IMPORTANDO ARQUIVO .CSV PARA .DB ***" + "\n\n\n"
                            + "\t" + "Iniciando importacao ..." + "\n");
        
        //Le o cabecalho da planilha
        linha = new String(scanner.nextLine());

        //Escreve os metadados no arquivo
        arq_db.setLength(0);
        arq_db.seek(0);
        arq_db.writeInt(id_metadados);

        //Exibe mensagem para o usuario
        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t" + "*** IMPORTANDO ARQUIVO .CSV PARA .DB ***" + "\n\n\n"
                            + "\t" + "Importando arquivo ..." + "\n");

        //Le o arquivo csv e passa as informacoes para o arquivo db
        while (scanner.hasNextLine()) {
            linha = new String(scanner.nextLine());
            
            //Trata a string lida e a coloca em um vetor de string
            atributos_csv = tratar_string(linha).split(",");
            
            //Cria o regirtro
            pokemon = new Pokemon (id_metadados, Integer.parseInt(atributos_csv[0]), 
                                atributos_csv[1], Integer.parseInt(atributos_csv[2]),
                                atributos_csv[5], Float.parseFloat(atributos_csv[8]), 
                                Float.parseFloat(atributos_csv[9]), atributos_csv[6], 
                                atributos_csv[7], Integer.parseInt(atributos_csv[10]), 
                                Integer.parseInt(atributos_csv[11]), Integer.parseInt(atributos_csv[12]), 
                                Integer.parseInt(atributos_csv[13]), Integer.parseInt(atributos_csv[14]),
                                Integer.parseInt(atributos_csv[15]), Boolean.parseBoolean(atributos_csv[3]), 
                                Boolean.parseBoolean(atributos_csv[4]));

            //Escreve o registro
            poke_info_byte = pokemon.toByteArray();
            arq_db.writeByte(' ');
            arq_db.writeInt(poke_info_byte.length);
            arq_db.write(poke_info_byte);

            //Atualiza o id
            id_metadados++;
        }
        
        //Atualiza metadados de arquivo
        arq_db.seek(0);
        arq_db.writeInt(id_metadados-1);

        //Exibe mensagem para o usuario
        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t" + "*** IMPORTANDO ARQUIVO .CSV PARA .DB ***" + "\n\n\n"
                            + "\t" + "Importacao concluida com sucesso !!!");

        scanner.close();
    }
}
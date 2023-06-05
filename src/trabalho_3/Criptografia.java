package trabalho_3;

import java.io.IOException;

import aplicacao.Tela;

/**
 * Classe com algoritmos de criptografia
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class Criptografia {
    private RSAcode rsa;
    private Cifra_de_bloco bloco;

    /**
     * Construtor da classe
     * Inicializa os algoritmos RSA e Cifra de Bloco
     * 
     * @throws Exception
     */
    public Criptografia () throws Exception {
        Tela.println (  "\n\t\t\t\t\t" + "*** CRIPTOGRAFIA ***" + "\n\n\n"
                            + "\t" + "Preparando ambiente para o algoritmo RSA ..." + "\n");
        rsa = new RSAcode();

        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t" + "*** CRIPTOGRAFIA ***" + "\n\n\n"
                            + "\t" + "Preparando ambiente para a Cifra de Bloco ..." + "\n");
        bloco = new Cifra_de_bloco();
    }
    
    /**
     * Cifra uma mensagem
     * 
     * @param mensagem a ser cifrada
     * @return mensagem cifrada
     * @throws IOException
     */
    public String cifrar (String mensagem) throws IOException {
        return bloco.cifrar(rsa.cifrar(mensagem));
    }

    /**
     * Decifra uma mensagem
     * 
     * @param mensagem_cifrada a ser decifrada
     * @param chave de criptografia
     * @return mensagem decifrada ou null, se a chave ser invalida
     * @throws IOException
     */
    public String decifrar (String mensagem_cifrada, String chave) throws IOException {
        return rsa.decifrar(bloco.decifrar(mensagem_cifrada), bloco.decifrar(chave));
    }

    /**
     * Retorna a chave de criptografia
     * 
     * @return chave de criptografia
     * @throws IOException
     */
    public String getKey () throws IOException {
        return bloco.cifrar(rsa.getKey());
    }
}

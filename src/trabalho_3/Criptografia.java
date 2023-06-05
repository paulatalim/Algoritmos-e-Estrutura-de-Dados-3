package trabalho_3;

import java.io.IOException;

import aplicacao.Tela;

public class Criptografia {
    private RSAcode rsa;
    private Cifra_de_bloco bloco;

    public Criptografia () throws Exception {
        Tela.println (  "\n\t\t\t\t\t" + "*** CRIPTOGRAFIA ***" + "\n\n\n"
                            + "\t" + "Preparando ambiente para o algoritmo RSA ..." + "\n");
        rsa = new RSAcode();

        Tela.limpar_console();
        Tela.println (  "\n\t\t\t\t\t" + "*** CRIPTOGRAFIA ***" + "\n\n\n"
                            + "\t" + "Preparando ambiente para a Cifra de Bloco ..." + "\n");
        bloco = new Cifra_de_bloco();
    }
    
    public String cifrar (String mensagem) throws IOException {
        return bloco.cifrar(rsa.cifrar(mensagem));
    }

    public String decifrar (String mensagem_cifrada, String chave) throws IOException {
        return rsa.decifrar(bloco.decifrar(mensagem_cifrada), bloco.decifrar(chave));
    }

    public String getKey () throws IOException {
        return bloco.cifrar(rsa.getKey());
    }
}

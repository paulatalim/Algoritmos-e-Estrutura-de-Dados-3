package trabalho_3;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Classe com algoritmo de criptografia RSA
 * 
 * @author Mariana Aram
 * @author Paula Talim
 * @author Yago Garzon
 */
public class RSAcode {
    private BigDecimal e;
    private BigDecimal n;
    private BigDecimal d;
    private BigDecimal p;
    private BigDecimal q;
    private BigDecimal z;
    private HashMap<Character, String> dicionario;
    private HashMap<String, Character> diclo;

    /**
     * Construtor da clase
     */
    RSAcode () {
        //Criacao de chaves
        criar_chaves();

        // Inicializa e preenche o dicionario
        dicionario = new HashMap<Character, String>();
        preencher_dicionario(); 
        
        diclo = new HashMap<String, Character>();
        preencher_dicionario_decifragem();
    }

    /**
     * Cifra um caracter
     * 
     * @param letra a ser cifrada
     * @return string da letra cifrada
     */
    private String cifrar_letra (char letra) {
        return new BigDecimal(letra).pow(e.intValue()).remainder(n).toString();
    }

    private char decifrar_letra (String letra) {
        return (char) new BigDecimal(letra).pow(d.intValue()).remainder(n).intValue();
    }

    /**
     * Preenche o dicionario com o espaco, o ifen e as letras maiusculas e 
     * minusculas do alfabeto cifradas
     */
    private void preencher_dicionario () {
        // Adiciona as letras maiusculas no dicionario
        for (char i = 'A'; i <= 'Z'; i++) {
            dicionario.put(i, cifrar_letra(i));
        }

        // Aidiciona as letras minusculas no dicionario
        for (char i = 'a'; i <= 'z'; i++) {
            dicionario.put(i, cifrar_letra(i));
        }

        // Adiciona espaco e ifen
        dicionario.put(' ', cifrar_letra(' '));
        dicionario.put('-', cifrar_letra('-'));
    }

    private void preencher_dicionario_decifragem () {
        for (char key : dicionario.keySet()) {
            String letra = dicionario.get(key);
            diclo.put(letra, decifrar_letra(letra));
        }
    }

    /**
     * Gera as chaves de criptografia
     */
    private void criar_chaves() {
        // Atribuicoes de numeros primos
        p = new BigDecimal(86927);
        q = new BigDecimal(74653);
        d = new BigDecimal(43103);

        //Calculo do valor de n -> n = p * q
        n = p.multiply(q);

        // Calculo do valor de z -> z = (p - 1) * (q - 1)
        z = p.add(BigDecimal.valueOf(-1)).multiply(q.add(BigDecimal.valueOf(-1)));

        //Calculo do numero e -> e = (z + 1) / d
        e = z.add(BigDecimal.valueOf(1)).divide(d);
    }

    /**
     * Monta a chave publica
     * 
     * @return chave de criptografia publica
     */
    private String montar_chave_publica () {
        return e.toString() + n.toString();
    }

    /**
     * Valida a chave publica 
     * @param key a ser verificada
     * @return true, se for valida, false, se for invalida
     */
    private boolean validar_key (String key) {
        if (montar_chave_publica().equals(key)) {
            return true;
        }
        return false;
    }

    /**
     * Retorno da chave publica
     * 
     * @return chave de criptografia publica
     */
    public String getKey () {
        return montar_chave_publica();
    }

    /**
     * Cifra uma mensagem
     * 
     * @param mensagem a ser cifrada
     * @return mensagem cifrada
     */
    public String cifrar (String mensagem) {
        String mensagem_cifrada = "";
        String letra_cifrada;

        // Cifra cada letra da mensagem
        for (int i = 0; i < mensagem.length(); i++) {
            // Busca a letra no dicionario
            letra_cifrada = dicionario.get(mensagem.charAt(i));

            // Caso a letra nao existir no dicionario
            if (letra_cifrada == null) {
                //Cifra a letra
                letra_cifrada = cifrar_letra(mensagem.charAt(i));
            }
            
            // Adicao de letra cifrada
            mensagem_cifrada += letra_cifrada.length() + letra_cifrada;
        }

        return mensagem_cifrada;
    }

    /**
     * Decifra uma mensagem
     * 
     * @param mensagem a ser decifrada
     * @param chave de criptografia
     * @return mensagem decifrada ou null, se a chave ser invalida
     */
    public String decifrar (String mensagem, String chave) {
        //Caso a chave ser invalida
        if (!validar_key(chave)) {
            return null;
        }

        String mensagem_decifrada = "";
        int tamanho = 0;

        //Decifra a mensagem
        for (int i = 0; i < mensagem.length(); i += tamanho) {
            //Calcula o tamanho do caracter cifrado
            tamanho = Character.getNumericValue(mensagem.charAt(i++));

            if (tamanho < 8) {
                tamanho *= 10;
                tamanho +=  Character.getNumericValue(mensagem.charAt(i++));
            }

            char letra_decifrada;

            if (diclo.containsKey(mensagem.substring(i, i + tamanho))) {
                letra_decifrada = diclo.get(mensagem.substring(i, i + tamanho));
            } else {
                letra_decifrada = decifrar_letra(mensagem.substring(i, i + tamanho));
            }
            
            //Decifra o proximo caracter
            mensagem_decifrada += letra_decifrada;
        }
       
        return mensagem_decifrada;
    }
}
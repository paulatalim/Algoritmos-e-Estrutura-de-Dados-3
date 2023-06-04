package trabalho_3;

import java.math.BigDecimal;
import java.util.HashMap;

public class RSAcode {
    private BigDecimal e;
    private BigDecimal n;
    private BigDecimal d;
    private BigDecimal p;
    private BigDecimal q;
    private BigDecimal z;
    private HashMap<Character, String> dicionario;

    RSAcode () {
        criar_chaves();
        dicionario = new HashMap<Character, String>();

        for (char i = 'A'; i <= 'Z'; i++) {
            dicionario.put(i, cifrar_letra(i));
        }

        for (char i = 'a'; i <= 'z'; i++) {
            dicionario.put(i, cifrar_letra(i));
        }

        dicionario.put(' ', cifrar_letra(' '));
        dicionario.put('-', cifrar_letra('-'));
    }

    
    public void criar_chaves() {
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

    private String montar_chave_publica () {
        return e.toString() + n.toString();
    }

    public String getKey () {
        return montar_chave_publica();
    }

    private boolean validar_key (String key) {
        if (montar_chave_publica().equals(key)) {
            return true;
        }
        return false;
    }

    public String cifrar_letra (char letra) {
        return new BigDecimal(letra).pow(e.intValue()).remainder(n).toString();
    }

    public String cifrar (String mensagem) {
        String mensagem_cifrada = "";
        String letra_cifrada;

        //Cifra cada letra da mensagem
        for (int i = 0; i < mensagem.length(); i++) {

            letra_cifrada = dicionario.get(mensagem.charAt(i));

            if (letra_cifrada == null) {
                letra_cifrada = cifrar_letra(mensagem.charAt(i));
            }
            
            mensagem_cifrada += letra_cifrada.length() + letra_cifrada;
        }

        return mensagem_cifrada;

    }

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

           // mensagem_decifrada += 

            //Decifra o proximo caracter
            mensagem_decifrada += (char) new BigDecimal(mensagem.substring(i, i + tamanho)).pow(d.intValue()).remainder(n).intValue();
        }
       
        return mensagem_decifrada;
    }
}

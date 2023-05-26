package trabalho_3;

import java.math.BigDecimal;

public class Criptografia {
    private BigDecimal e;
    private BigDecimal n;
    private BigDecimal d;
    private BigDecimal p;
    private BigDecimal q;
    private BigDecimal z;

    Criptografia () {
        criar_chaves();
    }

    public void criar_chaves() {

        // 27739
        /*
         * 96419
         * 86927
         * 74653
        //  */
        // p = 86927;
        // q = 74653;

        p = new BigDecimal(15649);
        q = new BigDecimal(20479);
        d = new BigDecimal(335539);

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

    public String cifrar (String mensagem) {
        String mensagem_cifrada = "";
        String letra_cifrada;

        //Cifra cada letra da mensagem
        for (int i = 0; i < mensagem.length(); i++) {
            letra_cifrada = new BigDecimal(mensagem.charAt(i)).pow(e.intValue()).remainder(n).toString();

            //resultado = (letra ^ e) % n
            mensagem_cifrada += letra_cifrada.length() + letra_cifrada;
        }

        return mensagem_cifrada;

    }

    // public String decifrar (long mensagem, String chave) {
    //     // String resultado = "";

    //     // if (!validar_key(chave)) {
    //     //     System.out.println("Chave invalida");

    //     // } else {
    //     //     String msg_convertida = Long.toString(mensagem);

    //     //     for (int i = 0; i < msg_convertida.length(); i++) {
    //     //         resultado += Math.pow((double) msg_convertida.charAt(i), d) % n;
    //     //     }
    //     // }
    //     // return resultado;
    // }
}

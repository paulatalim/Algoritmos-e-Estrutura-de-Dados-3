import java.math.BigDecimal;

public class App {
    public static void main(String[] args) throws Exception {
        String msg = "Paula";

        BigDecimal e, n, d ,p, q, z;

        p = new BigDecimal(86927);
        q = new BigDecimal(74653);
        d = new BigDecimal(43103);

        //Calculo do valor de n -> n = p * q
        n = p.multiply(q);

        // Calculo do valor de z -> z = (p - 1) * (q - 1)
        z = p.add(BigDecimal.valueOf(-1)).multiply(q.add(BigDecimal.valueOf(-1)));

        //Calculo do numero e -> e = (z + 1) / d
        e = z.add(BigDecimal.valueOf(1)).divide(d);

        String resultado = "";
        String result;
        BigDecimal cal;

        //Cifra cada letra da mensagem
        for (int i = 0; i < msg.length(); i++) {
            cal = new BigDecimal(msg.charAt(i)).pow(e.intValue()).remainder(n);
            result = cal.toString();

            //resultado = (letra ^ e) % n
            resultado += result.length() + result;

            System.out.println(result.length());
        }

        System.out.println(resultado);

        /* Decifragem */

        String msg1 = "";
        int tamanho = 0;

        for (int i = 0; i < resultado.length(); i += tamanho) {
            tamanho = Character.getNumericValue(resultado.charAt(i++));

            if (tamanho < 8) {
                tamanho *= 10;
                tamanho +=  Character.getNumericValue(resultado.charAt(i++));
            }

            // String letra = resultado.substring(i, i + tamanho);

            // BigDecimal l = new BigDecimal(letra);

            BigDecimal le = new BigDecimal(resultado.substring(i, i + tamanho)).pow(d.intValue()).remainder(n);

            msg1 += (char) new BigDecimal(resultado.substring(i, i + tamanho)).pow(d.intValue()).remainder(n).intValue();
        }
       

        System.out.println(msg1);

        // for (int i = 0; i < resultado.length(); i++)
    }
}
